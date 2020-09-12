package aldinh777.potatoheadshot.jei.cauldron;

import aldinh777.potatoheadshot.PotatoHeadshot;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public abstract class AbstractCauldronRecipeCategory<T extends AbstractCauldronRecipe> implements IRecipeCategory<T> {

	protected static final int input = 0;
	protected static final int rod = 1;
	protected static final int cauldron = 2;
	protected static final int output = 3;
	protected final IDrawable background;
	protected final IDrawable icon;
	
	public AbstractCauldronRecipeCategory(IGuiHelper gui, ResourceLocation texture, ItemStack icon) {
		this.background = gui.createDrawable(texture, 17, 9, 122, 66);
		this.icon = gui.createDrawableIngredient(icon);
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
	public void setRecipe(IRecipeLayout recipeLayout, T recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 32, 3);
		stacks.init(1, true, 8, 45);
		stacks.init(2, true, 32, 23);
		stacks.init(3, false, 91, 23);
		stacks.set(ingredients);
	}
}
