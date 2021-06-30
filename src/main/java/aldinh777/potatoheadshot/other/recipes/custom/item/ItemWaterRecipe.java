package aldinh777.potatoheadshot.other.recipes.custom.item;

public class ItemWaterRecipe extends ItemRecipe {
    public int water;

    public ItemWaterRecipe(String item, int meta, int water) {
        super(item, meta);
        this.water = water;
    }
}
