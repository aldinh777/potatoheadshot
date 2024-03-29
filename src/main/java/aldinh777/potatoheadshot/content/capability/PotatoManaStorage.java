package aldinh777.potatoheadshot.content.capability;

import net.minecraft.nbt.NBTTagCompound;

public class PotatoManaStorage implements IManaStorage {
    
    int capacity;
    int mana = 0;

    public PotatoManaStorage(int capacity) {
        this.capacity = capacity;
    }

    public void readFromNBT(NBTTagCompound compound) {
        this.mana = compound.getInteger("Mana");
    }
    
    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("Mana", this.mana);
    }

    public int getManaStored() {
        return this.mana;
    }

    public int getMaxManaStored() {
        return this.capacity;
    }

    public void collectMana(int mana) {
        if (this.mana + mana > this.capacity) {
            this.mana = this.capacity;
        } else {
            this.mana += mana;
        }
    }

    public void useMana(int mana) {
        if (this.mana - mana < 0) {
            this.mana = 0;
        } else {
            this.mana -= mana;
        }
    }

    @Override
    public void setMana(int mana) {
        this.mana = mana < 0 ? 0 : Math.min(mana, getMaxManaStored());
    }
}
