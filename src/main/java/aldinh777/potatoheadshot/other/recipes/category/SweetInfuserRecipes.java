package aldinh777.potatoheadshot.other.recipes.category;

import aldinh777.potatoheadshot.other.recipes.recipe.SweetInfuserRecipe;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SweetInfuserRecipes {
    
    public static final SweetInfuserRecipes INSTANCE = new SweetInfuserRecipes();
    private final Map<Item, List<SweetInfuserRecipe>> mainMap = new HashMap<>();

    public void addRecipe(Item input, SweetInfuserRecipe result) {
        List<SweetInfuserRecipe> results = mainMap.get(input);
        if (results == null) {
            results = Lists.newArrayList(result);
            mainMap.put(input, results);
        } else {
            results.add(result);
        }
    }

    public ItemStack getResult(ItemStack input, ItemStack ...fusion) {
        List<SweetInfuserRecipe> recipes = mainMap.get(input.getItem());

        for (SweetInfuserRecipe recipe : recipes) {
            if (recipe.getInput().getMetadata() == input.getMetadata()) {
                ItemStack[] recipeFusion = recipe.getFusion();
                int metadataMatch = 0;
                for (int i = 0; i < 6; i++) {
                    if (recipeFusion[i].getItem() != fusion[i].getItem()) {
                        break;
                    }

                    if (recipeFusion[i].getMetadata() == fusion[i].getMetadata()) {
                        metadataMatch++;
                    }
                }
                if (metadataMatch == 6) {
                    return recipe.getOutput();
                }
            }
        }

        return ItemStack.EMPTY;
    }
}
