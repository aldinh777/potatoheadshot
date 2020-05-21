package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityManaCollector extends TileEntityPotatoMachine {

    protected final ItemStackHandler inputHandler = new ItemStackHandler(1);
    protected final ItemStackHandler outputHandler = new ItemStackHandler(1);

    protected final int manaMaxSize = 60000;
    protected int manaSize = 0;
    protected int currentTick = 0;

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

        if (this.manaSize > this.manaMaxSize) {
            this.manaSize = this.manaMaxSize;
        }
    }

    private boolean canFuse() {
        ItemStack input = this.inputHandler.getStackInSlot(0);
        if (input.isEmpty()) {
            return false;
        }

        int manaValue = getManaValue(input);

        if (manaValue <= 0 || this.manaSize < manaValue) {
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

        if (this.manaSize < 0) {
            this.manaSize = 0;
        }
    }

    public ItemStack getResult(ItemStack stack) {
        if (stack.getItem() == PotatoItems.GLOWING_POTATO_DUST) {
            return new ItemStack(PotatoItems.GLOWING_MANA_DUST);
        } else if (stack.getItem() == Item.getItemFromBlock(Blocks.RED_FLOWER)) {
            return new ItemStack(PotatoBlocks.GLOWING_MANA_FLOWER);
        } else if (stack.getItem() == Item.getItemFromBlock(Blocks.TORCH)) {
            return new ItemStack(PotatoBlocks.GLOWING_MANA_TORCH);
        }
        return ItemStack.EMPTY;
    }

    // Static Methods

    private static int getManaValue(ItemStack stack) {
        if (stack.getItem() == PotatoItems.GLOWING_POTATO_DUST) return 1000;
        if (stack.getItem() == Item.getItemFromBlock(Blocks.RED_FLOWER)) return 1000;
        if (stack.getItem() == Item.getItemFromBlock(Blocks.TORCH)) return 1000;
        return 0;
    }
}
