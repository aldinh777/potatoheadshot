package aldinh777.potatoheadshot.other.compat.jei.crystalcharger;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.other.compat.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.other.lists.PotatoBlocks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class CrystalChargerRecipeCategory implements IRecipeCategory<CrystalChargerRecipe> {
	
	private static final String path = "potatoheadshot:textures/gui/container/sweet_crystal_charger.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation(path);
	
	private static final int input = 0;
	private static final int output = 1;

	private final IDrawable background;
	private final IDrawable icon;
	
	public CrystalChargerRecipeCategory(IGuiHelper gui) {
		this.background = gui.createDrawable(TEXTURE, 36, 18, 130, 32);
		this.icon = gui.createDrawableIngredient(new ItemStack(PotatoBlocks.SWEET_CRYSTAL_MAKER));
	}

	@Nonnull
	@Override
	public String getUid() {
		return JeiPotatoPlugin.CRYSTAL_CHARGER.toString();
	}

	@Nonnull
	@Override
	public String getTitle() {
		return "Crystal Charger";
	}

	@Nonnull
	@Override
	public String getModName() {
		return PotatoHeadshot.NAME;
	}

	@Nonnull
	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, @Nonnull CrystalChargerRecipe recipeWrapper, @Nonnull IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(input, true, 3, 4);
		stacks.init(output, false, 104, 8);
		stacks.set(ingredients);
	}
}
