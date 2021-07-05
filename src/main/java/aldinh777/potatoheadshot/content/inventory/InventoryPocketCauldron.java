package aldinh777.potatoheadshot.content.inventory;

import aldinh777.potatoheadshot.common.lists.PotatoItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class InventoryPocketCauldron extends ItemStackHandler {

    public static final int ESSENCE_SLOT = 0;
    public static final int INPUT_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;

    public InventoryPocketCauldron() {
        super(3);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot == ESSENCE_SLOT) {
            if (super.isItemValid(slot, stack)) {
                return stack.getItem() == PotatoItems.ESSENCE_MANA ||
                        stack.getItem() == PotatoItems.ESSENCE_FIRE ||
                        stack.getItem() == PotatoItems.ESSENCE_LIFE ||
                        stack.getItem() == PotatoItems.ESSENCE_NATURE;
            }
        }
        return super.isItemValid(slot, stack);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        switch (slot) {
            case ESSENCE_SLOT:
                return isItemValid(slot, stack) ? super.insertItem(slot, stack, simulate) : stack;
            case INPUT_SLOT:
                return super.insertItem(slot, stack, simulate);
            default:
                return stack;
        }
    }
}
