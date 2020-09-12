package aldinh777.potatoheadshot.jei.cauldron;

import aldinh777.potatoheadshot.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.lists.PotatoItems;
import mezz.jei.api.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class FireCauldronRecipeCategory extends AbstractCauldronRecipeCategory {
	
	private static final String path = "potatoheadshot:textures/gui/cauldron/cauldron_fire.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation("potatoheadshot:textures/gui/cauldron/cauldron_fire.png");
	
	public FireCauldronRecipeCategory(IGuiHelper gui) {
		super(gui, TEXTURE, new ItemStack(PotatoItems.ROD_FIRE));
	}

	@Override	
	public String getUid() {
		return JeiPotatoPlugin.CAULDRON_FIRE.toString();
	}

	@Override
	public String getTitle() {
		return "Mana Fire Cauldron";
	}
}
