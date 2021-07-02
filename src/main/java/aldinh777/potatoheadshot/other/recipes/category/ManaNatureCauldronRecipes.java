package aldinh777.potatoheadshot.other.recipes.category;

import aldinh777.potatoheadshot.other.recipes.recipe.CauldronRecipe;
import aldinh777.potatoheadshot.other.util.ItemHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ManaNatureCauldronRecipes implements IManaRecipes {

    public static IManaRecipes INSTANCE = new ManaNatureCauldronRecipes();
    private final Map<Item, ItemStack> recipes = new HashMap<>();

    public ManaNatureCauldronRecipes() {
        for (CauldronRecipe recipe : CauldronRecipe.getNatureRecipes()) {
            addRecipe(recipe.getInput(), recipe.getOutput());
        }
    }

    private void addRecipe(Item input, ItemStack output) {
        this.recipes.put(input, output);
    }

    @Override
    public ItemStack getResult(ItemStack input) {
        if (input.getItem() == Items.DYE && input.getMetadata() == 3) {
            return new ItemStack(Items.WHEAT_SEEDS);
        }

        ItemStack seedResult = recipes.get(input.getItem());
        if (seedResult != null && !seedResult.isEmpty()) {
            return seedResult;
        }

        ItemStack plantResult = ItemHelper.rotatePlant(input);
        if (!plantResult.isEmpty()) {
            return plantResult;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public int getCost(ItemStack input) {
        return 5;
    }

}
