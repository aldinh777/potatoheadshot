package aldinh777.potatoheadshot.util;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import java.util.function.Consumer;

public interface InventoryHelper {

    static void addSlotPlayer(Consumer<Slot> addSlotFunction, InventoryPlayer inventoryPlayer) {
        for (int y = 0; y < 3; y++) {
            for(int x = 0; x < 9; x++) {
                addSlotFunction.accept(new Slot(inventoryPlayer, x + y*9 + 9, 8 + x*18, 84 + y*18));
            }
        }

        for (int x = 0; x < 9; x++) {
            addSlotFunction.accept(new Slot(inventoryPlayer, x, 8 + x * 18, 142));
        }
    }
}
