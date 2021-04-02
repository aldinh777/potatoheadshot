package aldinh777.potatoheadshot.lists;

import aldinh777.potatoheadshot.block.blocks.ManaCauldron;
import aldinh777.potatoheadshot.block.blocks.PotatoFoodBlock;
import aldinh777.potatoheadshot.item.items.*;
import aldinh777.potatoheadshot.util.BlockType;
import aldinh777.potatoheadshot.util.FoodEffects;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PotatoItems {

    public static List<Item> LISTS = new ArrayList<>();

    public static Item HOT_POTATO;
    public static Item EXTRA_HOT_POTATO;
    public static Item EXTREME_HOT_POTATO;
    public static Item LAVA_POTATO;
    public static Item WET_POTATO;
    public static Item SUPER_WET_POTATO;
    public static Item WATER_POTATO;
    public static Item BAKED_SMALL_POTATO_PLANKS;
    public static Item FRIED_FRIES;
    public static Item BAKED_POTATO_CHIP;
    public static Item SALT_POTATO;
    public static Item SWEET_POTATO;
    public static Item BAKED_SWEET_POTATO;
    public static Item GLOWING_POTATO;
    public static Item STICKY_POTATO;
    public static Item RED_POTATO;
    public static Item FROZEN_POTATO;
    public static Item ICE_POTATO;

    public static Item LAVA_POTATO_SEED;
    public static Item WATER_POTATO_SEED;
    public static Item ICE_POTATO_SEED;

    public static Item SMALL_POTATO_PLANKS;
    public static Item POTATO_STICK;
    public static Item POTATO_LEAVES;
    public static Item DRIED_POTATO;
    public static Item POTATO_CHIP;
    public static Item POTATO_STARCH;
    public static Item RAW_SALT;
    public static Item DRIED_SWEET_POTATO;
    public static Item SWEET_POTATO_DUST;
    public static Item SWEET_POTATO_INGOT;
    public static Item GLOWING_POTATO_DUST;
    public static Item MANA_DUST;
    public static Item ESSENCE_LIFE;
    public static Item ESSENCE_NATURE;
    public static Item ESSENCE_FIRE;
    public static Item ROD_MANA;
    public static Item ROD_LIFE;
    public static Item ROD_NATURE;
    public static Item ROD_FIRE;

    public static Item CRYSTAL_SHARD;
    public static Item CRYSTAL;
    public static Item CHARGED_CRYSTAL_SHARD;
    public static Item CHARGED_CRYSTAL;
    public static Item CONCENTRATED_CRYSTAL_SHARD;
    public static Item CONCENTRATED_CRYSTAL;
    public static Item ULTIMATE_CRYSTAL;
    public static Item ULTIMATE_CHARGED_CRYSTAL;
    public static Item ULTIMATE_CONCENTRATED_CRYSTAL;
    public static Item POCKET_CAULDRON;
    public static Item ULTIMATE_POCKET_CAULDRON;

    public static Item POTATO_KNIFE;
    public static Item POTATO_MANA_KNIFE;
    public static Item LAVA_HOE;
    public static Item SWEET_POTATO_BUCKET;
    public static Item SWEET_EMPTY_BUCKET;
    public static Item SWEET_LAVA_BUCKET;
    public static Item SWEET_WATER_BUCKET;
    public static Item SWEET_MILK_BUCKET;
    public static Item CARBONATED_COAL;
    public static Item ULTIMATE_BROKEN_FUEL;

    public static Item SPLASH_MANA_FIRE;
    public static Item SPLASH_MANA_LIFE;

    public static Item COOKED_DIRT;
    public static Item BAKED_POTATO_PLANKS;
    public static Item BAKED_POTATO_BLOCK;

    public static void addFoodEffects(Item item, FoodEffects... effects) {
        if (item instanceof PotatoFood) {
            PotatoFood food = (PotatoFood) item;
            food.addEffects(effects);

        } else if (item instanceof SweetBucket) {
            SweetBucket food = (SweetBucket) item;
            food.addEffects(effects);
        }
    }

    public static void init() {

        // Crop
        SWEET_POTATO = new PotatoCrop("sweet_potato", 1, 0.3f, PotatoBlocks.SWEET_POTATOES);
        GLOWING_POTATO = new PotatoCrop("glowing_potato", 1, 0.3f, PotatoBlocks.GLOWING_POTATOES);
        STICKY_POTATO = new PotatoCrop("sticky_potato", 1, 0.3f, PotatoBlocks.STICKY_POTATOES);
        RED_POTATO = new PotatoCrop("red_potato", 1, 0.3f, PotatoBlocks.RED_POTATOES);

        // Seeds
        LAVA_POTATO_SEED = new LavaPotatoSeed("lava_potato_seed");
        WATER_POTATO_SEED = new PotatoSeed("water_potato_seed", PotatoBlocks.WATER_POTATOES);
        ICE_POTATO_SEED = new PotatoSeed("ice_potato_seed", PotatoBlocks.ICE_POTATO_STEM);

        // Potato Food Section
        BAKED_SMALL_POTATO_PLANKS = new PotatoFood("baked_small_potato_planks", 3, 0.4f);
        BAKED_POTATO_CHIP = new PotatoFood("baked_potato_chip", 1, 0.2f);
        FRIED_FRIES = new PotatoFood("fried_fries", 2, 0.3f);
        BAKED_SWEET_POTATO = new PotatoFood("baked_sweet_potato", 5, 0.6f);
        SALT_POTATO = new PotatoFood("salt_potato", 2, 0.3f);
        HOT_POTATO = new PotatoFood("hot_potato", 2, 0.3f);
        EXTRA_HOT_POTATO = new PotatoFoodBucket("extra_hot_potato", Blocks.FIRE, 2, 0.3f);
        EXTREME_HOT_POTATO = new PotatoFoodBucket("extreme_hot_potato", Blocks.AIR, 2, 0.3f);
        LAVA_POTATO = new PotatoFoodBucket("lava_potato", Blocks.FLOWING_LAVA, 2, 0.3f);
        WET_POTATO = new PotatoFood("wet_potato", 2, 0.3f);
        SUPER_WET_POTATO = new PotatoFood("super_wet_potato", 2, 0.3f);
        WATER_POTATO = new PotatoFoodBucket("water_potato", Blocks.FLOWING_WATER, 2, 0.3f);
        FROZEN_POTATO = new PotatoFood("frozen_potato", 1, 0.3f);

        // Potato Items
        ICE_POTATO = new PotatoItem("ice_potato");
        POTATO_LEAVES = new PotatoItem("potato_leaves");
        SMALL_POTATO_PLANKS = new PotatoItem("small_potato_planks");
        DRIED_POTATO = new PotatoItem("dried_potato");
        DRIED_SWEET_POTATO = new PotatoItem("dried_sweet_potato");
        POTATO_CHIP = new PotatoItem("potato_chip");
        POTATO_STICK = new PotatoItem("potato_stick", 100);
        POTATO_STARCH = new PotatoItem("potato_starch");
        RAW_SALT = new PotatoItem("raw_salt");
        SWEET_POTATO_DUST = new PotatoItem("sweet_potato_dust");
        GLOWING_POTATO_DUST = new PotatoItem("glowing_potato_dust");
        MANA_DUST = new PotatoItem("glowing_mana_dust");
        ESSENCE_LIFE = new PotatoItem("essence_life");
        ESSENCE_NATURE = new PotatoItem("essence_nature");
        ESSENCE_FIRE = new PotatoItem("essence_fire");
        ROD_MANA = new PotatoItem("rod_mana");
        ROD_LIFE = new PotatoItem("rod_life");
        ROD_NATURE = new PotatoItem("rod_nature");
        ROD_FIRE = new PotatoItem("rod_fire");
        POCKET_CAULDRON = new PocketCauldron("pocket_cauldron");
        ULTIMATE_POCKET_CAULDRON = new PocketCauldron("ultimate_pocket_cauldron").setUltimate();

        // Crystal
        CRYSTAL_SHARD = new PotatoItem("crystal_shard");
        CHARGED_CRYSTAL_SHARD = new PotatoItem("charged_crystal_shard");
        CONCENTRATED_CRYSTAL_SHARD = new PotatoItem("concentrated_crystal_shard");
        CRYSTAL = new PotatoItem("crystal");
        CHARGED_CRYSTAL = new PotatoItem("charged_crystal");
        CONCENTRATED_CRYSTAL = new PotatoItem("concentrated_crystal");
        ULTIMATE_CRYSTAL = new PotatoItem("ultimate_crystal");
        ULTIMATE_CHARGED_CRYSTAL = new PotatoItem("ultimate_charged_crystal");
        ULTIMATE_CONCENTRATED_CRYSTAL = new PotatoItem("ultimate_concentrated_crystal");

        // Bucket
        SWEET_POTATO_INGOT = new PotatoItem("sweet_potato_ingot");
        SWEET_POTATO_BUCKET = new PotatoItem("sweet_potato_bucket");
        SWEET_EMPTY_BUCKET = new SweetFoodBucket("sweet_bucket_empty", 5, 0.3f);
        SWEET_LAVA_BUCKET = new SweetBucket("sweet_bucket_lava", Blocks.FLOWING_LAVA);
        SWEET_WATER_BUCKET = new SweetBucket("sweet_bucket_water", Blocks.FLOWING_WATER);
        SWEET_MILK_BUCKET = new SweetBucket("sweet_bucket_milk");

        // Potato other thing
        POTATO_KNIFE = new PotatoKnife("potato_knife");
        POTATO_MANA_KNIFE = new PotatoManaKnife("potato_mana_knife");
        LAVA_HOE = new LavaHoe("lava_hoe");
        CARBONATED_COAL = new PotatoItem("carbonated_coal", 102400);
        ULTIMATE_BROKEN_FUEL = new PotatoItem("ultimate_broken_fuel", 819200) {
            @Override
            public boolean hasEffect(@Nonnull ItemStack stack) {
                return true;
            }
        };

        // Potato Potions
        SPLASH_MANA_FIRE = new SplashManaPotion("splash_mana_fire", ManaCauldron.Element.FIRE);
        SPLASH_MANA_LIFE = new SplashManaPotion("splash_mana_life", ManaCauldron.Element.LIFE);

        // This is kinda complicated, but i have to put the food block here to access the item
        // since they are extend ItemFood not ItemBlock so i can't get like Item#getItemFromBlock
        PotatoFoodBlock cookedDirt = new PotatoFoodBlock("cooked_dirt", BlockType.GROUND);
        PotatoFoodBlock bakedPotatoPlanks = new PotatoFoodBlock("baked_potato_planks", BlockType.WOOD);
        PotatoFoodBlock bakedPotatoBlock = new PotatoFoodBlock("baked_potato_block", BlockType.POTATO);

        COOKED_DIRT = new PotatoFoodItemBlock(cookedDirt, 3, 0.2f);
        BAKED_POTATO_PLANKS = new PotatoFoodItemBlock(bakedPotatoPlanks, 5, 0.5f);
        BAKED_POTATO_BLOCK = new PotatoFoodItemBlock(bakedPotatoBlock, 10, 0.8f);

        cookedDirt.setDroppedItem(COOKED_DIRT);
        bakedPotatoPlanks.setDroppedItem(BAKED_POTATO_PLANKS);
        bakedPotatoBlock.setDroppedItem(BAKED_POTATO_BLOCK);

        // Configuration Definition
        PotatoFoodBucket extraHotPotato = (PotatoFoodBucket) EXTRA_HOT_POTATO;
        PotatoFoodBucket extremeHotPotato = (PotatoFoodBucket) EXTREME_HOT_POTATO;
        PotatoFoodBucket lavaPotato = (PotatoFoodBucket) LAVA_POTATO;
        PotatoFoodBucket waterPotato = (PotatoFoodBucket) WATER_POTATO;

        FoodEffects hotPotatoEffects = FoodEffects.combine(FoodEffects.poisonPlayer, FoodEffects.burnPlayer);
        FoodEffects extraHotPotatoEffects = FoodEffects.combine(hotPotatoEffects, FoodEffects.confusePlayer);
        FoodEffects extremeHotPotatoEffects = FoodEffects.combine(extraHotPotatoEffects, FoodEffects.explodePlayer);
        FoodEffects lavaPotatoEffects = FoodEffects.combine(extremeHotPotatoEffects, FoodEffects.struckPlayer);
        FoodEffects wetPotatoEffects = FoodEffects.combine(FoodEffects.slowPlayer, FoodEffects.weakenPlayer);
        FoodEffects superWetPotatoEffects = FoodEffects.combine(wetPotatoEffects, FoodEffects.blindPlayer);
        FoodEffects waterPotatoEffects = FoodEffects.combine(superWetPotatoEffects,
                FoodEffects.tirePlayer, FoodEffects.fireResistPlayer);
        FoodEffects glowingPotatoEffects = FoodEffects.visionPlayer;

        // Configuration
        extraHotPotato.setSoundEvent(SoundEvents.ENTITY_BLAZE_SHOOT).setBurnTime(1600);
        extremeHotPotato.setSoundEvent(SoundEvents.ENTITY_GENERIC_EXPLODE).setBurnTime(3200).setExplodeOnPlaced();
        lavaPotato.setSoundEvent(SoundEvents.ITEM_BUCKET_EMPTY_LAVA).setBurnTime(6400).setMaxStackSize(16);
        waterPotato.setSoundEvent(SoundEvents.ITEM_BUCKET_EMPTY).setMaxStackSize(16);

        addFoodEffects(HOT_POTATO, hotPotatoEffects);
        addFoodEffects(EXTRA_HOT_POTATO, extraHotPotatoEffects);
        addFoodEffects(EXTREME_HOT_POTATO, extremeHotPotatoEffects);
        addFoodEffects(LAVA_POTATO, lavaPotatoEffects);
        addFoodEffects(WET_POTATO, wetPotatoEffects);
        addFoodEffects(SUPER_WET_POTATO, superWetPotatoEffects);
        addFoodEffects(WATER_POTATO, waterPotatoEffects);
        addFoodEffects(GLOWING_POTATO, glowingPotatoEffects);

        addFoodEffects(SWEET_LAVA_BUCKET, FoodEffects.burnPlayer);
        addFoodEffects(SWEET_WATER_BUCKET, FoodEffects.unBurnPlayer);
        addFoodEffects(SWEET_MILK_BUCKET, FoodEffects.curePlayer);

        ULTIMATE_BROKEN_FUEL.setContainerItem(ULTIMATE_BROKEN_FUEL);
        ULTIMATE_BROKEN_FUEL.setMaxStackSize(1);
    }
}
