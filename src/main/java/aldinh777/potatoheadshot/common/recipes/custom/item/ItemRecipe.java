package aldinh777.potatoheadshot.common.recipes.custom.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRecipe {

    public String item;
    public int meta;

    public ItemRecipe(String item, int meta) {
        this.item = item;
        this.meta = meta;
    }

    public ItemStack toItemStack() {
        Item item = Item.getByNameOrId(this.item);
        if (item != null) {
            return new ItemStack(item, 1, meta);
        }
        return ItemStack.EMPTY;
    }
}
