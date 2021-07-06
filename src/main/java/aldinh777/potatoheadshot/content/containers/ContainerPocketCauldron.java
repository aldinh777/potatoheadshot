package aldinh777.potatoheadshot.content.containers;

import aldinh777.potatoheadshot.content.capability.CapabilityMana;
import aldinh777.potatoheadshot.content.capability.IManaStorage;
import aldinh777.potatoheadshot.content.capability.PotatoManaStorage;
import aldinh777.potatoheadshot.content.inventory.InventoryPocketCauldron;
import aldinh777.potatoheadshot.content.items.PocketCauldron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
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

    private static class RestrictedSlot extends Slot {

        public RestrictedSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return false;
        }

        @Override
        public boolean canTakeStack(@Nonnull EntityPlayer playerIn) {
            return false;
        }
    }

    public ContainerPocketCauldron(EntityPlayer player) {
        this.player = player;
        this.stack = PocketCauldron.findPocketCauldron(player);

        IItemHandler inventory = this.stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

        this.addSlotToContainer(new SlotItemHandler(inventory, InventoryPocketCauldron.ESSENCE_SLOT, 24, 48));
        this.addSlotToContainer(new SlotItemHandler(inventory, InventoryPocketCauldron.INPUT_SLOT, 47, 48));
        this.addSlotToContainer(new SlotItemHandler(inventory, InventoryPocketCauldron.OUTPUT_SLOT, 117, 47));

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
            IManaStorage manaStorage = stack.getCapability(CapabilityMana.MANA, EnumFacing.UP);
            if (manaStorage instanceof PotatoManaStorage) {
                manaStorage.setMana(data);
            }
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        IManaStorage manaStorage = stack.getCapability(CapabilityMana.MANA, EnumFacing.UP);
        if (manaStorage instanceof PotatoManaStorage) {
            for (IContainerListener listener : listeners) {
                int manaSize = manaStorage.getManaStored();
                listener.sendWindowProperty(this, 0, manaSize);
            }
            manaSize = manaStorage.getManaStored();
        }
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
