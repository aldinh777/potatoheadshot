package aldinh777.potatoheadshot.other.compat.botania;

import aldinh777.potatoheadshot.content.capability.PotatoManaStorage;
import aldinh777.potatoheadshot.other.handler.ConfigHandler;
import aldinh777.potatoheadshot.other.util.EnergyUtil;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.api.mana.IManaPool;

public class BotaniaCompat {

    public static boolean isInstanceOfManaPool(TileEntity tileEntity) {
        return tileEntity instanceof IManaPool;
    }

    public static boolean isBotaniaAvailable() {
        if (ConfigHandler.BOTANIA_COMPAT) {
            try {
                Class.forName("vazkii.botania.common.Botania");
            } catch (ClassNotFoundException exception) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static int getManaSize(TileEntity tileEntity) {
        IManaPool pool = (IManaPool) tileEntity;
        return pool.getCurrentMana();
    }

    public static void spreadMana(TileEntity tileEntity, PotatoManaStorage storage, int size) {
        IManaPool pool = (IManaPool) tileEntity;

        int mana = storage.getManaStored();
        int toFull = pool.isFull() ? 0 : size;

        EnergyUtil.checkTransferableEnergy(mana, toFull, size, (transferable) -> {
            pool.recieveMana(transferable);
            storage.useMana(transferable);
        });
    }

    public static void absorbMana(TileEntity tileEntity, PotatoManaStorage storage, int size) {
        IManaPool pool = (IManaPool) tileEntity;

        int mana = pool.getCurrentMana();
        int storageLeftUntilFull = storage.getMaxManaStored() - storage.getManaStored();

        EnergyUtil.checkTransferableEnergy(mana, storageLeftUntilFull, size, (transferable) -> {
            pool.recieveMana(-transferable);
            storage.collectMana(transferable);
        });
    }
}
