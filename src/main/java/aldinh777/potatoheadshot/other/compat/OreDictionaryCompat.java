package aldinh777.potatoheadshot.other.compat;

import aldinh777.potatoheadshot.other.handler.ConfigHandler;
import aldinh777.potatoheadshot.other.lists.PotatoBlocks;
import aldinh777.potatoheadshot.other.lists.PotatoItems;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryCompat {

    public static void register() {
        if (ConfigHandler.POTATO_PLANKS) {
            OreDictionary.registerOre("stickWood", PotatoItems.POTATO_STICK);
            OreDictionary.registerOre("plankWood", PotatoBlocks.POTATO_PLANKS);
            if (ConfigHandler.COOKED_POTATO_VARIANT) {
                OreDictionary.registerOre("stickWood", PotatoItems.FRIED_FRIES);
                OreDictionary.registerOre("plankWood", PotatoItems.BAKED_POTATO_PLANKS);
            }

            OreDictionary.registerOre("cropSweetpotato", PotatoItems.SWEET_POTATO);
            OreDictionary.registerOre("cropGlowingpotato", PotatoItems.GLOWING_POTATO);
            OreDictionary.registerOre("slimeball", PotatoItems.STICKY_POTATO);

            OreDictionary.registerOre("dustPotato", PotatoItems.POTATO_STARCH);
            OreDictionary.registerOre("salt", PotatoItems.RAW_SALT);
            OreDictionary.registerOre("dustSweetpotato", PotatoItems.SWEET_POTATO_DUST);
            OreDictionary.registerOre("dustGlowingpotato", PotatoItems.GLOWING_POTATO_DUST);

            OreDictionary.registerOre("blockPotato", PotatoBlocks.POTATO_BLOCK);
            OreDictionary.registerOre("blockSweetPotato", PotatoBlocks.SWEET_POTATO_BLOCK);
            OreDictionary.registerOre("ingotSweetpotato", PotatoItems.SWEET_POTATO_INGOT);
        }
    }
}
