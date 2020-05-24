package aldinh777.potatoheadshot.block.recipes;

import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ManaFireCauldronRecipes implements IManaRecipes {

    public static IManaRecipes INSTANCE = new ManaFireCauldronRecipes();

    @Override
    public ItemStack getResult(ItemStack input) {
        if (input.getItem() == Items.IRON_HOE) {
            return new ItemStack(PotatoItems.LAVA_HOE);
        } else {
            return FurnaceRecipes.instance().getSmeltingResult(input);
        }
    }

    @Override
    public int getCost(ItemStack input) {
        if (input.getItem() == Items.IRON_HOE) return 32000;
        if (!FurnaceRecipes.instance().getSmeltingResult(input).isEmpty()) return 4000;
        return 0;
    }
}
