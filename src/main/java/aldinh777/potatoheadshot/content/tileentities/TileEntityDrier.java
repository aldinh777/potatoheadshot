package aldinh777.potatoheadshot.content.tileentities;

import aldinh777.potatoheadshot.content.blocks.machines.BlockDrier;
import aldinh777.potatoheadshot.content.capability.DrierWaterCapability;
import aldinh777.potatoheadshot.content.inventory.InventoryDrier;
import aldinh777.potatoheadshot.content.inventory.InventoryDrierUpgrade;
import aldinh777.potatoheadshot.content.items.UpgradeDrierWater;
import aldinh777.potatoheadshot.other.lists.PotatoItems;
import aldinh777.potatoheadshot.other.recipes.category.PotatoDrierRecipes;
import aldinh777.potatoheadshot.other.recipes.recipe.PotatoDrierRecipe;
import aldinh777.potatoheadshot.other.util.FuelHelper;
import aldinh777.potatoheadshot.other.util.InventoryHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityDrier extends AbstractMachine {

    private final InventoryDrier inventoryDrier = new InventoryDrier();
    private final InventoryDrierUpgrade inventoryUpgradeDrier = new InventoryDrierUpgrade();
    private final DrierWaterCapability waterCapability = new DrierWaterCapability();

    public int activeStateLimit = 0;

    public int burnProgress = 0;
    public int burnTime = 0;
    public int dryProgress = 0;
    public int maxDryProgress = 200;

    public int waterVolume = 0;
    public int maxWaterVolume = 8000;

    public int waterProgress = 0;
    public int maxWaterProgress = 1000;

    public InventoryDrierUpgrade getUpgrade() {
        return inventoryUpgradeDrier;
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString("Potato Drier");
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        } else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null) {
                return (T) inventoryDrier;
            } else if (facing == EnumFacing.UP) {
                return (T) inventoryDrier.getInputHandler();
            } else if (facing == EnumFacing.DOWN) {
                return (T) inventoryDrier.getOutputHandler();
            } else {
                return (T) inventoryDrier.getFuelHandler();
            }
        } else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) waterCapability;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventoryDrier.deserializeNBT(compound.getCompoundTag("Inventory"));
        inventoryUpgradeDrier.deserializeNBT(compound.getCompoundTag("Upgrade"));
        burnProgress = compound.getInteger("BurnProgress");
        burnTime = compound.getInteger("BurnTime");
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Inventory", inventoryDrier.serializeNBT());
        compound.setTag("Upgrade", inventoryUpgradeDrier.serializeNBT());
        compound.setInteger("BurnProgress", burnProgress);
        compound.setInteger("BurnTime", burnTime);
        return compound;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            updateState();

            applyBooster();
            updateGuiVariable();

            if (isBurning()) {
                progressDryByCoal();
            } else {
                burnFuel();
            }

            if (isWaterEnough()) {
                progressWater();
            } else if (waterProgress > 0) {
                waterProgress--;
            }

            if (dryProgress >= maxDryProgress) {
                dryItem();
            }
            if (waterProgress >= maxWaterProgress) {
                waterItem();
            }
        }

        markDirty();
    }

    public boolean isBurning() {
        return burnTime > 0;
    }

    private void applyBooster() {
        int maxDryProgress;
        int maxWaterProgress;
        switch (inventoryUpgradeDrier.getBoosterLevel()) {
            case 1:
                maxDryProgress = 100;
                maxWaterProgress = 500;
                break;
            case 2:
                maxDryProgress = 50;
                maxWaterProgress = 250;
                break;
            default:
                maxDryProgress = 200;
                maxWaterProgress = 1000;
                break;
        }
        if (this.maxDryProgress != maxDryProgress || this.maxWaterProgress != maxWaterProgress) {
            this.maxDryProgress = maxDryProgress;
            this.maxWaterProgress = maxWaterProgress;
        }
    }

    private void updateGuiVariable() {
        if (hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP)) {
            IFluidHandler fluidHandler = getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
            if (fluidHandler instanceof UpgradeDrierWater.WaterCapability) {
                UpgradeDrierWater.WaterCapability waterHandler = (UpgradeDrierWater.WaterCapability) fluidHandler;
                FluidStack fluidStack = waterHandler.getFluid();
                if (fluidStack != null) {
                    if (waterVolume != fluidStack.amount) {
                        waterVolume = fluidStack.amount;
                    }
                    if (maxWaterVolume != waterHandler.getMaxVolume()) {
                        maxWaterVolume = waterHandler.getMaxVolume();
                    }
                } else if (waterVolume != 0) {
                    waterVolume = 0;
                }
            }
        }
    }

    private void updateState() {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockDrier) {
            boolean active = state.getValue(BlockDrier.ACTIVE);

            boolean isActive = isBurning() || --activeStateLimit > 0;

            if (active != isActive) {
                world.setBlockState(pos, state.withProperty(BlockDrier.ACTIVE, isActive));
            }
        }
    }

    private boolean isWaterEnough() {
        if (hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP)) {
            IFluidHandler fluidHandler = getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
            if (fluidHandler instanceof UpgradeDrierWater.WaterCapability) {
                UpgradeDrierWater.WaterCapability water = (UpgradeDrierWater.WaterCapability) fluidHandler;
                FluidStack fluidStack = water.getFluid();
                if (fluidStack != null) {
                    return fluidStack.amount / Math.pow(2, inventoryUpgradeDrier.getBoosterLevel()) >= maxWaterProgress - waterProgress;
                }
            }
        }
        return false;
    }

    private void progressDryByCoal() {
        ItemStack input = inventoryDrier.getStackInSlot(InventoryDrier.DRIER_INPUT_SLOT);
        ItemStack output = inventoryDrier.getStackInSlot(InventoryDrier.DRIER_OUTPUT_SLOT);
        ItemStack result = PotatoDrierRecipes.INSTANCE.getDryResult(input).getOutput().copy();
        if (!result.isEmpty()) {
            result.grow(inventoryUpgradeDrier.getMultiplierLevel());
        }
        if (input.isEmpty() || result.isEmpty()) {
            if (dryProgress > 0) {
                dryProgress--;
            }
        } else if (output.isEmpty() || (output.isItemEqual(result) && !InventoryHelper.isOutputOverflow(output, result))) {
            dryProgress++;
            activeStateLimit = 20;
        }
        burnTime -= Math.pow(2, inventoryUpgradeDrier.getBoosterLevel());
    }

    private void progressWater() {
        ItemStack input = inventoryDrier.getStackInSlot(InventoryDrier.WATER_INPUT_SLOT);
        ItemStack output = inventoryDrier.getStackInSlot(InventoryDrier.WATER_OUTPUT_SLOT);
        ItemStack result = PotatoDrierRecipes.INSTANCE.getWetResult(input).getOutput().copy();
        if (!result.isEmpty()) {
            result.grow(inventoryUpgradeDrier.getMultiplierLevel());
        }
        if (input.isEmpty() || result.isEmpty()) {
            if (waterProgress > 0) {
                waterProgress--;
            }
        } else if (output.isEmpty() || (output.isItemEqual(result) && !InventoryHelper.isOutputOverflow(output, result))) {
            if (hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP)) {
                IFluidHandler fluidHandler = getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
                if (fluidHandler != null) {
                    fluidHandler.drain((int) Math.pow(2, inventoryUpgradeDrier.getBoosterLevel()), true);
                    waterProgress++;
                }
            }
        }
    }

    private void burnFuel() {
        ItemStack fuel = inventoryDrier.getStackInSlot(InventoryDrier.FUEL_SLOT);
        ItemStack input = inventoryDrier.getStackInSlot(InventoryDrier.DRIER_INPUT_SLOT);
        ItemStack output = inventoryDrier.getStackInSlot(InventoryDrier.DRIER_OUTPUT_SLOT);
        ItemStack result = PotatoDrierRecipes.INSTANCE.getDryResult(input).getOutput().copy();
        if (!result.isEmpty()) {
            result.grow(inventoryUpgradeDrier.getMultiplierLevel());
        }
        if (fuel.isEmpty() || input.isEmpty() || result.isEmpty()) {
            if (dryProgress > 0) {
                dryProgress--;
            }
        } else if (output.isEmpty() || (output.isItemEqual(result) && !InventoryHelper.isOutputOverflow(output, result))) {
            burnTime = FuelHelper.getItemBurnTime(fuel);
            burnProgress = burnTime;
            InventoryHelper.shrinkIntoContainer(inventoryDrier, InventoryDrier.FUEL_SLOT, fuel);
        }
    }

    private void dryItem() {
        ItemStack input = inventoryDrier.getStackInSlot(InventoryDrier.DRIER_INPUT_SLOT);
        if (!input.isEmpty()) {
            PotatoDrierRecipe recipe = PotatoDrierRecipes.INSTANCE.getDryResult(input);
            ItemStack result = recipe.getOutput().copy();
            if (!result.isEmpty()) {
                if (!result.isItemEqual(new ItemStack(Blocks.SPONGE))) {
                    result.grow(inventoryUpgradeDrier.getMultiplierLevel());
                }
                if (InventoryHelper.setOutputSlot(inventoryDrier, InventoryDrier.DRIER_OUTPUT_SLOT, result)) {
                    InventoryHelper.shrinkIntoContainer(inventoryDrier, InventoryDrier.DRIER_INPUT_SLOT, input);
                    dryProgress = 0;
                    IFluidHandler fluidHandler = getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
                    if (fluidHandler != null) {
                        int waterVolume = recipe.getWaterValue();
                        FluidStack fluidStack = new FluidStack(FluidRegistry.WATER, waterVolume);
                        fluidHandler.fill(fluidStack, true);
                    }
                }
            }
        }
    }

    private void waterItem() {
        ItemStack input = inventoryDrier.getStackInSlot(InventoryDrier.WATER_INPUT_SLOT);
        if (!input.isEmpty()) {
            PotatoDrierRecipe recipe = PotatoDrierRecipes.INSTANCE.getWetResult(input);
            ItemStack result = recipe.getOutput().copy();
            if (!result.isEmpty()) {
                boolean isBucket = input.getItem() == PotatoItems.SWEET_EMPTY_BUCKET || input.getItem() == Items.BUCKET;
                boolean isWetSponge = result.isItemEqual(new ItemStack(Blocks.SPONGE, 1, 1));
                if (!isBucket && !isWetSponge) {
                    result.grow(inventoryUpgradeDrier.getMultiplierLevel());
                }
                if (InventoryHelper.setOutputSlot(inventoryDrier, InventoryDrier.WATER_OUTPUT_SLOT, result)) {
                    InventoryHelper.shrinkIntoContainer(inventoryDrier, InventoryDrier.WATER_INPUT_SLOT, input);
                    waterProgress = 0;
                }
            }
        }
    }
}
