package aldinh777.potatoheadshot.other.compat.jei.crystalmaker;

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

public class CrystalMakerRecipeCategory implements IRecipeCategory<CrystalMakerRecipe> {
	
	private static final String path = "potatoheadshot:textures/gui/container/sweet_crystal_maker.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation(path);
	
	private static final int input1 = 0;
	private static final int input2 = 1;
	private static final int output = 2;

	private final IDrawable background;
	private final IDrawable icon;
	
	public CrystalMakerRecipeCategory(IGuiHelper gui) {
		this.background = gui.createDrawable(TEXTURE, 36, 18, 131, 53);
		this.icon = gui.createDrawableIngredient(new ItemStack(PotatoBlocks.SWEET_CRYSTAL_MAKER));
	}

	@Nonnull
	@Override
	public String getUid() {
		return JeiPotatoPlugin.CRYSTAL_MAKER.toString();
	}

	@Nonnull
	@Override
	public String getTitle() {
		return "Sweet Crystal Maker";
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
	public void setRecipe(IRecipeLayout recipeLayout, @Nonnull CrystalMakerRecipe recipeWrapper, @Nonnull IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(input1, true, 3, 33);
		stacks.init(input2, true, 3, 4);
		stacks.init(output, false, 104, 8);
		stacks.set(ingredients);
	}
}
