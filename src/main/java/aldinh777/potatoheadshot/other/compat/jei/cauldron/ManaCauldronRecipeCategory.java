package aldinh777.potatoheadshot.other.compat.jei.cauldron;

import aldinh777.potatoheadshot.other.compat.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.other.lists.PotatoItems;
import mezz.jei.api.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class ManaCauldronRecipeCategory extends AbstractCauldronRecipeCategory<ManaCauldronRecipe> {
	
	private static final String path = "potatoheadshot:textures/gui/cauldron/cauldron_mana.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation(path);

	public ManaCauldronRecipeCategory(IGuiHelper gui) {
		super(gui, TEXTURE, new ItemStack(PotatoItems.ESSENCE_MANA));
	}

	@Nonnull
	@Override
	public String getUid() {
		return JeiPotatoPlugin.CAULDRON_MANA.toString();
	}

	@Nonnull
	@Override
	public String getTitle() {
		return "Mana Cauldron";
	}
}
