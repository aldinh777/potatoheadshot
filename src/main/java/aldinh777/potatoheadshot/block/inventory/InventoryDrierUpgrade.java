package aldinh777.potatoheadshot.block.inventory;

import aldinh777.potatoheadshot.block.blocks.machines.BlockDrier;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class InventoryDrierUpgrade extends ItemStackHandler {

    public static final int WATER_UPGRADE_SLOT = 0;
    public static final int MODE_UPGRADE_SLOT = 1;
    public static final int MULTIPLIER_UPGRADE_SLOT_1 = 2;
    public static final int MULTIPLIER_UPGRADE_SLOT_2 = 3;
    public static final int BOOSTER_UPGRADE_SLOT_1 = 4;
    public static final int BOOSTER_UPGRADE_SLOT_2 = 5;

    public InventoryDrierUpgrade() {
        super(6);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        switch (slot) {
            case WATER_UPGRADE_SLOT:
                return stack.getItem() == PotatoItems.UPGRADE_DRIER_WATER;
            case MODE_UPGRADE_SLOT:
                return stack.getItem() == PotatoItems.UPGRADE_MODE_FLUX ||
                        stack.getItem() == PotatoItems.UPGRADE_MODE_MANA;
            case BOOSTER_UPGRADE_SLOT_1:
            case BOOSTER_UPGRADE_SLOT_2:
                return stack.getItem() == PotatoItems.UPGRADE_BOOSTER;
            case MULTIPLIER_UPGRADE_SLOT_1:
            case MULTIPLIER_UPGRADE_SLOT_2:
                return stack.getItem() == PotatoItems.UPGRADE_MULTIPLIER;
            default:
                return false;
        }
    }

    public BlockDrier.Mode getMode() {
        if (hasFluxUpgrade()) {
            return BlockDrier.Mode.FLUX;
        } else if (hasManaUpgrade()) {
            return BlockDrier.Mode.MANA;
        } else {
            return BlockDrier.Mode.BASIC;
        }
    }

    public boolean hasWaterUpgrade() {
        ItemStack upgrade = getStackInSlot(WATER_UPGRADE_SLOT);
        return upgrade.getItem() == PotatoItems.UPGRADE_DRIER_WATER;
    }

    public boolean hasFluxUpgrade() {
        ItemStack upgrade = getStackInSlot(MODE_UPGRADE_SLOT);
        return upgrade.getItem() == PotatoItems.UPGRADE_MODE_FLUX;
    }

    public boolean hasManaUpgrade() {
        ItemStack upgrade = getStackInSlot(MODE_UPGRADE_SLOT);
        return upgrade.getItem() == PotatoItems.UPGRADE_MODE_MANA;
    }

    public boolean hasEnergyUpgrade() {
        return hasFluxUpgrade() || hasManaUpgrade();
    }

    public int getMultiplierLevel() {
        ItemStack upgrade1 = getStackInSlot(MULTIPLIER_UPGRADE_SLOT_1);
        ItemStack upgrade2 = getStackInSlot(MULTIPLIER_UPGRADE_SLOT_2);
        int multiplierLevel = 0;

        if (!upgrade1.isEmpty()) {
            multiplierLevel++;
        }
        if (!upgrade2.isEmpty()) {
            multiplierLevel++;
        }

        return multiplierLevel;
    }

    public int getBoosterLevel() {
        ItemStack upgrade1 = getStackInSlot(BOOSTER_UPGRADE_SLOT_1);
        ItemStack upgrade2 = getStackInSlot(BOOSTER_UPGRADE_SLOT_2);
        int boosterLevel = 0;

        if (!upgrade1.isEmpty()) {
            boosterLevel++;
        }
        if (!upgrade2.isEmpty()) {
            boosterLevel++;
        }

        return boosterLevel;
    }
}
