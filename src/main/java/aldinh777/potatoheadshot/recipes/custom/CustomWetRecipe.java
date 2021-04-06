package aldinh777.potatoheadshot.recipes.custom;

public class CustomWetRecipe {
    public ItemRecipe input;
    public ItemRecipe output;

    public CustomWetRecipe(ItemRecipe input, ItemRecipe output) {
        this.input = input;
        this.output = output;
    }


    public static class ItemRecipe {
        public String item;
        public int meta;

        public ItemRecipe(String item, int meta) {
            this.item = item;
            this.meta = meta;
        }
    }
}
