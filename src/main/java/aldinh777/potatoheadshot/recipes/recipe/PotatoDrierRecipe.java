package aldinh777.potatoheadshot.recipes.recipe;

import aldinh777.potatoheadshot.handler.ConfigHandler;
import aldinh777.potatoheadshot.lists.PotatoItems;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class PotatoDrierRecipe {

    private final Item input;
    private final ItemStack output;

    public PotatoDrierRecipe(Item input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public Item getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public static List<PotatoDrierRecipe> getDryRecipes() {
        List<PotatoDrierRecipe> recipes = Lists.newArrayList();
        Item sponge = Item.getItemFromBlock(Blocks.SPONGE);

        addRecipe(recipes, Items.POTATO, new ItemStack(PotatoItems.DRIED_POTATO));
        addRecipe(recipes, PotatoItems.DRIED_POTATO, new ItemStack(PotatoItems.POTATO_STARCH));
        addRecipe(recipes, PotatoItems.SWEET_POTATO, new ItemStack(PotatoItems.DRIED_SWEET_POTATO));
        addRecipe(recipes, PotatoItems.DRIED_SWEET_POTATO, new ItemStack(PotatoItems.SWEET_POTATO_DUST));
        addRecipe(recipes, PotatoItems.GLOWING_POTATO, new ItemStack(PotatoItems.GLOWING_POTATO_DUST));
        if (ConfigHandler.WET_POTATO) {
            addRecipe(recipes, PotatoItems.WET_POTATO, new ItemStack(Items.CLAY_BALL));
            addRecipe(recipes, PotatoItems.SUPER_WET_POTATO, new ItemStack(PotatoItems.SALT_POTATO));
            addRecipe(recipes, PotatoItems.WATER_POTATO, new ItemStack(PotatoItems.RAW_SALT));
        }
        addRecipe(recipes, Items.WATER_BUCKET, new ItemStack(PotatoItems.RAW_SALT));
        if (ConfigHandler.SWEET_BUCKET) {
            addRecipe(recipes, PotatoItems.SWEET_WATER_BUCKET, new ItemStack(PotatoItems.RAW_SALT));
        }
        addRecipe(recipes, sponge, new ItemStack(Blocks.SPONGE));

        return recipes;
    }

    public static List<PotatoDrierRecipe> getWetRecipes() {
        List<PotatoDrierRecipe> recipes = Lists.newArrayList();
        Item sponge = Item.getItemFromBlock(Blocks.SPONGE);

        addRecipe(recipes, Items.POTATO, new ItemStack(PotatoItems.WET_POTATO));
        if (ConfigHandler.WET_POTATO) {
            addRecipe(recipes, PotatoItems.WET_POTATO, new ItemStack(PotatoItems.SUPER_WET_POTATO));
            addRecipe(recipes, PotatoItems.SUPER_WET_POTATO, new ItemStack(PotatoItems.WATER_POTATO));
        }
        addRecipe(recipes, Items.BUCKET, new ItemStack(Items.WATER_BUCKET));
        if (ConfigHandler.SWEET_BUCKET) {
            addRecipe(recipes, PotatoItems.SWEET_EMPTY_BUCKET, new ItemStack(PotatoItems.SWEET_WATER_BUCKET));
        }
        addRecipe(recipes, PotatoItems.DRIED_POTATO, new ItemStack(Items.POTATO));
        if (ConfigHandler.HOT_POTATO) {
            addRecipe(recipes, PotatoItems.EXTRA_HOT_POTATO, new ItemStack(Items.COAL, 1, 1));
            addRecipe(recipes, PotatoItems.EXTREME_HOT_POTATO, new ItemStack(Items.COAL));
            addRecipe(recipes, PotatoItems.LAVA_POTATO, new ItemStack(Item.getItemFromBlock(Blocks.OBSIDIAN)));
        }
        addRecipe(recipes, sponge, new ItemStack(sponge, 1, 1));

        return recipes;
    }

    public static void addRecipe(List<PotatoDrierRecipe> recipes, Item input, ItemStack output) {
        recipes.add(new PotatoDrierRecipe(input, output));
    }
}
