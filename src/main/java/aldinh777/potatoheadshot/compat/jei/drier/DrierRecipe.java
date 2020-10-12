package aldinh777.potatoheadshot.compat.jei.drier;

import aldinh777.potatoheadshot.recipes.PotatoDrierRecipe;
import com.google.common.collect.Lists;
import java.util.List;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DrierRecipe implements IRecipeWrapper {
	
	private final List<ItemStack> inputs;
	private final List<ItemStack> output;
	
	public DrierRecipe(List<ItemStack> inputs, List<ItemStack> output) {
		this.inputs = inputs;
		this.output = output;
	}
	
	public static List<DrierRecipe> getRecipes() {
		List<DrierRecipe> jeiRecipes = Lists.newArrayList();

		for (PotatoDrierRecipe dryRecipe : PotatoDrierRecipe.getDryRecipes()) {
			addDryRecipe(jeiRecipes, dryRecipe.getInput(), dryRecipe.getOutput());
		}

		for (PotatoDrierRecipe wetRecipe : PotatoDrierRecipe.getWetRecipes()) {
			addWetRecipe(jeiRecipes, wetRecipe.getInput(), wetRecipe.getOutput());
		}

		return jeiRecipes;
	}
	
	private static void addDryRecipe(List<DrierRecipe> list, Item input, ItemStack output) {
		Item sponge = Item.getItemFromBlock(Blocks.SPONGE);
		ItemStack stack = (input == sponge) ? new ItemStack(sponge, 1, 1) : new ItemStack(input);
		
		List<ItemStack> inputs = Lists.newArrayList(stack, ItemStack.EMPTY);
		List<ItemStack> outputs = Lists.newArrayList(output, ItemStack.EMPTY);
		list.add(new DrierRecipe(inputs, outputs));
	}
	
	private static void addWetRecipe(List<DrierRecipe> list, Item input, ItemStack output) {
		List<ItemStack> inputs = Lists.newArrayList(ItemStack.EMPTY, new ItemStack(input));
		List<ItemStack> outputs = Lists.newArrayList(ItemStack.EMPTY, output);
		list.add(new DrierRecipe(inputs, outputs));
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, this.inputs);
		ingredients.setOutputs(ItemStack.class, this.output);
	}
}
