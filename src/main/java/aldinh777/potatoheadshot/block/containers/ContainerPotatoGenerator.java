package aldinh777.potatoheadshot.block.containers;

import aldinh777.potatoheadshot.block.slots.SlotGeneratorHandler;
import aldinh777.potatoheadshot.block.slots.SlotOutputHandler;
import aldinh777.potatoheadshot.block.slots.SlotProcessHandler;
import aldinh777.potatoheadshot.block.tileentities.TileEntityPotatoGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class ContainerPotatoGenerator extends Container {

    private final TileEntityPotatoGenerator tileEntity;
    private int energy, totalCookTime, currentCookTime;

    public ContainerPotatoGenerator(InventoryPlayer player, TileEntityPotatoGenerator tileEntity) {
        this.tileEntity = tileEntity;
        IItemHandler handler = tileEntity.handler;

        this.addSlotToContainer(new SlotGeneratorHandler(handler, 0, 42, 35));
        this.addSlotToContainer(new SlotProcessHandler(handler, 1, 63, 35));
        this.addSlotToContainer(new SlotOutputHandler(handler, 2, 131, 33));

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
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileEntity.isUsableByPlayer(playerIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        this.tileEntity.setField(id, data);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener listener : this.listeners) {

            boolean flagUpdateAll = false;

            int energy = this.tileEntity.getField("energy");
            int totalCookTime = this.tileEntity.getField("totalCookTime");
            int currentCookTime = this.tileEntity.getField("currentCookTime");
            int maxCapacity = this.tileEntity.getMaxEnergyStored();

            if (this.energy != energy || this.energy <= 0 || this.energy >= maxCapacity)
                flagUpdateAll = true;
            if (this.totalCookTime != totalCookTime)
                flagUpdateAll = true;
            if (this.currentCookTime != currentCookTime)
                flagUpdateAll = true;

            if (flagUpdateAll) {
                listener.sendWindowProperty(this, 0, energy);
                listener.sendWindowProperty(this, 1, totalCookTime);
                listener.sendWindowProperty(this, 2, currentCookTime);
            }
        }

        this.energy = this.tileEntity.getField("energy");
        this.totalCookTime = this.tileEntity.getField("totalCookTime");
        this.currentCookTime = this.tileEntity.getField("currentCookTime");
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();

            if (index == 0 || index == 2) {
                if (!this.mergeItemStack(stack, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(stack, itemstack);
            } else if (index != 1) {
                if (TileEntityPotatoGenerator.isItemFuel(stack)) {
                    if (this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 30) {
                    if (!this.mergeItemStack(stack, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 39) {
                    if (!this.mergeItemStack(stack, 3, 30, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.mergeItemStack(stack, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if(stack.isEmpty()) {
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
