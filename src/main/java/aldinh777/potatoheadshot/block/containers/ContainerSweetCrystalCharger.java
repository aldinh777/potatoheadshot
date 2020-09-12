package aldinh777.potatoheadshot.block.containers;

import aldinh777.potatoheadshot.block.slots.SlotOutputHandler;
import aldinh777.potatoheadshot.block.tileentities.TileEntitySweetCrystalCharger;
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

public class ContainerSweetCrystalCharger extends Container {
	
	private final TileEntitySweetCrystalCharger tileEntity;
	private int energy;
	private int fullCharge;

	public ContainerSweetCrystalCharger(InventoryPlayer player, TileEntitySweetCrystalCharger tileEntity) {
		this.tileEntity = tileEntity;
		IItemHandler inputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		IItemHandler outputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
		
		this.addSlotToContainer(new SlotItemHandler(inputHandler, 0, 40, 23));
		this.addSlotToContainer(new SlotOutputHandler(outputHandler, 0, 140, 26));
		
		for (int y = 0; y < 3; y++) {
			for (int i = 0; i < 9; i++) {
				this.addSlotToContainer(new Slot(player, i + y * 9 + 9, 8 + i * 18, 84 + y * 18));
			}
		} 
		
		for (int x = 0; x < 9; x++)
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142)); 
	}
	
	@SideOnly(Side.CLIENT)
	public void func_75137_b(int id, int data) {
		this.tileEntity.setField(id, data);
	}
	private int currentCharged;

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (IContainerListener listener : this.listeners) {
			
			int energy = this.tileEntity.getField("energy");
			int fullCharge = this.tileEntity.getField("fullCharge");
			int currentCharged = this.tileEntity.getField("currentCharged");
			int maxCapacity = this.tileEntity.getMaxEnergyStored();
			
			if (this.energy != energy || this.energy <= 0 || this.energy >= maxCapacity)
				listener.sendWindowProperty(this, 0, energy);
			if (this.fullCharge != fullCharge)
				listener.sendWindowProperty(this, 1, fullCharge);
			if (this.currentCharged != currentCharged) {
				listener.sendWindowProperty(this, 2, currentCharged);
			}
		} 
		this.energy = this.tileEntity.getField("energy");
		this.fullCharge = this.tileEntity.getField("fullCharge");
		this.currentCharged = this.tileEntity.getField("currentCharged");
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
			
			if (index == 0 || index == 1) {
				if (!this.mergeItemStack(stack, 2, 38, false)) {
					return ItemStack.EMPTY;
				}
			} else {
				ItemStack result = TileEntitySweetCrystalCharger.getResult(stack);
				
				if (!result.isEmpty()) {
					if (this.mergeItemStack(stack, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 29) {
					if (!this.mergeItemStack(stack, 29, 38, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 38 && 
					!this.mergeItemStack(stack, 2, 29, false)) {
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
