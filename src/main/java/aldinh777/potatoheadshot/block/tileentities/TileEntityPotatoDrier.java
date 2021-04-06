package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.blocks.machines.PotatoDrier;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.recipes.category.PotatoDrierRecipes;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.recipes.recipe.PotatoDrierRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityPotatoDrier extends TileEntityPotatoMachine {

    private final ItemStackHandler fuelHandler = new ItemStackHandler(1) {
        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return isItemFuel(stack) ? super.insertItem(slot, stack, simulate) : stack;
        }
    };
    private final ItemStackHandler inputHandler = new ItemStackHandler(2);
    private final ItemStackHandler outputHandler = new ItemStackHandler(2);

    private int waterSize;
    private int wateringTime;
    private int burnTime;
    private int currentBurnTime;
    private int currentWateringTime;

    private int dryTime;
    private int wetTime;
    private int totalDryTime = 200;
    private int totalWetTime = 1000;

    // Override Methods

    @Override
    public void update() {
        boolean flagBurning = this.isBurning();
        boolean dryingFlag = false;
        boolean wettingFlag = false;

        if (this.isBurning()) {
            --this.burnTime;
        }
        if (this.isWatering()) {
            --this.wateringTime;
        }

        if (!this.world.isRemote) {
            ItemStack fuel = this.fuelHandler.getStackInSlot(0);
            ItemStack dryInput = this.inputHandler.getStackInSlot(0);
            ItemStack wetInput = this.inputHandler.getStackInSlot(1);

            this.checkBurn();

            if (tryDrying(fuel, dryInput)) {
                flagBurning = true;
            }

            if (tryWetting(wetInput)) {
                wettingFlag = true;
            }

            if (flagBurning != this.isBurning()) {
                dryingFlag = true;
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
            } else {
                return (T)this.fuelHandler;
            }
        }

        return super.getCapability(capability, facing);
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return this.customOrDefaultName("Potato Drier");
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.fuelHandler.deserializeNBT(compound.getCompoundTag("InventoryFuel"));
        this.inputHandler.deserializeNBT(compound.getCompoundTag("InventoryInput"));
        this.outputHandler.deserializeNBT(compound.getCompoundTag("InventoryOutput"));
        this.waterSize = compound.getInteger("WaterVolume");
        this.burnTime = compound.getInteger("BurnTime");
        this.wateringTime = compound.getInteger("WateringTime");
        this.dryTime = compound.getInteger("DryTime");
        this.wetTime = compound.getInteger("WetTime");
        this.totalDryTime = compound.getInteger("DryTimeTotal");
        this.totalWetTime = compound.getInteger("WetTimeTotal");
        this.currentBurnTime = getItemBurnTime(this.fuelHandler.getStackInSlot(0));
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("InventoryFuel", this.fuelHandler.serializeNBT());
        compound.setTag("InventoryInput", this.inputHandler.serializeNBT());
        compound.setTag("InventoryOutput", this.outputHandler.serializeNBT());
        compound.setInteger("WaterVolume", (short)this.waterSize);
        compound.setInteger("BurnTime", (short)this.burnTime);
        compound.setInteger("WateringTime", (short)this.wateringTime);
        compound.setInteger("DryTime", (short)this.dryTime);
        compound.setInteger("WetTime", (short)this.wetTime);
        compound.setInteger("DryTimeTotal", (short)this.totalDryTime);
        compound.setInteger("WetTimeTotal", (short)this.totalWetTime);

        return compound;
    }

    @Override
    public int getField(String id) {
        switch(id) {
            case "waterSize":
                return this.waterSize;
            case "burnTime":
                return this.burnTime;
            case "wateringTime":
                return this.wateringTime;
            case "dryTime":
                return this.dryTime;
            case "wetTime":
                return this.wetTime;
            case "currentBurnTime":
                return this.currentBurnTime;
            case "currentWateringTime":
                return this.currentWateringTime;
            case "totalDryTime":
                return this.totalDryTime;
            case "totalWetTime":
                return this.totalWetTime;
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
                this.burnTime = value;
                break;
            case 2:
                this.wateringTime = value;
                break;
            case 3:
                this.dryTime = value;
                break;
            case 4:
                this.wetTime = value;
                break;
            case 5:
                this.currentBurnTime = value;
                break;
            case 6:
                this.currentWateringTime = value;
                break;
            case 7:
                this.totalDryTime = value;
                break;
            case 8:
                this.totalWetTime = value;
                break;
        }
    }

    // Custom Method

    public int getMaxWaterSize() {
        return 4000;
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    public boolean isWatering() {
        return this.wateringTime > 0;
    }

    private boolean canDry() {
        ItemStack fuel = this.fuelHandler.getStackInSlot(0);
        ItemStack dryInput = this.inputHandler.getStackInSlot(0);
        ItemStack dryOutput = this.outputHandler.getStackInSlot(0);

        if (fuel.isEmpty() && dryInput.isEmpty()) {
            return false;

        } else {
            PotatoDrierRecipe recipeResult = PotatoDrierRecipes.INSTANCE.getDryResult(dryInput.getItem());
            ItemStack result = recipeResult.getOutput();

            if (result.isEmpty()) {
                return false;

            } else {
                if (dryOutput.isEmpty()) {
                    return true;

                } else {
                    if (dryOutput.isItemEqual(result)) {
                        int res = dryOutput.getCount() + result.getCount();
                        return res <= dryOutput.getMaxStackSize();

                    } else {
                        return false;
                    }
                }
            }
        }
    }

    private boolean canWet() {
        ItemStack wetInput = this.inputHandler.getStackInSlot(1);
        ItemStack wetOutput = this.outputHandler.getStackInSlot(1);

        if (this.waterSize < 1000 || wetInput.isEmpty()) {
            return false;
        }

        PotatoDrierRecipe recipeResult = PotatoDrierRecipes.INSTANCE.getWetResult(wetInput.getItem());
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
        if (this.canDry()) {
            ItemStack dryInput = this.inputHandler.getStackInSlot(0);
            ItemStack dryOutput = this.outputHandler.getStackInSlot(0);
            PotatoDrierRecipe recipeResult = PotatoDrierRecipes.INSTANCE.getDryResult(dryInput.getItem());
            ItemStack result = recipeResult.getOutput();

            if (dryOutput.isEmpty()) {
                this.outputHandler.setStackInSlot(0, result.copy());
            }
            else if (dryOutput.getItem() == result.getItem()) {
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
    }

    private void wetItem() {
        if (this.canWet()) {
            ItemStack wetInput = this.inputHandler.getStackInSlot(1);
            ItemStack wetOutput = this.outputHandler.getStackInSlot(1);
            PotatoDrierRecipe recipeResult = PotatoDrierRecipes.INSTANCE.getWetResult(wetInput.getItem());
            ItemStack result = recipeResult.getOutput();

            if (wetOutput.isEmpty()) {
                this.outputHandler.setStackInSlot(1, result.copy());
            } else if (wetOutput.getItem() == result.getItem()) {
                wetOutput.grow(result.getCount());
            }

            wetInput.shrink(1);
        }
    }

    private boolean tryDrying(ItemStack fuel, ItemStack dryInput) {
        boolean dryingFlag = false;

        if (this.isBurning() || !fuel.isEmpty() && !dryInput.isEmpty()) {
            if (!this.isBurning() && this.canDry()) {
                this.burnTime = getItemBurnTime(fuel);
                this.currentBurnTime = this.burnTime;

                if (this.isBurning()) {
                    dryingFlag = true;

                    if (!fuel.isEmpty()) {
                        Item item = fuel.getItem();
                        fuel.shrink(1);

                        if (fuel.isEmpty()) {
                            ItemStack containerItem = item.getContainerItem(fuel);
                            this.fuelHandler.setStackInSlot(0, containerItem);
                        }
                    }
                }
            }

            if (this.isBurning() && this.canDry()) {
                ++this.dryTime;

                if (this.dryTime == this.totalDryTime) {
                    this.dryTime = 0;
                    this.totalDryTime = 200;
                    this.dryItem();
                    dryingFlag = true;
                }
            }
            else {
                this.dryTime = 0;
            }

        }
        else if (!this.isBurning() && this.dryTime > 0) {
            this.dryTime = MathHelper.clamp(this.dryTime - 2, 0, this.totalDryTime);
        }

        return dryingFlag;
    }

    private boolean tryWetting(ItemStack wetInput) {
        boolean wettingFlag = false;

        if (this.isWatering() || this.waterSize >= 1000 && !wetInput.isEmpty()) {
            if (!this.isWatering() && this.canWet()) {
                this.wateringTime = 1000;
                this.currentWateringTime = this.wateringTime;

                if (this.isWatering()) {
                    wettingFlag = true;

                    if (this.waterSize >= 1000) {
                        this.waterSize -= 1000;
                    }
                }
            }

            if (this.isWatering() && this.canWet()) {
                ++this.wetTime;

                if (this.wetTime == this.totalWetTime) {
                    this.wetTime = 0;
                    this.totalWetTime = 1000;
                    this.wetItem();
                    wettingFlag = true;
                }
            }
            else {
                this.wetTime = 0;
            }
        }
        else if (!this.isWatering() && this.wetTime > 0) {
            this.wetTime = MathHelper.clamp(this.wetTime - 2, 0, this.totalWetTime);
        }

        return wettingFlag;
    }

    // Static Method

    public static boolean isBurning(TileEntityPotatoDrier te) {
        return te.getField("burnTime") > 0;
    }

    public static boolean isWatering(TileEntityPotatoDrier te) {
        return te.getField("wateringTime") > 0;
    }

    public static int getItemBurnTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            Item item = fuel.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.WOODEN_SLAB) return 150;
                if (block.getDefaultState().getMaterial() == Material.WOOD) return 300;
                if (block == Blocks.COAL_BLOCK) return 16000;
            }

            if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())) return 200;
            if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())) return 200;
            if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())) return 200;
            if (item == Items.STICK) return 100;
            if (item == Items.COAL) return 1600;
            if (item == Items.LAVA_BUCKET) return 20000;
            if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
            if (item == Items.BLAZE_ROD) return 2400;

            return ForgeEventFactory.getItemBurnTime(fuel);
        }
    }

    private void checkBurn() {
        IBlockState state = this.world.getBlockState(this.pos);

        if (state.getBlock() != PotatoBlocks.POTATO_DRIER) {
            return;
        }

        boolean active = state.getValue(PotatoDrier.ACTIVE);

        if (active && !this.isBurning()) {
            setState(false, this.world, this.pos);
        } else if (!active && isBurning()) {
            setState(true, this.world, this.pos);
        }
    }

    private void setState(boolean active, World worldIn, BlockPos pos) {
        IBlockState newState = this.world.getBlockState(this.pos)
                .withProperty(PotatoDrier.ACTIVE, active);

        worldIn.setBlockState(pos, newState, 3);
    }

    public static boolean isItemFuel(ItemStack fuel) {
        return getItemBurnTime(fuel) > 0;
    }
}
