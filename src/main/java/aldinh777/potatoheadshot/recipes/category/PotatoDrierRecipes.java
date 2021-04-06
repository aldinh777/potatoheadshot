package aldinh777.potatoheadshot.recipes.category;

import aldinh777.potatoheadshot.recipes.recipe.PotatoDrierRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PotatoDrierRecipes {

    public static final PotatoDrierRecipes INSTANCE = new PotatoDrierRecipes();
    private final Map<Item, PotatoDrierRecipe> dryList = new HashMap<>();
    private final Map<Item, PotatoDrierRecipe> wetList = new HashMap<>();

    private PotatoDrierRecipes() {
        for (PotatoDrierRecipe dryRecipe : PotatoDrierRecipe.getDryRecipes()) {
            addDryRecipe(dryRecipe.getInput(), dryRecipe);
        }

        for (PotatoDrierRecipe wetRecipe : PotatoDrierRecipe.getWetRecipes()) {
            addWetRecipe(wetRecipe.getInput(), wetRecipe);
        }
    }

    public void addDryRecipe(Item input, PotatoDrierRecipe result) {
        this.dryList.put(input, result);
    }

    public void addWetRecipe(Item input, PotatoDrierRecipe result) {
        this.wetList.put(input, result);
    }

    public PotatoDrierRecipe getDryResult(Item input) {
        PotatoDrierRecipe result = dryList.get(input);
        return result == null ? new PotatoDrierRecipe(Items.AIR, ItemStack.EMPTY, 0) : result;
    }

    public PotatoDrierRecipe getWetResult(Item input) {
        PotatoDrierRecipe result = wetList.get(input);
        return result == null ? new PotatoDrierRecipe(Items.AIR, ItemStack.EMPTY, 0) : result;
    }

    public boolean isDryRecipeExists(Item input) {
        return !this.getDryResult(input).getOutput().isEmpty();
    }

    public boolean isWetRecipeExists(Item input) {
        return !this.getWetResult(input).getOutput().isEmpty();
    }
}
