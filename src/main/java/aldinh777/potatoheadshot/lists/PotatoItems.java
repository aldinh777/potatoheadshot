package aldinh777.potatoheadshot.lists;

import aldinh777.potatoheadshot.item.*;
import aldinh777.potatoheadshot.util.FoodEffects;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;

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
    public static Item GLOWING_MANA_DUST;

    public static Item POTATO_KNIFE;
    public static Item SWEET_POTATO_BUCKET;
    public static Item SWEET_EMPTY_BUCKET;
    public static Item SWEET_LAVA_BUCKET;
    public static Item SWEET_WATER_BUCKET;
    public static Item SWEET_MILK_BUCKET;
    public static Item ULTIMATE_BROKEN_FUEL;
    public static Item USELESS_POTATO;

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

        // Potato Food Section
        HOT_POTATO = new PotatoFood("hot_potato", 2, 0.3f);
        EXTRA_HOT_POTATO = new PotatoFoodBucket("extra_hot_potato", Blocks.FIRE, 2, 0.3f);
        EXTREME_HOT_POTATO = new PotatoFoodBucket("extreme_hot_potato", Blocks.AIR, 2, 0.3f);
        LAVA_POTATO = new PotatoFoodBucket("lava_potato", Blocks.FLOWING_LAVA, 2, 0.3f);
        WET_POTATO = new PotatoFood("wet_potato", 2, 0.3f);
        SUPER_WET_POTATO = new PotatoFood("super_wet_potato", 2, 0.3f);
        WATER_POTATO = new PotatoFoodBucket("water_potato", Blocks.FLOWING_WATER, 2, 0.3f);
        SALT_POTATO = new PotatoFood("salt_potato", 2, 0.3f);
        BAKED_SMALL_POTATO_PLANKS = new PotatoFood("baked_small_potato_planks", 3, 0.4f);
        BAKED_POTATO_CHIP = new PotatoFood("baked_potato_chip", 1, 0.2f);
        FRIED_FRIES = new PotatoFood("fried_fries", 2, 0.3f);

        // Sweet Potato Food Section
        SWEET_POTATO = new PotatoCrop("sweet_potato", 1, 0.3f, PotatoBlocks.SWEET_POTATOES);
        BAKED_SWEET_POTATO = new PotatoFood("baked_sweet_potato", 5, 0.6f);

        // Glowing Potato Food Section
        GLOWING_POTATO = new PotatoCrop("glowing_potato", 1, 0.3f, PotatoBlocks.GLOWING_POTATOES);

        // Potato Items
        SMALL_POTATO_PLANKS = new PotatoItem("small_potato_planks");
        DRIED_POTATO = new PotatoItem("dried_potato");
        POTATO_CHIP = new PotatoItem("potato_chip");
        POTATO_STICK = new PotatoItem("potato_stick", 100);
        POTATO_LEAVES = new PotatoItem("potato_leaves");
        POTATO_STARCH = new PotatoItem("potato_starch");
        RAW_SALT = new PotatoItem("raw_salt");
        DRIED_SWEET_POTATO = new PotatoItem("dried_sweet_potato");
        SWEET_POTATO_DUST = new PotatoItem("sweet_potato_dust");
        SWEET_POTATO_INGOT = new PotatoItem("sweet_potato_ingot");
        GLOWING_POTATO_DUST = new PotatoItem("glowing_potato_dust");
        GLOWING_MANA_DUST = new PotatoItem("glowing_mana_dust");

        // Bucket
        SWEET_POTATO_BUCKET = new PotatoItem("sweet_potato_bucket");
        SWEET_EMPTY_BUCKET = new SweetFoodBucket("sweet_bucket_empty", 5, 0.3f);
        SWEET_LAVA_BUCKET = new SweetBucket("sweet_bucket_lava", Blocks.FLOWING_LAVA);
        SWEET_WATER_BUCKET = new SweetBucket("sweet_bucket_water", Blocks.FLOWING_WATER);
        SWEET_MILK_BUCKET = new SweetBucket("sweet_bucket_milk");

        // Potato other thing
        POTATO_KNIFE = new PotatoKnife("potato_knife");
        ULTIMATE_BROKEN_FUEL = new PotatoItem("ultimate_broken_fuel", 200);
        USELESS_POTATO = new UselessPotato();


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

        addFoodEffects(SWEET_LAVA_BUCKET, FoodEffects.burnPlayer);
        addFoodEffects(SWEET_WATER_BUCKET, FoodEffects.unBurnPlayer);
        addFoodEffects(SWEET_MILK_BUCKET, FoodEffects.curePlayer);

        ULTIMATE_BROKEN_FUEL.setContainerItem(ULTIMATE_BROKEN_FUEL);
        ULTIMATE_BROKEN_FUEL.setMaxStackSize(1);
    }
}
