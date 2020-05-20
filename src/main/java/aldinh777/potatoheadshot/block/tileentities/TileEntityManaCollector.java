package aldinh777.potatoheadshot.block.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityManaCollector extends TileEntityPotatoMachine {

    private final ItemStackHandler inputHandler = new ItemStackHandler(1);
    private final ItemStackHandler outputHandler = new ItemStackHandler(1);

    private final int manaMaxSize = 60000;
    private int manaSize = 0;
    private int currentTick = 0;

    // Override Methods

    @Override
    public void update() {
        if (!world.isRemote) {
            collectMana();

            if (canFuse()) {
                fuseItemWithMana();
            }

            markDirty();
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
                return true;
            }
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.UP) {
                return (T)this.inputHandler;
            } else if (facing == EnumFacing.DOWN) {
                return (T)this.outputHandler;
            }
        }
        return super.getCapability(capability, facing);
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return customOrDefaultName("Mana Collector");
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inputHandler.deserializeNBT(compound.getCompoundTag("InventoryInput"));
        this.outputHandler.deserializeNBT(compound.getCompoundTag("InventoryOutput"));
        this.manaSize = compound.getInteger("ManaVolume");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("InventoryInput", this.inputHandler.serializeNBT());
        compound.setTag("InventoryOutput", this.outputHandler.serializeNBT());
        compound.setInteger("ManaVolume", this.manaSize);
        return compound;
    }

    @Override
    public int getField(String id) {
        switch (id) {
            case "manaSize":
                return this.manaSize;
            case "manaMaxSize":
                return this.manaMaxSize;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        if (id == 0) {
            this.manaSize = value;
        }
    }

    // Custom Methods

    private void collectMana() {
        ++this.currentTick;
        if (currentTick >= 1 && this.manaSize < this.manaMaxSize) {
            ++this.manaSize;
            this.currentTick = 0;
        }
    }

    private boolean canFuse() {
        ItemStack input = this.inputHandler.getStackInSlot(0);
        if (input.isEmpty()) {
            return false;
        }

        int manaValue = getManaValue(input);

        if (this.manaSize < manaValue) {
            return false;
        }

        ItemStack output = this.outputHandler.getStackInSlot(0);
        ItemStack result = getResult(input);

        if (output.isEmpty()) {
            return true;
        } else if (output.getItem().equals(result.getItem())) {
            return output.getCount() < output.getMaxStackSize();
        } else {
            return false;
        }
    }

    private void fuseItemWithMana() {
        ItemStack input = this.inputHandler.getStackInSlot(0);
        ItemStack output = this.outputHandler.getStackInSlot(0);
        ItemStack result = getResult(input);
        int manaValue = getManaValue(input);

        if (output.isEmpty()) {
            this.outputHandler.setStackInSlot(0, result);
        } else if (output.getItem().equals(result.getItem())) {
            if (output.getCount() < output.getMaxStackSize()) {
                output.grow(1);
            }
        }

        this.manaSize -= manaValue;
        input.shrink(1);
    }

    // Static Methods

    private static int getManaValue(ItemStack stack) {
        return 0;
    }

    public static ItemStack getResult(ItemStack stack) {
        return ItemStack.EMPTY;
    }
}
