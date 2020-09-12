package aldinh777.potatoheadshot.jei.crystalcharger;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CrystalChargerRecipeCategory implements IRecipeCategory<CrystalChargerRecipe> {
	
	private static final String path = "potatoheadshot:textures/gui/container/sweet_crystal_charger.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation("potatoheadshot:textures/gui/container/sweet_crystal_charger.png");
	
	private static final int input = 0;
	
	private static final int output = 1;
	private final IDrawable background;
	private final IDrawable icon;
	
	public CrystalChargerRecipeCategory(IGuiHelper gui) {
		this.background = gui.createDrawable(TEXTURE, 36, 18, 130, 32);
		this.icon = gui.createDrawableIngredient(new ItemStack(PotatoBlocks.SWEET_CRYSTAL_MAKER));
	}

	@Override
	public String getUid() {
		return JeiPotatoPlugin.CRYSTAL_CHARGER.toString();
	}

	@Override
	public String getTitle() {
		return "Crystal Charger";
	}

	@Override
	public String getModName() {
		return PotatoHeadshot.NAME;
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CrystalChargerRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 3, 4);
		stacks.init(1, false, 104, 8);
		stacks.set(ingredients);
	}
}
