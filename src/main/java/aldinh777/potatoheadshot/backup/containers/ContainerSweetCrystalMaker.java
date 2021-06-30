package aldinh777.potatoheadshot.backup.containers;

import aldinh777.potatoheadshot.backup.tileentities.TileEntitySweetCrystalMaker;
import aldinh777.potatoheadshot.backup.slots.SlotIceHandler;
import aldinh777.potatoheadshot.backup.slots.SlotOutputHandler;
import aldinh777.potatoheadshot.other.lists.PotatoItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerSweetCrystalMaker extends Container {

    private final TileEntitySweetCrystalMaker tileEntity;

    public ContainerSweetCrystalMaker(InventoryPlayer player, TileEntitySweetCrystalMaker tileEntity) {
        this.tileEntity = tileEntity;
        IItemHandler iceHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        IItemHandler inputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        IItemHandler outputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

        this.addSlotToContainer(new SlotIceHandler(iceHandler, 0, 40, 52));
        this.addSlotToContainer(new SlotItemHandler(inputHandler, 0, 40, 23));
        this.addSlotToContainer(new SlotOutputHandler(outputHandler, 0, 141, 27));

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
            int energy = this.tileEntity.getField("energy");
            int crystalTime = this.tileEntity.getField("crystalTime");
            int crystalization = this.tileEntity.getField("crystalization");

            listener.sendWindowProperty(this, 0, energy);
            listener.sendWindowProperty(this, 1, crystalTime);
            listener.sendWindowProperty(this, 2, crystalization);
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

            if (index == 0 || index == 1 || index == 2) {
                if (!this.mergeItemStack(stack, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (stack.getItem() == PotatoItems.RAW_SALT) {
                    if (this.mergeItemStack(stack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getItem() == Item.getItemFromBlock(Blocks.ICE)) {
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
