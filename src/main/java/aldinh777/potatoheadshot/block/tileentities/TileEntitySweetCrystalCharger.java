package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.energy.PotatoEnergyStorage;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntitySweetCrystalCharger extends TileEntityPotatoMachine {
		
	private final ItemStackHandler inputHandler = new ItemStackHandler(1);
	private final ItemStackHandler outputHandler = new ItemStackHandler(1);
	
	private PotatoEnergyStorage storage = new PotatoEnergyStorage(800000, 400, 0);
	private int energy = this.storage.getEnergyStored();
	private int currentCharged = 0;
	private int fullCharge = 100000;
	private boolean ultimate = false;

	// Override Methods

	@Override
	public void update() {
		if (!this.world.isRemote) {
			boolean flag = false;
			
			if (chargeable()) {
				int currentFullCharge = getFullCharge(this.inputHandler.getStackInSlot(0));
				if (this.fullCharge != currentFullCharge) {
					this.fullCharge = currentFullCharge;
					flag = true;
				} 
				
				if (this.currentCharged >= this.fullCharge) {
					chargeItem();
					this.currentCharged = 0;
					flag = true;
				}
				else if (this.storage.getEnergyStored() >= 400) {
					this.currentCharged += 400;
					this.storage.useEnergy(400);
					flag = true;
				}
			
			} else if (this.currentCharged >= 400) {
				this.currentCharged -= 400;
				flag = true;
			} 

			
			if (this.energy != this.storage.getEnergyStored()) {
				this.energy = this.storage.getEnergyStored();
				flag = true;
			} 
			
			if (flag) {
				this.markDirty();
			}
		} 
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) return true; 
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (facing == EnumFacing.UP || facing == EnumFacing.DOWN);
		}
		return super.hasCapability(capability, facing);
	}
	
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) return (T)this.storage; 
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == EnumFacing.UP)
				return (T)this.inputHandler; 
			if (facing == EnumFacing.DOWN) {
				return (T)this.outputHandler;
			}
		} 
		return (T)super.getCapability(capability, facing);
	}
	
	@Nullable
	@Override
	public ITextComponent getDisplayName() {
		return customOrDefaultName("Sweet Crystal Charger");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inputHandler.deserializeNBT(compound.getCompoundTag("InventoryInput"));
		this.outputHandler.deserializeNBT(compound.getCompoundTag("InventoryOutput"));
		this.energy = compound.getInteger("GuiEnergy");
		this.currentCharged = compound.getInteger("CurrentCharged");
		this.fullCharge = compound.getInteger("FullCharge");
		this.storage.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("InventoryInput", this.inputHandler.serializeNBT());
		compound.setTag("InventoryOutput", this.outputHandler.serializeNBT());
		compound.setInteger("GuiEnergy", this.energy);
		compound.setInteger("CurrentCharged", (short)this.currentCharged);
		compound.setInteger("FullCharge", (short)this.fullCharge);
		this.storage.writeToNBT(compound);
		return compound;
	}

	@Override
	public int getField(String id) {
		switch (id) {
			case "energy":
				return this.energy;
			case "fullCharge":
				return this.fullCharge;
			case "currentCharged":
				return this.currentCharged;
		} 
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
			case 0:
				this.energy = value;
				break;
			case 1:
				this.fullCharge = value;
				break;
			case 2:
				this.currentCharged = value;
				break;
		} 
	}

	// Custom Methods

	public TileEntitySweetCrystalCharger setUltimate() {
		this.ultimate = true;
		this.storage = new PotatoEnergyStorage(32000000, 1600, 0);
		return this;
	}

	public int getMaxEnergyStored() {
		return this.storage.getMaxEnergyStored();
	}
	
	private boolean chargeable() {
		ItemStack input = this.inputHandler.getStackInSlot(0);
		ItemStack output = this.outputHandler.getStackInSlot(0);
		
		if (input.isEmpty()) {
			return false;
		}
		
		ItemStack result = getResult(input);
		
		if (result.isEmpty()) {
			return false;
		}
		if (output.isEmpty()) {
			return true;
		}
		if (output.isItemEqual(result)) {
			int res = output.getCount() + result.getCount();
			return (res <= output.getMaxStackSize());
		} 
		return false;
	}
	
	private void chargeItem() {
		ItemStack input = this.inputHandler.getStackInSlot(0);
		ItemStack output = this.outputHandler.getStackInSlot(0);
		ItemStack result = getResult(input);
		
		if (output.isEmpty()) {
			this.outputHandler.setStackInSlot(0, result.copy());
		} else if (output.isItemEqual(result)) {
			output.grow(result.getCount());
		}
		
		if (result.getItem() == PotatoItems.ULTIMATE_CHARGED_CRYSTAL && !this.ultimate) {
			this.world.createExplosion(null,
					(this.pos.getX() + 0.5F), this.pos.getY(), (this.pos.getZ() + 0.5F),
					5.0F, true
			);
		}
		
		input.shrink(1);
	}

	// Static Methods

	public static ItemStack getResult(ItemStack stack) {
		if (stack.getItem() == PotatoItems.CRYSTAL_SHARD)
			return new ItemStack(PotatoItems.CHARGED_CRYSTAL_SHARD); 
		if (stack.getItem() == PotatoItems.ULTIMATE_CRYSTAL) {
			return new ItemStack(PotatoItems.ULTIMATE_CHARGED_CRYSTAL);
		}
		return ItemStack.EMPTY;
	}
	
	public static int getFullCharge(ItemStack stack) {
		if (stack.getItem() == PotatoItems.CRYSTAL_SHARD) return 100000; 
		if (stack.getItem() == PotatoItems.CHARGED_CRYSTAL) return 800000;
		
		return 100000;
	}
}
