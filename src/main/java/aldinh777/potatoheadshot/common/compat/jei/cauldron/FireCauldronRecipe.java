package aldinh777.potatoheadshot.common.compat.jei.cauldron;

import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.common.recipes.recipe.CauldronRecipe;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.List;
import java.util.Map;

public class FireCauldronRecipe extends AbstractCauldronRecipe {
	
	public FireCauldronRecipe(ItemStack input, ItemStack output) {
		super(input, output, new ItemStack(PotatoItems.ESSENCE_FIRE));
	}
	
	public static List<FireCauldronRecipe> getRecipes() {
		List<FireCauldronRecipe> jeiRecipes = Lists.newArrayList();

		for (CauldronRecipe recipe : CauldronRecipe.getFireRecipes()) {
			addRecipe(jeiRecipes, recipe.getInput(), recipe.getOutput());
		}

		Map<ItemStack, ItemStack> furnaceRecipes = FurnaceRecipes.instance().getSmeltingList();

		for (ItemStack input : furnaceRecipes.keySet()) {
			ItemStack output = furnaceRecipes.get(input);
			addRecipe(jeiRecipes, input, output);
		}

		return jeiRecipes;
	}
	
	public static void addRecipe(List<FireCauldronRecipe> list, ItemStack input, ItemStack output) {
		list.add(new FireCauldronRecipe(input, output));
	}
}
