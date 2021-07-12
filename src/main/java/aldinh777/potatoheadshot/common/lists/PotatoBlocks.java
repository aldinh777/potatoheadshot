package aldinh777.potatoheadshot.common.lists;

import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.content.blocks.*;
import aldinh777.potatoheadshot.content.blocks.crops.*;
import aldinh777.potatoheadshot.content.blocks.flower.FlamingFlower;
import aldinh777.potatoheadshot.content.blocks.flower.GrowingFlower;
import aldinh777.potatoheadshot.content.blocks.flower.MistFlower;
import aldinh777.potatoheadshot.content.blocks.flower.RegenFlower;
import aldinh777.potatoheadshot.content.blocks.machines.BlockDrier;
import aldinh777.potatoheadshot.content.blocks.machines.EnergyTransfer;
import aldinh777.potatoheadshot.content.blocks.machines.ManaCauldron;
import aldinh777.potatoheadshot.common.util.BlockType;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class PotatoBlocks {

    public static List<Block> LISTS = new ArrayList<>();

    public static Block LAVA_FARMLAND = ConfigHandler.LAVA_POTATO ? new FarmlandLava("lava_farmland") : null;

    public static Block SWEET_POTATOES = new SweetPotatoCrops("sweet_potatoes");
    public static Block GLOWING_POTATOES = new GlowingPotatoCrops("glowing_potatoes");
    public static Block LAVA_POTATOES = ConfigHandler.LAVA_POTATO ? new LavaPotatoCrops("lava_potatoes") : null;
    public static Block WATER_POTATOES = ConfigHandler.WATER_POTATO ? new WaterPotatoCrops("water_potatoes") : null;
    public static Block ICE_POTATO_STEM = ConfigHandler.ICE_POTATO ? new IcePotatoStem("ice_potato_stem") : null;

    public static Block LOKBOMB_PLANT = new Lokbomb("lokbomb_plant");

    public static Block POTATO_PLANKS;
    public static Block POTATO_BLOCK;
    public static Block SWEET_POTATO_BLOCK;
    public static Block GLOWING_POTATO_BLOCK;
    public static Block MAGIC_SAPLING;
    public static Block ENERGY_TRANSFER;
    public static Block CORRUPTED_BLOCK;

    public static Block POTATO_DRIER;
    public static Block MANA_CAULDRON;

    public static Block FLAMING_FLOWER;
    public static Block MIST_FLOWER;
    public static Block GROWING_FLOWER;
    public static Block REGEN_FLOWER;

    public static void init() {

        // Normal Block
        if (ConfigHandler.POTATO_PLANKS) {
            POTATO_PLANKS = new PotatoBlock("potato_planks", BlockType.WOOD);
        }
        POTATO_BLOCK = new PotatoBlock("potato_block", BlockType.POTATO);
        SWEET_POTATO_BLOCK = new PotatoBlock("sweet_potato_block", BlockType.METAL);
        GLOWING_POTATO_BLOCK = new MagicBlock("glowing_potato_block", BlockType.MAGIC);
        CORRUPTED_BLOCK = new CorruptedBlock("corrupted_block");

        // Utility Block
        MAGIC_SAPLING = new MagicSapling("magic_sapling");
        POTATO_DRIER = new BlockDrier("potato_drier", BlockType.STONE);
        ENERGY_TRANSFER = new EnergyTransfer("energy_transfer", BlockType.GLASS);
        MANA_CAULDRON = new ManaCauldron("mana_cauldron");

        // Flower
        FLAMING_FLOWER = new FlamingFlower("flaming_flower");
        MIST_FLOWER = new MistFlower("mist_flower");
        GROWING_FLOWER = new GrowingFlower("growing_flower");
        REGEN_FLOWER = new RegenFlower("regen_flower");
    }
}
