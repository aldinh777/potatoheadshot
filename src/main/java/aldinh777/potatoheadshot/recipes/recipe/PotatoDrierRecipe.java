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
    private final int water;

    public PotatoDrierRecipe(Item input, ItemStack output, int waterValue) {
        this.input = input;
        this.output = output;
        this.water = waterValue;
    }

    public Item getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public int getWaterValue() {
        return this.water;
    }

    public static List<PotatoDrierRecipe> getDryRecipes() {
        List<PotatoDrierRecipe> recipes = Lists.newArrayList();
        Item sponge = Item.getItemFromBlock(Blocks.SPONGE);

        addRecipe(recipes, Items.POTATO, new ItemStack(PotatoItems.DRIED_POTATO), 100);
        addRecipe(recipes, PotatoItems.DRIED_POTATO, new ItemStack(PotatoItems.POTATO_STARCH), 0);
        addRecipe(recipes, PotatoItems.SWEET_POTATO, new ItemStack(PotatoItems.DRIED_SWEET_POTATO), 100);
        addRecipe(recipes, PotatoItems.DRIED_SWEET_POTATO, new ItemStack(PotatoItems.SWEET_POTATO_DUST), 0);
        addRecipe(recipes, PotatoItems.GLOWING_POTATO, new ItemStack(PotatoItems.GLOWING_POTATO_DUST), 0);
        if (ConfigHandler.WET_POTATO) {
            addRecipe(recipes, PotatoItems.WET_POTATO, new ItemStack(Items.CLAY_BALL), 200);
            if (ConfigHandler.SALT_POTATO) {
                addRecipe(recipes, PotatoItems.SUPER_WET_POTATO, new ItemStack(PotatoItems.SALT_POTATO), 400);
            }
            addRecipe(recipes, PotatoItems.WATER_POTATO, new ItemStack(PotatoItems.RAW_SALT), 1000);
        }
        addRecipe(recipes, Items.WATER_BUCKET, new ItemStack(PotatoItems.RAW_SALT), 1000);
        if (ConfigHandler.SWEET_BUCKET) {
            addRecipe(recipes, PotatoItems.SWEET_WATER_BUCKET, new ItemStack(PotatoItems.RAW_SALT), 1000);
        }
        addRecipe(recipes, sponge, new ItemStack(Blocks.SPONGE), 1000);

        return recipes;
    }

    public static List<PotatoDrierRecipe> getWetRecipes() {
        List<PotatoDrierRecipe> recipes = Lists.newArrayList();
        Item sponge = Item.getItemFromBlock(Blocks.SPONGE);

        addRecipe(recipes, Items.POTATO, new ItemStack(PotatoItems.WET_POTATO), 0);
        if (ConfigHandler.WET_POTATO) {
            addRecipe(recipes, PotatoItems.WET_POTATO, new ItemStack(PotatoItems.SUPER_WET_POTATO), 0);
            addRecipe(recipes, PotatoItems.SUPER_WET_POTATO, new ItemStack(PotatoItems.WATER_POTATO), 0);
        }
        addRecipe(recipes, Items.BUCKET, new ItemStack(Items.WATER_BUCKET), 0);
        if (ConfigHandler.SWEET_BUCKET) {
            addRecipe(recipes, PotatoItems.SWEET_EMPTY_BUCKET, new ItemStack(PotatoItems.SWEET_WATER_BUCKET), 0);
        }
        addRecipe(recipes, PotatoItems.DRIED_POTATO, new ItemStack(Items.POTATO), 0);
        if (ConfigHandler.HOT_POTATO) {
            addRecipe(recipes, PotatoItems.EXTRA_HOT_POTATO, new ItemStack(Items.COAL, 1, 1), 0);
            addRecipe(recipes, PotatoItems.EXTREME_HOT_POTATO, new ItemStack(Items.COAL), 0);
            addRecipe(recipes, PotatoItems.LAVA_POTATO, new ItemStack(Item.getItemFromBlock(Blocks.OBSIDIAN)), 0);
        }
        addRecipe(recipes, sponge, new ItemStack(sponge, 1, 1), 0);

        return recipes;
    }

    public static void addRecipe(List<PotatoDrierRecipe> recipes, Item input, ItemStack output, int water) {
        recipes.add(new PotatoDrierRecipe(input, output, water));
    }
}
