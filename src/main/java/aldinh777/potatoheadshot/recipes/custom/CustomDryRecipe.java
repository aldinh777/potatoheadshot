package aldinh777.potatoheadshot.recipes.custom;

public class CustomDryRecipe {
    public InputRecipe input;
    public OutputRecipe output;

    public CustomDryRecipe(InputRecipe input, OutputRecipe output) {
        this.input = input;
        this.output = output;
    }


    public static class InputRecipe {
        public String item;
        public int meta;

        public InputRecipe(String item, int meta) {
            this.item = item;
            this.meta = meta;
        }
    }


    public static class OutputRecipe {
        public String item;
        public int meta;
        public int water;

        public OutputRecipe(String item, int meta, int water) {
            this.item = item;
            this.meta = meta;
            this.water = water;
        }
    }
}
