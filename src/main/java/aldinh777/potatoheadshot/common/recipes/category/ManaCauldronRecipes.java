package aldinh777.potatoheadshot.common.recipes.category;

import aldinh777.potatoheadshot.common.recipes.recipe.CauldronRecipe;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ManaCauldronRecipes implements IManaRecipes {

    public static IManaRecipes PURE_RECIPES = new ManaCauldronRecipes(CauldronRecipe::getManaRecipes);
    public static IManaRecipes LIFE_RECIPES = new ManaCauldronRecipes(CauldronRecipe::getLifeRecipes);
    public static IManaRecipes FIRE_RECIPES = new ManaFireCauldronRecipes();
    public static IManaRecipes NATURE_RECIPES = new ManaCauldronRecipes(CauldronRecipe::getNatureRecipes);

    private final Map<Item, List<CauldronRecipe>> recipes = new HashMap<>();

    ManaCauldronRecipes(Supplier<List<CauldronRecipe>> recipesSupplier) {
        for (CauldronRecipe recipe : recipesSupplier.get()) {
            addRecipe(recipe.getInput().getItem(), recipe);
        }
    }

    public void addRecipe(Item input, CauldronRecipe result) {
        List<CauldronRecipe> results = recipes.get(input);
        if (results == null) {
            results = Lists.newArrayList(result);
            recipes.put(input, results);
        } else {
            results.add(result);
        }
    }

    @Override
    public CauldronRecipe getResult(ItemStack input) {
        return getRecipeFromMap(input, recipes);
    }

    public static CauldronRecipe getRecipeFromMap(ItemStack input, Map<Item,List<CauldronRecipe>> recipes) {
        List<CauldronRecipe> results = recipes.get(input.getItem());

        if (results == null || results.size() <= 0) {
            return new CauldronRecipe(input, ItemStack.EMPTY, 0);
        }

        for (CauldronRecipe recipe : results) {
            if (recipe.getInput().getMetadata() == input.getMetadata()) {
                return recipe;
            }
        }

        return new CauldronRecipe(input, ItemStack.EMPTY, 0);
    }
}
