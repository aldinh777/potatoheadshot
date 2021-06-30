package aldinh777.potatoheadshot.other.recipes.recipe;

import aldinh777.potatoheadshot.other.handler.ConfigHandler;
import aldinh777.potatoheadshot.other.lists.PotatoBlocks;
import aldinh777.potatoheadshot.other.lists.PotatoItems;
import aldinh777.potatoheadshot.other.recipes.custom.CustomInfuserRecipe;
import aldinh777.potatoheadshot.other.recipes.custom.item.ItemRecipe;
import aldinh777.potatoheadshot.other.util.ItemHelper;
import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Objects;

import static aldinh777.potatoheadshot.other.util.ItemHelper.DummyItem.*;

public class SweetInfuserRecipe {

    private final ItemStack input;
    private final ItemStack[] fusion;
    private final ItemStack output;

    public SweetInfuserRecipe(ItemStack output, ItemStack input, ItemStack[] fusion) {
        this.input = input;
        this.fusion = fusion;
        this.output = output;
    }

    public ItemStack getInput() {
        return this.input;
    }

    public ItemStack[] getFusion() {
        return this.fusion;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public CustomInfuserRecipe toCustomRecipe() {
        String inputId = Objects.requireNonNull(this.input.getItem().getRegistryName()).toString();
        String outputId = Objects.requireNonNull(this.output.getItem().getRegistryName()).toString();
        int inputMeta = this.input.getMetadata();
        int outputMeta = this.output.getMetadata();

        ItemRecipe input = new ItemRecipe(inputId, inputMeta);
        ItemRecipe output = new ItemRecipe(outputId, outputMeta);
        ItemRecipe[] fusion = new ItemRecipe[6];

        for (int i = 0; i < 6; i++) {
            String fusionId = Objects.requireNonNull(this.fusion[i].getItem().getRegistryName()).toString();
            int fusionMeta = this.fusion[i].getMetadata();
            fusion[i] = new ItemRecipe(fusionId, fusionMeta);
        }

        return new CustomInfuserRecipe(input, output, fusion);
    }

    public static void addRecipe(List<SweetInfuserRecipe> recipes, ItemStack output, ItemStack input, ItemStack ...fusion) {
        recipes.add(new SweetInfuserRecipe(output, input, fusion));
    }

    public static List<SweetInfuserRecipe> basicRecipes() {
        List<SweetInfuserRecipe> recipes = Lists.newArrayList();

        Item lava = PotatoItems.LAVA_POTATO;
        Item water = PotatoItems.WATER_POTATO;
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

        addRecipe(recipes, new ItemStack(gravel), new ItemStack(stone),
                new ItemStack(cobble), new ItemStack(cobble), new ItemStack(cobble),
                new ItemStack(sand), new ItemStack(sand), new ItemStack(sand));
        addRecipe(recipes, new ItemStack(rack), new ItemStack(stone),
                new ItemStack(cobble), new ItemStack(lava), new ItemStack(cobble),
                new ItemStack(cobble), new ItemStack(lava), new ItemStack(cobble));
        addRecipe(recipes, new ItemStack(grass), new ItemStack(dirt),
                new ItemStack(potatoLeaves), new ItemStack(potatoLeaves), new ItemStack(potatoLeaves),
                new ItemStack(dirt), new ItemStack(water), new ItemStack(dirt));
        addRecipe(recipes, new ItemStack(mycelium), new ItemStack(dirt),
                new ItemStack(brownMushroom), new ItemStack(brownMushroom), new ItemStack(brownMushroom),
                new ItemStack(dirt), new ItemStack(water), new ItemStack(dirt));
        addRecipe(recipes, new ItemStack(glowStone), new ItemStack(stone),
                new ItemStack(lava), new ItemStack(lava), new ItemStack(lava),
                new ItemStack(lava), new ItemStack(lava), new ItemStack(lava));
        addRecipe(recipes, new ItemStack(endStone), new ItemStack(stone),
                new ItemStack(enderPearl), new ItemStack(enderPearl), new ItemStack(enderPearl),
                new ItemStack(enderPearl), new ItemStack(enderPearl), new ItemStack(enderPearl));
        addRecipe(recipes, new ItemStack(packedIce), new ItemStack(ice),
                new ItemStack(ice), new ItemStack(ice), new ItemStack(ice),
                new ItemStack(ice), new ItemStack(ice), new ItemStack(ice));
        addRecipe(recipes, new ItemStack(cobweb), new ItemStack(string),
                new ItemStack(string), new ItemStack(string), new ItemStack(string),
                new ItemStack(string), new ItemStack(string), new ItemStack(string));
        addRecipe(recipes, new ItemStack(ultimateCrystal), new ItemStack(crystal),
                new ItemStack(chargedCrystal), new ItemStack(chargedCrystal), new ItemStack(chargedCrystal),
                new ItemStack(concentratedCrystal), new ItemStack(concentratedCrystal), new ItemStack(concentratedCrystal));
        addRecipe(recipes, new ItemStack(ultimateBrokenFuel), new ItemStack(carbonatedCoal),
                new ItemStack(ultimateCrystal), new ItemStack(ultimateChargedCrystal), new ItemStack(ultimateCrystal),
                new ItemStack(ultimateCrystal), new ItemStack(ultimateConcentratedCrystal), new ItemStack(ultimateCrystal));
        addRecipe(recipes, new ItemStack(ultimateManaFlower), new ItemStack(manaFlower),
                new ItemStack(ultimateCrystal), new ItemStack(ultimateChargedCrystal), new ItemStack(ultimateCrystal),
                new ItemStack(ultimateCrystal), new ItemStack(ultimateConcentratedCrystal), new ItemStack(ultimateCrystal));
        addRecipe(recipes, new ItemStack(carbonatedCoal), new ItemStack(coal),
                new ItemStack(coalBlock), new ItemStack(coalBlock), new ItemStack(coalBlock),
                new ItemStack(coalBlock), new ItemStack(coalBlock), new ItemStack(coalBlock));
        if (ConfigHandler.ICE_POTATO && ConfigHandler.FROZEN_POTATO) {
            addRecipe(recipes, new ItemStack(icePotato), new ItemStack(frozenPotato),
                    new ItemStack(ice), new ItemStack(ice), new ItemStack(ice),
                    new ItemStack(ice), new ItemStack(ice), new ItemStack(ice));
        }

        return recipes;
    }

    public static List<SweetInfuserRecipe> vanillaOreRecipes() {
        List<SweetInfuserRecipe> recipes = Lists.newArrayList();

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

        addRecipe(recipes, new ItemStack(ironOre), new ItemStack(stone),
                new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT),
                new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT));
        addRecipe(recipes, new ItemStack(goldOre), new ItemStack(stone),
                new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT),
                new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT));
        addRecipe(recipes, new ItemStack(diamondOre), new ItemStack(stone),
                new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND),
                new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND));
        addRecipe(recipes, new ItemStack(emeraldOre), new ItemStack(stone),
                new ItemStack(Items.EMERALD), new ItemStack(Items.EMERALD), new ItemStack(Items.EMERALD),
                new ItemStack(Items.EMERALD), new ItemStack(Items.EMERALD), new ItemStack(Items.EMERALD));
        addRecipe(recipes, new ItemStack(coalOre), new ItemStack(stone),
                new ItemStack(Items.COAL), new ItemStack(Items.COAL), new ItemStack(Items.COAL),
                new ItemStack(Items.COAL), new ItemStack(Items.COAL), new ItemStack(Items.COAL));
        addRecipe(recipes, new ItemStack(redstoneOre), new ItemStack(stone),
                new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE),
                new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE));
        addRecipe(recipes, new ItemStack(quartzOre), new ItemStack(rack),
                new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ),
                new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ), new ItemStack(Items.QUARTZ));
        addRecipe(recipes, new ItemStack(lapisOre), new ItemStack(stone),
                ItemHelper.getDyeFromItem(LAPIS), ItemHelper.getDyeFromItem(LAPIS), ItemHelper.getDyeFromItem(LAPIS),
                ItemHelper.getDyeFromItem(LAPIS), ItemHelper.getDyeFromItem(LAPIS), ItemHelper.getDyeFromItem(LAPIS));

        return recipes;
    }

    public static List<SweetInfuserRecipe> customOreRecipes() {
        List<SweetInfuserRecipe> recipes = Lists.newArrayList();

        Item lava = PotatoItems.LAVA_POTATO;
        Item water = PotatoItems.WATER_POTATO;
        Item driedSweet = PotatoItems.DRIED_SWEET_POTATO;

        Item stone = Item.getItemFromBlock(Blocks.STONE);
        Item gravel = Item.getItemFromBlock(Blocks.GRAVEL);
        Item rack = Item.getItemFromBlock(Blocks.NETHERRACK);
        Item cobble = Item.getItemFromBlock(Blocks.COBBLESTONE);

        Item ironOre = Item.getItemFromBlock(Blocks.IRON_ORE);
        Item goldOre = Item.getItemFromBlock(Blocks.GOLD_ORE);
        Item redstoneOre = Item.getItemFromBlock(Blocks.REDSTONE_ORE);

        addRecipe(recipes, new ItemStack(ironOre), new ItemStack(stone),
                new ItemStack(gravel), new ItemStack(lava), new ItemStack(gravel),
                new ItemStack(cobble), new ItemStack(water), new ItemStack(cobble));
        addRecipe(recipes, new ItemStack(goldOre), new ItemStack(stone),
                new ItemStack(rack), new ItemStack(lava), new ItemStack(rack),
                new ItemStack(cobble), new ItemStack(water), new ItemStack(cobble));
        addRecipe(recipes, new ItemStack(redstoneOre), new ItemStack(stone),
                ItemHelper.getDyeFromItem(DYE_RED), new ItemStack(driedSweet), ItemHelper.getDyeFromItem(DYE_RED),
                ItemHelper.getDyeFromItem(DYE_RED), new ItemStack(driedSweet), ItemHelper.getDyeFromItem(DYE_RED));

        return recipes;
    }

    public static List<SweetInfuserRecipe> gemRecipes() {
        List<SweetInfuserRecipe> recipes = Lists.newArrayList();

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

        addRecipe(recipes, new ItemStack(crystal), new ItemStack(crystalShard),
                new ItemStack(crystalShard), new ItemStack(crystalShard), new ItemStack(crystalShard),
                new ItemStack(crystalShard), new ItemStack(crystalShard), new ItemStack(crystalShard));
        addRecipe(recipes, new ItemStack(quartz), new ItemStack(crystalShard),
                ItemHelper.getDyeFromItem(DYE_WHITE), ItemHelper.getDyeFromItem(DYE_WHITE), ItemHelper.getDyeFromItem(DYE_WHITE),
                ItemHelper.getDyeFromItem(DYE_WHITE), ItemHelper.getDyeFromItem(DYE_WHITE), ItemHelper.getDyeFromItem(DYE_WHITE));
        addRecipe(recipes, new ItemStack(dye, 1, 4), new ItemStack(crystalShard),
                ItemHelper.getDyeFromItem(DYE_LIGHT_BLUE), ItemHelper.getDyeFromItem(DYE_LIGHT_BLUE), ItemHelper.getDyeFromItem(DYE_LIGHT_BLUE),
                ItemHelper.getDyeFromItem(DYE_LIGHT_BLUE), ItemHelper.getDyeFromItem(DYE_LIGHT_BLUE), ItemHelper.getDyeFromItem(DYE_LIGHT_BLUE));
        addRecipe(recipes, new ItemStack(diamond), new ItemStack(crystal),
                new ItemStack(crystalShard), new ItemStack(carbonatedCoal), new ItemStack(crystalShard),
                new ItemStack(carbonatedCoal), new ItemStack(crystalShard), new ItemStack(carbonatedCoal));
        addRecipe(recipes, new ItemStack(emerald), new ItemStack(crystal),
                new ItemStack(crystal), ItemHelper.getDyeFromItem(DYE_LIME), new ItemStack(crystal),
                ItemHelper.getDyeFromItem(DYE_LIME), new ItemStack(crystal), ItemHelper.getDyeFromItem(DYE_LIME));
        addRecipe(recipes, new ItemStack(chargedCrystal), new ItemStack(crystalShard),
                new ItemStack(chargedCrystalShard), new ItemStack(chargedCrystalShard), new ItemStack(chargedCrystalShard),
                new ItemStack(chargedCrystalShard), new ItemStack(chargedCrystalShard), new ItemStack(chargedCrystalShard));
        addRecipe(recipes, new ItemStack(concentratedCrystal), new ItemStack(crystalShard),
                new ItemStack(concentratedCrystalShard), new ItemStack(concentratedCrystalShard), new ItemStack(concentratedCrystalShard),
                new ItemStack(concentratedCrystalShard), new ItemStack(concentratedCrystalShard), new ItemStack(concentratedCrystalShard));

        return recipes;
    }
}
