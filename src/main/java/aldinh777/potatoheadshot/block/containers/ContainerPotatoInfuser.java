package aldinh777.potatoheadshot.block.containers;

import aldinh777.potatoheadshot.block.tileentities.TileEntityPotatoInfuser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerPotatoInfuser extends Container {

    private final TileEntityPotatoInfuser tileEntity;
    private int energy, totalInfuseTime, currentInfuseTime;

    public ContainerPotatoInfuser(InventoryPlayer player, TileEntityPotatoInfuser tileEntity) {
        this.tileEntity = tileEntity;
        IItemHandler inputHandler = tileEntity.getHandler("input");
        IItemHandler middleHandler = tileEntity.getHandler("middle");

        this.addSlotToContainer(new SlotItemHandler(middleHandler, 0, 84, 35));
        this.addSlotToContainer(new SlotItemHandler(inputHandler, 0, 58, 22));
        this.addSlotToContainer(new SlotItemHandler(inputHandler, 1, 84, 9));
        this.addSlotToContainer(new SlotItemHandler(inputHandler, 2, 110, 22));
        this.addSlotToContainer(new SlotItemHandler(inputHandler, 3, 58, 48));
        this.addSlotToContainer(new SlotItemHandler(inputHandler, 4, 84, 61));
        this.addSlotToContainer(new SlotItemHandler(inputHandler, 5, 110, 48));

        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 9; x++) {
                this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
            }
        }

        for(int x = 0; x < 9; x++) {
            this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        this.tileEntity.setField(id, data);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener listener : this.listeners) {

            int energy = this.tileEntity.getField("energy");
            int totalInfuseTime = this.tileEntity.getField("totalInfuseTime");
            int currentInfuseTime = this.tileEntity.getField("currentInfuseTime");
            int maxCapacity = this.tileEntity.getMaxEnergyStored();

            if (this.energy != energy || this.energy <= 0 || this.energy >= maxCapacity)
                listener.sendWindowProperty(this, 0, energy);
            if (this.totalInfuseTime != totalInfuseTime)
                listener.sendWindowProperty(this, 1, totalInfuseTime);
            if (this.currentInfuseTime != currentInfuseTime)
                listener.sendWindowProperty(this, 2, currentInfuseTime);
        }

        this.energy = this.tileEntity.getField("energy");
        this.totalInfuseTime = this.tileEntity.getField("totalInfuseTime");
        this.currentInfuseTime = this.tileEntity.getField("currentInfuseTime");
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileEntity.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();

            if (index < 7) {
                if (!this.mergeItemStack(stack, 7, 43, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (index < 34) {
                    if (!this.mergeItemStack(stack, 34, 43, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 43) {
                    if (!this.mergeItemStack(stack, 7, 34, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }
}
