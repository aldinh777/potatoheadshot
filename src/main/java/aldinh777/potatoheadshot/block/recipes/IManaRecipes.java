package aldinh777.potatoheadshot.block.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IManaRecipes {

    ItemStack getResult(Item input);

    int getCost(Item input);

    static IManaRecipes getRecipeById(int id) {
        switch (id) {
            case 1:
                return ManaLifeCauldronRecipes.INSTANCE;
            case 3:
                return ManaFireCauldronRecipes.INSTANCE;
            default:
                return ManaCauldronRecipes.INSTANCE;
        }
    }
}
