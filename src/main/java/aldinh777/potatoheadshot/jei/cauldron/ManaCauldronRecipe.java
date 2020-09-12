package aldinh777.potatoheadshot.jei.cauldron;

import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ManaCauldronRecipe extends AbstractCauldronRecipe {

	public ManaCauldronRecipe(ItemStack input, ItemStack output) {
		super(input, output, new ItemStack(PotatoItems.ROD_MANA));
	}
	
	public static List<ManaCauldronRecipe> getRecipes() {
		List<ManaCauldronRecipe> jeiRecipes = Lists.newArrayList();
		
		Item flower = Item.getItemFromBlock(Blocks.RED_FLOWER);
		Item torch = Item.getItemFromBlock(Blocks.TORCH);
		Item stone = Item.getItemFromBlock(Blocks.STONE);
		
		addRecipe(jeiRecipes, PotatoItems.GLOWING_POTATO_DUST, new ItemStack(PotatoItems.GLOWING_MANA_DUST));
		addRecipe(jeiRecipes, PotatoItems.POTATO_KNIFE, new ItemStack(PotatoItems.POTATO_MANA_KNIFE));
		addRecipe(jeiRecipes, flower, new ItemStack(PotatoBlocks.GLOWING_MANA_FLOWER));
		addRecipe(jeiRecipes, torch, new ItemStack(PotatoBlocks.GLOWING_MANA_TORCH));
		addRecipe(jeiRecipes, stone, new ItemStack(PotatoBlocks.GLOWING_MANA_STONE));
		addRecipe(jeiRecipes, PotatoItems.CRYSTAL_SHARD, new ItemStack(PotatoItems.CONCENTRATED_CRYSTAL_SHARD));
		addRecipe(jeiRecipes, PotatoItems.CONCENTRATED_CRYSTAL, new ItemStack(PotatoItems.ULTIMATE_CONCENTRATED_CRYSTAL));

		
		return jeiRecipes;
	}

	public static void addRecipe(List<ManaCauldronRecipe> list, Item input, ItemStack output) {
		list.add(new ManaCauldronRecipe(new ItemStack(input), output));
	}
}
