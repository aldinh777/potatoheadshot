package aldinh777.potatoheadshot.common.compat.jei.cauldron;

import aldinh777.potatoheadshot.common.compat.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import mezz.jei.api.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class FireCauldronRecipeCategory extends AbstractCauldronRecipeCategory<FireCauldronRecipe> {
	
	private static final String path = "potatoheadshot:textures/gui/cauldron/cauldron_fire.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation(path);
	
	public FireCauldronRecipeCategory(IGuiHelper gui) {
		super(gui, TEXTURE, new ItemStack(PotatoItems.ESSENCE_FIRE));
	}

	@Nonnull
	@Override
	public String getUid() {
		return JeiPotatoPlugin.CAULDRON_FIRE.toString();
	}

	@Nonnull
	@Override
	public String getTitle() {
		return "Fire Essence";
	}
}
