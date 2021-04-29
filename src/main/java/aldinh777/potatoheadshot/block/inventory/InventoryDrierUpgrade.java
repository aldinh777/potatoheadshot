package aldinh777.potatoheadshot.block.inventory;

import aldinh777.potatoheadshot.block.blocks.machines.BlockDrier;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryDrierUpgrade extends ItemStackHandler {

    public final int WATER_UPGRADE_SLOT = 0;
    public final int MODE_UPGRADE_SLOT = 1;
    public final int MULTIPLIER_UPGRADE_SLOT_1 = 2;
    public final int MULTIPLIER_UPGRADE_SLOT_2 = 3;
    public final int BOOSTER_UPGRADE_SLOT_1 = 4;
    public final int BOOSTER_UPGRADE_SLOT_2 = 5;

    public InventoryDrierUpgrade() {
        super(6);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    public boolean hasWaterBox() {
        return false;
    }

    public BlockDrier.Mode getMode() {
        return BlockDrier.Mode.BASIC;
    }

    public int getMultiplierLevel() {
        return 0;
    }

    public int getBoosterLevel() {
        return 0;
    }
}
