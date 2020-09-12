package aldinh777.potatoheadshot.jei.mana;

import aldinh777.potatoheadshot.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import mezz.jei.api.IGuiHelper;
import net.minecraft.item.ItemStack;

public class ExtractorRecipeCategory extends AbstractManaRecipeCategory<ExtractorRecipe> {
	
	public ExtractorRecipeCategory(IGuiHelper gui) {
		super(gui, new ItemStack(PotatoBlocks.MANA_EXTRACTOR));
	}

	@Override
	public String getUid() {
		return JeiPotatoPlugin.EXTRACTOR.toString();
	}

	@Override
	public String getTitle() {
		return "Mana Extractor";
	}
}
