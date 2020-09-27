package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TileEntityMana extends TileEntityPotatoMachine {

	protected final ItemStackHandler inputHandler = new ItemStackHandler(1);
	protected final ItemStackHandler outputHandler = new ItemStackHandler(1);
	
	protected final PotatoManaStorage storage = new PotatoManaStorage(64000);
	protected int manaSize = 0;
	protected int currentTick = 0;

	public abstract ItemStack getResult(ItemStack paramItemStack);

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && (
			facing == EnumFacing.UP || facing == EnumFacing.DOWN)) {
			return true;
		}
		
		return super.hasCapability(capability, facing);
	}
	
	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == EnumFacing.UP)
				return (T)this.inputHandler; 
			if (facing == EnumFacing.DOWN) {
				return (T)this.outputHandler;
			}
		} 
		return super.getCapability(capability, facing);
	}
	
	@Override
	public void readFromNBT(@Nonnull NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inputHandler.deserializeNBT(compound.getCompoundTag("InventoryInput"));
		this.outputHandler.deserializeNBT(compound.getCompoundTag("InventoryOutput"));
		this.manaSize = compound.getInteger("ManaVolume");
		this.storage.readFromNBT(compound);
	}

	@Nonnull
    @Override
	public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("InventoryInput", this.inputHandler.serializeNBT());
		compound.setTag("InventoryOutput", this.outputHandler.serializeNBT());
		compound.setInteger("ManaVolume", this.manaSize);
		this.storage.writeToNBT(compound);
		return compound;
	}

	@Override
	public int getField(String id) {
		switch (id) {
			case "manaSize":
				return this.manaSize;
			case "manaMaxSize":
				return this.storage.getMaxManaStored();
		} 
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if (id == 0)
			this.manaSize = value; 
	}
}
