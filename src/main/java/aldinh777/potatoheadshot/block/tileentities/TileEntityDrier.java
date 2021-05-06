package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.blocks.machines.BlockDrier;
import aldinh777.potatoheadshot.block.inventory.InventoryDrier;
import aldinh777.potatoheadshot.block.inventory.InventoryDrierUpgrade;
import aldinh777.potatoheadshot.energy.CapabilityMana;
import aldinh777.potatoheadshot.energy.IManaStorage;
import aldinh777.potatoheadshot.energy.PotatoEnergyStorage;
import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import aldinh777.potatoheadshot.item.items.UpgradeDrierWater;
import aldinh777.potatoheadshot.recipes.category.PotatoDrierRecipes;
import aldinh777.potatoheadshot.recipes.recipe.PotatoDrierRecipe;
import aldinh777.potatoheadshot.util.FuelHelper;
import aldinh777.potatoheadshot.util.InventoryHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityDrier extends AbstractMachine {

    private final ItemStackHandler inventoryDrier = new InventoryDrier();
    private final InventoryDrierUpgrade inventoryUpgradeDrier = new InventoryDrierUpgrade();

    public int burnProgress = 0;
    public int burnTime = 0;
    public int dryProgress = 0;
    public int maxDryProgress = 200;

    public int waterVolume = 0;
    public int fluxStored = 0;
    public int manaStored = 0;

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
        } else if (capability == CapabilityEnergy.ENERGY) {
            return inventoryUpgradeDrier.hasFluxUpgrade();
        } else if (capability == CapabilityMana.MANA) {
            return inventoryUpgradeDrier.hasManaUpgrade();
        } else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return inventoryUpgradeDrier.hasWaterUpgrade();
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) inventoryDrier;
        } else if (capability == CapabilityEnergy.ENERGY || capability == CapabilityMana.MANA) {
            ItemStack upgradeMode = inventoryUpgradeDrier.getStackInSlot(InventoryDrierUpgrade.MODE_UPGRADE_SLOT);
            return upgradeMode.getCapability(capability, facing);
        } else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            ItemStack upgradeWater = inventoryUpgradeDrier.getStackInSlot(InventoryDrierUpgrade.WATER_UPGRADE_SLOT);
            return (T) upgradeWater.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, facing);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventoryDrier.deserializeNBT(compound.getCompoundTag("Inventory"));
        inventoryUpgradeDrier.deserializeNBT(compound.getCompoundTag("Upgrade"));
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Inventory", inventoryDrier.serializeNBT());
        compound.setTag("Upgrade", inventoryUpgradeDrier.serializeNBT());
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
            } else if (isEnergyEnough()) {
                progressDryByEnergy();
            } else {
                burnFuel();
            }

            if (dryProgress >= maxDryProgress) {
                dryItem();
            }
        }

        markDirty();
    }

    public boolean isBurning() {
        return burnTime > 0;
    }

    private void applyBooster() {
        switch (inventoryUpgradeDrier.getBoosterLevel()) {
            case 1:
                maxDryProgress = 100;
                break;
            case 2:
                maxDryProgress = 50;
                break;
            default:
                maxDryProgress = 200;
                break;
        }
    }

    private void updateGuiVariable() {
        if (hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP)) {
            IFluidHandler fluidHandler = getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
            if (fluidHandler != null) {
                if (fluidHandler instanceof UpgradeDrierWater.WaterCapability) {
                    UpgradeDrierWater.WaterCapability waterHandler = (UpgradeDrierWater.WaterCapability) fluidHandler;
                    FluidStack fluidStack = waterHandler.getFluid();
                    if (fluidStack != null) {
                        if (waterVolume != fluidStack.amount) {
                            waterVolume = fluidStack.amount;
                        }
                    }
                }
            }
        }
        if (hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)) {
            IEnergyStorage energyStorage = getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);
            if (energyStorage != null) {
                if (fluxStored != energyStorage.getEnergyStored()) {
                    fluxStored = energyStorage.getEnergyStored();
                }
            }
        }
        if (hasCapability(CapabilityMana.MANA, EnumFacing.UP)) {
            IManaStorage manaStorage = getCapability(CapabilityMana.MANA, EnumFacing.UP);
            if (manaStorage != null) {
                if (manaStored != manaStorage.getManaStored()) {
                    manaStored = manaStorage.getManaStored();
                }
            }
        }
    }

    private void updateState() {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockDrier) {
            boolean active = state.getValue(BlockDrier.ACTIVE);
            boolean water = state.getValue(BlockDrier.WATER);
            BlockDrier.Mode mode = state.getValue(BlockDrier.MODE);

            if (active != isBurning()) {
                world.setBlockState(pos, state.withProperty(BlockDrier.ACTIVE, isBurning()));
            }
            if (water != inventoryUpgradeDrier.hasWaterUpgrade()) {
                world.setBlockState(pos, state.withProperty(BlockDrier.WATER, inventoryUpgradeDrier.hasWaterUpgrade()));
            }
            if (mode != inventoryUpgradeDrier.getMode()) {
                world.setBlockState(pos, state.withProperty(BlockDrier.MODE, inventoryUpgradeDrier.getMode()));
            }
        }
    }

    private boolean isEnergyEnough() {
        if (!inventoryUpgradeDrier.hasEnergyUpgrade()) {
            return false;
        }
        int energy = 0;
        if (hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)) {
            PotatoEnergyStorage energyStorage = (PotatoEnergyStorage) getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);
            if (energyStorage == null) {
                return false;
            }
            energy = energyStorage.getEnergyStored();
        } else if (hasCapability(CapabilityMana.MANA, EnumFacing.UP)) {
            PotatoManaStorage manaStorage = (PotatoManaStorage) getCapability(CapabilityMana.MANA, EnumFacing.UP);
            if (manaStorage == null) {
                return false;
            }
            energy = manaStorage.getManaStored() * 10;
        }
        return energy >= maxDryProgress - dryProgress;
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
        }
        burnTime--;
    }

    private void progressDryByEnergy() {
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
            if (hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)) {
                PotatoEnergyStorage energyStorage = (PotatoEnergyStorage) getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);
                if (energyStorage == null) {
                    return;
                }
                energyStorage.useEnergy(10);
                dryProgress++;
            } else if (hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)) {
                PotatoManaStorage manaStorage = (PotatoManaStorage) getCapability(CapabilityMana.MANA, EnumFacing.UP);
                if (manaStorage == null) {
                    return;
                }
                manaStorage.useMana(1);
                dryProgress++;
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
            fuel.shrink(1);
        }
    }

    private void dryItem() {
        ItemStack input = inventoryDrier.getStackInSlot(InventoryDrier.DRIER_INPUT_SLOT);

        if (!input.isEmpty()) {
            PotatoDrierRecipe recipe = PotatoDrierRecipes.INSTANCE.getDryResult(input);
            ItemStack result = recipe.getOutput().copy();
            result.grow(inventoryUpgradeDrier.getMultiplierLevel());

            if (!result.isEmpty()) {
                if (InventoryHelper.setOutputSlot(inventoryDrier, InventoryDrier.DRIER_OUTPUT_SLOT, result)) {
                    input.shrink(1);
                    dryProgress = 0;

                    if (inventoryUpgradeDrier.hasWaterUpgrade()) {
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
    }
}
