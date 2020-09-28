package aldinh777.potatoheadshot.jei.cauldron;

import aldinh777.potatoheadshot.lists.PotatoItems;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class LifeCauldronRecipe extends AbstractCauldronRecipe {
	
	public LifeCauldronRecipe(ItemStack input, ItemStack output) {
		super(input, output, new ItemStack(PotatoItems.ROD_LIFE));
	}
	
	public static List<LifeCauldronRecipe> getRecipes() {
		List<LifeCauldronRecipe> jeiRecipes = Lists.newArrayList();
		
		Item grass = Item.getItemFromBlock(Blocks.GRASS);
		Item dirt = Item.getItemFromBlock(Blocks.DIRT);
		Item sand = Item.getItemFromBlock(Blocks.SAND);
		Item log1 = Item.getItemFromBlock(Blocks.LOG);
		Item log2 = Item.getItemFromBlock(Blocks.LOG2);
		Item mycelium = Item.getItemFromBlock(Blocks.MYCELIUM);
		Item netherRack = Item.getItemFromBlock(Blocks.NETHERRACK);
		Item endStone = Item.getItemFromBlock(Blocks.END_STONE);
		Item ice = Item.getItemFromBlock(Blocks.ICE);
		
		addRecipe(jeiRecipes, PotatoItems.LAVA_POTATO, new ItemStack(PotatoItems.LAVA_POTATO_SEED));
		addRecipe(jeiRecipes, PotatoItems.WATER_POTATO, new ItemStack(PotatoItems.WATER_POTATO_SEED));
		addRecipe(jeiRecipes, Items.FISH, new ItemStack(Items.PRISMARINE_SHARD));
		addRecipe(jeiRecipes, Items.PRISMARINE_SHARD, new ItemStack(Items.PRISMARINE_CRYSTALS));
		addRecipe(jeiRecipes, Items.CLAY_BALL, new ItemStack(Items.SLIME_BALL));
		addRecipe(jeiRecipes, Items.STRING, new ItemStack(Blocks.VINE));
		addRecipe(jeiRecipes, Items.SUGAR, new ItemStack(Items.REEDS));
		addRecipe(jeiRecipes, netherRack, new ItemStack(Items.NETHER_WART));
		addRecipe(jeiRecipes, grass, new ItemStack(Items.WHEAT_SEEDS));
		addRecipe(jeiRecipes, dirt, new ItemStack(Blocks.GRASS));
		addRecipe(jeiRecipes, sand, new ItemStack(Blocks.CACTUS));
		addRecipe(jeiRecipes, log1, new ItemStack(Blocks.SAPLING));
		addRecipe(jeiRecipes, log2, new ItemStack(Blocks.SAPLING));
		addRecipe(jeiRecipes, mycelium, new ItemStack(Blocks.BROWN_MUSHROOM));
		addRecipe(jeiRecipes, endStone, new ItemStack(Blocks.CHORUS_FLOWER));
		addRecipe(jeiRecipes, ice, new ItemStack(Blocks.SNOW));
		
		return jeiRecipes;
	}
	
	public static void addRecipe(List<LifeCauldronRecipe> list, Item input, ItemStack output) {
		list.add(new LifeCauldronRecipe(new ItemStack(input), output));
	}
}
