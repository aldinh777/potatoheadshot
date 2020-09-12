package aldinh777.potatoheadshot.jei.cauldron;

import aldinh777.potatoheadshot.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.lists.PotatoItems;
import mezz.jei.api.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LifeCauldronRecipeCategory
	extends AbstractCauldronRecipeCategory<LifeCauldronRecipe> {
	private static final String path = "potatoheadshot:textures/gui/cauldron/cauldron_life.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation("potatoheadshot:textures/gui/cauldron/cauldron_life.png");
	
	public LifeCauldronRecipeCategory(IGuiHelper gui) {
		super(gui, TEXTURE, new ItemStack(PotatoItems.ROD_LIFE));
	}

	@Override
	public String getUid() {
		return JeiPotatoPlugin.CAULDRON_LIFE.toString();
	}

	@Override
	public String getTitle() {
		return "Mana Life Cauldron";
	}
}
