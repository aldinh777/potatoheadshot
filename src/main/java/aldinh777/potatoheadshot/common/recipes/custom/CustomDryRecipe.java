package aldinh777.potatoheadshot.common.recipes.custom;

import aldinh777.potatoheadshot.common.recipes.custom.item.ItemRecipe;
import aldinh777.potatoheadshot.common.recipes.custom.item.ItemWaterRecipe;

public class CustomDryRecipe {
    public ItemRecipe input;
    public ItemWaterRecipe output;

    public CustomDryRecipe(ItemRecipe input, ItemWaterRecipe output) {
        this.input = input;
        this.output = output;
    }
}
