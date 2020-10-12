package aldinh777.potatoheadshot.block.recipes;

import aldinh777.potatoheadshot.recipes.PotatoDrierRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PotatoDrierRecipes {

    public static final PotatoDrierRecipes INSTANCE = new PotatoDrierRecipes();
    private final Map<Item, ItemStack> dryList = new HashMap<>();
    private final Map<Item, ItemStack> wetList = new HashMap<>();

    private PotatoDrierRecipes() {
        for (PotatoDrierRecipe dryRecipe : PotatoDrierRecipe.getDryRecipes()) {
            addDryRecipe(dryRecipe.getInput(), dryRecipe.getOutput());
        }

        for (PotatoDrierRecipe wetRecipe : PotatoDrierRecipe.getWetRecipes()) {
            addWetRecipe(wetRecipe.getInput(), wetRecipe.getOutput());
        }
    }

    public void addDryRecipe(Item input, ItemStack result) {
        this.dryList.put(input, result);
    }

    public void addWetRecipe(Item input, ItemStack result) {
        this.wetList.put(input, result);
    }

    public ItemStack getDryResult(Item input) {
        ItemStack result = dryList.get(input);
        return result == null ? ItemStack.EMPTY : result;
    }

    public ItemStack getWetResult(Item input) {
        ItemStack result = wetList.get(input);
        return result == null ? ItemStack.EMPTY : result;
    }

    public boolean isDryRecipeExists(Item input) {
        return !this.getDryResult(input).isEmpty();
    }

    public boolean isWetRecipeExists(Item input) {
        return !this.getWetResult(input).isEmpty();
    }
}
