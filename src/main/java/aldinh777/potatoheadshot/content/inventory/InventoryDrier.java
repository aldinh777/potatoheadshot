package aldinh777.potatoheadshot.content.inventory;

import aldinh777.potatoheadshot.other.lists.PotatoItems;
import aldinh777.potatoheadshot.other.util.FuelHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class InventoryDrier extends ItemStackHandler {

    public static final int FUEL_SLOT = 0;
    public static final int DRIER_INPUT_SLOT = 1;
    public static final int DRIER_OUTPUT_SLOT = 2;
    public static final int WATER_INPUT_SLOT = 3;
    public static final int WATER_OUTPUT_SLOT = 4;

    public InventoryDrier() {
        super(5);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot == FUEL_SLOT) {
            return super.isItemValid(slot, stack) && FuelHelper.isItemFuel(stack);
        }
        return super.isItemValid(slot, stack);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        switch (slot) {
            case FUEL_SLOT:
                return FuelHelper.isItemFuel(stack) ? super.insertItem(slot, stack, simulate) : stack;
            case DRIER_INPUT_SLOT:
            case WATER_INPUT_SLOT:
                return super.insertItem(slot, stack, simulate);
            default:
                return stack;
        }
    }

    public ItemStackHandler getFuelHandler() {
        return new FuelHandler(this);
    }

    public ItemStackHandler getInputHandler() {
        return new InputHandler(this);
    }

    public ItemStackHandler getOutputHandler() {
        return new OutputHandler(this);
    }


    static class FuelHandler extends ItemStackHandler {
        FuelHandler(InventoryDrier drier) {
            super(drier.stacks);
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (slot == FUEL_SLOT) {
                return super.isItemValid(slot, stack) && FuelHelper.isItemFuel(stack);
            }
            return super.isItemValid(slot, stack);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (slot == FUEL_SLOT) {
                return FuelHelper.isItemFuel(stack) ? super.insertItem(slot, stack, simulate) : stack;
            }
            return stack;
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot == FUEL_SLOT) {
                return super.extractItem(slot, amount, simulate);
            }
            return ItemStack.EMPTY;
        }
    }

    static class InputHandler extends ItemStackHandler {
        InputHandler(InventoryDrier drier) {
            super(drier.stacks);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (slot == DRIER_INPUT_SLOT || slot == WATER_INPUT_SLOT) {
                return super.insertItem(slot, stack, simulate);
            }
            return stack;
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot == DRIER_INPUT_SLOT || slot == WATER_INPUT_SLOT) {
                return super.extractItem(slot, amount, simulate);
            }
            return ItemStack.EMPTY;
        }
    }

    static class OutputHandler extends ItemStackHandler {
        OutputHandler(InventoryDrier drier) {
            super(drier.stacks);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return stack;
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot == FUEL_SLOT || slot == DRIER_INPUT_SLOT) {
                ItemStack input = getStackInSlot(slot);
                if (input.getItem() == PotatoItems.SWEET_EMPTY_BUCKET || input.getItem() == Items.BUCKET) {
                    return super.extractItem(slot, amount, simulate);
                } else {
                    return ItemStack.EMPTY;
                }
            } else if (slot == DRIER_OUTPUT_SLOT || slot == WATER_OUTPUT_SLOT) {
                return super.extractItem(slot, amount, simulate);
            }
            return ItemStack.EMPTY;
        }
    }
}
