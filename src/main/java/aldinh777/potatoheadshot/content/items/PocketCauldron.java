package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.content.inventory.InventoryPocketCauldron;
import aldinh777.potatoheadshot.content.tileentities.TileEntityManaCauldron;
import aldinh777.potatoheadshot.common.capability.CapabilityMana;
import aldinh777.potatoheadshot.common.capability.PotatoManaStorage;
import aldinh777.potatoheadshot.common.compat.botania.BotaniaCompat;
import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.common.lists.PotatoTab;
import aldinh777.potatoheadshot.common.recipes.category.IManaRecipes;
import aldinh777.potatoheadshot.common.util.Constants;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

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

        int mana = getManaSize(stack);
        int maxMana = getMaxManaSize(stack);

        tooltip.add("Mana : " + mana + "/" + maxMana);
    }

    @Override
    public boolean showDurabilityBar(@Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(@Nonnull ItemStack stack) {
        return 1.0 - (double) getManaSize(stack) / (double) getMaxManaSize(stack);
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
                            int manaStored = BotaniaCompat.getManaSize(te);

                            int pocketMana = getManaSize(stack);

                            int transferRate = maxManaSize - pocketMana;
                            if (transferRate > manaStored) {
                                transferRate = manaStored;
                            }

                            int result = pocketMana + transferRate;
                            setManaSize(stack, result);
                            BotaniaCompat.absorbMana(te, new PotatoManaStorage(maxManaSize), transferRate);

                            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
                        }
                    }

                    if (te instanceof TileEntityManaCauldron) {
                        TileEntityManaCauldron manaCauldron = (TileEntityManaCauldron) te;
                        PotatoManaStorage cauldronStorage = manaCauldron.getManaStorage();
                        int pocketMana = getManaSize(stack);

                        int transferRate = maxManaSize - pocketMana;
                        if (transferRate > cauldronStorage.getManaStored()) {
                            transferRate = cauldronStorage.getManaStored();
                        }

                        int result = pocketMana + transferRate;
                        setManaSize(stack, result);
                        cauldronStorage.useMana(transferRate);

                        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
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
            int manaSize = getManaSize(stack);

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
                    setManaSize(stack, manaSize - cost);
                }

            } else {
                inventory.setStackInSlot(InventoryPocketCauldron.OUTPUT_SLOT, result.copy());

                inputSlot.shrink(1);
                setManaSize(stack, manaSize - cost);
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
            NBTTagCompound compound = stack.getTagCompound();

            if (compound == null) {
                compound = new NBTTagCompound();
            }

            if (inventory instanceof ItemStackHandler) {
                compound.setTag("InventoryInput", ((ItemStackHandler) inventory).serializeNBT());
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

    public static int getManaSize(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();

        if (compound == null) {
            compound = new NBTTagCompound();
            stack.setTagCompound(compound);
        }

        if (!compound.hasKey("Mana")) {
            compound.setInteger("Mana", 0);
        }

        return compound.getInteger("Mana");
    }

    public static void setManaSize(ItemStack stack, int value) {
        NBTTagCompound compound = stack.getTagCompound();

        if (compound == null) {
            compound = new NBTTagCompound();
            stack.setTagCompound(compound);
        }

        compound.setInteger("Mana", value);
    }

    public static int getMaxManaSize(ItemStack stack) {
        if (stack.getItem() == PotatoItems.POCKET_CAULDRON) {
            return maxManaSize;
        }
        return 0;
    }

    static class PocketCapability implements ICapabilitySerializable<NBTBase> {

        public final ItemStack stack;
        public InventoryPocketCauldron inventory = new InventoryPocketCauldron();

        public PocketCapability(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityMana.MANA
                    || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                return (T) this.inventory;
            }
            return null;
        }

        @Override
        public NBTBase serializeNBT() {
            NBTTagCompound stackNBT = this.stack.getTagCompound();
            if (stackNBT == null) {
                stackNBT = new NBTTagCompound();
            }
            stackNBT.setTag("Inventory", inventory.serializeNBT());
            stack.setTagCompound(stackNBT);

            NBTTagCompound nbt = new NBTTagCompound();
            NBTBase inventoryValue = Objects.requireNonNull(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory, EnumFacing.UP));

            nbt.setTag("Inventory", inventoryValue);

            return nbt;
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, EnumFacing.UP, ((NBTTagCompound) nbt).getTag("Inventory"));
        }
    }
}
