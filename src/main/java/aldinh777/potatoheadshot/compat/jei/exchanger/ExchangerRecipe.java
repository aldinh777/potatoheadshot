package aldinh777.potatoheadshot.compat.jei.exchanger;

import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class ExchangerRecipe implements IRecipeWrapper {

    private final ItemStack input;
    private final ItemStack output;

    public ExchangerRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public static List<ExchangerRecipe> getRecipes() {
        List<ExchangerRecipe> jeiRecipes = Lists.newArrayList();

        jeiRecipes.add(new ExchangerRecipe(
                new ItemStack(Blocks.DIRT),
                new ItemStack(Items.DIAMOND)
        ));
        jeiRecipes.add(new ExchangerRecipe(
                new ItemStack(Blocks.STONE),
                new ItemStack(Blocks.BEDROCK)
        ));

        return jeiRecipes;
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, this.input);
        ingredients.setOutput(ItemStack.class, this.output);
    }
}
