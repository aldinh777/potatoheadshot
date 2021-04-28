package aldinh777.potatoheadshot.block.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

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
    protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
        return 1;
    }
}
