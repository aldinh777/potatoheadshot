package aldinh777.potatoheadshot.block.containers;

import aldinh777.potatoheadshot.block.slots.SlotOutputHandler;
import aldinh777.potatoheadshot.block.tileentities.TileEntityVoidExchanger;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerVoidExchanger extends Container {

    private final TileEntityVoidExchanger tileEntity;
    private int progress, cost;

    public ContainerVoidExchanger(InventoryPlayer player, TileEntityVoidExchanger tileEntity) {
        this.tileEntity = tileEntity;
        IItemHandler bottleHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        IItemHandler inputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        IItemHandler outputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

        this.addSlotToContainer(new SlotItemHandler(bottleHandler, 0, 82, 24));
        this.addSlotToContainer(new SlotItemHandler(inputHandler, 0, 40, 48));
        this.addSlotToContainer(new SlotOutputHandler(outputHandler, 0, 118, 47));

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
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        this.tileEntity.setField(id, data);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener listener : this.listeners) {

            int progress = this.tileEntity.getField("progress");
            int cost = this.tileEntity.getField("cost");

            if (this.progress != progress)
                listener.sendWindowProperty(this, 0, progress);
            if (this.cost != cost)
                listener.sendWindowProperty(this, 1, cost);
        }

        this.progress = this.tileEntity.getField("totalFreezeTime");
        this.cost = this.tileEntity.getField("currentFreezeTime");
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        return tileEntity.isUsableByPlayer(playerIn);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();

            if (index == 0 || index == 1) {
                if (!this.mergeItemStack(stack, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                ItemStack result = TileEntityVoidExchanger.getResult(stack);

                if (!result.isEmpty()) {
                    if (this.mergeItemStack(stack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getItem() == PotatoItems.VOID_BOTTLE) {
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
