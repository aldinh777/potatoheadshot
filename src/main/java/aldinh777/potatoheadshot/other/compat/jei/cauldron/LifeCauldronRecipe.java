package aldinh777.potatoheadshot.other.compat.jei.cauldron;

import aldinh777.potatoheadshot.other.lists.PotatoItems;
import aldinh777.potatoheadshot.other.recipes.recipe.CauldronRecipe;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class LifeCauldronRecipe extends AbstractCauldronRecipe {
	
	public LifeCauldronRecipe(ItemStack input, ItemStack output) {
		super(input, output, new ItemStack(PotatoItems.ROD_LIFE));
	}
	
	public static List<LifeCauldronRecipe> getRecipes() {
		List<LifeCauldronRecipe> jeiRecipes = Lists.newArrayList();
		
		for (CauldronRecipe recipe : CauldronRecipe.getLifeRecipes()) {
			addRecipe(jeiRecipes, recipe.getInput(), recipe.getOutput());
		}

		return jeiRecipes;
	}
	
	public static void addRecipe(List<LifeCauldronRecipe> list, Item input, ItemStack output) {
		list.add(new LifeCauldronRecipe(new ItemStack(input), output));
	}
}
