package aldinh777.potatoheadshot.compat.jei;

import aldinh777.potatoheadshot.block.backup.containers.*;
import aldinh777.potatoheadshot.block.backup.guis.*;
import aldinh777.potatoheadshot.block.containers.ContainerDrier;
import aldinh777.potatoheadshot.block.guis.GuiDrier;
import aldinh777.potatoheadshot.compat.jei.cauldron.*;
import aldinh777.potatoheadshot.compat.jei.crystalcharger.CrystalChargerRecipe;
import aldinh777.potatoheadshot.compat.jei.crystalcharger.CrystalChargerRecipeCategory;
import aldinh777.potatoheadshot.compat.jei.crystalmaker.CrystalMakerRecipe;
import aldinh777.potatoheadshot.compat.jei.crystalmaker.CrystalMakerRecipeCategory;
import aldinh777.potatoheadshot.compat.jei.drier.DrierRecipe;
import aldinh777.potatoheadshot.compat.jei.drier.DrierRecipeCategory;
import aldinh777.potatoheadshot.compat.jei.freezer.FreezerRecipe;
import aldinh777.potatoheadshot.compat.jei.freezer.FreezerRecipeCategory;
import aldinh777.potatoheadshot.compat.jei.infuser.InfuserRecipe;
import aldinh777.potatoheadshot.compat.jei.infuser.InfuserRecipeCategory;
import aldinh777.potatoheadshot.compat.jei.mana.CollectorRecipe;
import aldinh777.potatoheadshot.compat.jei.mana.CollectorRecipeCategory;
import aldinh777.potatoheadshot.compat.jei.mana.ExtractorRecipe;
import aldinh777.potatoheadshot.compat.jei.mana.ExtractorRecipeCategory;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.util.ResourceLocation;

@JEIPlugin
public class JeiPotatoPlugin implements IModPlugin {
	
	public static final ResourceLocation DRIER = createUid("drier");
	public static final ResourceLocation FREEZER = createUid("freezer");
	public static final ResourceLocation INFUSER = createUid("infuser");
	public static final ResourceLocation CRYSTAL_MAKER = createUid("crystal_maker");
	public static final ResourceLocation CRYSTAL_CHARGER = createUid("crystal_charger");
	
	public static final ResourceLocation COLLECTOR = createUid("collector");
	public static final ResourceLocation EXTRACTOR = createUid("extractor");
	
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
		registry.addRecipeCategories(new InfuserRecipeCategory(gui));
		registry.addRecipeCategories(new FreezerRecipeCategory(gui));
		registry.addRecipeCategories(new CrystalChargerRecipeCategory(gui));
		registry.addRecipeCategories(new CrystalMakerRecipeCategory(gui));
		registry.addRecipeCategories(new CollectorRecipeCategory(gui));
		registry.addRecipeCategories(new ExtractorRecipeCategory(gui));
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

		registry.addRecipes(InfuserRecipe.getRecipes(), INFUSER.toString());
		registry.addRecipeClickArea(GuiSweetInfuser.class, 151, 17, 16, 45, INFUSER.toString());
		recipeTransfer.addRecipeTransferHandler(ContainerSweetInfuser.class, INFUSER.toString(), 0, 7, 7, 36);

		registry.addRecipes(FreezerRecipe.getRecipes(), FREEZER.toString());
		registry.addRecipeClickArea(GuiSweetFreezer.class, 91, 36, 20, 22, FREEZER.toString());
		recipeTransfer.addRecipeTransferHandler(ContainerSweetFreezer.class, FREEZER.toString(), 0, 3, 3, 36);

		registry.addRecipes(CrystalMakerRecipe.getRecipes(), CRYSTAL_MAKER.toString());
		registry.addRecipeClickArea(GuiSweetCrystalMaker.class, 62, 29, 24, 19, CRYSTAL_MAKER.toString());
		registry.addRecipeClickArea(GuiSweetCrystalMaker.class, 112, 28, 19, 20, CRYSTAL_MAKER.toString());
		recipeTransfer.addRecipeTransferHandler(ContainerSweetCrystalMaker.class, CRYSTAL_MAKER.toString(), 0, 3, 3, 36);

		registry.addRecipes(CrystalChargerRecipe.getRecipes(), CRYSTAL_CHARGER.toString());
		registry.addRecipeClickArea(GuiSweetCrystalCharger.class, 89, 23, 15, 20, CRYSTAL_CHARGER.toString());
		recipeTransfer.addRecipeTransferHandler(ContainerSweetCrystalCharger.class, CRYSTAL_CHARGER.toString(), 0, 2, 2, 36);

		registry.addRecipes(CollectorRecipe.getRecipes(), COLLECTOR.toString());
		registry.addRecipeClickArea(GuiManaCollector.class, 76, 49, 20, 22, COLLECTOR.toString());
		recipeTransfer.addRecipeTransferHandler(ContainerManaCollector.class, COLLECTOR.toString(), 0, 2, 2, 36);

		registry.addRecipes(ExtractorRecipe.getRecipes(), EXTRACTOR.toString());
		registry.addRecipeClickArea(GuiManaExtractor.class, 76, 49, 20, 22, EXTRACTOR.toString());
		recipeTransfer.addRecipeTransferHandler(ContainerManaExtractor.class, EXTRACTOR.toString(), 0, 2, 2, 36);

		registry.addRecipes(ManaCauldronRecipe.getRecipes(), CAULDRON_MANA.toString());
		registry.addRecipes(FireCauldronRecipe.getRecipes(), CAULDRON_FIRE.toString());
		registry.addRecipes(LifeCauldronRecipe.getRecipes(), CAULDRON_LIFE.toString());
		registry.addRecipes(NatureCauldronRecipe.getRecipes(), CAULDRON_NATURE.toString());
	}
}
