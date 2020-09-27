package aldinh777.potatoheadshot.block.recipes;

import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SweetInfuserRecipes {
    
    public static final SweetInfuserRecipes INSTANCE = new SweetInfuserRecipes();
    private final Map<Item, Object> mainMap = new HashMap<>();
    private final Item LAPIS = new Item();
    private final Item DYE_LIGHT_BLUE = new Item();
    private final Item DYE_RED = new Item();
    private final Item DYE_LIME = new Item();
    private final Item DYE_WHITE = new Item();

    private SweetInfuserRecipes() {

        Item lava = Items.LAVA_BUCKET;
        Item water = Items.WATER_BUCKET;
        Item enderPearl = Items.ENDER_PEARL;
        Item string = Items.STRING;
        Item coal = Items.COAL;
        Item potatoLeaves = PotatoItems.POTATO_LEAVES;

        Item crystal = PotatoItems.CRYSTAL;
        Item chargedCrystal = PotatoItems.CHARGED_CRYSTAL;
        Item concentratedCrystal = PotatoItems.CONCENTRATED_CRYSTAL;
        Item manaFlower = Item.getItemFromBlock(PotatoBlocks.MANA_FLOWER);
        Item ultimateCrystal = PotatoItems.ULTIMATE_CRYSTAL;
        Item ultimateChargedCrystal = PotatoItems.ULTIMATE_CHARGED_CRYSTAL;
        Item ultimateConcentratedCrystal = PotatoItems.ULTIMATE_CONCENTRATED_CRYSTAL;
        Item ultimateBrokenFuel = PotatoItems.ULTIMATE_BROKEN_FUEL;
        Item ultimateManaFlower = Item.getItemFromBlock(PotatoBlocks.ULTIMATE_FLOWER);

        Item grass = Item.getItemFromBlock(Blocks.GRASS);
        Item mycelium = Item.getItemFromBlock(Blocks.MYCELIUM);
        Item dirt = Item.getItemFromBlock(Blocks.DIRT);
        Item stone = Item.getItemFromBlock(Blocks.STONE);
        Item gravel = Item.getItemFromBlock(Blocks.GRAVEL);
        Item rack = Item.getItemFromBlock(Blocks.NETHERRACK);
        Item cobble = Item.getItemFromBlock(Blocks.COBBLESTONE);
        Item sand = Item.getItemFromBlock(Blocks.SAND);
        Item brownMushroom = Item.getItemFromBlock(Blocks.BROWN_MUSHROOM);
        Item glowStone = Item.getItemFromBlock(Blocks.GLOWSTONE);
        Item endStone = Item.getItemFromBlock(Blocks.END_STONE);
        Item ice = Item.getItemFromBlock(Blocks.ICE);
        Item packedIce = Item.getItemFromBlock(Blocks.PACKED_ICE);
        Item cobweb = Item.getItemFromBlock(Blocks.WEB);

        basicOreRecipes();
        customOreRecipes();
        gemRecipes();

        addRecipe(new ItemStack(gravel), stone,
                cobble, cobble, cobble,
                sand, sand, sand);
        addRecipe(new ItemStack(rack), stone,
                cobble, lava, cobble,
                cobble, lava, cobble);
        addRecipe(new ItemStack(grass), dirt,
                potatoLeaves, potatoLeaves, potatoLeaves,
                dirt, water, dirt);
        addRecipe(new ItemStack(mycelium), dirt,
                brownMushroom, brownMushroom, brownMushroom,
                dirt, water, dirt);
        addRecipe(new ItemStack(glowStone), stone,
                lava, lava, lava,
                lava, lava, lava);
        addRecipe(new ItemStack(endStone), stone,
                enderPearl, enderPearl, enderPearl,
                enderPearl, enderPearl, enderPearl);
        addRecipe(new ItemStack(packedIce), ice,
                ice, ice, ice,
                ice, ice, ice);
        addRecipe(new ItemStack(cobweb), string,
                string, string, string,
                string, string, string);
        addRecipe(new ItemStack(ultimateCrystal), crystal,
                chargedCrystal, chargedCrystal, chargedCrystal,
                concentratedCrystal, concentratedCrystal, concentratedCrystal);
        addRecipe(new ItemStack(ultimateBrokenFuel), coal,
                ultimateCrystal, ultimateChargedCrystal, ultimateCrystal,
                ultimateCrystal, ultimateConcentratedCrystal, ultimateCrystal);
        addRecipe(new ItemStack(ultimateManaFlower), manaFlower,
                ultimateCrystal, ultimateChargedCrystal, ultimateCrystal,
                ultimateCrystal, ultimateConcentratedCrystal, ultimateCrystal);
    }

    public <K, V> Map<K, V> getMap(Map<K, V> map, Item key) {
        Map<K, V> existingMap = (Map<K, V>)map.get((K)key);

        if (existingMap != null) {
            return existingMap;
        } else {
            Map<K, V> newMap = new HashMap<>();
            map.put((K)key, (V)newMap);
            return newMap;
        }
    }

    public <K, V> void addRecipe(ItemStack output, Item middle, Item ...recipes) {
        Map<K, V> map = (Map<K, V>)mainMap;
        map = getMap(map, middle);

        for (int i = 0; i < 5; i++) {
            Item recipe = recipes[i];
            map = getMap(map, recipe);
        }

        Item lastRecipe = recipes[5];
        map.put((K)lastRecipe, (V)output);
    }

    public <K, V> ItemStack getResult(Item middle, ItemStack ...recipes) {
        Map<K, V> map = (Map<K, V>)mainMap;
        map = (Map<K, V>)map.get((K)middle);

        if (map == null) {
            return ItemStack.EMPTY;
        }

        for (int i = 0; i < 5; i++) {
            Item recipe;

            if (recipes[i].getItem() == Items.DYE) {
                recipe = getDye(recipes[i]);
            } else {
                recipe = liquidify(recipes[i]);
            }

            map = (Map<K, V>)map.get((K)recipe);

            if (map == null) {
                return ItemStack.EMPTY;
            }
        }

        ItemStack result;
        Item lastRecipe;

        if (recipes[5].getItem() == Items.DYE) {
            lastRecipe = getDye(recipes[5]);
        } else {
            lastRecipe = liquidify(recipes[5]);
        }
        result = (ItemStack)map.get((K)lastRecipe);

        if (result == null) {
            return ItemStack.EMPTY;
        } else {
            return result;
        }
    }

    public void basicOreRecipes() {
        Item ironOre = Item.getItemFromBlock(Blocks.IRON_ORE);
        Item goldOre = Item.getItemFromBlock(Blocks.GOLD_ORE);
        Item diamondOre = Item.getItemFromBlock(Blocks.DIAMOND_ORE);
        Item emeraldOre = Item.getItemFromBlock(Blocks.EMERALD_ORE);
        Item coalOre = Item.getItemFromBlock(Blocks.COAL_ORE);
        Item redstoneOre = Item.getItemFromBlock(Blocks.REDSTONE_ORE);
        Item quartzOre = Item.getItemFromBlock(Blocks.QUARTZ_ORE);
        Item lapisOre = Item.getItemFromBlock(Blocks.LAPIS_ORE);

        Item stone = Item.getItemFromBlock(Blocks.STONE);
        Item rack = Item.getItemFromBlock(Blocks.NETHERRACK);

        addRecipe(new ItemStack(ironOre), stone,
                Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT,
                Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT);
        addRecipe(new ItemStack(goldOre), stone,
                Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT,
                Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT);
        addRecipe(new ItemStack(diamondOre), stone,
                Items.DIAMOND, Items.DIAMOND, Items.DIAMOND,
                Items.DIAMOND, Items.DIAMOND, Items.DIAMOND);
        addRecipe(new ItemStack(emeraldOre), stone,
                Items.EMERALD, Items.EMERALD, Items.EMERALD,
                Items.EMERALD, Items.EMERALD, Items.EMERALD);
        addRecipe(new ItemStack(coalOre), stone,
                Items.COAL, Items.COAL, Items.COAL,
                Items.COAL, Items.COAL, Items.COAL);
        addRecipe(new ItemStack(redstoneOre), stone,
                Items.REDSTONE, Items.REDSTONE, Items.REDSTONE,
                Items.REDSTONE, Items.REDSTONE, Items.REDSTONE);
        addRecipe(new ItemStack(quartzOre), rack,
                Items.QUARTZ, Items.QUARTZ, Items.QUARTZ,
                Items.QUARTZ, Items.QUARTZ, Items.QUARTZ);
        addRecipe(new ItemStack(lapisOre), stone,
                LAPIS, LAPIS, LAPIS,
                LAPIS, LAPIS, LAPIS);
    }

    public void customOreRecipes() {
        Item lava = Items.LAVA_BUCKET;
        Item water = Items.WATER_BUCKET;
        Item driedSweet = PotatoItems.DRIED_SWEET_POTATO;

        Item stone = Item.getItemFromBlock(Blocks.STONE);
        Item gravel = Item.getItemFromBlock(Blocks.GRAVEL);
        Item rack = Item.getItemFromBlock(Blocks.NETHERRACK);
        Item cobble = Item.getItemFromBlock(Blocks.COBBLESTONE);

        Item ironOre = Item.getItemFromBlock(Blocks.IRON_ORE);
        Item goldOre = Item.getItemFromBlock(Blocks.GOLD_ORE);
        Item redstoneOre = Item.getItemFromBlock(Blocks.REDSTONE_ORE);

        addRecipe(new ItemStack(ironOre), stone,
                gravel, lava, gravel,
                cobble, water, cobble);
        addRecipe(new ItemStack(goldOre), stone,
                rack, lava, rack,
                cobble, water, cobble);
        addRecipe(new ItemStack(redstoneOre), stone,
                DYE_RED, driedSweet, DYE_RED,
                DYE_RED, driedSweet, DYE_RED);
    }

    public void gemRecipes() {
        Item diamond = Items.DIAMOND;
        Item emerald = Items.EMERALD;
        Item quartz = Items.QUARTZ;
        Item dye = Items.DYE;

        Item crystalShard = PotatoItems.CRYSTAL_SHARD;
        Item crystal = PotatoItems.CRYSTAL;
        Item chargedCrystalShard = PotatoItems.CHARGED_CRYSTAL_SHARD;
        Item concentratedCrystalShard = PotatoItems.CONCENTRATED_CRYSTAL_SHARD;
        Item chargedCrystal = PotatoItems.CHARGED_CRYSTAL;
        Item concentratedCrystal = PotatoItems.CONCENTRATED_CRYSTAL;

        addRecipe(new ItemStack(crystal), crystalShard,
                crystalShard, crystalShard, crystalShard,
                crystalShard, crystalShard, crystalShard);
        addRecipe(new ItemStack(quartz), crystalShard,
                DYE_WHITE, DYE_WHITE, DYE_WHITE,
                DYE_WHITE, DYE_WHITE, DYE_WHITE);
        addRecipe(new ItemStack(dye, 1, 4), crystalShard,
                DYE_LIGHT_BLUE, DYE_LIGHT_BLUE, DYE_LIGHT_BLUE,
                DYE_LIGHT_BLUE, DYE_LIGHT_BLUE, DYE_LIGHT_BLUE);
        addRecipe(new ItemStack(diamond), crystal,
                crystalShard, DYE_LIGHT_BLUE, crystalShard,
                DYE_LIGHT_BLUE, crystalShard, DYE_LIGHT_BLUE);
        addRecipe(new ItemStack(emerald), crystal,
                crystalShard, DYE_LIME, crystalShard,
                DYE_LIME, crystalShard, DYE_LIME);
        addRecipe(new ItemStack(chargedCrystal), crystalShard,
                chargedCrystalShard, chargedCrystalShard, chargedCrystalShard,
                chargedCrystalShard, chargedCrystalShard, chargedCrystalShard);
        addRecipe(new ItemStack(concentratedCrystal), crystalShard,
                concentratedCrystalShard, concentratedCrystalShard, concentratedCrystalShard,
                concentratedCrystalShard, concentratedCrystalShard, concentratedCrystalShard);
    }

    public Item getDye(ItemStack stack) {
        switch (stack.getMetadata()) {
            case 1:
                return DYE_RED;
            case 4:
                return LAPIS;
            case 10:
                return DYE_LIME;
            case 12:
                return DYE_LIGHT_BLUE;
            case 15:
                return DYE_WHITE;
            default:
                return stack.getItem();
        }
    }

    public Item liquidify(ItemStack stack) {
        Item item = stack.getItem();

        if (item == PotatoItems.LAVA_POTATO || item == PotatoItems.SWEET_LAVA_BUCKET) {
            return Items.LAVA_BUCKET;
        }
        if (item == PotatoItems.WATER_POTATO || item == PotatoItems.SWEET_WATER_BUCKET) {
            return Items.WATER_BUCKET;
        }
        return item;
    }
}
