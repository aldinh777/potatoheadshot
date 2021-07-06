package aldinh777.potatoheadshot.content.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityBlood {

    @CapabilityInject(IBloodStorage.class)
    public static Capability<IBloodStorage> BLOOD = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IBloodStorage.class, new Capability.IStorage<IBloodStorage>() {
            @Override
            public NBTBase writeNBT(Capability<IBloodStorage> capability, IBloodStorage instance, EnumFacing side) {
                return new NBTTagFloat(instance.getBloodQuantity());
            }

            @Override
            public void readNBT(Capability<IBloodStorage> capability, IBloodStorage instance, EnumFacing side, NBTBase nbt) {
                if (!(instance instanceof PotatoBloodStorage)) {
                    throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                }
                ((PotatoBloodStorage) instance).blood = ((NBTTagFloat) nbt).getFloat();
            }
        }, () -> new PotatoBloodStorage(20));
    }
}
