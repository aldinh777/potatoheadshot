package aldinh777.potatoheadshot.content.capability.item;

import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.content.capability.CapabilityMana;
import aldinh777.potatoheadshot.content.capability.PotatoManaStorage;
import aldinh777.potatoheadshot.content.inventory.InventoryPocketCauldron;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class PocketCapability implements ICapabilitySerializable<NBTBase> {

        public final ItemStack stack;
        public InventoryPocketCauldron inventory = new InventoryPocketCauldron();
        public PotatoManaStorage storage = new PotatoManaStorage(ConfigHandler.POCKET_CAULDRON_CAPACITY);

        public PocketCapability(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityMana.MANA
                    || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                return (T) this.inventory;
            } else if (capability == CapabilityMana.MANA) {
                return (T) this.storage;
            }
            return null;
        }

        @Override
        public NBTBase serializeNBT() {
            NBTTagCompound compound = new NBTTagCompound();

            NBTBase inventoryValue = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory, EnumFacing.UP);
            NBTBase manaValue = CapabilityMana.MANA.writeNBT(storage, EnumFacing.UP);

            compound.setTag("Inventory", Objects.requireNonNull(inventoryValue));
            compound.setTag("Mana", Objects.requireNonNull(manaValue));

            return compound;
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, EnumFacing.UP, ((NBTTagCompound) nbt).getTag("Inventory"));
            CapabilityMana.MANA.readNBT(storage, EnumFacing.UP, ((NBTTagCompound) nbt).getTag("Mana"));
        }
    }
