package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.blocks.machines.BlockDrier;
import aldinh777.potatoheadshot.block.inventory.InventoryDrier;
import aldinh777.potatoheadshot.block.inventory.InventoryDrierUpgrade;
import aldinh777.potatoheadshot.energy.CapabilityPotato;
import aldinh777.potatoheadshot.energy.PotatoEnergyStorage;
import aldinh777.potatoheadshot.energy.PotatoManaStorage;
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
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) inventoryDrier;
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

    private void updateState() {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockDrier) {
            boolean active = state.getValue(BlockDrier.ACTIVE);
            boolean water = state.getValue(BlockDrier.WATER);
            BlockDrier.Mode mode = state.getValue(BlockDrier.MODE);

            if (active != isBurning()) {
                world.setBlockState(pos, state.withProperty(BlockDrier.ACTIVE, isBurning()));
            }
            if (water != inventoryUpgradeDrier.hasWaterCapacity()) {
                world.setBlockState(pos, state.withProperty(BlockDrier.WATER, inventoryUpgradeDrier.hasWaterCapacity()));
            }
            if (mode != inventoryUpgradeDrier.getMode()) {
                world.setBlockState(pos, state.withProperty(BlockDrier.MODE, inventoryUpgradeDrier.getMode()));
            }
        }
    }

    private boolean isEnergyEnough() {
        int energy = 0;
        if (hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)) {
            PotatoEnergyStorage energyStorage = (PotatoEnergyStorage) getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);
            if (energyStorage == null) {
                return false;
            }
            energy = energyStorage.getEnergyStored();
        } else if (hasCapability(CapabilityPotato.MANA, EnumFacing.UP)) {
            PotatoManaStorage manaStorage = (PotatoManaStorage) getCapability(CapabilityPotato.MANA, EnumFacing.UP);
            if (manaStorage == null) {
                return false;
            }
            energy = manaStorage.getManaStored() * 10;
        }
        return energy >= maxDryProgress - dryProgress;
    }

    private void progressDryByCoal() {
        ItemStack input = inventoryDrier.getStackInSlot(InventoryDrier.DRIER_INPUT_SLOT);
        ItemStack result = PotatoDrierRecipes.INSTANCE.getDryResult(input).getOutput();

        if (input.isEmpty() || result.isEmpty()) {
            if (dryProgress > 0) {
                dryProgress--;
            }
        } else {
            dryProgress++;
        }
        burnTime--;
    }

    private void progressDryByEnergy() {
        if (hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)) {
            PotatoEnergyStorage energyStorage = (PotatoEnergyStorage) getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);
            if (energyStorage == null) {
                return;
            }
            energyStorage.useEnergy(10);
            dryProgress++;
        } else if (hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)) {
            PotatoManaStorage manaStorage = (PotatoManaStorage) getCapability(CapabilityPotato.MANA, EnumFacing.UP);
            if (manaStorage == null) {
                return;
            }
            manaStorage.useMana(1);
            dryProgress++;
        }
    }

    private void burnFuel() {
        ItemStack fuel = inventoryDrier.getStackInSlot(InventoryDrier.FUEL_SLOT);
        ItemStack input = inventoryDrier.getStackInSlot(InventoryDrier.DRIER_INPUT_SLOT);
        ItemStack result = PotatoDrierRecipes.INSTANCE.getDryResult(input).getOutput();

        if (fuel.isEmpty() || input.isEmpty() || result.isEmpty()) {
            if (dryProgress > 0) {
                dryProgress--;
            }
            return;
        }

        burnTime = FuelHelper.getItemBurnTime(fuel);
        burnProgress = burnTime;
        fuel.shrink(1);
    }

    private void dryItem() {
        ItemStack input = inventoryDrier.getStackInSlot(InventoryDrier.DRIER_INPUT_SLOT);

        if (!input.isEmpty()) {
            PotatoDrierRecipe recipe = PotatoDrierRecipes.INSTANCE.getDryResult(input);
            ItemStack result = recipe.getOutput();

            if (!result.isEmpty()) {
                InventoryHelper.setOutputSlot(inventoryDrier, InventoryDrier.DRIER_OUTPUT_SLOT, result);
                input.shrink(1);
                dryProgress = 0;
            }
        }
    }
}
