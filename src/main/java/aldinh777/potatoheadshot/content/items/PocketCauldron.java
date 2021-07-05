package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.common.capability.CapabilityMana;
import aldinh777.potatoheadshot.common.capability.IManaStorage;
import aldinh777.potatoheadshot.common.capability.PocketCapability;
import aldinh777.potatoheadshot.common.capability.PotatoManaStorage;
import aldinh777.potatoheadshot.common.compat.botania.BotaniaCompat;
import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.common.lists.PotatoTab;
import aldinh777.potatoheadshot.common.recipes.category.IManaRecipes;
import aldinh777.potatoheadshot.common.util.Constants;
import aldinh777.potatoheadshot.content.inventory.InventoryPocketCauldron;
import aldinh777.potatoheadshot.content.tileentities.TileEntityManaCauldron;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class PocketCauldron extends Item {

    public static final int maxManaSize = ConfigHandler.POCKET_CAULDRON_CAPACITY;

    public PocketCauldron(String name) {
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setMaxStackSize(1);
        this.setCreativeTab(PotatoTab.POTATO_TAB);
        PotatoItems.LISTS.add(this);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        IManaStorage manaStorage = stack.getCapability(CapabilityMana.MANA, EnumFacing.UP);
        if (manaStorage instanceof PotatoManaStorage) {
            int mana = manaStorage.getManaStored();
            int maxMana = manaStorage.getMaxManaStored();

            tooltip.add("Mana : " + mana + "/" + maxMana);
        }
    }

    @Override
    public boolean showDurabilityBar(@Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(@Nonnull ItemStack stack) {
        IManaStorage manaStorage = stack.getCapability(CapabilityMana.MANA, EnumFacing.UP);
        if (manaStorage instanceof PotatoManaStorage) {
            return 1.0 - (double) manaStorage.getManaStored() / (double) manaStorage.getMaxManaStored();
        }
        return 0;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote) {
            RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);

            if (raytraceresult != null) {
                if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                    BlockPos blockPos = raytraceresult.getBlockPos();
                    TileEntity te = worldIn.getTileEntity(blockPos);

                    if (BotaniaCompat.isBotaniaAvailable()) {
                        if (BotaniaCompat.isInstanceOfManaPool(te)) {
                            IManaStorage manaStorage = stack.getCapability(CapabilityMana.MANA, EnumFacing.UP);
                            if (manaStorage instanceof PotatoManaStorage) {
                                int manaStored = BotaniaCompat.getManaSize(te);

                                int pocketMana = manaStorage.getManaStored();

                                int transferRate = maxManaSize - pocketMana;
                                if (transferRate > manaStored) {
                                    transferRate = manaStored;
                                }

                                int result = pocketMana + transferRate;
                                manaStorage.setMana(result);
                                BotaniaCompat.absorbMana(te, new PotatoManaStorage(maxManaSize), transferRate);

                                return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
                            }
                        }
                    }

                    if (te instanceof TileEntityManaCauldron) {
                        TileEntityManaCauldron manaCauldron = (TileEntityManaCauldron) te;
                        PotatoManaStorage cauldronStorage = manaCauldron.getManaStorage();
                        IManaStorage manaStorage = stack.getCapability(CapabilityMana.MANA, EnumFacing.UP);
                        if (manaStorage instanceof PotatoManaStorage) {
                            int pocketMana = manaStorage.getManaStored();

                            int transferRate = maxManaSize - pocketMana;
                            if (transferRate > cauldronStorage.getManaStored()) {
                                transferRate = cauldronStorage.getManaStored();
                            }

                            int result = pocketMana + transferRate;
                            manaStorage.setMana(result);
                            cauldronStorage.useMana(transferRate);

                            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
                        }
                    }
                }
            }

            BlockPos pos = playerIn.getPosition();
            int posX = pos.getX();
            int posY = pos.getY();
            int posZ = pos.getZ();

            playerIn.openGui(PotatoHeadshot.INSTANCE, Constants.POCKET_CAULDRON, worldIn, posX, posY, posZ);
            return ActionResult.newResult(EnumActionResult.PASS, stack);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void onUpdate(@Nonnull ItemStack stack, World worldIn, @Nonnull Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isRemote) {
            ItemStackHandler inventory = (ItemStackHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

            if (inventory == null) {
                throw new NullPointerException("Item Stack don't have Item capabilities");
            }

            ItemStack rodSlot = inventory.getStackInSlot(InventoryPocketCauldron.ESSENCE_SLOT);
            ItemStack inputSlot = inventory.getStackInSlot(InventoryPocketCauldron.INPUT_SLOT);
            ItemStack outputSlot = inventory.getStackInSlot(InventoryPocketCauldron.OUTPUT_SLOT);

            IManaStorage manaStorage = stack.getCapability(CapabilityMana.MANA, EnumFacing.UP);
            if (manaStorage instanceof PotatoManaStorage) {

                int manaSize = manaStorage.getManaStored();

                if (rodSlot.isEmpty() || inputSlot.isEmpty()) {
                    return;
                }

                IManaRecipes recipes = IManaRecipes.getRecipeById(0);

                if (rodSlot.getItem().equals(PotatoItems.ESSENCE_MANA)) {
                    recipes = IManaRecipes.getRecipeById(0);
                } else if (rodSlot.getItem().equals(PotatoItems.ESSENCE_LIFE)) {
                    recipes = IManaRecipes.getRecipeById(1);
                } else if (rodSlot.getItem().equals(PotatoItems.ESSENCE_NATURE)) {
                    recipes = IManaRecipes.getRecipeById(2);
                } else if (rodSlot.getItem().equals(PotatoItems.ESSENCE_FIRE)) {
                    recipes = IManaRecipes.getRecipeById(3);
                }

                ItemStack result = recipes.getResult(inputSlot);
                int cost = recipes.getCost(inputSlot);

                if (result.isEmpty() || cost > manaSize) {
                    return;
                }

                if (result.isItemEqual(outputSlot)) {
                    if (outputSlot.getCount() + result.getCount() <= outputSlot.getMaxStackSize()) {
                        outputSlot.grow(result.getCount());

                        inputSlot.shrink(1);
                        manaStorage.setMana(manaSize - cost);
                    }

                } else {
                    inventory.setStackInSlot(InventoryPocketCauldron.OUTPUT_SLOT, result.copy());

                    inputSlot.shrink(1);
                    manaStorage.setMana(manaSize - cost);
                }
            }
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new PocketCapability(stack);
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(@Nonnull ItemStack stack) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            IItemHandler inventory = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
            IManaStorage manaStorage = stack.getCapability(CapabilityMana.MANA, EnumFacing.UP);
            NBTTagCompound compound = stack.getTagCompound();
            NBTTagCompound manaCompound = new NBTTagCompound();

            if (compound == null) {
                compound = new NBTTagCompound();
            }

            if (inventory instanceof ItemStackHandler) {
                compound.setTag("Inventory", ((ItemStackHandler) inventory).serializeNBT());
            }

            if (manaStorage instanceof PotatoManaStorage) {
                ((PotatoManaStorage) manaStorage).writeToNBT(manaCompound);
                compound.setTag("Mana", manaCompound);
            }

            return compound;
        }

        return stack.getTagCompound();
    }

    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound compound) {
        stack.setTagCompound(compound);
        if (compound != null) {
            if (compound.hasKey("Inventory")) {
                IItemHandler inventory = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

                if (inventory instanceof ItemStackHandler) {
                    ((ItemStackHandler) inventory).deserializeNBT(compound.getCompoundTag("Inventory"));
                }
            }

            if (compound.hasKey("Mana")) {
                IManaStorage manaStorage = stack.getCapability(CapabilityMana.MANA, EnumFacing.UP);

                if (manaStorage instanceof PotatoManaStorage) {
                    ((PotatoManaStorage) manaStorage).readFromNBT(compound.getCompoundTag("Mana"));
                }
            }
        }
    }

    public static ItemStack findPocketCauldron(EntityPlayer player) {
        ItemStack mainHand = player.getHeldItemMainhand();
        if (mainHand.getItem() instanceof PocketCauldron) {
            return mainHand;
        }

        ItemStack offHand = player.getHeldItemOffhand();
        if (offHand.getItem() instanceof PocketCauldron) {
            return offHand;
        }

        throw new NullPointerException("No Pocket Cauldron Found in Hand");
    }
}
