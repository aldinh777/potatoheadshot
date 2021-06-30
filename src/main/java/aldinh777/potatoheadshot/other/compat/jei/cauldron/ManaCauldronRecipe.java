package aldinh777.potatoheadshot.other.compat.jei.cauldron;

import aldinh777.potatoheadshot.other.lists.PotatoItems;
import aldinh777.potatoheadshot.other.recipes.recipe.CauldronRecipe;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ManaCauldronRecipe extends AbstractCauldronRecipe {

	public ManaCauldronRecipe(ItemStack input, ItemStack output) {
		super(input, output, new ItemStack(PotatoItems.ROD_MANA));
	}
	
	public static List<ManaCauldronRecipe> getRecipes() {
		List<ManaCauldronRecipe> jeiRecipes = Lists.newArrayList();
		
		for (CauldronRecipe recipe : CauldronRecipe.getManaRecipes()) {
			addRecipe(jeiRecipes, recipe.getInput(), recipe.getOutput());
		}

		return jeiRecipes;
	}

	public static void addRecipe(List<ManaCauldronRecipe> list, Item input, ItemStack output) {
		list.add(new ManaCauldronRecipe(new ItemStack(input), output));
	}
}
