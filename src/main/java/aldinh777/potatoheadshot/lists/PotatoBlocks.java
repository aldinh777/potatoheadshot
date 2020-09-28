package aldinh777.potatoheadshot.lists;

import aldinh777.potatoheadshot.block.blocks.*;
import aldinh777.potatoheadshot.block.crops.*;
import aldinh777.potatoheadshot.util.BlockType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PotatoBlocks {

    public static List<Block> LISTS = new ArrayList<>();

    public static Block LAVA_FARMLAND = new FarmlandLava("lava_farmland");

    public static Block SWEET_POTATOES = new SweetPotatoCrops("sweet_potatoes");
    public static Block GLOWING_POTATOES = new GlowingPotatoCrops("glowing_potatoes");
    public static Block LAVA_POTATOES = new LavaPotatoCrops("lava_potatoes");
    public static Block WATER_POTATOES = new WaterPotatoCrops("water_potatoes");
    public static Block STICKY_POTATOES = new StickyPotatoCrops("sticky_potatoes");

    public static Block COOKED_DIRT;
    public static Block BAKED_POTATO_PLANKS;
    public static Block BAKED_POTATO_BLOCK;

    public static Block POTATO_PLANKS;
    public static Block POTATO_BLOCK;
    public static Block SWEET_POTATO_BLOCK;
    public static Block SWEET_MACHINE_FRAME;
    public static Block GLOWING_POTATO_BLOCK;
    public static Block MANA_BLOCK;
    public static Block MANA_FLOWER;
    public static Block MANA_TORCH;
    public static Block MANA_STONE;
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

        // Place able Item
        MANA_TORCH = new Potatorch("glowing_mana_torch");
        MANA_FLOWER = new ManaFlower("glowing_mana_flower");
        ULTIMATE_FLOWER = new ManaFlower("ultimate_mana_flower").setLightLevel(1.0f);

        // Food Block
        COOKED_DIRT = new PotatoFoodBlock("cooked_dirt", BlockType.GROUND, 3, 0.2f);
        BAKED_POTATO_PLANKS = new PotatoFoodBlock("baked_potato_planks", BlockType.WOOD, 5, 0.5f);
        BAKED_POTATO_BLOCK = new PotatoFoodBlock("baked_potato_block", BlockType.POTATO, 10, 0.8f);

        // Normal Block
        POTATO_PLANKS = new PotatoBlock("potato_planks", BlockType.WOOD);
        POTATO_BLOCK = new PotatoBlock("potato_block", BlockType.POTATO);
        SWEET_POTATO_BLOCK = new PotatoBlock("sweet_potato_block", BlockType.METAL);
        SWEET_MACHINE_FRAME = new PotatoBlock("sweet_machine_frame", BlockType.METAL) {
            @Nonnull
            @Override
            public BlockRenderLayer getBlockLayer() {
                return BlockRenderLayer.CUTOUT;
            }

            @Override
            public boolean isOpaqueCube(@Nonnull IBlockState state) {
                return false;
            }

            @Override
            public boolean isFullCube(@Nonnull IBlockState state) {
                return false;
            }
        };
        GLOWING_POTATO_BLOCK = new PotatoBlock("glowing_potato_block", BlockType.GLASS) {
            @Nonnull
            @Override
            public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
                return PotatoItems.GLOWING_POTATO_DUST;
            }
        }.setLightLevel(1.0f);
        MANA_BLOCK = new PotatoBlock("glowing_mana_block", BlockType.GLASS) {
            @Nonnull
            @Override
            public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
                return PotatoItems.MANA_DUST;
            }
        }.setLightLevel(1.0f);
        MANA_STONE = new PotatoBlock("glowing_mana_stone", BlockType.STONE).setLightLevel(0.7f).setResistance(6000);

        // Utility Block
        POTATO_DRIER = new PotatoDrier("potato_drier", BlockType.STONE);
        SWEET_POTATO_GENERATOR = new SweetPotatoGenerator("sweet_potato_generator", BlockType.METAL);
        SWEET_FREEZER = new SweetFreezer("sweet_freezer", BlockType.METAL);
        SWEET_CRYSTAL_MAKER = new SweetCrystalMaker("sweet_crystal_maker", BlockType.METAL);
        SWEET_CRYSTAL_CHARGER = new SweetCrystalCharger("sweet_crystal_charger", BlockType.METAL);
        ULTIMATE_CRYSTAL_CHARGER = new SweetCrystalCharger("ultimate_crystal_charger", BlockType.METAL).setUltimate();
        SWEET_INFUSER = new SweetInfuser("sweet_infuser", BlockType.METAL);
        MANA_COLLECTOR = new ManaCollector("mana_collector", BlockType.GLASS).setLightLevel(0.5f);
        MANA_EXTRACTOR = new ManaExtractor("mana_extractor", BlockType.GLASS).setLightLevel(0.5f);
        MANA_CAULDRON = new ManaCauldron("mana_cauldron").setLightLevel(0.9f);
        ULTIMATE_CAULDRON = new ManaCauldron("ultimate_mana_cauldron").setUltimate();
    }
}
