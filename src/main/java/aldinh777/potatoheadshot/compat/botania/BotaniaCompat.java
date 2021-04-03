package aldinh777.potatoheadshot.compat.botania;

import aldinh777.potatoheadshot.energy.PotatoManaStorage;
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

        int transferable = size;
        int manaLeftUntilFull = manaItem.getMaxMana(stack) - manaItem.getMana(stack);

        if (storage.getManaStored() < transferable) {
            transferable = storage.getManaStored();
        }
        if (manaLeftUntilFull < transferable) {
            transferable = manaLeftUntilFull;
        }

        if (transferable > 0) {
            manaItem.addMana(stack, transferable);
            storage.useMana(transferable);
        }
    }

    public static void extractMana(ItemStack stack, PotatoManaStorage storage, int size) {
        IManaItem manaItem = (IManaItem) stack.getItem();

        int transferable = size;
        int storageLeftUntilFull = storage.getMaxManaStored() - storage.getManaStored();

        if (manaItem.getMana(stack) < transferable) {
            transferable = manaItem.getMana(stack);
        }
        if (storageLeftUntilFull < transferable) {
            transferable = storageLeftUntilFull;
        }

        if (transferable > 0) {
            manaItem.addMana(stack, -transferable);
            storage.collectMana(transferable);
        }
    }

    public static void spreadMana(TileEntity tileEntity, PotatoManaStorage storage, int size) {
        IManaPool pool = (IManaPool) tileEntity;

        if (!pool.isFull()) {
            int transferable = size;

            if (storage.getManaStored() < transferable) {
                transferable = storage.getManaStored();
            }

            if (transferable > 0) {
                pool.recieveMana(transferable);
                storage.useMana(transferable);
            }
        }
    }

    public static void absorbMana(TileEntity tileEntity, PotatoManaStorage storage, int size) {
        IManaPool pool = (IManaPool) tileEntity;

        int transferable = size;
        int storageLeftUntilFull = storage.getMaxManaStored() - storage.getManaStored();

        if (pool.getCurrentMana() < transferable) {
            transferable = pool.getCurrentMana();
        }
        if (storageLeftUntilFull < transferable) {
            transferable = storageLeftUntilFull;
        }

        if (transferable > 0) {
            pool.recieveMana(-transferable);
            storage.collectMana(transferable);
        }

    }
}
