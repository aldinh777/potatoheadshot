package aldinh777.potatoheadshot.item;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.block.recipes.IManaRecipes;
import aldinh777.potatoheadshot.block.tileentities.TileEntityManaCauldron;
import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import aldinh777.potatoheadshot.util.Constants;
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
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class PocketCauldron extends Item {

    public static final int maxManaSize = 800000;

    public PocketCauldron(String name) {
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setMaxStackSize(1);
        this.setCreativeTab(PotatoTab.POTATO_TAB);
        PotatoItems.LISTS.add(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote) {
            RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);

            if (raytraceresult != null) {
                if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                    BlockPos blockPos = raytraceresult.getBlockPos();
                    TileEntity te = worldIn.getTileEntity(blockPos);

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

            int mana = getManaSize(stack);
            System.out.println("NBT Mana : " + mana);

            playerIn.openGui(PotatoHeadshot.INSTANCE, Constants.POCKET_CAULDRON, worldIn, posX, posY, posZ);
            return ActionResult.newResult(EnumActionResult.PASS, stack);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isRemote) {
            ItemStackHandler inputHandler = (ItemStackHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
            ItemStackHandler outputHandler = (ItemStackHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

            if (inputHandler == null || outputHandler == null) {
                throw new NullPointerException("Item Stack don't have Item capabilities");
            }

            ItemStack rodSlot = inputHandler.getStackInSlot(0);
            ItemStack inputSlot = inputHandler.getStackInSlot(1);
            ItemStack outputSlot = outputHandler.getStackInSlot(0);
            int manaSize = getManaSize(stack);

            if (rodSlot.isEmpty() || inputSlot.isEmpty()) {
                return;
            }

            if (rodSlot.getItem() == PotatoItems.ROD_VOID) {
                inputSlot.shrink(inputSlot.getCount());
                return;
            }

            IManaRecipes recipes = IManaRecipes.getRecipeById(0);

            if (rodSlot.getItem() == PotatoItems.ROD_MANA) {
                recipes = IManaRecipes.getRecipeById(0);
            } else if (rodSlot.getItem() == PotatoItems.ROD_LIFE) {
                recipes = IManaRecipes.getRecipeById(1);
            } else if (rodSlot.getItem() == PotatoItems.ROD_NATURE) {
                recipes = IManaRecipes.getRecipeById(2);
            } else if (rodSlot.getItem() == PotatoItems.ROD_FIRE) {
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
                outputHandler.setStackInSlot(0, result.copy());

                inputSlot.shrink(1);
                setManaSize(stack, manaSize - cost);
            }
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new PocketCapability(stack);
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            IItemHandler inputHandler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
            IItemHandler outputHandler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
            NBTTagCompound compound = stack.getTagCompound();

            if (compound == null) {
                compound = new NBTTagCompound();
            }

            if (inputHandler instanceof ItemStackHandler) {
                compound.setTag("InventoryInput", ((ItemStackHandler) inputHandler).serializeNBT());
            }

            if (outputHandler instanceof ItemStackHandler) {
                compound.setTag("InventoryOutput", ((ItemStackHandler) outputHandler).serializeNBT());
            }

            return compound;
        }

        return stack.getTagCompound();
    }

    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound compound) {
        stack.setTagCompound(compound);
        if (compound != null) {
            if (compound.hasKey("InventoryInput")) {
                IItemHandler inputHandler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

                if (inputHandler instanceof ItemStackHandler) {
                    ((ItemStackHandler) inputHandler).deserializeNBT(compound.getCompoundTag("InventoryInput"));
                }
            }

            if (compound.hasKey("InventoryOutput")) {
                IItemHandler outputHandler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

                if (outputHandler instanceof ItemStackHandler) {
                    ((ItemStackHandler) outputHandler).deserializeNBT(compound.getCompoundTag("InventoryOutput"));
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

    public static int getMaxManaSize() {
        return maxManaSize;
    }

    static class PocketCapability implements ICapabilitySerializable<NBTBase> {

        public final ItemStack stack;
        public IItemHandler inputHandler = new ItemStackHandler(2);
        public IItemHandler outputHandler = new ItemStackHandler(1);

        public PocketCapability(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            if (capability == CapabilityEnergy.ENERGY) {
                return true;
            } else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                return facing == EnumFacing.UP || facing == EnumFacing.DOWN;
            }
            return false;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                if (facing == EnumFacing.UP) {
                    return (T) this.inputHandler;
                } else if (facing == EnumFacing.DOWN) {
                    return (T) this.outputHandler;
                }
            }
            return null;
        }

        @Override
        public NBTBase serializeNBT() {
            NBTTagCompound stackNBT = this.stack.getTagCompound();
            if (stackNBT == null) {
                stackNBT = new NBTTagCompound();
            }
            stackNBT.setTag("InventoryInput", ((ItemStackHandler) this.inputHandler).serializeNBT());
            stackNBT.setTag("InventoryOutput", ((ItemStackHandler) this.outputHandler).serializeNBT());
            stack.setTagCompound(stackNBT);

            NBTTagCompound nbt = new NBTTagCompound();
            NBTBase inputValue = Objects.requireNonNull(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(this.inputHandler, EnumFacing.UP));
            NBTBase outputValue = Objects.requireNonNull(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(this.outputHandler, EnumFacing.DOWN));

            nbt.setTag("InputInventory", inputValue);
            nbt.setTag("OutputInventory", outputValue);

            return nbt;
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inputHandler, EnumFacing.UP, ((NBTTagCompound) nbt).getTag("InputInventory"));
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(outputHandler, EnumFacing.DOWN, ((NBTTagCompound) nbt).getTag("OutputInventory"));
        }
    }
}
