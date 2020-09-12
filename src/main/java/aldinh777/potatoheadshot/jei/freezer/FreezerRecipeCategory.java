package aldinh777.potatoheadshot.jei.freezer;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import javax.annotation.Nullable;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class FreezerRecipeCategory implements IRecipeCategory<FreezerRecipe> {

	private static final String path = "potatoheadshot:textures/gui/container/sweet_freezer.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation("potatoheadshot:textures/gui/container/sweet_freezer.png");

	private static final int fuel = 0;

	private static final int input = 1;
	private static final int output = 2;
	private final IDrawable background;
	private final IDrawable icon;

	public FreezerRecipeCategory(IGuiHelper gui) {
		this.background = (IDrawable)gui.createDrawable(TEXTURE, 56, 18, 101, 49);
		this.icon = gui.createDrawableIngredient(new ItemStack(PotatoBlocks.SWEET_FREEZER));
	}

	@Override
	public String getUid() {
		return JeiPotatoPlugin.FREEZER.toString();
	}

	@Override
	public String getTitle() {
		return "Sweet Freezer";
	}

	@Override
	public String getModName() {
		return PotatoHeadshot.NAME;
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Nullable
	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, FreezerRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, false, 5, 28);
		stacks.init(1, true, 5, 4);
		stacks.init(2, false, 75, 15);
		stacks.set(ingredients);
	}
}
