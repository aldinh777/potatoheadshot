package aldinh777.potatoheadshot.common.recipes.category;

import aldinh777.potatoheadshot.common.recipes.recipe.CauldronRecipe;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManaFireCauldronRecipes implements IManaRecipes {

    private final Map<Item, List<CauldronRecipe>> recipes = new HashMap<>();

    ManaFireCauldronRecipes() {
        for (CauldronRecipe recipe : CauldronRecipe.getFireRecipes()) {
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
        CauldronRecipe result = ManaCauldronRecipes.getRecipeFromMap(input, recipes);

        if (!result.getOutput().isEmpty()) {
            return result;
        }

        ItemStack furnaceResult = FurnaceRecipes.instance().getSmeltingResult(input);
        if (furnaceResult.isEmpty()) {
            return new CauldronRecipe(input, ItemStack.EMPTY, 0);
        }

        return new CauldronRecipe(input, furnaceResult, 200);
    }
}
