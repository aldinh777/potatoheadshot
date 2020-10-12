package aldinh777.potatoheadshot.block.recipes;

import aldinh777.potatoheadshot.recipes.CauldronRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ManaCauldronRecipes implements IManaRecipes {

    public static IManaRecipes INSTANCE = new ManaCauldronRecipes();
    private final Map<Item, ItemStack> recipes = new HashMap<>();
    private final Map<Item, Integer> costs = new HashMap<>();

    private ManaCauldronRecipes() {
        for (CauldronRecipe recipe : CauldronRecipe.getManaRecipes()) {
            addRecipe(recipe.getInput(), recipe.getOutput(), recipe.getCost());
        }
    }

    public void addRecipe(Item input, ItemStack output, int manaCost) {
        this.recipes.put(input, output);
        this.costs.put(input, manaCost);
    }

    @Override
    public ItemStack getResult(ItemStack input) {
        ItemStack result = this.recipes.get(input.getItem());

        if (result != null) {
            return result;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public int getCost(ItemStack input) {
        Integer result = this.costs.get(input.getItem());

        if (result != null) {
            return result;
        } else {
            return 0;
        }
    }
}
