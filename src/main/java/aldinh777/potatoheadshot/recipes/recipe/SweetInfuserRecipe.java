package aldinh777.potatoheadshot.recipes.recipe;

import aldinh777.potatoheadshot.handler.ConfigHandler;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

import static aldinh777.potatoheadshot.util.ItemHelper.DummyItem.*;

public class SweetInfuserRecipe {

    private final Item catalyst;
    private final Item[] inputs;
    private final ItemStack output;

    public SweetInfuserRecipe(ItemStack output, Item catalyst, Item ...inputs) {
        this.catalyst = catalyst;
        this.inputs = inputs;
        this.output = output;
    }

    public Item getCatalyst() {
        return this.catalyst;
    }

    public Item[] getInputs() {
        return this.inputs;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public static List<SweetInfuserRecipe> getRecipes() {
        List<SweetInfuserRecipe> recipes = Lists.newArrayList();

        basicRecipes(recipes);
        vanillaOreRecipes(recipes);
        customOreRecipes(recipes);
        gemRecipes(recipes);

        return recipes;
    }

    public static void addRecipe(List<SweetInfuserRecipe> recipes, ItemStack output, Item catalyst, Item ...inputs) {
        recipes.add(new SweetInfuserRecipe(output, catalyst, inputs));
    }

    private static void basicRecipes(List<SweetInfuserRecipe> recipes) {
        Item lava = Items.LAVA_BUCKET;
        Item water = Items.WATER_BUCKET;
        Item enderPearl = Items.ENDER_PEARL;
        Item string = Items.STRING;
        Item coal = Items.COAL;
        Item potatoLeaves = PotatoItems.POTATO_LEAVES;
        Item frozenPotato = PotatoItems.FROZEN_POTATO;
        Item icePotato = PotatoItems.ICE_POTATO;

        Item crystal = PotatoItems.CRYSTAL;
        Item chargedCrystal = PotatoItems.CHARGED_CRYSTAL;
        Item concentratedCrystal = PotatoItems.CONCENTRATED_CRYSTAL;
        Item manaFlower = Item.getItemFromBlock(PotatoBlocks.MANA_FLOWER);
        Item ultimateCrystal = PotatoItems.ULTIMATE_CRYSTAL;
        Item ultimateChargedCrystal = PotatoItems.ULTIMATE_CHARGED_CRYSTAL;
        Item ultimateConcentratedCrystal = PotatoItems.ULTIMATE_CONCENTRATED_CRYSTAL;
        Item carbonatedCoal = PotatoItems.CARBONATED_COAL;
        Item ultimateBrokenFuel = PotatoItems.ULTIMATE_BROKEN_FUEL;
        Item ultimateManaFlower = Item.getItemFromBlock(PotatoBlocks.ULTIMATE_FLOWER);

        Item coalBlock = Item.getItemFromBlock(Blocks.COAL_BLOCK);
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

        addRecipe(recipes, new ItemStack(gravel), stone,
                cobble, cobble, cobble,
                sand, sand, sand);
        addRecipe(recipes, new ItemStack(rack), stone,
                cobble, lava, cobble,
                cobble, lava, cobble);
        addRecipe(recipes, new ItemStack(grass), dirt,
                potatoLeaves, potatoLeaves, potatoLeaves,
                dirt, water, dirt);
        addRecipe(recipes, new ItemStack(mycelium), dirt,
                brownMushroom, brownMushroom, brownMushroom,
                dirt, water, dirt);
        addRecipe(recipes, new ItemStack(glowStone), stone,
                lava, lava, lava,
                lava, lava, lava);
        addRecipe(recipes, new ItemStack(endStone), stone,
                enderPearl, enderPearl, enderPearl,
                enderPearl, enderPearl, enderPearl);
        addRecipe(recipes, new ItemStack(packedIce), ice,
                ice, ice, ice,
                ice, ice, ice);
        addRecipe(recipes, new ItemStack(cobweb), string,
                string, string, string,
                string, string, string);
        if (ConfigHandler.ULTIMATE_CRYSTALS) {
            addRecipe(recipes, new ItemStack(ultimateCrystal), crystal,
                    chargedCrystal, chargedCrystal, chargedCrystal,
                    concentratedCrystal, concentratedCrystal, concentratedCrystal);
            if (ConfigHandler.ULTIMATE_BROKEN_FUEL) {
                addRecipe(recipes, new ItemStack(ultimateBrokenFuel), carbonatedCoal,
                        ultimateCrystal, ultimateChargedCrystal, ultimateCrystal,
                        ultimateCrystal, ultimateConcentratedCrystal, ultimateCrystal);
            }
            if (ConfigHandler.ULTIMATE_FLOWER) {
                addRecipe(recipes, new ItemStack(ultimateManaFlower), manaFlower,
                        ultimateCrystal, ultimateChargedCrystal, ultimateCrystal,
                        ultimateCrystal, ultimateConcentratedCrystal, ultimateCrystal);
            }
        }
        addRecipe(recipes, new ItemStack(carbonatedCoal), coal,
                coalBlock, coalBlock, coalBlock,
                coalBlock, coalBlock, coalBlock);
        addRecipe(recipes, new ItemStack(icePotato), frozenPotato,
                ice, ice, ice,
                ice, ice, ice);
    }

    private static void vanillaOreRecipes(List<SweetInfuserRecipe> lists) {
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

        addRecipe(lists, new ItemStack(ironOre), stone,
                Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT,
                Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT);
        addRecipe(lists, new ItemStack(goldOre), stone,
                Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT,
                Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT);
        addRecipe(lists, new ItemStack(diamondOre), stone,
                Items.DIAMOND, Items.DIAMOND, Items.DIAMOND,
                Items.DIAMOND, Items.DIAMOND, Items.DIAMOND);
        addRecipe(lists, new ItemStack(emeraldOre), stone,
                Items.EMERALD, Items.EMERALD, Items.EMERALD,
                Items.EMERALD, Items.EMERALD, Items.EMERALD);
        addRecipe(lists, new ItemStack(coalOre), stone,
                Items.COAL, Items.COAL, Items.COAL,
                Items.COAL, Items.COAL, Items.COAL);
        addRecipe(lists, new ItemStack(redstoneOre), stone,
                Items.REDSTONE, Items.REDSTONE, Items.REDSTONE,
                Items.REDSTONE, Items.REDSTONE, Items.REDSTONE);
        addRecipe(lists, new ItemStack(quartzOre), rack,
                Items.QUARTZ, Items.QUARTZ, Items.QUARTZ,
                Items.QUARTZ, Items.QUARTZ, Items.QUARTZ);
        addRecipe(lists, new ItemStack(lapisOre), stone,
                LAPIS, LAPIS, LAPIS,
                LAPIS, LAPIS, LAPIS);
    }

    private static void customOreRecipes(List<SweetInfuserRecipe> recipes) {
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

        addRecipe(recipes, new ItemStack(ironOre), stone,
                gravel, lava, gravel,
                cobble, water, cobble);
        addRecipe(recipes, new ItemStack(goldOre), stone,
                rack, lava, rack,
                cobble, water, cobble);
        addRecipe(recipes, new ItemStack(redstoneOre), stone,
                DYE_RED, driedSweet, DYE_RED,
                DYE_RED, driedSweet, DYE_RED);
    }

    private static void gemRecipes(List<SweetInfuserRecipe> recipes) {
        Item diamond = Items.DIAMOND;
        Item emerald = Items.EMERALD;
        Item quartz = Items.QUARTZ;
        Item dye = Items.DYE;

        Item carbonatedCoal = PotatoItems.CARBONATED_COAL;
        Item crystalShard = PotatoItems.CRYSTAL_SHARD;
        Item crystal = PotatoItems.CRYSTAL;
        Item chargedCrystalShard = PotatoItems.CHARGED_CRYSTAL_SHARD;
        Item concentratedCrystalShard = PotatoItems.CONCENTRATED_CRYSTAL_SHARD;
        Item chargedCrystal = PotatoItems.CHARGED_CRYSTAL;
        Item concentratedCrystal = PotatoItems.CONCENTRATED_CRYSTAL;

        addRecipe(recipes, new ItemStack(crystal), crystalShard,
                crystalShard, crystalShard, crystalShard,
                crystalShard, crystalShard, crystalShard);
        addRecipe(recipes, new ItemStack(quartz), crystalShard,
                DYE_WHITE, DYE_WHITE, DYE_WHITE,
                DYE_WHITE, DYE_WHITE, DYE_WHITE);
        addRecipe(recipes, new ItemStack(dye, 1, 4), crystalShard,
                DYE_LIGHT_BLUE, DYE_LIGHT_BLUE, DYE_LIGHT_BLUE,
                DYE_LIGHT_BLUE, DYE_LIGHT_BLUE, DYE_LIGHT_BLUE);
        addRecipe(recipes, new ItemStack(diamond), crystal,
                crystalShard, carbonatedCoal, crystalShard,
                carbonatedCoal, crystalShard, carbonatedCoal);
        addRecipe(recipes, new ItemStack(emerald), crystal,
                crystal, DYE_LIME, crystal,
                DYE_LIME, crystal, DYE_LIME);
        if (ConfigHandler.ULTIMATE_CRYSTALS) {
            addRecipe(recipes, new ItemStack(chargedCrystal), crystalShard,
                    chargedCrystalShard, chargedCrystalShard, chargedCrystalShard,
                    chargedCrystalShard, chargedCrystalShard, chargedCrystalShard);
            addRecipe(recipes, new ItemStack(concentratedCrystal), crystalShard,
                    concentratedCrystalShard, concentratedCrystalShard, concentratedCrystalShard,
                    concentratedCrystalShard, concentratedCrystalShard, concentratedCrystalShard);
        }
    }
}
