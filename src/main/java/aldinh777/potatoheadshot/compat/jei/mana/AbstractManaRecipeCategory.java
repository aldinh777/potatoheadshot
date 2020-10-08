package aldinh777.potatoheadshot.compat.jei.mana;

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

public abstract class AbstractManaRecipeCategory<T extends AbstractManaRecipe> implements IRecipeCategory<T> {

	private static final String path = "potatoheadshot:textures/gui/container/mana_collector.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation(path);
	
	protected static final int input = 0;
	protected static final int output = 1;

	protected final IDrawable background;
	protected final IDrawable icon;
	
	public AbstractManaRecipeCategory(IGuiHelper gui, ItemStack icon) {
		this.background = gui.createDrawable(TEXTURE, 40, 38, 100, 30);
		this.icon = gui.createDrawableIngredient(icon);
	}

	@Nonnull
	@Override
	public String getModName() {
		return "PotatoHeadshot";
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
	public void setRecipe(IRecipeLayout recipeLayout, @Nonnull T recipeWrapper, @Nonnull IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(input, true, 6, 9);
		stacks.init(output, false, 76, 8);
		stacks.set(ingredients);
	}
}
