package aldinh777.potatoheadshot.other.recipes.category;

import aldinh777.potatoheadshot.other.handler.ConfigHandler;
import aldinh777.potatoheadshot.other.lists.PotatoItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ManaFireCauldronRecipes implements IManaRecipes {

    public static IManaRecipes INSTANCE = new ManaFireCauldronRecipes();

    @Override
    public ItemStack getResult(ItemStack input) {
        if (input.getItem() == Items.IRON_HOE) {
            if (ConfigHandler.LAVA_POTATO) {
                return new ItemStack(PotatoItems.LAVA_HOE);
            }
        }

        if (input.getItem() instanceof ItemSplashPotion) {
            if (ConfigHandler.SPLASH_MANA) {
                ItemSplashPotion splash = (ItemSplashPotion) input.getItem();
                if (splash.getItemStackDisplayName(input).equals("Awkward Splash Potion")) {
                    return new ItemStack(PotatoItems.SPLASH_MANA_FIRE);
                }
            }
        }

        return FurnaceRecipes.instance().getSmeltingResult(input);
    }

    @Override
    public int getCost(ItemStack input) {
        if (ConfigHandler.LAVA_POTATO) {
            if (input.getItem() == Items.IRON_HOE) return 32000;
        }
        if (ConfigHandler.SPLASH_MANA) {
            if (input.getItem() == Items.SPLASH_POTION) return 8000;
        }
        if (!FurnaceRecipes.instance().getSmeltingResult(input).isEmpty()) return 1000;
        return 0;
    }
}
