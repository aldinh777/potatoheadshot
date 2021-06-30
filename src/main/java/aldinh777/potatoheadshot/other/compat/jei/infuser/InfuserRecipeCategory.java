package aldinh777.potatoheadshot.other.compat.jei.infuser;

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
import javax.annotation.Nullable;

public class InfuserRecipeCategory implements IRecipeCategory<InfuserRecipe> {
	
	private static final String path = "potatoheadshot:textures/gui/container/sweet_infuser_jei.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation(path);
	
	private static final int input = 0;
	private static final int fuse1 = 1;
	private static final int fuse2 = 2;
	private static final int fuse3 = 3;
	private static final int fuse4 = 4;
	private static final int fuse5 = 5;
	private static final int fuse6 = 6;
	private static final int output = 7;

	private final IDrawable background;
	private final IDrawable icon;
	
	public InfuserRecipeCategory(IGuiHelper gui) {
		this.background = gui.createDrawable(TEXTURE, 10, 4, 150, 76);
		this.icon = gui.createDrawableIngredient(new ItemStack(PotatoBlocks.SWEET_INFUSER));
	}

	@Nonnull
	@Override
	public String getUid() {
		return JeiPotatoPlugin.INFUSER.toString();
	}

	@Nonnull
	@Override
	public String getTitle() {
		return "Sweet Infuser";
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
	public void setRecipe(IRecipeLayout recipeLayout, @Nonnull InfuserRecipe recipeWrapper, @Nonnull IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(input, true, 32, 29);
		stacks.init(fuse1, true, 6, 16);
		stacks.init(fuse2, true, 32, 3);
		stacks.init(fuse3, true, 58, 16);
		stacks.init(fuse4, true, 6, 42);
		stacks.init(fuse5, true, 32, 55);
		stacks.init(fuse6, true, 58, 42);
		stacks.init(output, false, 125, 30);
		stacks.set(ingredients);
	}
}
