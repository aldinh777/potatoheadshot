package aldinh777.potatoheadshot.block.inventory;

import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.util.FuelHelper;
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

    public ItemStackHandler getFuelHandler(boolean disabled) {
        return new FuelHandler(this, disabled);
    }

    public ItemStackHandler getInputHandler(boolean includeWater) {
        return new InputHandler(this, includeWater);
    }

    public ItemStackHandler getOutputHandler(boolean includeWater) {
        return new OutputHandler(this, includeWater);
    }

    static class FuelHandler extends ItemStackHandler {
        private final boolean disabled;

        FuelHandler(InventoryDrier drier, boolean disabled) {
            super(drier.stacks);
            this.disabled = disabled;
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (slot == FUEL_SLOT) {
                return !disabled && super.isItemValid(slot, stack) && FuelHelper.isItemFuel(stack);
            }
            return super.isItemValid(slot, stack);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (slot == FUEL_SLOT && !disabled) {
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
        private final boolean includeWater;

        InputHandler(InventoryDrier drier, boolean includeWater) {
            super(drier.stacks);
            this.includeWater = includeWater;
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (slot == DRIER_INPUT_SLOT || (slot == WATER_INPUT_SLOT && includeWater)) {
                return super.insertItem(slot, stack, simulate);
            }
            return stack;
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot == DRIER_INPUT_SLOT || (slot == WATER_INPUT_SLOT && includeWater)) {
                return super.extractItem(slot, amount, simulate);
            }
            return ItemStack.EMPTY;
        }
    }

    static class OutputHandler extends ItemStackHandler {
        private final boolean includeWater;

        OutputHandler(InventoryDrier drier, boolean includeWater) {
            super(drier.stacks);
            this.includeWater = includeWater;
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
            } else if (slot == DRIER_OUTPUT_SLOT || (slot == WATER_OUTPUT_SLOT && includeWater)) {
                return super.extractItem(slot, amount, simulate);
            }
            return ItemStack.EMPTY;
        }
    }
}
