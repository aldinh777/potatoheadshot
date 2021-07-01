package aldinh777.potatoheadshot.other.compat.jei;

import aldinh777.potatoheadshot.content.containers.ContainerDrier;
import aldinh777.potatoheadshot.content.guis.GuiDrier;
import aldinh777.potatoheadshot.other.compat.jei.cauldron.*;
import aldinh777.potatoheadshot.other.compat.jei.drier.DrierRecipe;
import aldinh777.potatoheadshot.other.compat.jei.drier.DrierRecipeCategory;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.util.ResourceLocation;

@JEIPlugin
public class JeiPotatoPlugin implements IModPlugin {
	
	public static final ResourceLocation DRIER = createUid("drier");

	public static final ResourceLocation CAULDRON_MANA = createUid("mana_cauldron");
	public static final ResourceLocation CAULDRON_FIRE = createUid("fire_cauldron");
	public static final ResourceLocation CAULDRON_LIFE = createUid("life_cauldron");
	public static final ResourceLocation CAULDRON_NATURE = createUid("nature_cauldron");

	private static ResourceLocation createUid(String name) {
		return new ResourceLocation("potatoheadshot", name);
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers helpers = registry.getJeiHelpers();
		IGuiHelper gui = helpers.getGuiHelper();

		registry.addRecipeCategories(new DrierRecipeCategory(gui));
		registry.addRecipeCategories(new ManaCauldronRecipeCategory(gui));
		registry.addRecipeCategories(new FireCauldronRecipeCategory(gui));
		registry.addRecipeCategories(new LifeCauldronRecipeCategory(gui));
		registry.addRecipeCategories(new NatureCauldronRecipeCategory(gui));
	}
	
	@Override
	public void register(IModRegistry registry) {
		IRecipeTransferRegistry recipeTransfer = registry.getRecipeTransferRegistry();

		registry.addRecipes(DrierRecipe.getRecipes(), DRIER.toString());
		registry.addRecipeClickArea(GuiDrier.class, 109, 28, 20, 22, DRIER.toString());
		registry.addRecipeClickArea(GuiDrier.class, 109, 60, 20, 22, DRIER.toString());
		recipeTransfer.addRecipeTransferHandler(ContainerDrier.class, DRIER.toString(), 1, 4, 5, 36);

		registry.addRecipes(ManaCauldronRecipe.getRecipes(), CAULDRON_MANA.toString());
		registry.addRecipes(FireCauldronRecipe.getRecipes(), CAULDRON_FIRE.toString());
		registry.addRecipes(LifeCauldronRecipe.getRecipes(), CAULDRON_LIFE.toString());
		registry.addRecipes(NatureCauldronRecipe.getRecipes(), CAULDRON_NATURE.toString());
	}
}
