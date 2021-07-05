package aldinh777.potatoheadshot.common.util;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface ItemHelper {

    static ItemStack rotatePlant(ItemStack input) {
        Item redFlower = Item.getItemFromBlock(Blocks.RED_FLOWER);
        Item yellowFlower = Item.getItemFromBlock(Blocks.YELLOW_FLOWER);
        Item doublePlant = Item.getItemFromBlock(Blocks.DOUBLE_PLANT);
        Item sapling = Item.getItemFromBlock(Blocks.SAPLING);
        int meta = input.getMetadata();

        if (input.getItem().equals(redFlower)) {
            if (meta == 0) {
                return new ItemStack(yellowFlower);
            } if (meta == 8) {
                return new ItemStack(redFlower, 1, 0);
            } else {
                return new ItemStack(redFlower, 1, meta + 1);
            }
        } else if (input.getItem().equals(yellowFlower)) {
            return new ItemStack(redFlower, 1, 1);
        } else if (input.getItem().equals(sapling)) {
            if (meta == 5) {
                return new ItemStack(sapling);
            } else {
                return new ItemStack(sapling, 1, meta + 1);
            }
        } else if (input.getItem().equals(doublePlant)) {
            if (meta == 5) {
                return new ItemStack(doublePlant);
            } else {
                return new ItemStack(doublePlant, 1, meta + 1);
            }
        } else {
            return ItemStack.EMPTY;
        }
    }
}
