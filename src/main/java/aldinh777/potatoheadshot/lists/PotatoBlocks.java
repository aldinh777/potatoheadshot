package aldinh777.potatoheadshot.lists;

import aldinh777.potatoheadshot.block.backup.blocks.machines.*;
import aldinh777.potatoheadshot.block.blocks.*;
import aldinh777.potatoheadshot.block.blocks.crops.*;
import aldinh777.potatoheadshot.block.blocks.machines.BlockDrier;
import aldinh777.potatoheadshot.util.BlockType;
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
    public static Block SWEET_MACHINE_FRAME;
    public static Block GLOWING_POTATO_BLOCK;
    public static Block MANA_BLOCK;
    public static Block MANA_FLOWER;
    public static Block MANA_TORCH;
    public static Block MANA_STONE;
    public static Block ENERGY_TRANSFER;
    public static Block ULTIMATE_FLOWER;

    public static Block POTATO_DRIER;
    public static Block SWEET_POTATO_GENERATOR;
    public static Block SWEET_FREEZER;
    public static Block SWEET_CRYSTAL_MAKER;
    public static Block SWEET_CRYSTAL_CHARGER;
    public static Block SWEET_INFUSER;
    public static Block MANA_COLLECTOR;
    public static Block MANA_EXTRACTOR;
    public static Block MANA_CAULDRON;
    public static Block ULTIMATE_CRYSTAL_CHARGER;
    public static Block ULTIMATE_CAULDRON;

    public static void init() {

        // Normal Block
        POTATO_PLANKS = new PotatoBlock("potato_planks", BlockType.WOOD);
        POTATO_BLOCK = new PotatoBlock("potato_block", BlockType.POTATO);
        SWEET_POTATO_BLOCK = new PotatoBlock("sweet_potato_block", BlockType.METAL);
        SWEET_MACHINE_FRAME = new SweetMachineFrame("sweet_machine_frame", BlockType.METAL);
        GLOWING_POTATO_BLOCK = new BlockCustomDrop("glowing_potato_block", PotatoItems.GLOWING_POTATO_DUST);
        MANA_BLOCK = new BlockCustomDrop("glowing_mana_block", PotatoItems.MANA_DUST).setResistance(6000);
        MANA_STONE = new PotatoBlock("glowing_mana_stone", BlockType.STONE).setLightLevel(0.7f).setResistance(6000);

        // Place able Item
        MANA_TORCH = new Potatorch("glowing_mana_torch");
        MANA_FLOWER = new ManaFlower("glowing_mana_flower");
        ULTIMATE_FLOWER = new ManaFlower("ultimate_mana_flower").setLightLevel(1.0f);

        // Utility Block
        POTATO_DRIER = new BlockDrier("potato_drier", BlockType.STONE);
        SWEET_POTATO_GENERATOR = new SweetPotatoGenerator("sweet_potato_generator", BlockType.METAL);
        SWEET_FREEZER = new SweetFreezer("sweet_freezer", BlockType.METAL);
        SWEET_CRYSTAL_MAKER = new SweetCrystalMaker("sweet_crystal_maker", BlockType.METAL);
        SWEET_CRYSTAL_CHARGER = new SweetCrystalCharger("sweet_crystal_charger", BlockType.METAL);
        ULTIMATE_CRYSTAL_CHARGER = new UltCrystalCharger("ultimate_crystal_charger", BlockType.METAL);
        SWEET_INFUSER = new SweetInfuser("sweet_infuser", BlockType.METAL);
        MANA_COLLECTOR = new ManaCollector("mana_collector", BlockType.GLASS).setLightLevel(0.5f);
        MANA_EXTRACTOR = new ManaExtractor("mana_extractor", BlockType.GLASS).setLightLevel(0.5f);
        ENERGY_TRANSFER = new EnergyTransfer("energy_transfer", BlockType.GLASS);
        MANA_CAULDRON = new ManaCauldron("mana_cauldron").setLightLevel(0.9f);
        ULTIMATE_CAULDRON = new UltManaCauldron("ultimate_mana_cauldron");
    }
}
