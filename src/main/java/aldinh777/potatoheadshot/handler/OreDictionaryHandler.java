package aldinh777.potatoheadshot.handler;

import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryHandler {

    public static void register() {
        OreDictionary.registerOre("stickWood", PotatoItems.POTATO_STICK);
        OreDictionary.registerOre("stickWood", PotatoItems.FRIED_FRIES);
        OreDictionary.registerOre("plankWood", PotatoBlocks.POTATO_PLANKS);
        OreDictionary.registerOre("plankWood", PotatoItems.BAKED_POTATO_PLANKS);
    }
}
