package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.recipes.SweetInfuserRecipes;
import aldinh777.potatoheadshot.energy.PotatoEnergyStorage;
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

public class TileEntitySweetInfuser extends TileEntityPotatoMachine {

    private final ItemStackHandler inputHandler = new ItemStackHandler(1);
    private final ItemStackHandler outputHandler = new ItemStackHandler(1);
    private final ItemStackHandler fusionHandler = new ItemStackHandler(6);

    private final PotatoEnergyStorage storage = new PotatoEnergyStorage(40000, 40, 0);
    private int energy = this.storage.getEnergyStored();
    private int currentInfuseTime = 0;
    private int totalInfuseTime = 500;

    // Override Methods

    @Override
    public void update() {
        if (!this.world.isRemote) {
            boolean flag = false;

            if (this.canInfuse()) {
                if (this.currentInfuseTime >= this.totalInfuseTime) {
                    infuseItem();
                    this.currentInfuseTime = 0;
                    flag = true;

                } else if (this.storage.getEnergyStored() >= 20) {
                    this.currentInfuseTime++;
                    this.storage.useEnergy(20);
                    flag = true;
                }
            } else if (this.currentInfuseTime > 0) {
                this.currentInfuseTime-=2;
                if (this.currentInfuseTime < 0) {
                    this.currentInfuseTime = 0;
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
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return true;
        }

        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T)this.storage;
        }

        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.UP) {
                return (T)this.inputHandler;
            } else if (facing == EnumFacing.DOWN) {
                return (T)this.outputHandler;
            } else {
                return (T)this.fusionHandler;
            }
        }

        return super.getCapability(capability, facing);
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return customOrDefaultName("Sweet Infuser");
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inputHandler.deserializeNBT(compound.getCompoundTag("InventoryInput"));
        this.outputHandler.deserializeNBT(compound.getCompoundTag("InventoryOutput"));
        this.fusionHandler.deserializeNBT(compound.getCompoundTag("InventoryFusion"));
        this.energy = compound.getInteger("GuiEnergy");
        this.currentInfuseTime = compound.getInteger("CurrentInfuseTime");
        this.totalInfuseTime = compound.getInteger("TotalInfuseTime");
        this.storage.readFromNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("InventoryInput", this.inputHandler.serializeNBT());
        compound.setTag("InventoryOutput", this.outputHandler.serializeNBT());
        compound.setTag("InventoryFusion", this.fusionHandler.serializeNBT());
        compound.setInteger("GuiEnergy", this.energy);
        compound.setInteger("CurrentInfuseTime", (short)this.currentInfuseTime);
        compound.setInteger("TotalInfuseTime", (short)this.totalInfuseTime);
        this.storage.writeToNBT(compound);
        return compound;
    }

    @Override
    public int getField(String id) {
        switch (id) {
            case "energy":
                return this.energy;
            case "totalInfuseTime":
                return this.totalInfuseTime;
            case "currentInfuseTime":
                return this.currentInfuseTime;
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
                this.totalInfuseTime = value;
                break;
            case 2:
                this.currentInfuseTime = value;
                break;
        }
    }

    // Custom Methods

    public int getMaxEnergyStored() {
        return this.storage.getMaxEnergyStored();
    }

    public ItemStackHandler getHandler(String name) {
        switch (name) {
            case "input":
                return this.inputHandler;
            case "output":
                return this.outputHandler;
            case "fusion":
                return this.fusionHandler;
            default:
                return null;
        }
    }

    private boolean canInfuse() {
        ItemStack inputSlot = this.inputHandler.getStackInSlot(0);
        ItemStack outputSlot = this.outputHandler.getStackInSlot(0);
        if (inputSlot.isEmpty()) {
            return false;
        }

        ItemStack[] fusionItems = new ItemStack[6];

        for (int i = 0; i < 6; i++) {
            ItemStack fusionItem = this.fusionHandler.getStackInSlot(i);
            if (fusionItem.isEmpty()) {
                return false;
            }
            fusionItems[i] = fusionItem;
        }

        ItemStack result = SweetInfuserRecipes.INSTANCE.getResult(inputSlot.getItem(), fusionItems);

        if (result.isEmpty()) {
            return false;
        } else if (outputSlot.isEmpty()) {
            return true;
        } else if (result.isItemEqual(outputSlot)) {
            return outputSlot.getCount() < outputSlot.getMaxStackSize();
        }

        return false;
    }

    private void infuseItem() {
        ItemStack inputSlot = this.inputHandler.getStackInSlot(0);
        ItemStack outputSlot = this.outputHandler.getStackInSlot(0);
        ItemStack[] fusionItems = new ItemStack[6];

        for (int i = 0; i < 6; i++) {
            fusionItems[i] = this.fusionHandler.getStackInSlot(i);
        }

        ItemStack infuseResult = SweetInfuserRecipes.INSTANCE.getResult(inputSlot.getItem(), fusionItems);
        ItemStack result = infuseResult.copy();

        if (!outputSlot.isEmpty()) {
            outputSlot.grow(1);
        } else {
            this.outputHandler.setStackInSlot(0, result);
        }

        inputSlot.shrink(1);

        for (int i = 0; i < 6; i++) {
            ItemStack fusionItem = fusionItems[i];
            Item item = fusionItem.getItem();
            ItemStack containerItem = item.getContainerItem(fusionItem);

            fusionItem.shrink(1);

            if (fusionItem.isEmpty()) {
                this.fusionHandler.setStackInSlot(i, containerItem);
            }
        }
    }
}
