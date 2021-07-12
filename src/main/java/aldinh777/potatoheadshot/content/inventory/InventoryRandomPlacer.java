package aldinh777.potatoheadshot.content.inventory;

import aldinh777.potatoheadshot.content.items.PotatoFoodItemBlock;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class InventoryRandomPlacer extends ItemStackHandler {

    public InventoryRandomPlacer() {
        super(9);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (stack.getItem() instanceof ItemBlock || stack.getItem() instanceof PotatoFoodItemBlock) {
            return !stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        }
        return false;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (isItemValid(slot, stack)) {
            return super.insertItem(slot, stack, simulate);
        }
        return stack;
    }

    public int getRandomAvailableSlot() {
        List<Integer> activeSlots = Lists.newArrayList();
        for (int i = 0; i < 9; i++) {
            if (!getStackInSlot(i).isEmpty()) {
                activeSlots.add(i);
            }
        }
        if (activeSlots.size() > 0) {
            return activeSlots.get((int)(Math.random() * activeSlots.size()));
        }
        return -1;
    }
}
