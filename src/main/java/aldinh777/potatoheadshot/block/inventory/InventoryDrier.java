package aldinh777.potatoheadshot.block.inventory;

import aldinh777.potatoheadshot.util.FuelHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class InventoryDrier extends ItemStackHandler {

    public final int FUEL_SLOT = 0;
    public final int DRIER_INPUT_SLOT = 1;
    public final int DRIER_OUTPUT_SLOT = 2;
    public final int WATER_INPUT_SLOT = 3;
    public final int WATER_OUTPUT_SLOT = 4;

    public InventoryDrier() {
        super(5);
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

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        switch (slot) {
            case DRIER_OUTPUT_SLOT:
            case WATER_OUTPUT_SLOT:
                return super.extractItem(slot, amount, simulate);
            default:
                return ItemStack.EMPTY;
        }
    }
}
