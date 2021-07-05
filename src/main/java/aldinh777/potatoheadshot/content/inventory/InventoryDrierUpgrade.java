package aldinh777.potatoheadshot.content.inventory;

import aldinh777.potatoheadshot.common.lists.PotatoItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.stream.Stream;

public class InventoryDrierUpgrade extends ItemStackHandler {

    public static final int[] MULTIPLIER_UPGRADE_SLOT = { 0, 1, 2, 3, 4, 5, 6 };
    public static final int[] BOOSTER_UPGRADE_SLOT = { 7, 8, 9, 10, 11, 12, 13 };

    public InventoryDrierUpgrade() {
        super(14);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (Arrays.stream(MULTIPLIER_UPGRADE_SLOT).anyMatch(i -> i == slot)) {
            return stack.getItem() == PotatoItems.UPGRADE_MULTIPLIER;

        } else if (Arrays.stream(BOOSTER_UPGRADE_SLOT).anyMatch(i -> i == slot)) {
            return stack.getItem() == PotatoItems.UPGRADE_BOOSTER;
        }

        return false;
    }

    public int getMultiplierLevel() {
        Stream<ItemStack> upgrades = Arrays.stream(MULTIPLIER_UPGRADE_SLOT).mapToObj(this::getStackInSlot);
        return (int) upgrades.filter(stack -> !stack.isEmpty()).count();
    }

    public int getBoosterLevel() {
        Stream<ItemStack> upgrades = Arrays.stream(BOOSTER_UPGRADE_SLOT).mapToObj(this::getStackInSlot);
        return (int) upgrades.filter(stack -> !stack.isEmpty()).count();
    }
}
