package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.item.items.VoidBottle;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityBedrockMiner extends TileEntityPotatoMachine {

    private final ItemStackHandler bottleHandler = new ItemStackHandler(1) {
        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return stack.getItem() == PotatoItems.VOID_BOTTLE ? super.insertItem(slot, stack, simulate) : stack;
        }
    };
    private final ItemStackHandler outputHandler = new ItemStackHandler(1);

    private static final int speed = 8;
    private static final int cost = 128000;
    private int progress = 0;

    // Override Methods

    @Override
    public void update() {
        if (!this.world.isRemote) {
            if (this.canMine()) {
                if (this.progress >= cost) {
                    this.mineBlockUnder();
                    this.progress = 0;
                } else {
                    this.progress += this.progressMining();
                }
            } else if (this.progress > 0) {
                this.progress--;
            }

            this.markDirty();
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.DOWN) {
                return (T)this.outputHandler;
            } else {
                return (T)this.bottleHandler;
            }
        }

        return super.getCapability(capability, facing);
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return customOrDefaultName("Bedrock Miner");
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.bottleHandler.deserializeNBT(compound.getCompoundTag("InventoryBottle"));
        this.outputHandler.deserializeNBT(compound.getCompoundTag("InventoryOutput"));
        this.progress = compound.getInteger("Progress");
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("InventoryBottle", this.bottleHandler.serializeNBT());
        compound.setTag("InventoryOutput", this.outputHandler.serializeNBT());
        compound.setInteger("Progress", this.progress);
        return compound;
    }

    @Override
    public int getField(String id) {
        switch (id) {
            case "progress":
                return this.progress;
            case "cost":
                return cost;
        }
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        if (id == 0) {
            this.progress = value;
        }
    }

    // Custom Methods

    private boolean canMine() {
        ItemStack bottle = this.bottleHandler.getStackInSlot(0);
        ItemStack output = this.outputHandler.getStackInSlot(0);

        if (bottle.isEmpty() || !output.isEmpty()) {
            return false;
        } else if (bottle.getItem() != PotatoItems.VOID_BOTTLE) {
            return false;
        } else if (VoidBottle.getVoidSize(bottle) <= 0) {
            return false;
        } else {
            IBlockState under = this.world.getBlockState(this.pos.down());
            float hardness = under.getBlockHardness(this.world, this.pos.down());

            return hardness < 0f;
        }
    }

    private void mineBlockUnder() {
        IBlockState under = this.world.getBlockState(this.pos.down());
        ItemStack result = new ItemStack(under.getBlock());

        this.outputHandler.setStackInSlot(0, result);
        this.world.setBlockState(this.pos.down(), Blocks.AIR.getDefaultState());
    }

    private int progressMining() {
        ItemStack bottle = this.bottleHandler.getStackInSlot(0);
        int voidSize = VoidBottle.getVoidSize(bottle);

        if (voidSize > 0) {
            if (voidSize >= speed) {
                VoidBottle.setVoidSize(bottle, voidSize - speed);
                return speed;
            } else {
                VoidBottle.setVoidSize(bottle, 0);
                return voidSize;
            }
        }

        return 0;
    }
}
