package aldinh777.potatoheadshot.common.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class PotatoEnergyStorage extends EnergyStorage {

    public PotatoEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public void readFromNBT(NBTTagCompound compound) {
        this.energy = compound.getInteger("Energy");
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("Energy", this.energy);
    }

    public void generateEnergy(int energy) {
        if (this.energy + energy > this.capacity) {
            this.energy = this.capacity;
        } else {
            this.energy += energy;
        }
    }

    public void useEnergy(int energy) {
        if (this.energy - energy < 0) {
            this.energy = 0;
        } else {
            this.energy -= energy;
        }
    }
}
