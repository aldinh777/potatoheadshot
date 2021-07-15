package aldinh777.potatoheadshot.common.compat.jei.cauldron;

import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.common.recipes.recipe.CauldronRecipe;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import java.util.List;

public class NatureCauldronRecipe extends AbstractCauldronRecipe {
	
	public NatureCauldronRecipe(ItemStack input, ItemStack output) {
		super(input, output, new ItemStack(PotatoItems.ESSENCE_NATURE));
	}
	
	public static List<NatureCauldronRecipe> getRecipes() {
		List<NatureCauldronRecipe> jeiRecipes = Lists.newArrayList();

		for (CauldronRecipe recipe : CauldronRecipe.getNatureRecipes()) {
			addRecipe(jeiRecipes, recipe.getInput(), recipe.getOutput());
		}

		return jeiRecipes;
	}
	
	public static void addRecipe(List<NatureCauldronRecipe> list, ItemStack input, ItemStack output) {
		list.add(new NatureCauldronRecipe(input, output));
	}
}
