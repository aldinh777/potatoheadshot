package aldinh777.potatoheadshot.compat.jei.infuser;

import aldinh777.potatoheadshot.recipes.recipe.SweetInfuserRecipe;
import aldinh777.potatoheadshot.util.ItemHelper;
import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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

		for (SweetInfuserRecipe recipe : SweetInfuserRecipe.getRecipes()) {
			addRecipe(jeiRecipes, recipe.getOutput(), recipe.getCatalyst(), recipe.getInputs());
		}

		return jeiRecipes;
	}
	

	private static void addRecipe(List<InfuserRecipe> list, ItemStack output, Item middle, Item... fuses) {
		List<ItemStack> inputs = Lists.newArrayList(new ItemStack(middle));
		
		for (Item fuse : fuses) {
			inputs.add(ItemHelper.getDyeFromItem(fuse));
		}
		list.add(new InfuserRecipe(inputs, output));
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, this.inputs);
		ingredients.setOutput(ItemStack.class, this.output);
	}
}
