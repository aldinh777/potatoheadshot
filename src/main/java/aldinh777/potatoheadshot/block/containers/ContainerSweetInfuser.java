package aldinh777.potatoheadshot.block.containers;

import aldinh777.potatoheadshot.block.slots.SlotOutputHandler;
import aldinh777.potatoheadshot.block.tileentities.TileEntitySweetInfuser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerSweetInfuser extends Container {

    private final TileEntitySweetInfuser tileEntity;

    public ContainerSweetInfuser(InventoryPlayer player, TileEntitySweetInfuser tileEntity) {
        this.tileEntity = tileEntity;
        IItemHandler inputHandler = tileEntity.getHandler("input");
        IItemHandler outputHandler = tileEntity.getHandler("output");
        IItemHandler fusionHandler = tileEntity.getHandler("fusion");

        this.addSlotToContainer(new SlotItemHandler(inputHandler, 0, 63, 35));
        this.addSlotToContainer(new SlotItemHandler(fusionHandler, 0, 37, 22));
        this.addSlotToContainer(new SlotItemHandler(fusionHandler, 1, 63, 9));
        this.addSlotToContainer(new SlotItemHandler(fusionHandler, 2, 89, 22));
        this.addSlotToContainer(new SlotItemHandler(fusionHandler, 3, 37, 48));
        this.addSlotToContainer(new SlotItemHandler(fusionHandler, 4, 63, 61));
        this.addSlotToContainer(new SlotItemHandler(fusionHandler, 5, 89, 48));
        this.addSlotToContainer(new SlotOutputHandler(outputHandler, 0, 122, 35));

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

            listener.sendWindowProperty(this, 0, energy);
            listener.sendWindowProperty(this, 1, totalInfuseTime);
            listener.sendWindowProperty(this, 2, currentInfuseTime);
        }
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        return this.tileEntity.isUsableByPlayer(playerIn);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();

            if (index < 8) {
                if (!this.mergeItemStack(stack, 8, 44, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 35) {
                if (!this.mergeItemStack(stack, 35, 44, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 44) {
                if (!this.mergeItemStack(stack, 8, 35, false)) {
                    return ItemStack.EMPTY;
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
