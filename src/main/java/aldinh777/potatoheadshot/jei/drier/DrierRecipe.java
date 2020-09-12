package aldinh777.potatoheadshot.jei.drier;

import aldinh777.potatoheadshot.lists.PotatoItems;
import com.google.common.collect.Lists;
import java.util.List;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
		
		Item sponge = Item.getItemFromBlock(Blocks.SPONGE);
		
		addDryRecipe(jeiRecipes, Items.POTATO, new ItemStack(PotatoItems.DRIED_POTATO));
		addDryRecipe(jeiRecipes, PotatoItems.DRIED_POTATO, new ItemStack(PotatoItems.POTATO_STARCH));
		addDryRecipe(jeiRecipes, PotatoItems.SWEET_POTATO, new ItemStack(PotatoItems.DRIED_SWEET_POTATO));
		addDryRecipe(jeiRecipes, PotatoItems.DRIED_SWEET_POTATO, new ItemStack(PotatoItems.SWEET_POTATO_DUST));
		addDryRecipe(jeiRecipes, PotatoItems.GLOWING_POTATO, new ItemStack(PotatoItems.GLOWING_POTATO_DUST));
		addDryRecipe(jeiRecipes, PotatoItems.WET_POTATO, new ItemStack(Items.CLAY_BALL));
		addDryRecipe(jeiRecipes, PotatoItems.SUPER_WET_POTATO, new ItemStack(PotatoItems.SALT_POTATO));
		addDryRecipe(jeiRecipes, PotatoItems.WATER_POTATO, new ItemStack(PotatoItems.RAW_SALT));
		addDryRecipe(jeiRecipes, Items.WATER_BUCKET, new ItemStack(PotatoItems.RAW_SALT));
		addDryRecipe(jeiRecipes, PotatoItems.SWEET_WATER_BUCKET, new ItemStack(PotatoItems.RAW_SALT));
		addDryRecipe(jeiRecipes, sponge, new ItemStack(sponge));
		
		addWetRecipe(jeiRecipes, Items.POTATO, new ItemStack(PotatoItems.WET_POTATO));
		addWetRecipe(jeiRecipes, PotatoItems.WET_POTATO, new ItemStack(PotatoItems.SUPER_WET_POTATO));
		addWetRecipe(jeiRecipes, PotatoItems.SUPER_WET_POTATO, new ItemStack(PotatoItems.WATER_POTATO));
		addWetRecipe(jeiRecipes, Items.BUCKET, new ItemStack(Items.WATER_BUCKET));
		addWetRecipe(jeiRecipes, PotatoItems.SWEET_EMPTY_BUCKET, new ItemStack(PotatoItems.SWEET_WATER_BUCKET));
		addWetRecipe(jeiRecipes, PotatoItems.DRIED_POTATO, new ItemStack(Items.POTATO));
		addWetRecipe(jeiRecipes, PotatoItems.EXTRA_HOT_POTATO, new ItemStack(Items.COAL, 1, 1));
		addWetRecipe(jeiRecipes, PotatoItems.EXTREME_HOT_POTATO, new ItemStack(Items.COAL));
		addWetRecipe(jeiRecipes, PotatoItems.LAVA_POTATO, new ItemStack(Item.getItemFromBlock(Blocks.OBSIDIAN)));
		addWetRecipe(jeiRecipes, sponge, new ItemStack(sponge, 1, 1));
		
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
