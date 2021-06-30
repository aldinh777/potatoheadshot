package aldinh777.potatoheadshot.content.containers;

import aldinh777.potatoheadshot.content.inventory.InventoryDrier;
import aldinh777.potatoheadshot.content.tileentities.TileEntityDrier;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerDrier extends ContainerMachine {

    public ContainerDrier(InventoryPlayer inventoryPlayer, TileEntityDrier tileEntity) {
        super(inventoryPlayer, tileEntity, 5);

        IItemHandler inventoryDrier = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.FUEL_SLOT, 14, 34));
        addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.DRIER_INPUT_SLOT, 87, 26));
        addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.DRIER_OUTPUT_SLOT, 141, 22));
        addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.WATER_INPUT_SLOT, 87, 58));
        addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.WATER_OUTPUT_SLOT, 141, 54));
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (tileEntity instanceof TileEntityDrier) {
            TileEntityDrier tileEntity = (TileEntityDrier) this.tileEntity;
            for (IContainerListener listener : listeners) {
                listener.sendWindowProperty(this, 0, tileEntity.burnProgress);
                listener.sendWindowProperty(this, 1, tileEntity.burnTime);
                listener.sendWindowProperty(this, 2, tileEntity.dryProgress);
                listener.sendWindowProperty(this, 3, tileEntity.maxDryProgress);

                listener.sendWindowProperty(this, 4, tileEntity.waterVolume);
                listener.sendWindowProperty(this, 5, tileEntity.maxWaterVolume);

                listener.sendWindowProperty(this, 6, tileEntity.activeStateLimit);
                listener.sendWindowProperty(this, 7, tileEntity.waterProgress);
                listener.sendWindowProperty(this, 8, tileEntity.maxWaterProgress);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        if (tileEntity instanceof TileEntityDrier) {
            TileEntityDrier tileEntity = (TileEntityDrier) this.tileEntity;
            switch (id) {
                case 0:
                    tileEntity.burnProgress = data;
                    break;
                case 1:
                    tileEntity.burnTime = data;
                    break;
                case 2:
                    tileEntity.dryProgress = data;
                    break;
                case 3:
                    tileEntity.maxDryProgress = data;
                    break;
                case 4:
                    tileEntity.waterVolume = data;
                    break;
                case 5:
                    tileEntity.maxWaterVolume = data;
                    break;
                case 6:
                    tileEntity.activeStateLimit = data;
                    break;
                case 7:
                    tileEntity.waterProgress = data;
                    break;
                case 8:
                    tileEntity.maxWaterProgress = data;
                    break;
            }
        }
    }

    @Override
    public void initSlots() {
        IItemHandler inventoryDrier = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.FUEL_SLOT, 14, 34));
        addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.DRIER_INPUT_SLOT, 87, 26));
        addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.DRIER_OUTPUT_SLOT, 141, 22));
        addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.WATER_INPUT_SLOT, 87, 58));
        addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.WATER_OUTPUT_SLOT, 141, 54));
    }
}
