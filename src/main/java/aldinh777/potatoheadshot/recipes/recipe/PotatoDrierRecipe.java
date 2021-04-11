package aldinh777.potatoheadshot.recipes.recipe;

import aldinh777.potatoheadshot.handler.ConfigHandler;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.recipes.custom.CustomDryRecipe;
import aldinh777.potatoheadshot.recipes.custom.CustomWetRecipe;
import aldinh777.potatoheadshot.recipes.custom.item.ItemRecipe;
import aldinh777.potatoheadshot.recipes.custom.item.ItemWaterRecipe;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Objects;

public class PotatoDrierRecipe {

    private final ItemStack input;
    private final ItemStack output;
    private final int water;

    public PotatoDrierRecipe(ItemStack input, ItemStack output, int waterValue) {
        this.input = input;
        this.output = output;
        this.water = waterValue;
    }

    public ItemStack getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public int getWaterValue() {
        return this.water;
    }

    public CustomDryRecipe toCustomDryRecipe() {
        String inputId = Objects.requireNonNull(this.input.getItem().getRegistryName()).toString();
        String outputId = Objects.requireNonNull(this.output.getItem().getRegistryName()).toString();
        int inputMeta = this.input.getMetadata();
        int outputMeta = this.output.getMetadata();
        int water = this.water;

        ItemRecipe input = new ItemRecipe(inputId, inputMeta);
        ItemWaterRecipe output = new ItemWaterRecipe(outputId, outputMeta, water);

        return new CustomDryRecipe(input, output);
    }

    public CustomWetRecipe toCustomWetRecipe() {
        String inputId = Objects.requireNonNull(this.input.getItem().getRegistryName()).toString();
        String outputId = Objects.requireNonNull(this.output.getItem().getRegistryName()).toString();
        int inputMeta = this.input.getMetadata();
        int outputMeta = this.output.getMetadata();

        ItemRecipe input = new ItemRecipe(inputId, inputMeta);
        ItemRecipe output = new ItemRecipe(outputId, outputMeta);

        return new CustomWetRecipe(input, output);
    }

    public static List<PotatoDrierRecipe> getDefaultDryRecipes() {
        List<PotatoDrierRecipe> recipes = Lists.newArrayList();

        addRecipe(recipes, new ItemStack(Items.POTATO), new ItemStack(PotatoItems.DRIED_POTATO), 100);
        addRecipe(recipes, new ItemStack(PotatoItems.DRIED_POTATO), new ItemStack(PotatoItems.POTATO_STARCH), 0);
        addRecipe(recipes, new ItemStack(PotatoItems.SWEET_POTATO), new ItemStack(PotatoItems.DRIED_SWEET_POTATO), 100);
        addRecipe(recipes, new ItemStack(PotatoItems.DRIED_SWEET_POTATO), new ItemStack(PotatoItems.SWEET_POTATO_DUST), 0);
        addRecipe(recipes, new ItemStack(PotatoItems.GLOWING_POTATO), new ItemStack(PotatoItems.GLOWING_POTATO_DUST), 0);
        if (ConfigHandler.WET_POTATO) {
            addRecipe(recipes, new ItemStack(PotatoItems.WET_POTATO), new ItemStack(Items.CLAY_BALL), 200);
            if (ConfigHandler.SALT_POTATO) {
                addRecipe(recipes, new ItemStack(PotatoItems.SUPER_WET_POTATO), new ItemStack(PotatoItems.SALT_POTATO), 400);
            }
        }
        if (ConfigHandler.WATER_POTATO) {
            addRecipe(recipes, new ItemStack(PotatoItems.WATER_POTATO), new ItemStack(PotatoItems.RAW_SALT), 800);
        }
        addRecipe(recipes, new ItemStack(Items.WATER_BUCKET), new ItemStack(PotatoItems.RAW_SALT), 800);
        if (ConfigHandler.SWEET_BUCKET) {
            addRecipe(recipes, new ItemStack(PotatoItems.SWEET_WATER_BUCKET), new ItemStack(PotatoItems.RAW_SALT), 800);
        }
        addRecipe(recipes, new ItemStack(Blocks.SPONGE, 1, 1), new ItemStack(Blocks.SPONGE), 800);

        return recipes;
    }

    public static List<PotatoDrierRecipe> getDefaultWetRecipes() {
        List<PotatoDrierRecipe> recipes = Lists.newArrayList();
        Item sponge = Item.getItemFromBlock(Blocks.SPONGE);

        addRecipe(recipes, new ItemStack(Items.POTATO), new ItemStack(PotatoItems.WET_POTATO), 0);
        if (ConfigHandler.WET_POTATO) {
            addRecipe(recipes, new ItemStack(PotatoItems.WET_POTATO), new ItemStack(PotatoItems.SUPER_WET_POTATO), 0);
            if (ConfigHandler.WATER_POTATO) {
                addRecipe(recipes, new ItemStack(PotatoItems.SUPER_WET_POTATO), new ItemStack(PotatoItems.WATER_POTATO), 0);
            }
        }
        addRecipe(recipes, new ItemStack(Items.BUCKET), new ItemStack(Items.WATER_BUCKET), 0);
        if (ConfigHandler.SWEET_BUCKET) {
            addRecipe(recipes, new ItemStack(PotatoItems.SWEET_EMPTY_BUCKET), new ItemStack(PotatoItems.SWEET_WATER_BUCKET), 0);
        }
        addRecipe(recipes, new ItemStack(PotatoItems.DRIED_POTATO), new ItemStack(Items.POTATO), 0);
        if (ConfigHandler.HOT_POTATO) {
            addRecipe(recipes, new ItemStack(PotatoItems.EXTRA_HOT_POTATO), new ItemStack(Items.COAL, 1, 1), 0);
            addRecipe(recipes, new ItemStack(PotatoItems.EXTREME_HOT_POTATO), new ItemStack(Items.COAL), 0);
            if (ConfigHandler.LAVA_POTATO) {
                addRecipe(recipes, new ItemStack(PotatoItems.LAVA_POTATO), new ItemStack(Item.getItemFromBlock(Blocks.OBSIDIAN)), 0);
            }
        }
        addRecipe(recipes, new ItemStack(sponge), new ItemStack(sponge, 1, 1), 0);

        return recipes;
    }

    public static void addRecipe(List<PotatoDrierRecipe> recipes, ItemStack input, ItemStack output, int water) {
        recipes.add(new PotatoDrierRecipe(input, output, water));
    }
}
