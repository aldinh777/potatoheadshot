package aldinh777.potatoheadshot.common.recipes.recipe;

import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.common.lists.PotatoBlocks;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CauldronRecipe {

    private final ItemStack input;
    private final ItemStack output;
    private final int cost;

    public CauldronRecipe(ItemStack input, ItemStack output, int cost) {
        this.input = input;
        this.output = output;
        this.cost = cost;
    }

    public ItemStack getInput() {
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

        Item redFlower = Item.getItemFromBlock(Blocks.RED_FLOWER);

        addRecipe(recipes, new ItemStack(PotatoItems.POTATO_KNIFE), new ItemStack(PotatoItems.POTATO_MANA_KNIFE), 200);
        addRecipe(recipes, createStack(redFlower, 0), new ItemStack(PotatoBlocks.FLAMING_FLOWER), 200);
        addRecipe(recipes, createStack(redFlower, 1), new ItemStack(PotatoBlocks.MIST_FLOWER), 200);
        addRecipe(recipes, createStack(redFlower, 2), new ItemStack(PotatoBlocks.REGEN_FLOWER), 200);
        addRecipe(recipes, createStack(redFlower, 8), new ItemStack(PotatoBlocks.GROWING_FLOWER), 200);
        if (ConfigHandler.ICE_POTATO) {
            addRecipe(recipes, new ItemStack(Items.WHEAT_SEEDS), new ItemStack(PotatoItems.ICE_POTATO_SEED), 200);
        }

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

        if (ConfigHandler.HOT_POTATO) {
            addRecipe(recipes, new ItemStack(PotatoItems.HOT_POTATO), new ItemStack(PotatoItems.LOKBOMB), 800);
        }
        if (ConfigHandler.LAVA_POTATO) {
            addRecipe(recipes, new ItemStack(PotatoItems.LAVA_POTATO), new ItemStack(PotatoItems.LAVA_POTATO_SEED), 800);
        }
        if (ConfigHandler.WATER_POTATO) {
            addRecipe(recipes, new ItemStack(PotatoItems.WATER_POTATO), new ItemStack(PotatoItems.WATER_POTATO_SEED), 800);
        }

        addRecipe(recipes, new ItemStack(Items.FISH), new ItemStack(Items.PRISMARINE_SHARD), 20);
        addRecipe(recipes, new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_CRYSTALS), 80);
        addRecipe(recipes, new ItemStack(Items.CLAY_BALL), new ItemStack(Items.SLIME_BALL), 80);
        addRecipe(recipes, new ItemStack(Items.STRING), new ItemStack(Blocks.VINE), 20);
        addRecipe(recipes, new ItemStack(Items.SUGAR), new ItemStack(Items.REEDS), 20);
        addRecipe(recipes, new ItemStack(netherRack), new ItemStack(Items.NETHER_WART), 20);
        addRecipe(recipes, new ItemStack(grass), new ItemStack(Items.WHEAT_SEEDS), 20);
        addRecipe(recipes, new ItemStack(dirt), new ItemStack(Blocks.GRASS), 20);
        addRecipe(recipes, new ItemStack(sand), new ItemStack(Blocks.CACTUS), 20);
        addRecipe(recipes, new ItemStack(log1), new ItemStack(Blocks.SAPLING), 20);
        addRecipe(recipes, new ItemStack(log2), new ItemStack(Blocks.SAPLING), 20);
        addRecipe(recipes, new ItemStack(mycelium), new ItemStack(Blocks.BROWN_MUSHROOM), 20);
        addRecipe(recipes, new ItemStack(endStone), new ItemStack(Blocks.CHORUS_FLOWER), 20);
        addRecipe(recipes, new ItemStack(ice), new ItemStack(Blocks.SNOW), 20);
        addRecipe(recipes, new ItemStack(Items.SKULL, 1, 1), new ItemStack(PotatoItems.CORRUPTED_SEED, 64), 20 * 64);
        if (ConfigHandler.SPLASH_MANA) {
            addRecipe(recipes, new ItemStack(Items.GLASS_BOTTLE), new ItemStack(PotatoItems.SPLASH_MANA_LIFE), 800);
        }

        return recipes;
    }
    
    public static List<CauldronRecipe> getFireRecipes() {
        List<CauldronRecipe> recipes = Lists.newArrayList();

        if (ConfigHandler.LAVA_POTATO) {
            addRecipe(recipes, new ItemStack(Items.IRON_HOE), new ItemStack(PotatoItems.LAVA_HOE), 800);
        }

        if (ConfigHandler.SPLASH_MANA) {
            addRecipe(recipes, new ItemStack(Items.GLASS_BOTTLE), new ItemStack(PotatoItems.SPLASH_MANA_FIRE), 200);
        }
        
        return recipes;
    }

    public static List<CauldronRecipe> getNatureRecipes() {
        List<CauldronRecipe> recipes = Lists.newArrayList();

        Item redMushroom = Item.getItemFromBlock(Blocks.RED_MUSHROOM);
        Item brownMushroom = Item.getItemFromBlock(Blocks.BROWN_MUSHROOM);
        Item cactus = Item.getItemFromBlock(Blocks.CACTUS);

        Item redFlower = Item.getItemFromBlock(Blocks.RED_FLOWER);
        Item yellowFlower = Item.getItemFromBlock(Blocks.YELLOW_FLOWER);
        Item doublePlant = Item.getItemFromBlock(Blocks.DOUBLE_PLANT);
        Item sapling = Item.getItemFromBlock(Blocks.SAPLING);

        addRecipe(recipes, new ItemStack(Items.DYE, 1, 3), new ItemStack(Items.WHEAT_SEEDS), 5);
        addRecipe(recipes, new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.MELON_SEEDS), 5);
        addRecipe(recipes, new ItemStack(Items.MELON_SEEDS), new ItemStack(Items.PUMPKIN_SEEDS), 5);
        addRecipe(recipes, new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(Items.BEETROOT_SEEDS), 5);
        addRecipe(recipes, new ItemStack(Items.BEETROOT_SEEDS), new ItemStack(Items.DYE, 1, 3), 5);
        addRecipe(recipes, new ItemStack(Items.NETHER_WART), new ItemStack(redMushroom), 5);
        addRecipe(recipes, new ItemStack(redMushroom), new ItemStack(brownMushroom), 5);
        addRecipe(recipes, new ItemStack(brownMushroom), new ItemStack(Items.NETHER_WART), 5);
        addRecipe(recipes, new ItemStack(cactus), new ItemStack(Items.REEDS), 5);
        addRecipe(recipes, new ItemStack(Items.REEDS), new ItemStack(cactus), 5);

        addRecipe(recipes, createStack(redFlower, 0), createStack(yellowFlower, 0), 5);
        addRecipe(recipes, createStack(yellowFlower, 0), createStack(redFlower, 1), 5);
        addRecipe(recipes, createStack(redFlower, 1), createStack(redFlower, 2), 5);
        addRecipe(recipes, createStack(redFlower, 2), createStack(redFlower, 3), 5);
        addRecipe(recipes, createStack(redFlower, 3), createStack(redFlower, 4), 5);
        addRecipe(recipes, createStack(redFlower, 4), createStack(redFlower, 5), 5);
        addRecipe(recipes, createStack(redFlower, 5), createStack(redFlower, 6), 5);
        addRecipe(recipes, createStack(redFlower, 6), createStack(redFlower, 7), 5);
        addRecipe(recipes, createStack(redFlower, 7), createStack(redFlower, 8), 5);
        addRecipe(recipes, createStack(redFlower, 8), createStack(redFlower, 0), 5);

        addRecipe(recipes, createStack(sapling, 0), createStack(sapling, 1), 5);
        addRecipe(recipes, createStack(sapling, 1), createStack(sapling, 2), 5);
        addRecipe(recipes, createStack(sapling, 2), createStack(sapling, 3), 5);
        addRecipe(recipes, createStack(sapling, 3), createStack(sapling, 4), 5);
        addRecipe(recipes, createStack(sapling, 4), createStack(sapling, 5), 5);
        addRecipe(recipes, createStack(sapling, 5), createStack(sapling, 0), 5);

        addRecipe(recipes, createStack(doublePlant, 0), createStack(doublePlant, 1), 5);
        addRecipe(recipes, createStack(doublePlant, 1), createStack(doublePlant, 2), 5);
        addRecipe(recipes, createStack(doublePlant, 2), createStack(doublePlant, 3), 5);
        addRecipe(recipes, createStack(doublePlant, 3), createStack(doublePlant, 4), 5);
        addRecipe(recipes, createStack(doublePlant, 4), createStack(doublePlant, 5), 5);
        addRecipe(recipes, createStack(doublePlant, 5), createStack(doublePlant, 0), 5);

        return recipes;
    }

    private static ItemStack createStack(Item item, int meta) {
        return new ItemStack(item, 1, meta);
    }

    public static void addRecipe(List<CauldronRecipe> recipes, ItemStack input, ItemStack output, int cost) {
        recipes.add(new CauldronRecipe(input, output, cost));
    }
}
