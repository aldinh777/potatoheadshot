package aldinh777.potatoheadshot.jei.crystalmaker;

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

public class CrystalMakerRecipeCategory implements IRecipeCategory<CrystalMakerRecipe> {
	
	private static final String path = "potatoheadshot:textures/gui/container/sweet_crystal_maker.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation("potatoheadshot:textures/gui/container/sweet_crystal_maker.png");
	
	private static final int input1 = 0;
	
	private static final int input2 = 1;
	private static final int output = 2;
	private final IDrawable background;
	private final IDrawable icon;
	
	public CrystalMakerRecipeCategory(IGuiHelper gui) {
		this.background = (IDrawable)gui.createDrawable(TEXTURE, 36, 18, 131, 53);
		this.icon = gui.createDrawableIngredient(new ItemStack(PotatoBlocks.SWEET_CRYSTAL_MAKER));
	}

	@Override
	public String getUid() {
		return JeiPotatoPlugin.CRYSTAL_MAKER.toString();
	}

	@Override
	public String getTitle() {
		return "Sweet Crystal Maker";
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
	public void setRecipe(IRecipeLayout recipeLayout, CrystalMakerRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 3, 33);
		stacks.init(1, true, 3, 4);
		stacks.init(2, false, 104, 8);
		stacks.set(ingredients);
	}
}
