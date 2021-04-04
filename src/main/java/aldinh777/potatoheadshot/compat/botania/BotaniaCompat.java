package aldinh777.potatoheadshot.compat.botania;

import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import aldinh777.potatoheadshot.util.EnergyUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.IManaPool;

public class BotaniaCompat {

    public static boolean isInstanceOfManaItem(Item item) {
        return item instanceof IManaItem;
    }

    public static boolean isInstanceOfManaPool(TileEntity tileEntity) {
        return tileEntity instanceof IManaPool;
    }

    public static boolean isBotaniaAvailable() {
        try {
            Class.forName("vazkii.botania.common.Botania");
        } catch (ClassNotFoundException exception) {
            return false;
        }
        return true;
    }

    public static int getManaSize(TileEntity tileEntity) {
        IManaPool pool = (IManaPool) tileEntity;
        return pool.getCurrentMana();
    }

    public static void chargeMana(ItemStack stack, PotatoManaStorage storage, int size) {
        IManaItem manaItem = (IManaItem) stack.getItem();

        int mana = storage.getManaStored();
        int manaLeftUntilFull = manaItem.getMaxMana(stack) - manaItem.getMana(stack);

        EnergyUtil.checkTransferableEnergy(mana, manaLeftUntilFull, size, (transferable) -> {
            manaItem.addMana(stack, transferable);
            storage.useMana(transferable);
        });
    }

    public static void extractMana(ItemStack stack, PotatoManaStorage storage, int size) {
        IManaItem manaItem = (IManaItem) stack.getItem();

        int mana = manaItem.getMana(stack);
        int storageLeftUntilFull = storage.getMaxManaStored() - storage.getManaStored();
        EnergyUtil.checkTransferableEnergy(mana, storageLeftUntilFull, size, (transferable) -> {
            manaItem.addMana(stack, -transferable);
            storage.collectMana(transferable);
        });
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
