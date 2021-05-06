package aldinh777.potatoheadshot.item.items;

import aldinh777.potatoheadshot.energy.CapabilityMana;
import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class UpgradeModeMana extends PotatoItem {

    public UpgradeModeMana(String name) {
        super(name);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ManaCapability(stack);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        PotatoManaStorage manaStorage = (PotatoManaStorage) stack.getCapability(CapabilityMana.MANA, EnumFacing.UP);
        if (manaStorage != null) {
            return manaStorage.getManaStored() > 0 ? 1 : super.getItemStackLimit(stack);
        }
        return super.getItemStackLimit(stack);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        PotatoManaStorage manaStorage = (PotatoManaStorage) stack.getCapability(CapabilityMana.MANA, EnumFacing.UP);
        if (manaStorage != null) {
            return manaStorage.getManaStored() > 0;
        }
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        PotatoManaStorage manaStorage = (PotatoManaStorage) stack.getCapability(CapabilityMana.MANA, EnumFacing.UP);
        if (manaStorage != null) {
            return 1.0 - (double) manaStorage.getManaStored() / (double) manaStorage.getMaxManaStored();
        }
        return super.getDurabilityForDisplay(stack);
    }

    static class ManaCapability implements ICapabilitySerializable<NBTBase> {

        private final ItemStack stack;
        private final PotatoManaStorage manaStorage = new PotatoManaStorage(6000);

        public ManaCapability(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityMana.MANA;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == CapabilityMana.MANA) {
                return (T) manaStorage;
            }
            return null;
        }

        @Override
        public NBTBase serializeNBT() {
            NBTTagCompound stackNBT = stack.getTagCompound();

            if (stackNBT == null) {
                stackNBT = new NBTTagCompound();
            }
            stackNBT.setInteger("mana", manaStorage.getManaStored());
            stack.setTagCompound(stackNBT);

            NBTTagCompound nbt = new NBTTagCompound();
            NBTBase mana = CapabilityMana.MANA.writeNBT(manaStorage, EnumFacing.UP);

            nbt.setTag("manaStorage", Objects.requireNonNull(mana));

            return nbt;
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            CapabilityMana.MANA.readNBT(manaStorage, EnumFacing.UP, ((NBTTagCompound) nbt).getTag("manaStorage"));
        }
    }
}
