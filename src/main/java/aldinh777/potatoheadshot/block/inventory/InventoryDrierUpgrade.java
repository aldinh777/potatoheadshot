package aldinh777.potatoheadshot.block.inventory;

import aldinh777.potatoheadshot.block.blocks.machines.BlockDrier;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

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

    public boolean hasWaterCapacity() {
        ItemStack upgrade = getStackInSlot(WATER_UPGRADE_SLOT);
        return !upgrade.isEmpty();
    }

    public BlockDrier.Mode getMode() {
        if (hasFluxCapacity()) {
            return BlockDrier.Mode.FLUX;
        } else if (hasManaCapacity()) {
            return BlockDrier.Mode.MANA;
        } else {
            return BlockDrier.Mode.BASIC;
        }
    }

    public boolean hasFluxCapacity() {
        ItemStack upgrade = getStackInSlot(MODE_UPGRADE_SLOT);
        return upgrade.getItem() == Items.REDSTONE;
    }

    public boolean hasManaCapacity() {
        ItemStack upgrade = getStackInSlot(MODE_UPGRADE_SLOT);
        return upgrade.getItem() == PotatoItems.MANA_DUST;
    }

    public boolean hasEnergyCapacity() {
        return hasFluxCapacity() || hasManaCapacity();
    }

    public int getMultiplierLevel() {
        return 0;
    }

    public int getBoosterLevel() {
        return 0;
    }
}
