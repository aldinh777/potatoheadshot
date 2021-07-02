package aldinh777.potatoheadshot.other.handler;

import aldinh777.potatoheadshot.PotatoHeadshot;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;

    // Potatoes
    public static boolean RED_POTATO;
    public static boolean STICKY_POTATO;
    public static boolean LAVA_POTATO;
    public static boolean WATER_POTATO;
    public static boolean ICE_POTATO;
    public static boolean HOT_POTATO;
    public static boolean WET_POTATO;
    public static boolean SALT_POTATO;

    // Trivial
    public static boolean COOKED_DIRT;
    public static boolean COOKED_POTATO_VARIANT;
    public static boolean POTATO_PLANKS;
    public static boolean POTATO_CHIP;
    public static boolean SWEET_BUCKET;
    public static boolean SPLASH_MANA;
    public static boolean SPLASH_FIRE_COOK_GRASS;

    // Mana
    public static int MANA_CAULDRON_CAPACITY;
    public static int ENERGY_TRANSFER_RANGE;

    // Compat
    public static boolean BOTANIA_COMPAT;


    public static void init() {
        File configDirectory = PotatoHeadshot.CONFIG_DIR;
        boolean configJustCreated = configDirectory.mkdirs();

        File configFile = new File(configDirectory.getPath(), "config.cfg");
        config = new Configuration(configFile);

        if (!configJustCreated) {
            config.load();
        }

        String category;

        category = "Potatoes";
        config.addCustomCategoryComment(category, "Should these potatoes be registered?");
        RED_POTATO = config.getBoolean("RED_POTATO", category, true, "");
        STICKY_POTATO = config.getBoolean("STICKY_POTATO", category, true, "");
        LAVA_POTATO = config.getBoolean("LAVA_POTATO", category, true, "");
        WATER_POTATO = config.getBoolean("WATER_POTATO", category, true, "");
        ICE_POTATO = config.getBoolean("ICE_POTATO", category, true, "");
        HOT_POTATO = config.getBoolean("HOT_POTATO", category, true, "");
        WET_POTATO = config.getBoolean("WET_POTATO", category, true, "");
        SALT_POTATO = config.getBoolean("SALT_POTATO", category, true, "");


        category = "Trivia";
        config.addCustomCategoryComment(category, "Feature that don't actually have to be exists");
        COOKED_DIRT = config.getBoolean("COOKED_DIRT", category, true, "");
        POTATO_PLANKS = config.getBoolean("POTATO_PLANKS", category, true, "");
        COOKED_POTATO_VARIANT = config.getBoolean("COOKED_POTATO_VARIANT", category, true, "");
        POTATO_CHIP = config.getBoolean("POTATO_CHIP", category, true, "");
        SWEET_BUCKET = config.getBoolean("SWEET_BUCKET", category, true, "");
        SPLASH_MANA = config.getBoolean("SPLASH_MANA", category, true, "");
        SPLASH_FIRE_COOK_GRASS = config.getBoolean("SPLASH_FIRE_COOK_GRASS", category, true, "");


        category = "Mana";
        config.addCustomCategoryComment(category, "Magical Machines Configuration");
        MANA_CAULDRON_CAPACITY = config.getInt("MANA_CAULDRON_CAPACITY", category, 64_000, 0, Integer.MAX_VALUE, "");
        ENERGY_TRANSFER_RANGE = config.getInt("ENERGY_TRANSFER_RANGE", category, 8, 0, Integer.MAX_VALUE, "");


        category = "Compatibility";
        config.addCustomCategoryComment(category, "Support for other mods");
        BOTANIA_COMPAT = config.getBoolean("BOTANIA_COMPAT", category, true, "");


        config.save();
    }
}
