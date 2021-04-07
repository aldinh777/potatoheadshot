package aldinh777.potatoheadshot.compat.jei.drier;

import aldinh777.potatoheadshot.handler.CustomRecipeHandler;
import aldinh777.potatoheadshot.recipes.recipe.PotatoDrierRecipe;
import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.List;

public class DrierRecipe implements IRecipeWrapper {
	
	private final List<ItemStack> dryInput;
	private final ItemStack dryOutput;
	private final List<ItemStack> wetInput;
	private final ItemStack wetOutput;

	public DrierRecipe(List<ItemStack> dryInput, ItemStack dryOutput, List<ItemStack> wetInput, ItemStack wetOutput) {
		this.dryInput = dryInput;
		this.dryOutput = dryOutput;
		this.wetInput = wetInput;
		this.wetOutput = wetOutput;
	}
	
	public static List<DrierRecipe> getRecipes() {
		List<DrierRecipe> jeiRecipes = Lists.newArrayList();

		for (PotatoDrierRecipe dryRecipe : CustomRecipeHandler.DRY_RECIPES) {
			addDryRecipe(jeiRecipes, dryRecipe.getInput(), dryRecipe.getOutput());
		}

		for (PotatoDrierRecipe wetRecipe : CustomRecipeHandler.WET_RECIPES) {
			addWetRecipe(jeiRecipes, wetRecipe.getInput(), wetRecipe.getOutput());
		}

		return jeiRecipes;
	}
	
	private static void addDryRecipe(List<DrierRecipe> list, ItemStack input, ItemStack output) {
		List<ItemStack> inputs = Lists.newArrayList(input, ItemStack.EMPTY);
		list.add(new DrierRecipe(inputs, output, Lists.newArrayList(), ItemStack.EMPTY));
	}
	
	private static void addWetRecipe(List<DrierRecipe> list, ItemStack input, ItemStack output) {
		List<ItemStack> inputs = Lists.newArrayList(ItemStack.EMPTY, input);
		list.add(new DrierRecipe(Lists.newArrayList(), ItemStack.EMPTY, inputs, output));
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, Lists.newArrayList(this.dryInput, this.wetInput));
		ingredients.setOutputs(ItemStack.class, Lists.newArrayList(this.dryOutput, this.wetOutput));
	}
}
