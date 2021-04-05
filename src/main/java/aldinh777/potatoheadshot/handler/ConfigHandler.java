package aldinh777.potatoheadshot.handler;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;

    // Crops
    public static boolean RED_POTATO;
    public static boolean STICKY_POTATO;
    public static boolean LAVA_POTATO_SEED;
    public static boolean WATER_POTATO_SEED;
    public static boolean ICE_POTATO_SEED;

    // Trivial
    public static boolean HOT_POTATO;
    public static boolean WET_POTATO;
    public static boolean FROZEN_POTATO;

    public static boolean COOKED_DIRT;
    public static boolean COOKED_POTATO_VARIANT;
    public static boolean POTATO_PLANKS;
    public static boolean SALT_POTATO;
    public static boolean POTATO_CHIP;
    public static boolean SWEET_BUCKET;
    public static boolean SPLASH_MANA;

    // Machines
    public static boolean POTATO_DRIER;
    public static boolean SWEET_POTATO_GENERATOR;
    public static boolean SWEET_FREEZER;
    public static boolean SWEET_CRYSTAL_MAKER;
    public static boolean SWEET_CRYSTAL_CHARGER;
    public static boolean SWEET_INFUSER;

    public static int GENERATOR_RATE;

    // Mana
    public static boolean MANA_COLLECTOR;
    public static boolean MANA_EXTRACTOR;
    public static boolean MANA_CAULDRON;
    public static boolean MANA_TORCH;
    public static boolean ENERGY_TRANSFER;

    public static int MANA_COLLECTOR_RATE;
    public static int MANA_FLOWER_RATE;
    public static int MANA_CAULDRON_CAPACITY;

    // Ultimate
    public static boolean ULTIMATE_CRYSTALS;
    public static boolean ULTIMATE_FLOWER;
    public static boolean ULTIMATE_BROKEN_FUEL;
    public static boolean ULTIMATE_CRYSTAL_CHARGER;
    public static boolean ULTIMATE_CAULDRON;

    public static boolean CHARGED_CRYSTAL_EXPLOSION;
    public static boolean CONCENTRATED_CRYSTAL_EXPLOSION;

    public static int ULTIMATE_FLOWER_RATE;
    public static int ULTIMATE_CAULDRON_CAPACITY;

    public static int CHARGED_SHARD_ENERGY_REQUIRED;
    public static int CONCENTRATED_SHARD_MANA_REQUIRED;
    public static int CHARGED_CRYSTAL_ENERGY_REQUIRED;
    public static int CONCENTRATED_CRYSTAL_MANA_REQUIRED;

    // Compat
    public static boolean BOTANIA_COMPAT;


    public static void init(FMLPreInitializationEvent event) {
        File configDirectory = new File(event.getModConfigurationDirectory() + "/potatoHeadshot");
        boolean configJustCreated = configDirectory.mkdirs();
        File configFile = new File(configDirectory.getPath(), "config.cfg");
        config = new Configuration(configFile);

        if (!configJustCreated) {
            config.load();
        }

        String category;

        category = "Crops";
        config.addCustomCategoryComment(category, "Crops Config");

        RED_POTATO = config.getBoolean("RED_POTATO", category, true, "");
        STICKY_POTATO = config.getBoolean("STICKY_POTATO", category, true, "");
        LAVA_POTATO_SEED = config.getBoolean("LAVA_POTATO_SEED", category, true, "");
        WATER_POTATO_SEED = config.getBoolean("WATER_POTATO_SEED", category, true, "");
        ICE_POTATO_SEED = config.getBoolean("ICE_POTATO_SEED", category, true, "");


        category = "Trivia";
        config.addCustomCategoryComment(category, "Feature that don't actually have to be exists");

        HOT_POTATO = config.getBoolean("HOT_POTATO", category, true, "");
        WET_POTATO = config.getBoolean("WET_POTATO", category, true, "");
        FROZEN_POTATO = config.getBoolean("FROZEN_POTATO", category, true, "");

        COOKED_DIRT = config.getBoolean("COOKED_DIRT", category, true, "");
        POTATO_PLANKS = config.getBoolean("POTATO_PLANKS", category, true, "");
        COOKED_POTATO_VARIANT = config.getBoolean("COOKED_POTATO_VARIANT", category, true, "");
        SALT_POTATO = config.getBoolean("SALT_POTATO", category, true, "");
        POTATO_CHIP = config.getBoolean("POTATO_CHIP", category, true, "");
        SWEET_BUCKET = config.getBoolean("SWEET_BUCKET", category, true, "");
        SPLASH_MANA = config.getBoolean("SPLASH_MANA", category, true, "");


        category = "Machines";
        config.addCustomCategoryComment(category, "Machines Config");

        POTATO_DRIER = config.getBoolean("POTATO_DRIER", category, true, "");
        SWEET_POTATO_GENERATOR = config.getBoolean("SWEET_POTATO_GENERATOR", category, true, "");
        SWEET_FREEZER = config.getBoolean("SWEET_FREEZER", category, true, "");
        SWEET_CRYSTAL_MAKER = config.getBoolean("SWEET_CRYSTAL_MAKER", category, true, "");
        SWEET_CRYSTAL_CHARGER = config.getBoolean("SWEET_CRYSTAL_CHARGER", category, true, "");
        SWEET_INFUSER = config.getBoolean("SWEET_INFUSER", category, true, "");

        GENERATOR_RATE = config.getInt("GENERATOR_RATE", category, 10, 0, Integer.MAX_VALUE, "");


        category = "Mana";
        config.addCustomCategoryComment(category, "Mana Config");

        MANA_COLLECTOR = config.getBoolean("MANA_COLLECTOR", category, true, "");
        MANA_EXTRACTOR = config.getBoolean("MANA_EXTRACTOR", category, true, "");
        MANA_CAULDRON = config.getBoolean("MANA_CAULDRON", category, true, "");
        MANA_TORCH = config.getBoolean("MANA_TORCH", category, true, "");

        MANA_COLLECTOR_RATE = config.getInt("MANA_COLLECTOR_RATE", category, 2, 0, Integer.MAX_VALUE, "");
        MANA_FLOWER_RATE = config.getInt("MANA_FLOWER_RATE", category, 8, 0, Integer.MAX_VALUE, "");
        MANA_CAULDRON_CAPACITY = config.getInt("MANA_CAULDRON_CAPACITY", category, 320_000, 0, Integer.MAX_VALUE, "");


        category = "Ultimate";
        config.addCustomCategoryComment(category, "Ultimate Config");

        ULTIMATE_CRYSTALS = config.getBoolean("ULTIMATE_CRYSTALS", category, true, "");
        ULTIMATE_FLOWER = config.getBoolean("ULTIMATE_FLOWER", category, true, "");
        ULTIMATE_BROKEN_FUEL = config.getBoolean("ULTIMATE_BROKEN_FUEL", category, true, "");
        ULTIMATE_CRYSTAL_CHARGER = config.getBoolean("ULTIMATE_CRYSTAL_CHARGER", category, true, "");
        ULTIMATE_CAULDRON = config.getBoolean("ULTIMATE_CAULDRON", category, true, "");

        CHARGED_CRYSTAL_EXPLOSION = config.getBoolean("CHARGED_CRYSTAL_EXPLOSION", category, true, "");
        CONCENTRATED_CRYSTAL_EXPLOSION = config.getBoolean("CONCENTRATED_CRYSTAL_EXPLOSION", category, true, "");

        ULTIMATE_FLOWER_RATE = config.getInt("ULTIMATE_FLOWER_RATE", category, 3200, 0, Integer.MAX_VALUE, "");
        ULTIMATE_CAULDRON_CAPACITY = config.getInt("ULTIMATE_CAULDRON_CAPACITY", category, 3_200_000, 0, Integer.MAX_VALUE, "");

        CHARGED_SHARD_ENERGY_REQUIRED = config.getInt("CHARGED_SHARD_ENERGY_REQUIRED", category, 160_000, 0, Integer.MAX_VALUE, "");
        CONCENTRATED_SHARD_MANA_REQUIRED = config.getInt("CONCENTRATED_SHARD_MANA_REQUIRED", category, 3_200_000, 0, Integer.MAX_VALUE, "");
        CHARGED_CRYSTAL_ENERGY_REQUIRED = config.getInt("CHARGED_CRYSTAL_ENERGY_REQUIRED", category, 16_000, 0, Integer.MAX_VALUE, "");
        CONCENTRATED_CRYSTAL_MANA_REQUIRED = config.getInt("CONCENTRATED_CRYSTAL_MANA_REQUIRED", category, 320_000, 0, Integer.MAX_VALUE, "");


        category = "Compatibility";
        config.addCustomCategoryComment(category, "Support for other mods");

        BOTANIA_COMPAT = config.getBoolean("BOTANIA_COMPAT", category, true, "");


        config.save();
    }
}
