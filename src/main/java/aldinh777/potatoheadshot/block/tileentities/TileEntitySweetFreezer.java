package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.energy.PotatoEnergyStorage;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.recipes.recipe.SweetFreezerRecipe;
import net.minecraft.init.Items;
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

public class TileEntitySweetFreezer extends TileEntityPotatoMachine {

    private final ItemStackHandler saltHandler = new ItemStackHandler(1) {
        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return stack.getItem() == PotatoItems.RAW_SALT ? super.insertItem(slot, stack, simulate) : stack;
        }
    };
    private final ItemStackHandler inputHandler = new ItemStackHandler(1);
    private final ItemStackHandler outputHandler = new ItemStackHandler(1);

    private final PotatoEnergyStorage storage = new PotatoEnergyStorage(40000, 40, 0);
    private int energy = this.storage.getEnergyStored();
    private int currentFreezeTime = 0;
    private int totalFreezeTime = 200;

    // Override Methods

    @Override
    public void update() {
        if (!this.world.isRemote) {
            boolean flag = false;

            if (this.canFreeze()) {
                if (this.currentFreezeTime >= this.totalFreezeTime) {
                    ItemStack salt = this.saltHandler.getStackInSlot(0);
                    this.freezeItem();
                    this.currentFreezeTime = 0;
                    salt.shrink(1);
                    flag = true;

                } else if (this.storage.getEnergyStored() >= 20) {
                    this.currentFreezeTime++;
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
        if (capability == CapabilityEnergy.ENERGY) return true;
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) this.storage;
        }

        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.UP) {
                return (T)this.inputHandler;
            } else if (facing == EnumFacing.DOWN) {
                return (T)this.outputHandler;
            } else {
                return (T)this.saltHandler;
            }
        }

        return super.getCapability(capability, facing);
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return customOrDefaultName("Sweet Freezer");
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.saltHandler.deserializeNBT(compound.getCompoundTag("InventorySalt"));
        this.inputHandler.deserializeNBT(compound.getCompoundTag("InventoryInput"));
        this.outputHandler.deserializeNBT(compound.getCompoundTag("InventoryOutput"));
        this.energy = compound.getInteger("GuiEnergy");
        this.currentFreezeTime = compound.getInteger("CurrentFreezeTime");
        this.totalFreezeTime = compound.getInteger("TotalFreezeTime");
        this.storage.readFromNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("InventoryInput", this.inputHandler.serializeNBT());
        compound.setTag("InventorySalt", this.saltHandler.serializeNBT());
        compound.setTag("InventoryOutput", this.outputHandler.serializeNBT());
        compound.setInteger("GuiEnergy", this.energy);
        compound.setInteger("CurrentFreezeTime", (short)this.currentFreezeTime);
        compound.setInteger("TotalFreezeTime", (short)this.totalFreezeTime);
        this.storage.writeToNBT(compound);
        return compound;
    }

    @Override
    public int getField(String id) {
        switch (id) {
            case "energy":
                return this.energy;
            case "totalFreezeTime":
                return this.totalFreezeTime;
            case "currentFreezeTime":
                return this.currentFreezeTime;
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
                this.totalFreezeTime = value;
                break;
            case 2:
                this.currentFreezeTime = value;
                break;
        }
    }

    // Custom Methods

    public int getMaxEnergyStored() {
        return this.storage.getMaxEnergyStored();
    }

    private boolean canFreeze() {
        ItemStack salt = this.saltHandler.getStackInSlot(0);
        ItemStack freezeInput = this.inputHandler.getStackInSlot(0);
        ItemStack freezeOutput = this.outputHandler.getStackInSlot(0);

        if (salt.isEmpty() || freezeInput.isEmpty()) {
            return false;

        } else {
            ItemStack result = getResult(freezeInput);

            if (result.isEmpty()) {
                return false;
            } else {
                if (freezeOutput.isEmpty()) {
                    return true;
                } else {
                    if (freezeOutput.isItemEqual(result)) {
                        int res = freezeOutput.getCount() + result.getCount();
                        return res <= freezeOutput.getMaxStackSize();
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    private void freezeItem() {
        ItemStack freezeInput = this.inputHandler.getStackInSlot(0);
        ItemStack freezeOutput = this.outputHandler.getStackInSlot(0);
        ItemStack result = getResult(freezeInput);

        if (freezeOutput.isEmpty()) {
            this.outputHandler.setStackInSlot(0, result.copy());
        } else if (freezeOutput.isItemEqual(result)) {
            freezeOutput.grow(result.getCount());
        }

        if (freezeInput.getItem() == Items.WATER_BUCKET) {
            this.inputHandler.setStackInSlot(0, new ItemStack(Items.BUCKET));
        } else if (freezeInput.getItem() == PotatoItems.SWEET_WATER_BUCKET) {
            this.inputHandler.setStackInSlot(0, new ItemStack(PotatoItems.SWEET_EMPTY_BUCKET));
        } else {
            freezeInput.shrink(1);
        }
    }

    // Static Methods

    public static ItemStack getResult(ItemStack stack) {
        for (SweetFreezerRecipe recipe :  SweetFreezerRecipe.getRecipes()) {
            if (stack.getItem() == recipe.getInput()) return recipe.getOutput();
        }
        return ItemStack.EMPTY;
    }
}
