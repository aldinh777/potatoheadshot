package aldinh777.potatoheadshot.jei.cauldron;

import aldinh777.potatoheadshot.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import mezz.jei.api.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ManaCauldronRecipeCategory extends AbstractCauldronRecipeCategory<FireCauldronRecipe> {
	
	private static final String path = "potatoheadshot:textures/gui/cauldron/cauldron_mana.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation("potatoheadshot:textures/gui/cauldron/cauldron_mana.png");
	
	public ManaCauldronRecipeCategory(IGuiHelper gui) {
		super(gui, TEXTURE, new ItemStack(PotatoBlocks.MANA_CAULDRON));
	}

	@Override
	public String getUid() {
		return JeiPotatoPlugin.CAULDRON_MANA.toString();
	}

	@Override
	public String getTitle() {
		return "Mana Cauldron";
	}
}
