package aldinh777.potatoheadshot.block.containers;

import aldinh777.potatoheadshot.recipes.category.PotatoDrierRecipes;
import aldinh777.potatoheadshot.block.slots.SlotFuelHandler;
import aldinh777.potatoheadshot.block.slots.SlotOutputHandler;
import aldinh777.potatoheadshot.block.tileentities.TileEntityPotatoDrier;
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

public class ContainerPotatoDrier extends Container {

    private final TileEntityPotatoDrier tileEntity;

    public ContainerPotatoDrier(InventoryPlayer player, TileEntityPotatoDrier tileEntity) {
        this.tileEntity = tileEntity;
        IItemHandler fuelHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.WEST);
        IItemHandler inputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        IItemHandler outputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

        this.addSlotToContainer(new SlotFuelHandler(fuelHandler, 0, 14, 34));
        this.addSlotToContainer(new SlotItemHandler(inputHandler, 0, 87, 26));
        this.addSlotToContainer(new SlotItemHandler(inputHandler, 1, 87, 58));
        this.addSlotToContainer(new SlotOutputHandler(outputHandler, 0, 141, 22));
        this.addSlotToContainer(new SlotOutputHandler(outputHandler, 1, 141, 54));

        for (int y = 0; y < 3; y++) {
            for(int x = 0; x < 9; x++) {
                this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
            }
        }

        for (int x = 0; x < 9; x++) {
            this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener listener : this.listeners) {
            int waterSize = this.tileEntity.getField("waterSize");
            int wateringTime = this.tileEntity.getField("wateringTime");
            int burnTime = this.tileEntity.getField("burnTime");
            int dryTime = this.tileEntity.getField("dryTime");
            int wetTime = this.tileEntity.getField("wetTime");
            int currentBurnTime = this.tileEntity.getField("currentBurnTime");
            int currentWateringTime = this.tileEntity.getField("currentWateringTime");
            int totalDryTime = this.tileEntity.getField("totalDryTime");
            int totalWetTime = this.tileEntity.getField("totalWetTime");

            listener.sendWindowProperty(this, 0, waterSize);
            listener.sendWindowProperty(this, 1, burnTime);
            listener.sendWindowProperty(this, 2, wateringTime);
            listener.sendWindowProperty(this, 3, dryTime);
            listener.sendWindowProperty(this, 4, wetTime);
            listener.sendWindowProperty(this, 5, currentBurnTime);
            listener.sendWindowProperty(this, 6, currentWateringTime);
            listener.sendWindowProperty(this, 7, totalDryTime);
            listener.sendWindowProperty(this, 8, totalWetTime);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        this.tileEntity.setField(id, data);
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

            if (index == 3 || index == 4) {
                if (!this.mergeItemStack(stack, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index !=2 && index != 1 && index != 0) {
                if (TileEntityPotatoDrier.isItemFuel(stack)) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (PotatoDrierRecipes.INSTANCE.isDryRecipeExists(stack)) {
                    if (!this.mergeItemStack(stack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (PotatoDrierRecipes.INSTANCE.isWetRecipeExists(stack)) {
                    if (!this.mergeItemStack(stack, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 32) {
                    if (!this.mergeItemStack(stack, 32, 41, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 41 && !this.mergeItemStack(stack, 5, 32, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stack, 5, 41, false)) {
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
