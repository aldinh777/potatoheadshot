package aldinh777.potatoheadshot.content.capability.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SwapArmorCapability implements ICapabilitySerializable<NBTBase> {

    public final ItemStack stack;
    public ItemStackHandler stackHandler = new ItemStackHandler(4);

    public SwapArmorCapability(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) stackHandler : null;
    }

    @Override
    public NBTBase serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();

        NBTBase nbt = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(stackHandler, EnumFacing.UP);

        if (nbt != null) {
            compound.setTag("Inventory", nbt);
        }

        return compound;
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(stackHandler, EnumFacing.UP, ((NBTTagCompound) nbt).getTag("Inventory"));
    }
}
