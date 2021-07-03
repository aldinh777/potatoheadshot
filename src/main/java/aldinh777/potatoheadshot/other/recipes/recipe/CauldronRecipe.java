package aldinh777.potatoheadshot.other.recipes.recipe;

import aldinh777.potatoheadshot.other.handler.ConfigHandler;
import aldinh777.potatoheadshot.other.lists.PotatoItems;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CauldronRecipe {

    private final Item input;
    private final ItemStack output;
    private final int cost;

    public CauldronRecipe(Item input, ItemStack output, int cost) {
        this.input = input;
        this.output = output;
        this.cost = cost;
    }

    public Item getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public int getCost() {
        return this.cost;
    }

    public static List<CauldronRecipe> getManaRecipes() {
        List<CauldronRecipe> recipes = Lists.newArrayList();

        addRecipe(recipes, PotatoItems.POTATO_KNIFE, new ItemStack(PotatoItems.POTATO_MANA_KNIFE), 200);
        return recipes;
    }

    public static List<CauldronRecipe> getLifeRecipes() {
        List<CauldronRecipe> recipes = Lists.newArrayList();

        Item grass = Item.getItemFromBlock(Blocks.GRASS);
        Item dirt = Item.getItemFromBlock(Blocks.DIRT);
        Item sand = Item.getItemFromBlock(Blocks.SAND);
        Item log1 = Item.getItemFromBlock(Blocks.LOG);
        Item log2 = Item.getItemFromBlock(Blocks.LOG2);
        Item mycelium = Item.getItemFromBlock(Blocks.MYCELIUM);
        Item netherRack = Item.getItemFromBlock(Blocks.NETHERRACK);
        Item endStone = Item.getItemFromBlock(Blocks.END_STONE);
        Item ice = Item.getItemFromBlock(Blocks.ICE);

        if (ConfigHandler.LAVA_POTATO) {
            addRecipe(recipes, PotatoItems.LAVA_POTATO, new ItemStack(PotatoItems.LAVA_POTATO_SEED), 800);
        }
        if (ConfigHandler.WATER_POTATO) {
            addRecipe(recipes, PotatoItems.WATER_POTATO, new ItemStack(PotatoItems.WATER_POTATO_SEED), 800);
        }

        addRecipe(recipes, Items.FISH, new ItemStack(Items.PRISMARINE_SHARD), 20);
        addRecipe(recipes, Items.PRISMARINE_SHARD, new ItemStack(Items.PRISMARINE_CRYSTALS), 80);
        addRecipe(recipes, Items.CLAY_BALL, new ItemStack(Items.SLIME_BALL), 80);
        addRecipe(recipes, Items.STRING, new ItemStack(Blocks.VINE), 20);
        addRecipe(recipes, Items.SUGAR, new ItemStack(Items.REEDS), 20);
        addRecipe(recipes, netherRack, new ItemStack(Items.NETHER_WART), 20);
        addRecipe(recipes, grass, new ItemStack(Items.WHEAT_SEEDS), 20);
        addRecipe(recipes, dirt, new ItemStack(Blocks.GRASS), 20);
        addRecipe(recipes, sand, new ItemStack(Blocks.CACTUS), 20);
        addRecipe(recipes, log1, new ItemStack(Blocks.SAPLING), 20);
        addRecipe(recipes, log2, new ItemStack(Blocks.SAPLING), 20);
        addRecipe(recipes, mycelium, new ItemStack(Blocks.BROWN_MUSHROOM), 20);
        addRecipe(recipes, endStone, new ItemStack(Blocks.CHORUS_FLOWER), 20);
        addRecipe(recipes, ice, new ItemStack(Blocks.SNOW), 20);
        if (ConfigHandler.SPLASH_MANA) {
            addRecipe(recipes, Items.GLASS_BOTTLE, new ItemStack(PotatoItems.SPLASH_MANA_LIFE), 800);
        }

        return recipes;
    }

    public static List<CauldronRecipe> getNatureRecipes() {
        List<CauldronRecipe> recipes = Lists.newArrayList();

        Item redMushroom = Item.getItemFromBlock(Blocks.RED_MUSHROOM);
        Item brownMushroom = Item.getItemFromBlock(Blocks.BROWN_MUSHROOM);
        Item cactus = Item.getItemFromBlock(Blocks.CACTUS);

        addRecipe(recipes, Items.WHEAT_SEEDS, new ItemStack(Items.MELON_SEEDS), 5);
        addRecipe(recipes, Items.MELON_SEEDS, new ItemStack(Items.PUMPKIN_SEEDS), 5);
        addRecipe(recipes, Items.PUMPKIN_SEEDS, new ItemStack(Items.BEETROOT_SEEDS), 5);
        addRecipe(recipes, Items.BEETROOT_SEEDS, new ItemStack(Items.DYE, 1, 3), 5);
        addRecipe(recipes, Items.NETHER_WART, new ItemStack(redMushroom), 5);
        addRecipe(recipes, redMushroom, new ItemStack(brownMushroom), 5);
        addRecipe(recipes, brownMushroom, new ItemStack(Items.NETHER_WART), 5);
        addRecipe(recipes, cactus, new ItemStack(Items.REEDS), 5);
        addRecipe(recipes, Items.REEDS, new ItemStack(cactus), 5);

        return recipes;
    }

    public static void addRecipe(List<CauldronRecipe> recipes, Item input, ItemStack output, int cost) {
        recipes.add(new CauldronRecipe(input, output, cost));
    }
}
