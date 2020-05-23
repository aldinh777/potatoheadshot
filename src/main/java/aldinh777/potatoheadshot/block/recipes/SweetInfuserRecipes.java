package aldinh777.potatoheadshot.block.recipes;

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

    private SweetInfuserRecipes() {

        Item lava = Items.LAVA_BUCKET;
        Item water = Items.WATER_BUCKET;
        Item enderPearl = Items.ENDER_PEARL;
        Item string = Items.STRING;
        Item potatoLeaves = PotatoItems.POTATO_LEAVES;

        Item ironOre = Item.getItemFromBlock(Blocks.IRON_ORE);
        Item goldOre = Item.getItemFromBlock(Blocks.GOLD_ORE);
        
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

        addRecipe(new ItemStack(gravel), stone,
                cobble, cobble, cobble,
                sand, sand, sand);
        addRecipe(new ItemStack(rack), stone,
                cobble, lava, cobble,
                cobble, lava, cobble);
        addRecipe(new ItemStack(ironOre), stone,
                gravel, lava, gravel,
                cobble, water, cobble);
        addRecipe(new ItemStack(goldOre), stone,
                rack, lava, rack,
                cobble, water, cobble);
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
            if (recipes[i] == PotatoItems.LAVA_POTATO || recipes[i] == PotatoItems.SWEET_LAVA_BUCKET) {
                recipe = Items.LAVA_BUCKET;
            }
            if (recipes[i] == PotatoItems.WATER_POTATO || recipes[i] == PotatoItems.SWEET_WATER_BUCKET) {
                recipe = Items.WATER_BUCKET;
            }
            map = getMap(map, recipe);
        }

        Item lastRecipe = recipes[5];
        if (recipes[5] == PotatoItems.LAVA_POTATO || recipes[5] == PotatoItems.SWEET_LAVA_BUCKET) {
            lastRecipe = Items.LAVA_BUCKET;
        }
        if (recipes[5] == PotatoItems.WATER_POTATO || recipes[5] == PotatoItems.SWEET_WATER_BUCKET) {
            lastRecipe = Items.WATER_BUCKET;
        }
        map.put((K)lastRecipe, (V)output);
    }

    public <K, V> ItemStack getResult(Item middle, Item ...recipes) {
        Map<K, V> map = (Map<K, V>)mainMap;
        map = (Map<K, V>)map.get((K)middle);

        if (map == null) {
            return ItemStack.EMPTY;
        }

        for (int i = 0; i < 5; i++) {
            Item recipe = recipes[i];
            if (recipes[i] == PotatoItems.LAVA_POTATO || recipes[i] == PotatoItems.SWEET_LAVA_BUCKET) {
                recipe = Items.LAVA_BUCKET;
            }
            if (recipes[i] == PotatoItems.WATER_POTATO || recipes[i] == PotatoItems.SWEET_WATER_BUCKET) {
                recipe = Items.WATER_BUCKET;
            }
            map = (Map<K, V>)map.get((K)recipe);
            if (map == null) {
                return ItemStack.EMPTY;
            }
        }

        Item lastRecipe = recipes[5];
        if (recipes[5] == PotatoItems.LAVA_POTATO || recipes[5] == PotatoItems.SWEET_LAVA_BUCKET) {
            lastRecipe = Items.LAVA_BUCKET;
        }
        if (recipes[5] == PotatoItems.WATER_POTATO || recipes[5] == PotatoItems.SWEET_WATER_BUCKET) {
            lastRecipe = Items.WATER_BUCKET;
        }
        ItemStack result = (ItemStack)map.get((K)lastRecipe);
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
    }
}
