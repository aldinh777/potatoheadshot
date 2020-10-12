package aldinh777.potatoheadshot.util;

import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface ItemHelper {

    static Item getDyeFromMeta(ItemStack stack) {
        switch (stack.getMetadata()) {
            case 1:
                return DummyItem.DYE_RED;
            case 4:
                return DummyItem.LAPIS;
            case 10:
                return DummyItem.DYE_LIME;
            case 12:
                return DummyItem.DYE_LIGHT_BLUE;
            case 15:
                return DummyItem.DYE_WHITE;
            default:
                return stack.getItem();
        }
    }

    static Item getLiquid(ItemStack stack) {
        Item item = stack.getItem();

        if (item == PotatoItems.LAVA_POTATO || item == PotatoItems.SWEET_LAVA_BUCKET) {
            return Items.LAVA_BUCKET;
        }
        if (item == PotatoItems.WATER_POTATO || item == PotatoItems.SWEET_WATER_BUCKET) {
            return Items.WATER_BUCKET;
        }

        return item;
    }

    static ItemStack getDyeFromItem(Item item) {
        if (item == DummyItem.LAPIS) {
            return new ItemStack(Items.DYE, 1, 4);
        }
        if (item == DummyItem.DYE_LIGHT_BLUE) {
            return new ItemStack(Items.DYE, 1, 12);
        }
        if (item == DummyItem.DYE_RED) {
            return new ItemStack(Items.DYE, 1, 1);
        }
        if (item == DummyItem.DYE_LIME) {
            return new ItemStack(Items.DYE, 1, 10);
        }
        if (item == DummyItem.DYE_WHITE) {
            return new ItemStack(Items.DYE, 1, 15);
        }
        return new ItemStack(item);
    }

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

    class DummyItem {
        public static final Item LAPIS = new Item();
        public static final Item DYE_LIGHT_BLUE = new Item();
        public static final Item DYE_RED = new Item();
        public static final Item DYE_LIME = new Item();
        public static final Item DYE_WHITE = new Item();
    }
}
