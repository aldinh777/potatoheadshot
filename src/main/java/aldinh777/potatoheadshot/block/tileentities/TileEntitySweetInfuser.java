package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.recipes.SweetInfuserRecipes;
import aldinh777.potatoheadshot.energy.PotatoEnergyStorage;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntitySweetInfuser extends TileEntityPotatoMachine {

    private final ItemStackHandler inputHandler = new ItemStackHandler(6);
    private final ItemStackHandler middleHandler = new ItemStackHandler(1);

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
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T)this.storage;
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
        this.middleHandler.deserializeNBT(compound.getCompoundTag("InventoryMiddle"));
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
        compound.setTag("InventoryMiddle", this.middleHandler.serializeNBT());
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
            case "middle":
                return this.middleHandler;
            default:
                return null;
        }
    }

    private boolean canInfuse() {
        ItemStack middle = this.middleHandler.getStackInSlot(0);
        if (middle.isEmpty()) {
            return false;
        }

        ItemStack[] inputs = new ItemStack[6];

        for (int i = 0; i < 6; i++) {
            ItemStack input = this.inputHandler.getStackInSlot(i);
            if (input.isEmpty()) {
                return false;
            }
            inputs[i] = input;
        }

        ItemStack result = SweetInfuserRecipes.INSTANCE.getResult(middle.getItem(), inputs);

        return !result.isEmpty();
    }

    private void infuseItem() {
        ItemStack middle = this.middleHandler.getStackInSlot(0);
        ItemStack[] inputs = new ItemStack[6];

        for (int i = 0; i < 6; i++) {
            inputs[i] = this.inputHandler.getStackInSlot(i);
        }

        ItemStack infuseResult = SweetInfuserRecipes.INSTANCE.getResult(middle.getItem(), inputs);
        ItemStack result = infuseResult.copy();

        if (middle.getCount() > 1) {
            EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.75, pos.getZ() + 0.5, result);
            this.world.spawnEntity(item);
            middle.shrink(1);
        } else {
            this.middleHandler.setStackInSlot(0, result);
        }

        for (int i = 0; i < 6; i++) {
            if (inputs[i].getItem() == Items.WATER_BUCKET || inputs[i].getItem() == Items.LAVA_BUCKET) {
                this.inputHandler.setStackInSlot(i, new ItemStack(Items.BUCKET));
            } else if (inputs[i].getItem() == PotatoItems.SWEET_WATER_BUCKET) {
                this.inputHandler.setStackInSlot(i, new ItemStack(PotatoItems.SWEET_EMPTY_BUCKET));
            } else if (inputs[i].getItem() == PotatoItems.SWEET_LAVA_BUCKET) {
                this.inputHandler.setStackInSlot(i, new ItemStack(PotatoItems.SWEET_EMPTY_BUCKET));
            } else {
                inputs[i].shrink(1);
            }
        }
    }
}
