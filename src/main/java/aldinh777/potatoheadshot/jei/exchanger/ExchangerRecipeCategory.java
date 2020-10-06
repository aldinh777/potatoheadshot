package aldinh777.potatoheadshot.jei.exchanger;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.jei.JeiPotatoPlugin;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class ExchangerRecipeCategory implements IRecipeCategory<ExchangerRecipe> {

    private static final String path = "potatoheadshot:textures/gui/container/void_exchanger.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(path);

    private static final int input = 0;
    private static final int output = 1;

    private final IDrawable background;
    private final IDrawable icon;

    public ExchangerRecipeCategory(IGuiHelper gui) {
        this.background = gui.createDrawable(TEXTURE, 32, 16, 112, 52);
        this.icon = gui.createDrawableIngredient(new ItemStack(PotatoBlocks.VOID_EXCHANGER));
    }

    @Nonnull
    @Override
    public String getUid() {
        return JeiPotatoPlugin.VOID_EXCHANGER.toString();
    }

    @Nonnull
    @Override
    public String getTitle() {
        return "Void Exchanger";
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

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @Nonnull ExchangerRecipe recipe, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(input, true, 7, 31);
        stacks.init(output, false, 85, 30);
        stacks.set(ingredients);
    }
}
