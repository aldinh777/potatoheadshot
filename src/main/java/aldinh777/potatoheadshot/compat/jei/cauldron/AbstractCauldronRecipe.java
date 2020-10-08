package aldinh777.potatoheadshot.compat.jei.cauldron;

import aldinh777.potatoheadshot.lists.PotatoBlocks;
import com.google.common.collect.Lists;
import java.util.List;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public abstract class AbstractCauldronRecipe implements IRecipeWrapper {

	private final ItemStack input;
	private final ItemStack output;
	private final ItemStack rod;
	
	public AbstractCauldronRecipe(ItemStack input, ItemStack output, ItemStack rod) {
		this.input = input;
		this.output = output;
		this.rod = rod;
	}
	
	public void getIngredients(IIngredients ingredients) {
		List<ItemStack> inputs = Lists.newArrayList();
		
		inputs.add(this.input);
		inputs.add(this.rod);
		inputs.add(new ItemStack(PotatoBlocks.MANA_CAULDRON));
		
		ingredients.setInputs(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, this.output);
	}
}
