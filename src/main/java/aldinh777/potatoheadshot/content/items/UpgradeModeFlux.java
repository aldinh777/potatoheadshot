package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.content.energy.PotatoEnergyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class UpgradeModeFlux extends PotatoItem {

    public UpgradeModeFlux(String name) {
        super(name);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new EnergyCapability(stack);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        PotatoEnergyStorage energyStorage = (PotatoEnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);
        if (energyStorage != null) {
            return energyStorage.getEnergyStored() > 0 ? 1 : super.getItemStackLimit(stack);
        }
        return super.getItemStackLimit(stack);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        PotatoEnergyStorage energyStorage = (PotatoEnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);
        if (energyStorage != null) {
            return energyStorage.getEnergyStored() > 0;
        }
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        PotatoEnergyStorage energyStorage = (PotatoEnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);
        if (energyStorage != null) {
            return 1.0 - (double) energyStorage.getEnergyStored() / (double) energyStorage.getMaxEnergyStored();
        }
        return super.getDurabilityForDisplay(stack);
    }

    static class EnergyCapability implements ICapabilitySerializable<NBTBase> {

        private final ItemStack stack;
        private final PotatoEnergyStorage energyStorage = new PotatoEnergyStorage(60000, 80, 80);

        public EnergyCapability(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityEnergy.ENERGY;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == CapabilityEnergy.ENERGY) {
                return (T) energyStorage;
            }
            return null;
        }

        @Override
        public NBTBase serializeNBT() {
            NBTTagCompound stackNBT = stack.getTagCompound();

            if (stackNBT == null) {
                stackNBT = new NBTTagCompound();
            }
            stackNBT.setInteger("energy", energyStorage.getEnergyStored());
            stack.setTagCompound(stackNBT);

            NBTTagCompound nbt = new NBTTagCompound();
            NBTBase energy = CapabilityEnergy.ENERGY.writeNBT(energyStorage, EnumFacing.UP);

            nbt.setTag("energyStorage", Objects.requireNonNull(energy));

            return nbt;
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            CapabilityEnergy.ENERGY.readNBT(energyStorage, EnumFacing.UP, ((NBTTagCompound) nbt).getTag("energyStorage"));
        }
    }
}
