package aldinh777.potatoheadshot.common.recipes.category;

import aldinh777.potatoheadshot.common.recipes.recipe.CauldronRecipe;
import aldinh777.potatoheadshot.common.util.Element;
import net.minecraft.item.ItemStack;

public interface IManaRecipes {

    CauldronRecipe getResult(ItemStack input);

    static IManaRecipes getRecipeByElement(Element element) {
        switch (element) {
            case LIFE:
                return ManaCauldronRecipes.LIFE_RECIPES;
            case NATURE:
                return ManaCauldronRecipes.NATURE_RECIPES;
            case FIRE:
                return ManaCauldronRecipes.FIRE_RECIPES;
            default:
                return ManaCauldronRecipes.PURE_RECIPES;
        }
    }
}
