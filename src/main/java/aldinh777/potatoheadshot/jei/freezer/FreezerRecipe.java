package aldinh777.potatoheadshot.jei.freezer;

import aldinh777.potatoheadshot.lists.PotatoItems;
import com.google.common.collect.Lists;
import java.util.List;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FreezerRecipe implements IRecipeWrapper {

	private final ItemStack input;
	private final ItemStack output;

	public FreezerRecipe(ItemStack input) {
		this(input, new ItemStack(Blocks.ICE));
	}

	public FreezerRecipe(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}
	
	public static List<FreezerRecipe> getRecipes() {
		List<FreezerRecipe> jeiRecipes = Lists.newArrayList();

		jeiRecipes.add(new FreezerRecipe(new ItemStack(Items.POTATO), new ItemStack(PotatoItems.FROZEN_POTATO)));
		jeiRecipes.add(new FreezerRecipe(new ItemStack(Items.WATER_BUCKET)));
		jeiRecipes.add(new FreezerRecipe(new ItemStack(PotatoItems.WATER_POTATO)));
		jeiRecipes.add(new FreezerRecipe(new ItemStack(PotatoItems.SWEET_WATER_BUCKET)));
		
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
