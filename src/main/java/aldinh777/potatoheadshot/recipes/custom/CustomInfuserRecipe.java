package aldinh777.potatoheadshot.recipes.custom;

import aldinh777.potatoheadshot.recipes.custom.item.ItemRecipe;

public class CustomInfuserRecipe {

    public ItemRecipe input;
    public ItemRecipe output;
    public ItemRecipe[] fusion;

    public CustomInfuserRecipe(ItemRecipe input, ItemRecipe output, ItemRecipe ...fusion) {
        this.input = input;
        this.output = output;
        this.fusion = fusion;
    }
}
