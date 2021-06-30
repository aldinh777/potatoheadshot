package aldinh777.potatoheadshot.content.containers;

import aldinh777.potatoheadshot.other.util.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class ContainerMachine extends Container {

    protected final TileEntity tileEntity;
    protected final int machineSlot;

    public ContainerMachine(InventoryPlayer inventoryPlayer, TileEntity tileEntity, int maxSlot) {
        this.tileEntity = tileEntity;
        machineSlot = maxSlot;
        initSlots();
        InventoryHelper.addSlotPlayer(this::addSlotToContainer, inventoryPlayer);
    }

    public abstract void initSlots();

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
