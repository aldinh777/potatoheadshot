package aldinh777.potatoheadshot.other.lists;

import aldinh777.potatoheadshot.content.blocks.*;
import aldinh777.potatoheadshot.content.blocks.crops.*;
import aldinh777.potatoheadshot.content.blocks.machines.BlockDrier;
import aldinh777.potatoheadshot.content.blocks.machines.EnergyTransfer;
import aldinh777.potatoheadshot.content.blocks.machines.ManaCauldron;
import aldinh777.potatoheadshot.other.util.BlockType;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class PotatoBlocks {

    public static List<Block> LISTS = new ArrayList<>();

    public static Block LAVA_FARMLAND = new FarmlandLava("lava_farmland");

    public static Block SWEET_POTATOES = new SweetPotatoCrops("sweet_potatoes");
    public static Block GLOWING_POTATOES = new GlowingPotatoCrops("glowing_potatoes");
    public static Block LAVA_POTATOES = new LavaPotatoCrops("lava_potatoes");
    public static Block WATER_POTATOES = new WaterPotatoCrops("water_potatoes");
    public static Block STICKY_POTATOES = new StickyPotatoCrops("sticky_potatoes");
    public static Block RED_POTATOES = new RedPotatoCrops("red_potatoes");
    public static Block ICE_POTATO_STEM = new IcePotatoStem("ice_potato_stem");

    public static Block POTATO_PLANKS;
    public static Block POTATO_BLOCK;
    public static Block SWEET_POTATO_BLOCK;
    public static Block GLOWING_POTATO_BLOCK;
    public static Block ENERGY_TRANSFER;

    public static Block POTATO_DRIER;
    public static Block MANA_CAULDRON;

    public static void init() {

        // Normal Block
        POTATO_PLANKS = new PotatoBlock("potato_planks", BlockType.WOOD);
        POTATO_BLOCK = new PotatoBlock("potato_block", BlockType.POTATO);
        SWEET_POTATO_BLOCK = new PotatoBlock("sweet_potato_block", BlockType.METAL);
        GLOWING_POTATO_BLOCK = new BlockCustomDrop("glowing_potato_block", PotatoItems.GLOWING_POTATO_DUST);

        // Utility Block
        POTATO_DRIER = new BlockDrier("potato_drier", BlockType.STONE);
        ENERGY_TRANSFER = new EnergyTransfer("energy_transfer", BlockType.GLASS);
        MANA_CAULDRON = new ManaCauldron("mana_cauldron").setLightLevel(0.9f);
    }
}
