package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.energy.PotatoEnergyStorage;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntitySweetCrystalMaker extends TileEntityPotatoMachine {

    private final ItemStackHandler iceHandler = new ItemStackHandler(1) {
        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            Item ice = Item.getItemFromBlock(Blocks.ICE);
            return stack.getItem() == ice ? super.insertItem(slot, stack, simulate) : stack;
        }
    };
    private final ItemStackHandler inputHandler = new ItemStackHandler(1);
    private final ItemStackHandler outputHandler = new ItemStackHandler(1);

    private final PotatoEnergyStorage storage = new PotatoEnergyStorage(40000, 40, 0);
    private int energy = this.storage.getEnergyStored();
    private final int totalCrystalTime = 100;
    private final int totalCrystalization = 800;
    private int crystalTime = 0;
    private int crystalization = 0;

    // Override Methods

    @Override
    public void update() {
        if (!this.world.isRemote) {
            boolean flag = false;

            if (this.crystalization >= this.totalCrystalization) {
                this.createCrystal();
                this.crystalization = 0;
                flag = true;
            }

            if (this.canCrystalize()) {
                if (this.crystalTime >= this.totalCrystalTime) {
                    ItemStack ice = this.iceHandler.getStackInSlot(0);
                    this.crystalizeItem();
                    this.crystalTime = 0;
                    ice.shrink(1);
                    flag = true;

                } else if (this.storage.getEnergyStored() >= 20) {
                    this.crystalTime++;
                    this.storage.useEnergy(20);
                    flag = true;
                }
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
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return (T) this.storage;
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.UP) {
                return (T)this.inputHandler;
            } else if (facing == EnumFacing.DOWN) {
                return (T)this.outputHandler;
            } else {
                return (T)this.iceHandler;
            }
        }
        return super.getCapability(capability, facing);
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return customOrDefaultName("Sweet Crystal Maker");
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.iceHandler.deserializeNBT(compound.getCompoundTag("InventoryIce"));
        this.inputHandler.deserializeNBT(compound.getCompoundTag("InventoryInput"));
        this.outputHandler.deserializeNBT(compound.getCompoundTag("InventoryOutput"));
        this.energy = compound.getInteger("GuiEnergy");
        this.crystalTime = compound.getInteger("CrystalTime");
        this.crystalization = compound.getInteger("Crystalization");
        this.storage.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("InventoryIce", this.iceHandler.serializeNBT());
        compound.setTag("InventoryInput", this.inputHandler.serializeNBT());
        compound.setTag("InventoryOutput", this.outputHandler.serializeNBT());
        compound.setInteger("GuiEnergy", this.energy);
        compound.setInteger("CrystalTime", (short)this.crystalTime);
        compound.setInteger("Crystalization", (short)this.crystalization);
        this.storage.writeToNBT(compound);
        return compound;
    }

    @Override
    public int getField(String id) {
        switch (id) {
            case "energy":
                return this.energy;
            case "crystalTime":
                return this.crystalTime;
            case "totalCrystalTime":
                return this.totalCrystalTime;
            case "crystalization":
                return this.crystalization;
            case "totalCrystalization":
                return this.totalCrystalization;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.energy = value;
                break;
            case 1:
                this.crystalTime = value;
                break;
            case 2:
                this.crystalization = value;
                break;
        }
    }

    // Custom Methods

    public int getMaxEnergyStored() {
        return this.storage.getMaxEnergyStored();
    }

    private void createCrystal() {
        ItemStack crystalOutput = this.outputHandler.getStackInSlot(0);
        ItemStack result = new ItemStack(PotatoItems.CRYSTAL_SHARD);

        if (crystalOutput.isEmpty()) {
            this.outputHandler.setStackInSlot(0, result);
        } else if (crystalOutput.isItemEqual(result)) {
            crystalOutput.grow(result.getCount());
        }
    }

    private void crystalizeItem() {
        ItemStack salt = this.inputHandler.getStackInSlot(0);

        salt.shrink(1);
        this.crystalization += this.totalCrystalTime;
    }

    private boolean canCrystalize() {
        ItemStack iceFuel = this.iceHandler.getStackInSlot(0);
        ItemStack salt = this.inputHandler.getStackInSlot(0);
        ItemStack crystalOutput = this.outputHandler.getStackInSlot(0);

        if (iceFuel.isEmpty() || salt.isEmpty()) {
            return false;

        } else {
            Item ice = Item.getItemFromBlock(Blocks.ICE);
            if (!ice.equals(iceFuel.getItem()) || !salt.getItem().equals(PotatoItems.RAW_SALT)) {
                return false;

            } else {
                if (crystalOutput.isEmpty()) {
                    return true;
                } else {
                    ItemStack result = new ItemStack(PotatoItems.CRYSTAL_SHARD);

                    if (crystalOutput.isItemEqual(result)) {
                        int res = crystalOutput.getCount() + result.getCount();
                        return res <= crystalOutput.getMaxStackSize();
                    } else {
                        return false;
                    }
                }
            }
        }
    }
}
