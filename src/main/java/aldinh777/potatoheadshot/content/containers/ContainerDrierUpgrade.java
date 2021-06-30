package aldinh777.potatoheadshot.content.containers;

import aldinh777.potatoheadshot.backup.slots.SlotUpgradeHandler;
import aldinh777.potatoheadshot.content.inventory.InventoryDrierUpgrade;
import aldinh777.potatoheadshot.content.tileentities.TileEntityDrier;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerDrierUpgrade extends ContainerMachine {

    public ContainerDrierUpgrade(InventoryPlayer inventoryPlayer, TileEntityDrier tileEntity) {
        super(inventoryPlayer, tileEntity, 14);
    }

    @Override
    public void initSlots() {
        if (tileEntity instanceof TileEntityDrier) {
            TileEntityDrier tileEntity = (TileEntityDrier) this.tileEntity;
            InventoryDrierUpgrade upgrade = tileEntity.getUpgrade();

            for (int i = 0; i < InventoryDrierUpgrade.MULTIPLIER_UPGRADE_SLOT.length; i++) {
                addSlotToContainer(new SlotUpgradeHandler(upgrade, i, 14 + (i * 22), 30));
            }

            for (int i = 0; i < InventoryDrierUpgrade.BOOSTER_UPGRADE_SLOT.length; i++) {
                int slot = i + 7;
                addSlotToContainer(new SlotUpgradeHandler(upgrade, slot, 14 + (i * 22), 56));
            }
        }
    }
}
