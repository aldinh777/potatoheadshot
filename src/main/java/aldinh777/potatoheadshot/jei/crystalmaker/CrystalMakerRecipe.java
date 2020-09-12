package aldinh777.potatoheadshot.jei.crystalmaker;

import aldinh777.potatoheadshot.lists.PotatoItems;
import com.google.common.collect.Lists;
import java.util.List;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CrystalMakerRecipe implements IRecipeWrapper {

	public static List<CrystalMakerRecipe> getRecipes() {
		return Lists.newArrayList(new CrystalMakerRecipe());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<ItemStack> inputs = Lists.newArrayList();
		inputs.add(new ItemStack(Blocks.ICE));
		inputs.add(new ItemStack(PotatoItems.RAW_SALT));
		
		ingredients.setInputs(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, new ItemStack(PotatoItems.CRYSTAL_SHARD));
	}
}
