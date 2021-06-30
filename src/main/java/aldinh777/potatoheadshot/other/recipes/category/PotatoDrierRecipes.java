package aldinh777.potatoheadshot.other.recipes.category;

import aldinh777.potatoheadshot.other.recipes.recipe.PotatoDrierRecipe;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PotatoDrierRecipes {

    public static final PotatoDrierRecipes INSTANCE = new PotatoDrierRecipes();
    private final Map<Item, List<PotatoDrierRecipe>> dryList = new HashMap<>();
    private final Map<Item, List<PotatoDrierRecipe>> wetList = new HashMap<>();

    private void addRecipe(Item input, PotatoDrierRecipe result, Map<Item, List<PotatoDrierRecipe>> list) {
        List<PotatoDrierRecipe> results = list.get(input);
        if (results == null) {
            results = Lists.newArrayList(result);
            list.put(input, results);
        } else {
            results.add(result);
        }
    }

    public void addDryRecipe(Item input, PotatoDrierRecipe result) {
        addRecipe(input, result, dryList);
    }

    public void addWetRecipe(Item input, PotatoDrierRecipe result) {
        addRecipe(input, result, wetList);
    }

    private PotatoDrierRecipe getResult(ItemStack input, Map<Item, List<PotatoDrierRecipe>> list) {
        List<PotatoDrierRecipe> results = list.get(input.getItem());

        if (results == null) {
            return new PotatoDrierRecipe(ItemStack.EMPTY, ItemStack.EMPTY, 0);
        }

        if (results.size() <= 0) {
            return new PotatoDrierRecipe(ItemStack.EMPTY, ItemStack.EMPTY, 0);
        }

        for (PotatoDrierRecipe recipe : results) {
            if (recipe.getInput().getMetadata() == input.getMetadata()) {
                return recipe;
            }
        }

        return new PotatoDrierRecipe(ItemStack.EMPTY, ItemStack.EMPTY, 0);
    }

    public PotatoDrierRecipe getDryResult(ItemStack input) {
        return getResult(input, dryList);
    }

    public PotatoDrierRecipe getWetResult(ItemStack input) {
        return getResult(input, wetList);
    }

    public boolean isDryRecipeExists(ItemStack input) {
        return !this.getDryResult(input).getOutput().isEmpty();
    }

    public boolean isWetRecipeExists(ItemStack input) {
        return !this.getWetResult(input).getOutput().isEmpty();
    }
}
