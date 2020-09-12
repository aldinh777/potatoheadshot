package aldinh777.potatoheadshot.jei.crystalcharger;

import aldinh777.potatoheadshot.lists.PotatoItems;
import com.google.common.collect.Lists;
import java.util.List;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class CrystalChargerRecipe implements IRecipeWrapper {

	public final ItemStack input;
	public final ItemStack output;
	
	public CrystalChargerRecipe(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}
	
	public static List<CrystalChargerRecipe> getRecipes() {
		List<CrystalChargerRecipe> jeiRecipes = Lists.newArrayList();
		
		jeiRecipes.add(new CrystalChargerRecipe(
						new ItemStack(PotatoItems.CRYSTAL_SHARD),
						new ItemStack(PotatoItems.CHARGED_CRYSTAL_SHARD)
		));
		jeiRecipes.add(new CrystalChargerRecipe(
						new ItemStack(PotatoItems.CHARGED_CRYSTAL),
						new ItemStack(PotatoItems.ULTIMATE_CHARGED_CRYSTAL)
		));
		
		return jeiRecipes;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, this.input);
		ingredients.setOutput(ItemStack.class, this.output);
	}
}
