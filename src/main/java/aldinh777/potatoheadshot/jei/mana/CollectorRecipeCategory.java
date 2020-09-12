package aldinh777.potatoheadshot.jei.mana;

import aldinh777.potatoheadshot.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import mezz.jei.api.IGuiHelper;
import net.minecraft.item.ItemStack;

public class CollectorRecipeCategory extends AbstractManaRecipeCategory<CollectorRecipe> {

	public CollectorRecipeCategory(IGuiHelper gui) {
		super(gui, new ItemStack(PotatoBlocks.MANA_COLLECTOR));
	}

	@Override
	public String getUid() {
		return JeiPotatoPlugin.COLLECTOR.toString();
	}

	@Override
	public String getTitle() {
		return "Mana Collector";
	}
}
