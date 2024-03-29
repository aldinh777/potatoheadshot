package aldinh777.potatoheadshot.common.compat.jei.cauldron;

import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.common.recipes.recipe.CauldronRecipe;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.List;

public class LifeCauldronRecipe extends AbstractCauldronRecipe {
	
	public LifeCauldronRecipe(ItemStack input, ItemStack output) {
		super(input, output, new ItemStack(PotatoItems.ESSENCE_LIFE));
	}
	
	public static List<LifeCauldronRecipe> getRecipes() {
		List<LifeCauldronRecipe> jeiRecipes = Lists.newArrayList();
		
		for (CauldronRecipe recipe : CauldronRecipe.getLifeRecipes()) {
			addRecipe(jeiRecipes, recipe.getInput(), recipe.getOutput());
		}

		return jeiRecipes;
	}
	
	public static void addRecipe(List<LifeCauldronRecipe> list, ItemStack input, ItemStack output) {
		list.add(new LifeCauldronRecipe(input, output));
	}
}
