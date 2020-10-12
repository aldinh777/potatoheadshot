package aldinh777.potatoheadshot.compat.jei.cauldron;

import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.recipes.CauldronRecipe;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class NatureCauldronRecipe extends AbstractCauldronRecipe {
	
	public NatureCauldronRecipe(ItemStack input, ItemStack output) {
		super(input, output, new ItemStack(PotatoItems.ROD_NATURE));
	}
	
	public static List<NatureCauldronRecipe> getRecipes() {
		List<NatureCauldronRecipe> jeiRecipes = Lists.newArrayList();

		Item redFlower = Item.getItemFromBlock(Blocks.RED_FLOWER);
		Item yellowFlower = Item.getItemFromBlock(Blocks.YELLOW_FLOWER);
		Item doublePlant = Item.getItemFromBlock(Blocks.DOUBLE_PLANT);
		Item sapling = Item.getItemFromBlock(Blocks.SAPLING);
		
		for (CauldronRecipe recipe : CauldronRecipe.getNatureRecipes()) {
			addRecipe(jeiRecipes, recipe.getInput(), recipe.getOutput());
		}

		addRecipe(jeiRecipes, createStack(Items.DYE, 3), createStack(Items.WHEAT_SEEDS));
		
		addRecipe(jeiRecipes, createStack(redFlower, 0), createStack(yellowFlower));
		addRecipe(jeiRecipes, createStack(yellowFlower), createStack(redFlower, 1));
		addRecipe(jeiRecipes, createStack(redFlower, 1), createStack(redFlower, 2));
		addRecipe(jeiRecipes, createStack(redFlower, 2), createStack(redFlower, 3));
		addRecipe(jeiRecipes, createStack(redFlower, 3), createStack(redFlower, 4));
		addRecipe(jeiRecipes, createStack(redFlower, 4), createStack(redFlower, 5));
		addRecipe(jeiRecipes, createStack(redFlower, 5), createStack(redFlower, 6));
		addRecipe(jeiRecipes, createStack(redFlower, 6), createStack(redFlower, 7));
		addRecipe(jeiRecipes, createStack(redFlower, 7), createStack(redFlower, 8));
		addRecipe(jeiRecipes, createStack(redFlower, 8), createStack(redFlower, 0));
		
		addRecipe(jeiRecipes, createStack(sapling, 0), createStack(sapling, 1));
		addRecipe(jeiRecipes, createStack(sapling, 1), createStack(sapling, 2));
		addRecipe(jeiRecipes, createStack(sapling, 2), createStack(sapling, 3));
		addRecipe(jeiRecipes, createStack(sapling, 3), createStack(sapling, 4));
		addRecipe(jeiRecipes, createStack(sapling, 4), createStack(sapling, 5));
		addRecipe(jeiRecipes, createStack(sapling, 5), createStack(sapling, 0));
		
		addRecipe(jeiRecipes, createStack(doublePlant, 0), createStack(doublePlant, 1));
		addRecipe(jeiRecipes, createStack(doublePlant, 1), createStack(doublePlant, 2));
		addRecipe(jeiRecipes, createStack(doublePlant, 2), createStack(doublePlant, 3));
		addRecipe(jeiRecipes, createStack(doublePlant, 3), createStack(doublePlant, 4));
		addRecipe(jeiRecipes, createStack(doublePlant, 4), createStack(doublePlant, 5));
		addRecipe(jeiRecipes, createStack(doublePlant, 5), createStack(doublePlant, 0));
		
		return jeiRecipes;
	}
	
	private static ItemStack createStack(Item item) {
		return createStack(item, 0);
	}
	
	private static ItemStack createStack(Item item, int meta) {
		return new ItemStack(item, 1, meta);
	}
	
	public static void addRecipe(List<NatureCauldronRecipe> list, ItemStack input, ItemStack output) {
		list.add(new NatureCauldronRecipe(input, output));
	}
	
	public static void addRecipe(List<NatureCauldronRecipe> list, Item input, ItemStack output) {
		list.add(new NatureCauldronRecipe(new ItemStack(input), output));
	}
}
