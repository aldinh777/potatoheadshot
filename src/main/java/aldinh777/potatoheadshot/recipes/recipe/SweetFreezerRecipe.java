package aldinh777.potatoheadshot.recipes.recipe;

import aldinh777.potatoheadshot.handler.ConfigHandler;
import aldinh777.potatoheadshot.lists.PotatoItems;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SweetFreezerRecipe {

    private final Item input;
    private final ItemStack output;

    public SweetFreezerRecipe(Item input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public Item getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public static List<SweetFreezerRecipe> getRecipes() {
        List<SweetFreezerRecipe> recipes = Lists.newArrayList();

        if (ConfigHandler.FROZEN_POTATO) {
            addRecipe(recipes, Items.POTATO, new ItemStack(PotatoItems.FROZEN_POTATO));
        }
        addRecipe(recipes, Items.WATER_BUCKET, new ItemStack(Blocks.ICE));
        if (ConfigHandler.WATER_POTATO) {
            addRecipe(recipes, PotatoItems.WATER_POTATO, new ItemStack(Blocks.ICE));
        }
        if (ConfigHandler.SWEET_BUCKET) {
            addRecipe(recipes, PotatoItems.SWEET_WATER_BUCKET, new ItemStack(Blocks.ICE));
        }

        return recipes;
    }

    public static void addRecipe(List<SweetFreezerRecipe> recipes, Item input, ItemStack output) {
        recipes.add(new SweetFreezerRecipe(input, output));
    }
}
