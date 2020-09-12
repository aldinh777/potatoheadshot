package aldinh777.potatoheadshot.jei.infuser;

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

public class InfuserRecipeCategory implements IRecipeCategory<InfuserRecipe> {
	
	private static final String path = "potatoheadshot:textures/gui/container/sweet_infuser_jei.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation("potatoheadshot:textures/gui/container/sweet_infuser_jei.png");
	
	private static final int middle = 0;
	
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
		this.background = (IDrawable)gui.createDrawable(TEXTURE, 10, 4, 150, 76);
		this.icon = gui.createDrawableIngredient(new ItemStack(PotatoBlocks.SWEET_INFUSER));
	}

	@Override
	public String getUid() {
		return JeiPotatoPlugin.INFUSER.toString();
	}

	@Override
	public String getTitle() {
		return "Sweet Infuser";
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
	public void setRecipe(IRecipeLayout recipeLayout, InfuserRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 32, 29);
		stacks.init(1, true, 6, 16);
		stacks.init(2, true, 32, 3);
		stacks.init(3, true, 58, 16);
		stacks.init(4, true, 6, 42);
		stacks.init(5, true, 32, 55);
		stacks.init(6, true, 58, 42);
		stacks.init(7, false, 125, 30);
		stacks.set(ingredients);
	}
}
