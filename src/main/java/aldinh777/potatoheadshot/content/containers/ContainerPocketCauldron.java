package aldinh777.potatoheadshot.content.containers;

import aldinh777.potatoheadshot.backup.slots.SlotOutputHandler;
import aldinh777.potatoheadshot.content.items.PocketCauldron;
import aldinh777.potatoheadshot.content.slots.RestrictedSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerPocketCauldron extends Container {

    public final ItemStack stack;
    public final EntityPlayer player;
    public int manaSize;

    public ContainerPocketCauldron(EntityPlayer player) {
        this.player = player;
        this.stack = PocketCauldron.findPocketCauldron(player);

        IItemHandler input = this.stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        IItemHandler output = this.stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

        this.addSlotToContainer(new SlotItemHandler(input, 0, 24, 48));
        this.addSlotToContainer(new SlotItemHandler(input, 1, 47, 48));
        this.addSlotToContainer(new SlotOutputHandler(output, 0, 117, 47));

        for (int y = 0; y < 3; y++) {
            for (int i = 0; i < 9; i++) {
                int index = i + y * 9 + 9;
                int xPos = 8 + i * 18;
                int yPos = 84 + y * 18;

                if (player.inventory.getStackInSlot(index).getItem() instanceof PocketCauldron) {
                    this.addSlotToContainer(new RestrictedSlot(player.inventory, index, xPos, yPos));
                } else {
                    this.addSlotToContainer(new Slot(player.inventory, index, xPos, yPos));
                }
            }
        }

        for (int index = 0; index < 9; index++) {
            int xPos = 8 + index * 18;
            int yPos = 142;

            if (player.inventory.getStackInSlot(index).getItem() instanceof PocketCauldron) {
                this.addSlotToContainer(new RestrictedSlot(player.inventory, index, xPos, yPos));
            } else {
                this.addSlotToContainer(new Slot(player.inventory, index, xPos, yPos));
            }
        }
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void updateProgressBar(int id, int data) {
        if (id == 0) {
            PocketCauldron.setManaSize(stack, data);
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener listener : this.listeners) {
            int manaSize = PocketCauldron.getManaSize(stack);
            listener.sendWindowProperty(this, 0, manaSize);
        }

        this.manaSize = PocketCauldron.getManaSize(stack);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();

            if (index == 0 || index == 1 || index == 2) {
                if (!this.mergeItemStack(stack, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }

            } else if (index < 30) {
                if (!this.mergeItemStack(stack, 30, 39, false)) {
                    return ItemStack.EMPTY;
                }

            } else if (index < 39 && !this.mergeItemStack(stack, 3, 30, false)) {
                return ItemStack.EMPTY;
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
