package aldinh777.potatoheadshot.jei.freezer;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FreezerRecipeCategory implements IRecipeCategory<FreezerRecipe> {

	private static final String path = "potatoheadshot:textures/gui/container/sweet_freezer.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation(path);

	private static final int fuel = 0;
	private static final int input = 1;
	private static final int output = 2;

	private final IDrawable background;
	private final IDrawable icon;

	public FreezerRecipeCategory(IGuiHelper gui) {
		this.background = gui.createDrawable(TEXTURE, 56, 18, 101, 49);
		this.icon = gui.createDrawableIngredient(new ItemStack(PotatoBlocks.SWEET_FREEZER));
	}

	@Nonnull
	@Override
	public String getUid() {
		return JeiPotatoPlugin.FREEZER.toString();
	}

	@Nonnull
	@Override
	public String getTitle() {
		return "Sweet Freezer";
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

	@Nullable
	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, @Nonnull FreezerRecipe recipeWrapper, @Nonnull IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(fuel, false, 5, 28);
		stacks.init(input, true, 5, 4);
		stacks.init(output, false, 75, 15);
		stacks.set(ingredients);
	}
}
