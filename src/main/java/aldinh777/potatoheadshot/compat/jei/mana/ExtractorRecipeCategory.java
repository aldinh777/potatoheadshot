package aldinh777.potatoheadshot.compat.jei.mana;

import aldinh777.potatoheadshot.compat.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import mezz.jei.api.IGuiHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ExtractorRecipeCategory extends AbstractManaRecipeCategory<ExtractorRecipe> {
	
	public ExtractorRecipeCategory(IGuiHelper gui) {
		super(gui, new ItemStack(PotatoBlocks.MANA_EXTRACTOR));
	}

	@Nonnull
	@Override
	public String getUid() {
		return JeiPotatoPlugin.EXTRACTOR.toString();
	}

	@Nonnull
	@Override
	public String getTitle() {
		return "Mana Extractor";
	}
}
