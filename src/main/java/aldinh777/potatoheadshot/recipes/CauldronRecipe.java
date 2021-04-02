package aldinh777.potatoheadshot.recipes;

import aldinh777.potatoheadshot.lists.PotatoItems;
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

        addRecipe(recipes, PotatoItems.GLOWING_POTATO_DUST, new ItemStack(PotatoItems.MANA_DUST), 1000);
        addRecipe(recipes, PotatoItems.POTATO_KNIFE, new ItemStack(PotatoItems.POTATO_MANA_KNIFE), 2000);
        addRecipe(recipes, PotatoItems.CRYSTAL_SHARD, new ItemStack(PotatoItems.CONCENTRATED_CRYSTAL_SHARD), 32000);
        addRecipe(recipes, PotatoItems.ULTIMATE_CRYSTAL, new ItemStack(PotatoItems.ULTIMATE_CONCENTRATED_CRYSTAL), 320000);

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

        addRecipe(recipes, PotatoItems.LAVA_POTATO, new ItemStack(PotatoItems.LAVA_POTATO_SEED), 12000);
        addRecipe(recipes, PotatoItems.WATER_POTATO, new ItemStack(PotatoItems.WATER_POTATO_SEED), 12000);
        addRecipe(recipes, PotatoItems.ICE_POTATO, new ItemStack(PotatoItems.ICE_POTATO_SEED), 12000);

        addRecipe(recipes, Items.FISH, new ItemStack(Items.PRISMARINE_SHARD), 2000);
        addRecipe(recipes, Items.PRISMARINE_SHARD, new ItemStack(Items.PRISMARINE_CRYSTALS), 8000);
        addRecipe(recipes, Items.CLAY_BALL, new ItemStack(Items.SLIME_BALL), 8000);
        addRecipe(recipes, Items.STRING, new ItemStack(Blocks.VINE), 2000);
        addRecipe(recipes, Items.SUGAR, new ItemStack(Items.REEDS), 2000);
        addRecipe(recipes, netherRack, new ItemStack(Items.NETHER_WART), 2000);
        addRecipe(recipes, grass, new ItemStack(Items.WHEAT_SEEDS), 2000);
        addRecipe(recipes, dirt, new ItemStack(Blocks.GRASS), 2000);
        addRecipe(recipes, sand, new ItemStack(Blocks.CACTUS), 2000);
        addRecipe(recipes, log1, new ItemStack(Blocks.SAPLING), 2000);
        addRecipe(recipes, log2, new ItemStack(Blocks.SAPLING), 2000);
        addRecipe(recipes, mycelium, new ItemStack(Blocks.BROWN_MUSHROOM), 2000);
        addRecipe(recipes, endStone, new ItemStack(Blocks.CHORUS_FLOWER), 2000);
        addRecipe(recipes, ice, new ItemStack(Blocks.SNOW), 2000);
        addRecipe(recipes, Items.SPLASH_POTION, new ItemStack(PotatoItems.SPLASH_MANA_LIFE), 8000);

        return recipes;
    }

    public static List<CauldronRecipe> getNatureRecipes() {
        List<CauldronRecipe> recipes = Lists.newArrayList();

        Item redMushroom = Item.getItemFromBlock(Blocks.RED_MUSHROOM);
        Item brownMushroom = Item.getItemFromBlock(Blocks.BROWN_MUSHROOM);
        Item cactus = Item.getItemFromBlock(Blocks.CACTUS);
        Item leaves = Item.getItemFromBlock(Blocks.LEAVES);

        addRecipe(recipes, Items.WHEAT_SEEDS, new ItemStack(Items.MELON_SEEDS), 100);
        addRecipe(recipes, Items.MELON_SEEDS, new ItemStack(Items.PUMPKIN_SEEDS), 100);
        addRecipe(recipes, Items.PUMPKIN_SEEDS, new ItemStack(Items.BEETROOT_SEEDS), 100);
        addRecipe(recipes, Items.BEETROOT_SEEDS, new ItemStack(Items.DYE, 1, 3), 100);
        addRecipe(recipes, Items.NETHER_WART, new ItemStack(redMushroom), 100);
        addRecipe(recipes, redMushroom, new ItemStack(brownMushroom), 100);
        addRecipe(recipes, brownMushroom, new ItemStack(Items.NETHER_WART), 100);
        addRecipe(recipes, cactus, new ItemStack(Items.REEDS), 100);
        addRecipe(recipes, Items.REEDS, new ItemStack(cactus), 100);
        addRecipe(recipes, PotatoItems.POTATO_LEAVES, new ItemStack(leaves), 100);

        return recipes;
    }

    public static void addRecipe(List<CauldronRecipe> recipes, Item input, ItemStack output, int cost) {
        recipes.add(new CauldronRecipe(input, output, cost));
    }
}
