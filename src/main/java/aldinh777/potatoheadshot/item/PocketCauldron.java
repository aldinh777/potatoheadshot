package aldinh777.potatoheadshot.item;

import aldinh777.potatoheadshot.PotatoHeadshot;
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
                        PotatoManaStorage pocketStorage = getEnergy(stack);

                        int transferRate = pocketStorage.getMaxManaStored() - pocketStorage.getManaStored();
                        if (transferRate > cauldronStorage.getManaStored()) {
                            transferRate = cauldronStorage.getManaStored();
                        }

                        pocketStorage.collectMana(transferRate);
                        cauldronStorage.useMana(transferRate);

                        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
                    }
                }
            }

            BlockPos pos = playerIn.getPosition();
            int posX = pos.getX();
            int posY = pos.getY();
            int posZ = pos.getZ();

            System.out.println("Current Storage : " + getEnergy(stack).getManaStored());

            playerIn.openGui(PotatoHeadshot.INSTANCE, Constants.POCKET_CAULDRON, worldIn, posX, posY, posZ);
            return ActionResult.newResult(EnumActionResult.PASS, stack);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
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
            PotatoManaStorage storage = getEnergy(stack);
            NBTTagCompound compound = stack.getTagCompound();

            if (compound == null) {
                compound = new NBTTagCompound();
            }

            storage.writeToNBT(compound);

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

            PotatoManaStorage storage = getEnergy(stack);
            storage.readFromNBT(compound);
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

    public static PotatoManaStorage getEnergy(ItemStack stack) {
        return (PotatoManaStorage) stack.getCapability(CapabilityEnergy.ENERGY, null);
    }

    static class PocketCapability implements ICapabilitySerializable<NBTBase> {

        public final ItemStack stack;
        public IItemHandler inputHandler = new ItemStackHandler(2);
        public IItemHandler outputHandler = new ItemStackHandler(1);
        public PotatoManaStorage storage = new PotatoManaStorage(800000);

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
            if (capability == CapabilityEnergy.ENERGY) {
                return (T) this.storage;
            } else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
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