package aldinh777.potatoheadshot.other.compat.jei.cauldron;

import aldinh777.potatoheadshot.other.handler.ConfigHandler;
import aldinh777.potatoheadshot.other.lists.PotatoItems;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FireCauldronRecipe extends AbstractCauldronRecipe {
	
	public FireCauldronRecipe(ItemStack input, ItemStack output) {
		super(input, output, new ItemStack(PotatoItems.ESSENCE_FIRE));
	}
	
	public static List<FireCauldronRecipe> getRecipes() {
		List<FireCauldronRecipe> jeiRecipes = Lists.newArrayList();

		if (ConfigHandler.LAVA_POTATO) {
			addRecipe(jeiRecipes, Items.IRON_HOE, new ItemStack(PotatoItems.LAVA_HOE));
		}
		if (ConfigHandler.SPLASH_MANA) {
			addRecipe(jeiRecipes, Items.GLASS_BOTTLE, new ItemStack(PotatoItems.SPLASH_MANA_FIRE));
		}

		return jeiRecipes;
	}
	
	public static void addRecipe(List<FireCauldronRecipe> list, Item input, ItemStack output) {
		list.add(new FireCauldronRecipe(new ItemStack(input), output));
	}
}
