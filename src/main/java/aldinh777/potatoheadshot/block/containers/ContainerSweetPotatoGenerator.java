package aldinh777.potatoheadshot.block.containers;

import aldinh777.potatoheadshot.block.slots.SlotOutputHandler;
import aldinh777.potatoheadshot.block.slots.SlotProcessHandler;
import aldinh777.potatoheadshot.block.tileentities.TileEntitySweetPotatoGenerator;
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

public class ContainerSweetPotatoGenerator extends Container {

    private final TileEntitySweetPotatoGenerator tileEntity;
    private int energy, totalCookTime, currentCookTime;

    public ContainerSweetPotatoGenerator(InventoryPlayer player, TileEntitySweetPotatoGenerator tileEntity) {
        this.tileEntity = tileEntity;
        IItemHandler processHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        IItemHandler inputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        IItemHandler outputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

        this.addSlotToContainer(new SlotItemHandler(inputHandler, 0, 42, 35));
        this.addSlotToContainer(new SlotProcessHandler(processHandler, 0, 63, 35));
        this.addSlotToContainer(new SlotOutputHandler(outputHandler, 0, 131, 33));

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
            int totalCookTime = this.tileEntity.getField("totalCookTime");
            int currentCookTime = this.tileEntity.getField("currentCookTime");
            int maxCapacity = this.tileEntity.getMaxEnergyStored();

            if (this.energy != energy || this.energy <= 0 || this.energy >= maxCapacity)
                listener.sendWindowProperty(this, 0, energy);
            if (this.totalCookTime != totalCookTime)
                listener.sendWindowProperty(this, 1, totalCookTime);
            if (this.currentCookTime != currentCookTime)
                listener.sendWindowProperty(this, 2, currentCookTime);
        }

        this.energy = this.tileEntity.getField("energy");
        this.totalCookTime = this.tileEntity.getField("totalCookTime");
        this.currentCookTime = this.tileEntity.getField("currentCookTime");
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

            if (index == 0 || index == 2) {
                if (!this.mergeItemStack(stack, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index != 1) {
                if (TileEntitySweetPotatoGenerator.isItemFuel(stack)) {
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
