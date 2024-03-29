package aldinh777.potatoheadshot.common.lists;

import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.common.util.BlockType;
import aldinh777.potatoheadshot.common.util.Element;
import aldinh777.potatoheadshot.common.util.FoodEffects;
import aldinh777.potatoheadshot.content.blocks.PotatoFoodBlock;
import aldinh777.potatoheadshot.content.items.*;
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
    public static Item LAVA_POTATO;
    public static Item WET_POTATO;
    public static Item WATER_POTATO;
    public static Item BAKED_SMALL_POTATO_PLANKS;
    public static Item FRIED_FRIES;
    public static Item BAKED_POTATO_CHIP;
    public static Item SALT_POTATO;
    public static Item SWEET_POTATO;
    public static Item BAKED_SWEET_POTATO;
    public static Item GLOWING_POTATO;
    public static Item LOKBOMB;

    public static Item LAVA_POTATO_SEED;
    public static Item WATER_POTATO_SEED;
    public static Item ICE_POTATO_SEED;
    public static Item LOKBOMB_SEED;
    public static Item CORRUPTED_SEED;

    public static Item SMALL_POTATO_PLANKS;
    public static Item POTATO_STICK;
    public static Item POTATO_CHIP;
    public static Item POTATO_STARCH;
    public static Item RAW_SALT;
    public static Item SWEET_POTATO_DUST;
    public static Item SWEET_POTATO_INGOT;
    public static Item GLOWING_POTATO_DUST;
    public static Item ESSENCE_MANA;
    public static Item ESSENCE_LIFE;
    public static Item ESSENCE_NATURE;
    public static Item ESSENCE_FIRE;

    public static Item POCKET_CAULDRON;

    public static Item POTATO_KNIFE;
    public static Item POTATO_MANA_KNIFE;
    public static Item LAVA_HOE;
    public static Item ARMOR_SWAP;
    public static Item INVENTORY_SWAP;
    public static Item HEART_CONTAINER;
    public static Item SWITCHING_CARD;
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

    public static Item UPGRADE_BOOSTER;
    public static Item UPGRADE_MULTIPLIER;

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

        // Seeds
        if (ConfigHandler.LAVA_POTATO) {
            LAVA_POTATO_SEED = new PotatoSeed("lava_potato_seed", PotatoBlocks.LAVA_POTATOES);
        }
        if (ConfigHandler.WATER_POTATO) {
            WATER_POTATO_SEED = new PotatoSeed("water_potato_seed", PotatoBlocks.WATER_POTATOES);
        }
        if (ConfigHandler.ICE_POTATO) {
            ICE_POTATO_SEED = new PotatoSeed("ice_potato_seed", PotatoBlocks.ICE_POTATO_STEM);
        }
        LOKBOMB_SEED = new PotatoSeed("lokbomb_seed", PotatoBlocks.LOKBOMB_PLANT);
        CORRUPTED_SEED = new CorruptedSeed("corrupted_seed");

        // Potato Food Section
        LOKBOMB = new PotatoFood("lokbomb", 3, 0.4f);
        if (ConfigHandler.POTATO_PLANKS && ConfigHandler.COOKED_POTATO_VARIANT) {
            BAKED_SMALL_POTATO_PLANKS = new PotatoFood("baked_small_potato_planks", 3, 0.4f);
            FRIED_FRIES = new PotatoFood("fried_fries", 2, 0.3f);
        }
        BAKED_SWEET_POTATO = new PotatoFood("baked_sweet_potato", 5, 0.6f);
        if (ConfigHandler.POTATO_CHIP) {
            BAKED_POTATO_CHIP = new PotatoFood("baked_potato_chip", 1, 0.2f);
        }
        if (ConfigHandler.SALT_POTATO) {
            SALT_POTATO = new PotatoFood("salt_potato", 2, 0.3f);
        }
        if (ConfigHandler.HOT_POTATO) {
            HOT_POTATO = new PotatoFoodBucket("extra_hot_potato", Blocks.FIRE, 2, 0.3f)
                    .setSoundEvent(SoundEvents.ENTITY_BLAZE_SHOOT).setBurnTime(1600);
        }
        if (ConfigHandler.LAVA_POTATO) {
            LAVA_POTATO = new PotatoFoodBucket("lava_potato", Blocks.FLOWING_LAVA, 2, 0.3f)
                    .setSoundEvent(SoundEvents.ITEM_BUCKET_EMPTY_LAVA).setBurnTime(6400).setMaxStackSize(16);
        }
        if (ConfigHandler.WET_POTATO) {
            WET_POTATO = new PotatoFood("super_wet_potato", 2, 0.3f);
        }
        if (ConfigHandler.WATER_POTATO) {
            WATER_POTATO = new PotatoFoodBucket("water_potato", Blocks.FLOWING_WATER, 2, 0.3f)
                    .setSoundEvent(SoundEvents.ITEM_BUCKET_EMPTY).setMaxStackSize(16);
        }

        // Potato Items
        if (ConfigHandler.POTATO_PLANKS) {
            SMALL_POTATO_PLANKS = new PotatoItem("small_potato_planks");
            POTATO_STICK = new PotatoItem("potato_stick", 100);
        }
        if (ConfigHandler.POTATO_CHIP) {
            POTATO_CHIP = new PotatoItem("potato_chip");
        }
        POTATO_STARCH = new PotatoItem("potato_starch");
        RAW_SALT = new PotatoItem("raw_salt");
        SWEET_POTATO_DUST = new PotatoItem("sweet_potato_dust");
        GLOWING_POTATO_DUST = new PotatoItem("glowing_potato_dust");
        ESSENCE_MANA = new PotatoItem("essence_mana");
        ESSENCE_LIFE = new PotatoItem("essence_life");
        ESSENCE_NATURE = new PotatoItem("essence_nature");
        ESSENCE_FIRE = new PotatoItem("essence_fire");
        POCKET_CAULDRON = new PocketCauldron("pocket_cauldron");

        // Bucket
        SWEET_POTATO_INGOT = new PotatoItem("sweet_potato_ingot");
        if (ConfigHandler.SWEET_BUCKET) {
            SWEET_POTATO_BUCKET = new PotatoItem("sweet_potato_bucket");
            SWEET_EMPTY_BUCKET = new SweetFoodBucket("sweet_bucket_empty", 5, 0.3f);
            SWEET_LAVA_BUCKET = new SweetBucket("sweet_bucket_lava", Blocks.FLOWING_LAVA);
            SWEET_WATER_BUCKET = new SweetBucket("sweet_bucket_water", Blocks.FLOWING_WATER);
            SWEET_MILK_BUCKET = new SweetBucket("sweet_bucket_milk");
        }

        // Potato other thing
        POTATO_KNIFE = new PotatoKnife("potato_knife");
        POTATO_MANA_KNIFE = new PotatoManaKnife("potato_mana_knife");
        if (ConfigHandler.LAVA_POTATO) {
            LAVA_HOE = new LavaHoe("lava_hoe");
        }
        ARMOR_SWAP = new ArmorSwap("armor_swap");
        INVENTORY_SWAP = new InventorySwap("inventory_swap");
        SWITCHING_CARD = new SwitchingCard("switching_card");
        HEART_CONTAINER = new HeartContainer("heart_container");
        CARBONATED_COAL = new PotatoItem("carbonated_coal", 102400);
        ULTIMATE_BROKEN_FUEL = new PotatoItem("ultimate_broken_fuel", 819200) {
            @Override
            public boolean hasEffect(@Nonnull ItemStack stack) {
                return true;
            }
        };

        // Potato Potions
        if (ConfigHandler.SPLASH_MANA) {
            SPLASH_MANA_FIRE = new SplashManaPotion("splash_mana_fire", Element.FIRE);
            SPLASH_MANA_LIFE = new SplashManaPotion("splash_mana_life", Element.LIFE);
        }

        // Upgrades
        UPGRADE_BOOSTER = new PotatoItem("upgrade_booster");
        UPGRADE_MULTIPLIER = new PotatoItem("upgrade_multiplier");

        // This is kinda complicated, but i have to put the food block here to access the item
        // since they are extend ItemFood not ItemBlock so i can't get like Item#getItemFromBlock
        if (ConfigHandler.COOKED_DIRT) {
            PotatoFoodBlock cookedDirt = new PotatoFoodBlock("cooked_dirt", BlockType.GROUND);
            COOKED_DIRT = new PotatoFoodItemBlock(cookedDirt, 3, 0.2f);
            cookedDirt.setDroppedItem(COOKED_DIRT);
        }
        if (ConfigHandler.POTATO_PLANKS && ConfigHandler.COOKED_POTATO_VARIANT) {
            PotatoFoodBlock bakedPotatoPlanks = new PotatoFoodBlock("baked_potato_planks", BlockType.WOOD);
            BAKED_POTATO_PLANKS = new PotatoFoodItemBlock(bakedPotatoPlanks, 5, 0.5f);
            bakedPotatoPlanks.setDroppedItem(BAKED_POTATO_PLANKS);
        }
        if (ConfigHandler.COOKED_POTATO_VARIANT) {
            PotatoFoodBlock bakedPotatoBlock = new PotatoFoodBlock("baked_potato_block", BlockType.POTATO);
            BAKED_POTATO_BLOCK = new PotatoFoodItemBlock(bakedPotatoBlock, 10, 0.8f);
            bakedPotatoBlock.setDroppedItem(BAKED_POTATO_BLOCK);
        }

        // Effects Definition
        FoodEffects hotPotatoEffects = FoodEffects.combine(FoodEffects.poisonPlayer, FoodEffects.burnPlayer);
        FoodEffects extraHotPotatoEffects = FoodEffects.combine(hotPotatoEffects, FoodEffects.confusePlayer);
        FoodEffects extremeHotPotatoEffects = FoodEffects.combine(extraHotPotatoEffects, FoodEffects.explodePlayer);
        FoodEffects lavaPotatoEffects = FoodEffects.combine(extremeHotPotatoEffects, FoodEffects.struckPlayer);
        FoodEffects wetPotatoEffects = FoodEffects.combine(FoodEffects.slowPlayer, FoodEffects.weakenPlayer);
        FoodEffects superWetPotatoEffects = FoodEffects.combine(wetPotatoEffects, FoodEffects.blindPlayer);
        FoodEffects waterPotatoEffects = FoodEffects.combine(superWetPotatoEffects,
                FoodEffects.tirePlayer, FoodEffects.fireResistPlayer);
        FoodEffects glowingPotatoEffects = FoodEffects.visionPlayer;
        FoodEffects lokbombEffects = FoodEffects.combine(
                FoodEffects.strengthenPlayer, FoodEffects.burnPlayer, FoodEffects.speedPlayer);

        // Configuration
        addFoodEffects(HOT_POTATO, hotPotatoEffects);
        addFoodEffects(HOT_POTATO, extraHotPotatoEffects);
        addFoodEffects(LAVA_POTATO, lavaPotatoEffects);
        addFoodEffects(WET_POTATO, superWetPotatoEffects);
        addFoodEffects(WATER_POTATO, waterPotatoEffects);
        addFoodEffects(GLOWING_POTATO, glowingPotatoEffects);
        addFoodEffects(LOKBOMB, lokbombEffects);

        addFoodEffects(SWEET_LAVA_BUCKET, FoodEffects.burnPlayer);
        addFoodEffects(SWEET_WATER_BUCKET, FoodEffects.unBurnPlayer);
        addFoodEffects(SWEET_MILK_BUCKET, FoodEffects.curePlayer);

        ULTIMATE_BROKEN_FUEL.setContainerItem(ULTIMATE_BROKEN_FUEL);
        ULTIMATE_BROKEN_FUEL.setMaxStackSize(1);
    }
}
