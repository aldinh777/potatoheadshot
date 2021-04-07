package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.blocks.machines.PotatoDrier;
import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.recipes.category.PotatoDrierRecipes;
import aldinh777.potatoheadshot.recipes.recipe.PotatoDrierRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityMagicDrier extends TileEntityPotatoMachine implements IManaStorage {

    private final PotatoManaStorage storage = new PotatoManaStorage(80_000);

    private final ItemStackHandler inputHandler = new ItemStackHandler(2);
    private final ItemStackHandler outputHandler = new ItemStackHandler(2);

    private int stateChangeTime = 0;

    private int totalDryTime = 100;
    private int totalWetTime = 500;
    private int guiMana;
    private int waterSize;
    private int dryTime;
    private int wetTime;

    @Override
    public void update() {
        boolean dryingFlag = false;
        boolean wettingFlag = false;

        if (!this.world.isRemote) {
            dryingFlag = this.tryDrying();
            wettingFlag = this.tryWetting();

            this.checkBurn();

            if (this.guiMana != this.storage.getManaStored()) {
                this.guiMana = this.storage.getManaStored();
            }
        }

        if (dryingFlag || wettingFlag) {
            this.markDirty();
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
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
        return this.customOrDefaultName("Magic Drier");
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.storage.readFromNBT(compound);
        this.inputHandler.deserializeNBT(compound.getCompoundTag("InventoryInput"));
        this.outputHandler.deserializeNBT(compound.getCompoundTag("InventoryOutput"));
        this.waterSize = compound.getInteger("WaterVolume");
        this.dryTime = compound.getInteger("DryTime");
        this.wetTime = compound.getInteger("WetTime");
        this.totalDryTime = compound.getInteger("DryTimeTotal");
        this.totalWetTime = compound.getInteger("WetTimeTotal");
        this.guiMana = compound.getInteger("GuiMana");
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.storage.writeToNBT(compound);
        compound.setTag("InventoryInput", this.inputHandler.serializeNBT());
        compound.setTag("InventoryOutput", this.outputHandler.serializeNBT());
        compound.setInteger("WaterVolume", (short)this.waterSize);
        compound.setInteger("DryTime", (short)this.dryTime);
        compound.setInteger("WetTime", (short)this.wetTime);
        compound.setInteger("DryTimeTotal", (short)this.totalDryTime);
        compound.setInteger("WetTimeTotal", (short)this.totalWetTime);
        compound.setInteger("GuiMana", this.guiMana);

        return compound;
    }

    @Override
    public int getField(String id) {
        switch(id) {
            case "waterSize":
                return this.waterSize;
            case "dryTime":
                return this.dryTime;
            case "wetTime":
                return this.wetTime;
            case "totalDryTime":
                return this.totalDryTime;
            case "totalWetTime":
                return this.totalWetTime;
            case "mana":
                return this.guiMana;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.waterSize = value;
                break;
            case 1:
                this.dryTime = value;
                break;
            case 2:
                this.wetTime = value;
                break;
            case 3:
                this.totalDryTime = value;
                break;
            case 4:
                this.totalWetTime = value;
                break;
            case 5:
                this.guiMana = value;
                break;
        }
    }

    @Override
    public PotatoManaStorage getManaStorage() {
        return this.storage;
    }

    // Custom Methods

    public int getMaxManaStored() {
        return this.storage.getMaxManaStored();
    }

    public int getMaxWaterSize() {
        return 8000;
    }

    private boolean canDry() {
        ItemStack dryInput = this.inputHandler.getStackInSlot(0);
        ItemStack dryOutput = this.outputHandler.getStackInSlot(0);

        if (dryInput.isEmpty() || this.storage.getManaStored() <= 0) {
            return false;
        }

        PotatoDrierRecipe recipeResult = PotatoDrierRecipes.INSTANCE.getDryResult(dryInput);
        ItemStack result = recipeResult.getOutput();

        if (result.isEmpty()) {
            return false;
        }

        if (dryOutput.isEmpty()) {
            return true;
        }

        if (dryOutput.isItemEqual(result)) {
            int res = dryOutput.getCount() + result.getCount();
            return res <= dryOutput.getMaxStackSize();
        } else {
            return false;
        }
    }

    private boolean canWet() {
        ItemStack wetInput = this.inputHandler.getStackInSlot(1);
        ItemStack wetOutput = this.outputHandler.getStackInSlot(1);

        if (this.waterSize < 1000) {
            return false;
        }

        if (wetInput.isEmpty()) {
            return false;
        }

        PotatoDrierRecipe recipeResult = PotatoDrierRecipes.INSTANCE.getWetResult(wetInput);
        ItemStack result = recipeResult.getOutput();

        if (result.isEmpty()) {
            return false;
        }

        if (wetOutput.isEmpty()) {
            return true;
        }

        if (wetOutput.isItemEqual(result)) {
            int res = wetOutput.getCount() + result.getCount();
            return res <= wetOutput.getMaxStackSize();
        } else {
            return false;
        }
    }

    private void dryItem() {
        ItemStack dryInput = this.inputHandler.getStackInSlot(0);
        ItemStack dryOutput = this.outputHandler.getStackInSlot(0);
        PotatoDrierRecipe recipeResult = PotatoDrierRecipes.INSTANCE.getDryResult(dryInput);
        ItemStack result = recipeResult.getOutput();

        if (dryOutput.isEmpty()) {
            this.outputHandler.setStackInSlot(0, result.copy());
        } else if (dryOutput.getItem() == result.getItem()) {
            dryOutput.grow(result.getCount());
        }

        this.waterSize += recipeResult.getWaterValue();
        if (this.waterSize > this.getMaxWaterSize()) {
            this.waterSize = this.getMaxWaterSize();
        }

        if (dryInput.getItem() == Items.WATER_BUCKET) {
            this.inputHandler.setStackInSlot(0, new ItemStack(Items.BUCKET));
        } else if (dryInput.getItem() == PotatoItems.SWEET_WATER_BUCKET) {
            this.inputHandler.setStackInSlot(0, new ItemStack(PotatoItems.SWEET_EMPTY_BUCKET));
        } else {
            dryInput.shrink(1);
        }
    }

    private void wetItem() {
        ItemStack wetInput = this.inputHandler.getStackInSlot(1);
        ItemStack wetOutput = this.outputHandler.getStackInSlot(1);
        PotatoDrierRecipe recipeResult = PotatoDrierRecipes.INSTANCE.getWetResult(wetInput);
        ItemStack result = recipeResult.getOutput();

        if (wetOutput.isEmpty()) {
            this.outputHandler.setStackInSlot(1, result.copy());
        } else if (wetOutput.getItem() == result.getItem()) {
            wetOutput.grow(result.getCount());
        }

        wetInput.shrink(1);
    }

    private boolean tryDrying() {
        boolean dryingFlag = false;

        if (this.canDry()) {
            this.storage.useMana(10);
            ++this.dryTime;

            if (this.dryTime == this.totalDryTime) {
                this.dryTime = 0;
                this.dryItem();
                dryingFlag = true;
            }
        } else if (this.dryTime > 0) {
            this.dryTime = 0;
        }

        return dryingFlag;
    }

    private boolean tryWetting() {
        boolean wettingFlag = false;

        if (this.canWet()) {
            this.waterSize-=2;
            ++this.wetTime;

            if (this.wetTime == this.totalWetTime) {
                this.wetTime = 0;
                this.wetItem();
                wettingFlag = true;
            }
        } else if (this.wetTime > 0) {
            this.wetTime = 0;
        }

        return wettingFlag;
    }

    private void checkBurn() {
        IBlockState state = this.world.getBlockState(this.pos);

        if (state.getBlock() != PotatoBlocks.MAGIC_DRIER) {
            return;
        }

        boolean active = state.getValue(PotatoDrier.ACTIVE);
        int stateChangeDelay = 10;

        if (active && !(this.dryTime > 0)) {
            ++this.stateChangeTime;
            if (this.stateChangeTime == stateChangeDelay) {
                setState(false, this.world, this.pos);
                this.stateChangeTime = 0;
            }
        } else if (!active && this.dryTime > 0) {
            ++this.stateChangeTime;
            if (this.stateChangeTime == stateChangeDelay) {
                setState(true, this.world, this.pos);
                this.stateChangeTime = 0;
            }
        }
    }

    private void setState(boolean active, World worldIn, BlockPos pos) {
        IBlockState newState = this.world.getBlockState(this.pos)
                .withProperty(PotatoDrier.ACTIVE, active);

        worldIn.setBlockState(pos, newState, 3);
    }
}
