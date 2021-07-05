package aldinh777.potatoheadshot.common.compat.jei.drier;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.common.compat.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.common.lists.PotatoBlocks;
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

public class DrierRecipeCategory implements IRecipeCategory<DrierRecipe> {

	private static final String path = "potatoheadshot:textures/gui/container/potato_drier.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation(path);
	
	private static final int input1 = 1;
	private static final int output1 = 2;
	private static final int input2 = 3;
	private static final int output2 = 4;

	private final IDrawable background;
	private final IDrawable icon;
	
	public DrierRecipeCategory(IGuiHelper gui) {
		this.background = gui.createDrawable(TEXTURE, 64, 12, 100, 66);
		this.icon = gui.createDrawableIngredient(new ItemStack(PotatoBlocks.POTATO_DRIER));
	}

	@Nonnull
	@Override
	public String getUid() {
		return JeiPotatoPlugin.DRIER.toString();
	}

	@Nonnull
	@Override
	public String getTitle() {
		return "Potato Drier";
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
	public void setRecipe(IRecipeLayout recipeLayout, @Nonnull DrierRecipe recipeWrapper, @Nonnull IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(input1, true, 22, 13);
		stacks.init(input2, true, 22, 45);
		stacks.init(output1, false, 76, 9);
		stacks.init(output2, false, 76, 41);
		stacks.set(ingredients);
	}
}
