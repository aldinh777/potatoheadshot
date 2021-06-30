package aldinh777.potatoheadshot.other.recipes.category;

import aldinh777.potatoheadshot.other.recipes.recipe.CauldronRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ManaLifeCauldronRecipes implements IManaRecipes {

    public static IManaRecipes INSTANCE = new ManaLifeCauldronRecipes();
    private final Map<Item, ItemStack> recipes = new HashMap<>();
    private final Map<Item, Integer> costs = new HashMap<>();

    private ManaLifeCauldronRecipes() {
        for (CauldronRecipe recipe : CauldronRecipe.getLifeRecipes()) {
            addRecipe(recipe.getInput(), recipe.getOutput(), recipe.getCost());
        }
    }

    private void addRecipe(Item input, ItemStack result, int cost) {
        this.recipes.put(input, result);
        this.costs.put(input, cost);
    }

    @Override
    public ItemStack getResult(ItemStack input) {
        ItemStack result = this.recipes.get(input.getItem());

        if (input.getItem() instanceof ItemSplashPotion) {
            ItemSplashPotion splash = (ItemSplashPotion) input.getItem();
            if (!splash.getItemStackDisplayName(input).equals("Awkward Splash Potion")) {
                return ItemStack.EMPTY;
            }
        }

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
