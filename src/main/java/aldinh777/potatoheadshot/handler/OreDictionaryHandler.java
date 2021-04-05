package aldinh777.potatoheadshot.handler;

import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryHandler {

    public static void register() {
        if (ConfigHandler.POTATO_PLANKS) {
            OreDictionary.registerOre("stickWood", PotatoItems.POTATO_STICK);
            OreDictionary.registerOre("plankWood", PotatoBlocks.POTATO_PLANKS);
            if (ConfigHandler.COOKED_POTATO_VARIANT) {
                OreDictionary.registerOre("stickWood", PotatoItems.FRIED_FRIES);
                OreDictionary.registerOre("plankWood", PotatoItems.BAKED_POTATO_PLANKS);
            }
        }
    }
}
