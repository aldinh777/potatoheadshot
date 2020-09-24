package aldinh777.potatoheadshot.jei.cauldron;

import aldinh777.potatoheadshot.lists.PotatoItems;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NatureCauldronRecipe extends AbstractCauldronRecipe {
	
	public NatureCauldronRecipe(ItemStack input, ItemStack output) {
		super(input, output, new ItemStack(PotatoItems.ROD_NATURE));
	}
	
	public static List<NatureCauldronRecipe> getRecipes() {
		List<NatureCauldronRecipe> jeiRecipes = Lists.newArrayList();
		
		Item redMushroom = Item.getItemFromBlock(Blocks.RED_MUSHROOM);
		Item brownMushroom = Item.getItemFromBlock(Blocks.BROWN_MUSHROOM);
		Item cactus = Item.getItemFromBlock(Blocks.CACTUS);
		Item leaves = Item.getItemFromBlock(Blocks.LEAVES);
		
		Item redFlower = Item.getItemFromBlock(Blocks.RED_FLOWER);
		Item yellowFlower = Item.getItemFromBlock(Blocks.YELLOW_FLOWER);
		Item doublePlant = Item.getItemFromBlock(Blocks.DOUBLE_PLANT);
		Item sapling = Item.getItemFromBlock(Blocks.SAPLING);
		
		addRecipe(jeiRecipes, Items.WHEAT_SEEDS, new ItemStack(Items.MELON_SEEDS));
		addRecipe(jeiRecipes, Items.MELON_SEEDS, new ItemStack(Items.PUMPKIN_SEEDS));
		addRecipe(jeiRecipes, Items.PUMPKIN_SEEDS, new ItemStack(Items.BEETROOT_SEEDS));
		addRecipe(jeiRecipes, Items.BEETROOT_SEEDS, new ItemStack(Items.DYE, 1, 3));
		addRecipe(jeiRecipes, Items.NETHER_WART, new ItemStack(redMushroom));
		addRecipe(jeiRecipes, redMushroom, new ItemStack(brownMushroom));
		addRecipe(jeiRecipes, brownMushroom, new ItemStack(Items.NETHER_WART));
		addRecipe(jeiRecipes, cactus, new ItemStack(Items.REEDS));
		addRecipe(jeiRecipes, Items.REEDS, new ItemStack(cactus));
		addRecipe(jeiRecipes, PotatoItems.POTATO_LEAVES, new ItemStack(leaves));
		
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


/* Location:							C:\Users\ASUS\Downloads\jd-gui-windows-1.6.6\potatoheadshot-1.0.1-1.12.2.jar!\aldinh777\potatoheadshot\jei\cauldron\NatureCauldronRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:			 1.1.3
 */