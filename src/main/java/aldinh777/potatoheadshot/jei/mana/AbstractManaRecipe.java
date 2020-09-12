package aldinh777.potatoheadshot.jei.mana;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public abstract class AbstractManaRecipe implements IRecipeWrapper {

	private final ItemStack input;
	private final ItemStack output;
	
	public AbstractManaRecipe(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, this.input);
		ingredients.setOutput(ItemStack.class, this.output);
	}
}
