package aldinh777.potatoheadshot.content.energy;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityMana {

    @CapabilityInject(IManaStorage.class)
    public static Capability<IManaStorage> MANA = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IManaStorage.class, new Capability.IStorage<IManaStorage>() {
            @Override
            public NBTBase writeNBT(Capability<IManaStorage> capability, IManaStorage instance, EnumFacing side) {
                return new NBTTagInt(instance.getManaStored());
            }

            @Override
            public void readNBT(Capability<IManaStorage> capability, IManaStorage instance, EnumFacing side, NBTBase nbt) {
                if (!(instance instanceof PotatoManaStorage)) {
                    throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                }
                ((PotatoManaStorage) instance).mana = ((NBTTagInt) nbt).getInt();
            }
        }, () -> new PotatoManaStorage(6400));
    }
}
