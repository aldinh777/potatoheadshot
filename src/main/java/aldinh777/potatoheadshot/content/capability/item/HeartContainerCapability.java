package aldinh777.potatoheadshot.content.capability.item;

import aldinh777.potatoheadshot.content.capability.CapabilityBlood;
import aldinh777.potatoheadshot.content.capability.PotatoBloodStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class HeartContainerCapability implements ICapabilitySerializable<NBTBase> {

    public ItemStack stack;
    public PotatoBloodStorage heartContainer = new PotatoBloodStorage(100);

    public HeartContainerCapability(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityBlood.BLOOD;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityBlood.BLOOD ? (T) heartContainer : null;
    }

    @Override
    public NBTBase serializeNBT() {
        NBTTagCompound stackNBT = this.stack.getTagCompound();
        if (stackNBT == null) {
            stackNBT = new NBTTagCompound();
        }
        stack.setTagCompound(stackNBT);

        NBTTagCompound nbt = new NBTTagCompound();
        NBTBase heartValue = CapabilityBlood.BLOOD.writeNBT(heartContainer, EnumFacing.UP);

        nbt.setTag("Heart", Objects.requireNonNull(heartValue));

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CapabilityBlood.BLOOD.readNBT(heartContainer, EnumFacing.UP, ((NBTTagCompound) nbt).getTag("Heart"));
    }
}
