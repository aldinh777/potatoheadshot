package aldinh777.potatoheadshot.block.containers;

import aldinh777.potatoheadshot.block.inventory.InventoryDrier;
import aldinh777.potatoheadshot.block.inventory.InventoryDrierUpgrade;
import aldinh777.potatoheadshot.block.tileentities.TileEntityDrier;
import aldinh777.potatoheadshot.util.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
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
    private final int machineSlot;

    public ContainerDrier(InventoryPlayer inventoryPlayer, TileEntityDrier tileEntity) {
        this.tileEntity = tileEntity;
        int machineSlot = 2;
        InventoryDrierUpgrade upgrade = tileEntity.getUpgrade();
        IItemHandler inventoryDrier = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

        if (!upgrade.hasEnergyUpgrade()) {
            addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.FUEL_SLOT, 14, 34));
            machineSlot++;
        }

        addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.DRIER_INPUT_SLOT, 87, 26));
        addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.DRIER_OUTPUT_SLOT, 141, 22));

        if (upgrade.hasWaterUpgrade()) {
            this.addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.WATER_INPUT_SLOT, 87, 58));
            this.addSlotToContainer(new SlotItemHandler(inventoryDrier, InventoryDrier.WATER_OUTPUT_SLOT, 141, 54));
            machineSlot+=2;
        }

        this.machineSlot = machineSlot;

        InventoryHelper.addSlotPlayer(this::addSlotToContainer, inventoryPlayer);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener listener : listeners) {
            listener.sendWindowProperty(this, 0, tileEntity.burnProgress);
            listener.sendWindowProperty(this, 1, tileEntity.burnTime);
            listener.sendWindowProperty(this, 2, tileEntity.dryProgress);
            listener.sendWindowProperty(this, 3, tileEntity.maxDryProgress);

            listener.sendWindowProperty(this, 4, tileEntity.waterVolume);
            listener.sendWindowProperty(this, 5, tileEntity.fluxStored);
            listener.sendWindowProperty(this, 6, tileEntity.manaStored);

            listener.sendWindowProperty(this, 7, tileEntity.maxWaterVolume);
            listener.sendWindowProperty(this, 8, tileEntity.maxFluxStored);
            listener.sendWindowProperty(this, 9, tileEntity.maxManaStored);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
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
                tileEntity.fluxStored = data;
                break;
            case 6:
                tileEntity.manaStored = data;
                break;
            case 7:
                tileEntity.maxWaterVolume = data;
                break;
            case 8:
                tileEntity.maxFluxStored = data;
                break;
            case 9:
                tileEntity.maxManaStored = data;
                break;
        }
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
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();

            if (index < machineSlot) {
                if (!mergeItemStack(stack, machineSlot, machineSlot + 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!mergeItemStack(stack, 0, machineSlot, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemStack;
    }
}
