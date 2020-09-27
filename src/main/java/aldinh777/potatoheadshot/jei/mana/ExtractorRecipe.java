package aldinh777.potatoheadshot.jei.mana;

import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ExtractorRecipe extends AbstractManaRecipe {

	public ExtractorRecipe(ItemStack input, ItemStack output) {
		super(input, output);
	}
	
	public static List<ExtractorRecipe> getRecipes() {
		List<ExtractorRecipe> jeiRecipes = Lists.newArrayList();
		
		Item flower = Item.getItemFromBlock(Blocks.RED_FLOWER);
		Item stone = Item.getItemFromBlock(Blocks.STONE);
		Item torch = Item.getItemFromBlock(Blocks.TORCH);
		
		Item manaFlower = Item.getItemFromBlock(PotatoBlocks.GLOWING_MANA_FLOWER);
		Item manaStone = Item.getItemFromBlock(PotatoBlocks.GLOWING_MANA_STONE);
		Item manaTorch = Item.getItemFromBlock(PotatoBlocks.GLOWING_MANA_TORCH);
		
		addRecipe(jeiRecipes, PotatoItems.GLOWING_POTATO_DUST, PotatoItems.GLOWING_MANA_DUST);
		addRecipe(jeiRecipes, flower, manaFlower);
		addRecipe(jeiRecipes, stone, manaStone);
		addRecipe(jeiRecipes, torch, manaTorch);
		
		return jeiRecipes;
	}
	
	protected static void addRecipe(List<ExtractorRecipe> list, Item output, Item input) {
		ItemStack inputStack = new ItemStack(input);
		ItemStack outputStack = new ItemStack(output);
		list.add(new ExtractorRecipe(inputStack, outputStack));
	}
}
