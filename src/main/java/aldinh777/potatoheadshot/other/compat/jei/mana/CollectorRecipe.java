package aldinh777.potatoheadshot.other.compat.jei.mana;

import aldinh777.potatoheadshot.other.lists.PotatoItems;
import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CollectorRecipe extends AbstractManaRecipe {

	public CollectorRecipe(ItemStack input, ItemStack output) {
		super(input, output);
	}
	
	public static List<CollectorRecipe> getRecipes() {
		List<CollectorRecipe> jeiRecipes = Lists.newArrayList();

		addRecipe(jeiRecipes, PotatoItems.GLOWING_POTATO_DUST, PotatoItems.MANA_DUST);

		return jeiRecipes;
	}

	protected static void addRecipe(List<CollectorRecipe> list, Item input, Item output) {
		ItemStack inputStack = new ItemStack(input);
		ItemStack outputStack = new ItemStack(output);
		list.add(new CollectorRecipe(inputStack, outputStack));
	}
}
