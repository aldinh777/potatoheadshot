package aldinh777.potatoheadshot.block.containers;

import aldinh777.potatoheadshot.block.inventory.InventoryDrier;
import aldinh777.potatoheadshot.block.inventory.InventoryDrierUpgrade;
import aldinh777.potatoheadshot.block.slots.SlotFuelHandler;
import aldinh777.potatoheadshot.block.slots.SlotOutputHandler;
import aldinh777.potatoheadshot.block.tileentities.TileEntityDrier;
import aldinh777.potatoheadshot.util.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerDrier extends Container {

    private final TileEntityDrier tileEntity;

    public ContainerDrier(InventoryPlayer inventoryPlayer, TileEntityDrier tileEntity) {
        this.tileEntity = tileEntity;
        InventoryDrierUpgrade upgrade = tileEntity.getUpgrade();
        IItemHandler inventoryDrier = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

        if (!upgrade.hasEnergyCapacity()) {
            addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.FUEL_SLOT, 14, 34));
        }

        addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.DRIER_INPUT_SLOT, 87, 26));
        addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.DRIER_OUTPUT_SLOT, 141, 22));

        if (upgrade.hasWaterCapacity()) {
            this.addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.WATER_INPUT_SLOT, 87, 58));
            this.addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.WATER_OUTPUT_SLOT, 141, 54));
        }

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
