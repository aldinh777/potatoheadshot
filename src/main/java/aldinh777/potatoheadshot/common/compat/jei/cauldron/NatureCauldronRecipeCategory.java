package aldinh777.potatoheadshot.common.compat.jei.cauldron;

import aldinh777.potatoheadshot.common.compat.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import mezz.jei.api.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class NatureCauldronRecipeCategory extends AbstractCauldronRecipeCategory<NatureCauldronRecipe> {
	
	private static final String path = "potatoheadshot:textures/gui/cauldron/cauldron_nature.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation(path);

	public NatureCauldronRecipeCategory(IGuiHelper gui) {
		super(gui, TEXTURE, new ItemStack(PotatoItems.ESSENCE_NATURE));
	}

	@Nonnull
	@Override
	public String getUid() {
		return JeiPotatoPlugin.CAULDRON_NATURE.toString();
	}

	@Nonnull
	@Override
	public String getTitle() {
		return "Nature Essence";
	}
}
