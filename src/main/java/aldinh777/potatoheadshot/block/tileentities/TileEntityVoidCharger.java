package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.item.items.VoidBottle;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityVoidCharger extends TileEntityPotatoMachine {

    private final ItemStackHandler inputHandler = new ItemStackHandler(1);
    private final ItemStackHandler outputHandler = new ItemStackHandler(1) {
        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return stack.getItem() == PotatoItems.VOID_BOTTLE ? super.insertItem(slot, stack, simulate) : stack;
        }
    };

    // Override Method

    @Override
    public void update() {
        if (!this.world.isRemote) {
            ItemStack input = inputHandler.getStackInSlot(0);
            ItemStack bottle = outputHandler.getStackInSlot(0);

            if (!input.isEmpty() && bottle.getItem() == PotatoItems.VOID_BOTTLE) {
                int voidSize = VoidBottle.getVoidSize(bottle);
                if (voidSize < VoidBottle.maxVoid) {
                    int count = input.getCount();
                    int voidValue = getValue(input);
                    int totalVoidValue = count * voidValue;

                    input.shrink(count);
                    VoidBottle.setVoidSize(bottle, voidSize + totalVoidValue);
                }
            }

            this.markDirty();
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.DOWN) {
                return (T)this.outputHandler;
            } else {
                return (T)this.inputHandler;
            }
        }

        return super.getCapability(capability, facing);
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return customOrDefaultName("Void Charger");
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inputHandler.deserializeNBT(compound.getCompoundTag("InventoryInput"));
        this.outputHandler.deserializeNBT(compound.getCompoundTag("InventoryBottle"));
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("InventoryInput", this.inputHandler.serializeNBT());
        compound.setTag("InventoryBottle", this.outputHandler.serializeNBT());
        return compound;
    }

    @Override
    public int getField(String id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    // Static Methods

    public int getValue(ItemStack stack) {
        Item voidBlock = Item.getItemFromBlock(PotatoBlocks.VOID_BLOCK);

        if (stack.getItem() == PotatoItems.VOID_QUANTUM) return 1;
        if (stack.getItem() == PotatoItems.VOID_MATTER) return 9;
        if (stack.getItem() == PotatoItems.VOID_PILE) return (int) Math.pow(9, 2);
        if (stack.getItem() == PotatoItems.VOID_NUGGET) return (int) Math.pow(9, 3);
        if (stack.getItem() == PotatoItems.VOID_INGOT) return (int) Math.pow(9, 4);
        if (stack.getItem() == voidBlock) return (int) Math.pow(9, 5);

        return 1;
    }
}
