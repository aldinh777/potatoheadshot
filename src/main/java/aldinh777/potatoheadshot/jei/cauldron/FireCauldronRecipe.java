package aldinh777.potatoheadshot.jei.cauldron;

import aldinh777.potatoheadshot.lists.PotatoItems;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FireCauldronRecipe extends AbstractCauldronRecipe {
	
	public FireCauldronRecipe(ItemStack input, ItemStack output) {
		super(input, output, new ItemStack(PotatoItems.ROD_FIRE));
	}
	
	public static List<FireCauldronRecipe> getRecipes() {
		List<FireCauldronRecipe> jeiRecipes = Lists.newArrayList();
		
		addRecipe(jeiRecipes, Items.IRON_HOE, new ItemStack(PotatoItems.LAVA_HOE));
		addRecipe(jeiRecipes, Items.SPLASH_POTION, new ItemStack(PotatoItems.SPLASH_MANA_FIRE));

		return jeiRecipes;
	}
	
	public static void addRecipe(List<FireCauldronRecipe> list, Item input, ItemStack output) {
		list.add(new FireCauldronRecipe(new ItemStack(input), output));
	}
}
