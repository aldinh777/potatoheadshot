package aldinh777.potatoheadshot.block.containers;

import aldinh777.potatoheadshot.block.inventory.InventoryDrierUpgrade;
import aldinh777.potatoheadshot.block.slots.SlotUpgradeHandler;
import aldinh777.potatoheadshot.block.tileentities.TileEntityDrier;
import aldinh777.potatoheadshot.util.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ContainerDrierUpgrade extends Container {

    private final TileEntityDrier tileEntity;

    public ContainerDrierUpgrade(InventoryPlayer inventoryPlayer, TileEntityDrier tileEntity) {
        this.tileEntity = tileEntity;
        InventoryDrierUpgrade upgrade = tileEntity.getUpgrade();

        addSlotToContainer(new SlotUpgradeHandler(upgrade, InventoryDrierUpgrade.WATER_UPGRADE_SLOT, 12, 18));
        addSlotToContainer(new SlotUpgradeHandler(upgrade, InventoryDrierUpgrade.MODE_UPGRADE_SLOT, 12, 56));
        addSlotToContainer(new SlotUpgradeHandler(upgrade, InventoryDrierUpgrade.MULTIPLIER_UPGRADE_SLOT_1, 124, 30));
        addSlotToContainer(new SlotUpgradeHandler(upgrade, InventoryDrierUpgrade.MULTIPLIER_UPGRADE_SLOT_2, 146, 30));
        addSlotToContainer(new SlotUpgradeHandler(upgrade, InventoryDrierUpgrade.BOOSTER_UPGRADE_SLOT_1, 124, 56));
        addSlotToContainer(new SlotUpgradeHandler(upgrade, InventoryDrierUpgrade.BOOSTER_UPGRADE_SLOT_2, 146, 56));

        InventoryHelper.addSlotPlayer(this::addSlotToContainer, inventoryPlayer);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener listener : listeners) {

        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        World world = tileEntity.getWorld();
        BlockPos pos = tileEntity.getPos();
        if (world.getTileEntity(pos) == tileEntity) {
            double posX = (double) pos.getX() + 0.5D;
            double posY = (double) pos.getY() + 0.5D;
            double posZ = (double) pos.getZ() + 0.5D;

            return playerIn.getDistanceSq(posX, posY, posZ) <= 64.0D;
        }
        return false;
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index) {
        return super.transferStackInSlot(playerIn, index);
    }
}
