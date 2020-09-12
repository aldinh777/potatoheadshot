package aldinh777.potatoheadshot.jei.cauldron;

import aldinh777.potatoheadshot.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.lists.PotatoItems;
import mezz.jei.api.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class NatureCauldronRecipeCategory extends AbstractCauldronRecipeCategory<NatureCauldronRecipe> {
	
	private static final String path = "potatoheadshot:textures/gui/cauldron/cauldron_nature.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation("potatoheadshot:textures/gui/cauldron/cauldron_nature.png");
	
	public NatureCauldronRecipeCategory(IGuiHelper gui) {
		super(gui, TEXTURE, new ItemStack(PotatoItems.ROD_NATURE));
	}

	@Override
	public String getUid() {
		return JeiPotatoPlugin.CAULDRON_NATURE.toString();
	}

	@Override
	public String getTitle() {
		return "Mana Nature Cauldron";
	}
}
