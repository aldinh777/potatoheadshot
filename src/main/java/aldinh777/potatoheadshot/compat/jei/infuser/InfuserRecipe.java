package aldinh777.potatoheadshot.compat.jei.infuser;

import aldinh777.potatoheadshot.handler.CustomRecipeHandler;
import aldinh777.potatoheadshot.recipes.recipe.SweetInfuserRecipe;
import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class InfuserRecipe implements IRecipeWrapper {
	
	private final List<ItemStack> inputs;
	private final ItemStack output;

	public InfuserRecipe(List<ItemStack> inputs, ItemStack output) {
		this.inputs = inputs;
		this.output = output;
	}
	
	public static List<InfuserRecipe> getRecipes() {
		List<InfuserRecipe> jeiRecipes = Lists.newArrayList();

		for (SweetInfuserRecipe recipe : CustomRecipeHandler.INFUSER_RECIPES) {
			addRecipe(jeiRecipes, recipe.getOutput(), recipe.getInput(), recipe.getFusion());
		}

		return jeiRecipes;
	}

	private static void addRecipe(List<InfuserRecipe> list, ItemStack output, ItemStack middle, ItemStack ...fuses) {
		List<ItemStack> inputs = Lists.newArrayList(middle);

		inputs.addAll(Arrays.asList(fuses));
		list.add(new InfuserRecipe(inputs, output));
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, this.inputs);
		ingredients.setOutput(ItemStack.class, this.output);
	}
}
