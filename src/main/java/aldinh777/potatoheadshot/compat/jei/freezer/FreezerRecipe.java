package aldinh777.potatoheadshot.compat.jei.freezer;

import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.recipes.SweetFreezerRecipe;
import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class FreezerRecipe implements IRecipeWrapper {

	private final ItemStack input;
	private final ItemStack output;

	public FreezerRecipe(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}
	
	public static List<FreezerRecipe> getRecipes() {
		List<FreezerRecipe> jeiRecipes = Lists.newArrayList();

		for (SweetFreezerRecipe recipe : SweetFreezerRecipe.getRecipes()) {
			jeiRecipes.add(new FreezerRecipe(new ItemStack(recipe.getInput()), recipe.getOutput()));
		}

		return jeiRecipes;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		Item rawSalt = PotatoItems.RAW_SALT;
		List<ItemStack> outputs = Lists.newArrayList(new ItemStack(rawSalt), this.output);
		
		ingredients.setInput(ItemStack.class, this.input);
		ingredients.setOutputs(ItemStack.class, outputs);
	}
}
