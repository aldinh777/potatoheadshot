package aldinh777.potatoheadshot.block.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ManaFireCauldronRecipes implements IManaRecipes {

    public static IManaRecipes INSTANCE = new ManaFireCauldronRecipes();

    @Override
    public ItemStack getResult(Item input) {
        return FurnaceRecipes.instance().getSmeltingResult(new ItemStack(input));
    }

    @Override
    public int getCost(Item input) {
        return 4000;
    }
}
