package aldinh777.potatoheadshot.other.recipes.category;

import aldinh777.potatoheadshot.other.handler.ConfigHandler;
import aldinh777.potatoheadshot.other.lists.PotatoItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ManaFireCauldronRecipes implements IManaRecipes {

    public static IManaRecipes INSTANCE = new ManaFireCauldronRecipes();

    @Override
    public ItemStack getResult(ItemStack input) {
        if (ConfigHandler.LAVA_POTATO && input.getItem() == Items.IRON_HOE) {
            return new ItemStack(PotatoItems.LAVA_HOE);
        }

        if (ConfigHandler.SPLASH_MANA && input.getItem() == Items.GLASS_BOTTLE) {
            return new ItemStack(PotatoItems.SPLASH_MANA_FIRE);
        }

        return FurnaceRecipes.instance().getSmeltingResult(input);
    }

    @Override
    public int getCost(ItemStack input) {
        if (ConfigHandler.LAVA_POTATO) {
            if (input.getItem() == Items.IRON_HOE) return 800;
        }
        if (ConfigHandler.SPLASH_MANA) {
            if (input.getItem() == Items.GLASS_BOTTLE) return 200;
        }
        if (!FurnaceRecipes.instance().getSmeltingResult(input).isEmpty()) return 25;
        return 0;
    }
}
