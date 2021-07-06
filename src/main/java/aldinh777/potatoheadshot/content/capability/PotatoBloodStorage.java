package aldinh777.potatoheadshot.content.capability;

import net.minecraft.nbt.NBTTagCompound;

public class PotatoBloodStorage implements IBloodStorage {

    float maxQuantity;
    float blood = 0;

    public PotatoBloodStorage(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public void readFromNBT(NBTTagCompound compound) {
        this.blood = compound.getInteger("Blood");
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setFloat("Blood", this.blood);
    }

    @Override
    public float getBloodQuantity() {
        return blood;
    }

    @Override
    public float getMaxQuantity() {
        return maxQuantity;
    }

    @Override
    public void setBlood(float quantity) {
        blood = quantity > 0 ? Math.min(quantity, maxQuantity) : 0;
    }

    @Override
    public void useBlood(float quantity) {
        blood = Math.max(blood - quantity, 0);
    }

    @Override
    public void increaseBlood(float quantity) {
        blood = Math.min(blood + quantity, maxQuantity);
    }
}
