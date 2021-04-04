package aldinh777.potatoheadshot.recipes.category;

import aldinh777.potatoheadshot.recipes.recipe.SweetInfuserRecipe;
import aldinh777.potatoheadshot.util.ItemHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SweetInfuserRecipes {
    
    public static final SweetInfuserRecipes INSTANCE = new SweetInfuserRecipes();
    private final Map<Item, Object> mainMap = new HashMap<>();

    private SweetInfuserRecipes() {
        for (SweetInfuserRecipe recipe : SweetInfuserRecipe.getRecipes()) {
            addRecipe(recipe.getOutput(), recipe.getCatalyst(), recipe.getInputs());
        }
    }

    public <K, V> Map<K, V> getMap(Map<K, V> map, Item key) {
        Map<K, V> existingMap = (Map<K, V>)map.get((K)key);

        if (existingMap != null) {
            return existingMap;
        } else {
            Map<K, V> newMap = new HashMap<>();
            map.put((K)key, (V)newMap);
            return newMap;
        }
    }

    public <K, V> void addRecipe(ItemStack output, Item middle, Item ...recipes) {
        Map<K, V> map = (Map<K, V>)mainMap;
        map = getMap(map, middle);

        for (int i = 0; i < 5; i++) {
            Item recipe = recipes[i];
            map = getMap(map, recipe);
        }

        Item lastRecipe = recipes[5];
        map.put((K)lastRecipe, (V)output);
    }

    public <K, V> ItemStack getResult(Item middle, ItemStack ...recipes) {
        Map<K, V> map = (Map<K, V>)mainMap;
        map = (Map<K, V>)map.get((K)middle);

        if (map == null) {
            return ItemStack.EMPTY;
        }

        for (int i = 0; i < 5; i++) {
            Item recipe;

            if (recipes[i].getItem() == Items.DYE) {
                recipe = ItemHelper.getDyeFromMeta(recipes[i]);
            } else {
                recipe = ItemHelper.getLiquid(recipes[i]);
            }

            map = (Map<K, V>)map.get((K)recipe);

            if (map == null) {
                return ItemStack.EMPTY;
            }
        }

        ItemStack result;
        Item lastRecipe;

        if (recipes[5].getItem() == Items.DYE) {
            lastRecipe = ItemHelper.getDyeFromMeta(recipes[5]);
        } else {
            lastRecipe = ItemHelper.getLiquid(recipes[5]);
        }
        result = (ItemStack)map.get((K)lastRecipe);

        if (result == null) {
            return ItemStack.EMPTY;
        } else {
            return result;
        }
    }
}
