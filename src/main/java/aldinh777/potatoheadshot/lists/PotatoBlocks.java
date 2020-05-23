package aldinh777.potatoheadshot.lists;

import aldinh777.potatoheadshot.block.*;
import aldinh777.potatoheadshot.util.BlockType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PotatoBlocks {

    public static List<Block> LISTS = new ArrayList<>();

    public static Block LAVA_FARMLAND = new FarmlandLava("lava_farmland");

    public static Block SWEET_POTATOES = new PotatoCrops("sweet_potatoes");
    public static Block GLOWING_POTATOES = new PotatoCrops("glowing_potatoes") {
        @Override
        protected Item getCrop() {
            return PotatoItems.GLOWING_POTATO;
        }

        @Override
        protected Item getSeed() {
            return PotatoItems.GLOWING_POTATO;
        }
    }.setLightLevel(0.8f);
    public static Block LAVA_POTATOES = new PotatoCrops("lava_potatoes") {
        @Override
        protected Item getCrop() {
            return PotatoItems.LAVA_POTATO;
        }

        @Override
        protected Item getSeed() {
            return PotatoItems.LAVA_POTATO_SEED;
        }

        @Override
        protected boolean canSustainBush(IBlockState state) {
            return state.getBlock() == PotatoBlocks.LAVA_FARMLAND;
        }

        @Override
        public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
            return state.getBlock() == PotatoBlocks.LAVA_FARMLAND;
        }

        @Override
        public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
            return worldIn.getBlockState(pos.down()).getBlock() == PotatoBlocks.LAVA_FARMLAND;
        }

        @Override
        public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
            return worldIn.getBlockState(pos.down()).getBlock() == PotatoBlocks.LAVA_FARMLAND;
        }
    }.setLightLevel(1.0f);

    public static Block COOKED_DIRT;
    public static Block BAKED_POTATO_PLANKS;
    public static Block BAKED_POTATO_BLOCK;

    public static Block POTATO_PLANKS;
    public static Block POTATO_BLOCK;
    public static Block SWEET_POTATO_BLOCK;
    public static Block SWEET_MACHINE_FRAME;
    public static Block GLOWING_POTATO_BLOCK;
    public static Block GLOWING_MANA_BLOCK;
    public static Block GLOWING_MANA_FLOWER;
    public static Block GLOWING_MANA_TORCH;
    public static Block GLOWING_MANA_STONE;

    public static Block POTATO_DRIER;
    public static Block LIT_POTATO_DRIER;
    public static Block SWEET_POTATO_GENERATOR;
    public static Block SWEET_FREEZER;
    public static Block SWEET_INFUSER;
    public static Block MANA_COLLECTOR;
    public static Block MANA_EXTRACTOR;
    public static Block MANA_CAULDRON;

    public static Block DIMENSIONAL_GLASS;

    public static void init() {

        // Place able Item
        GLOWING_MANA_FLOWER = new ManaFlower("glowing_mana_flower");
        GLOWING_MANA_TORCH = new Potatorch("glowing_mana_torch");

        // Food Block
        COOKED_DIRT = new PotatoFoodBlock("cooked_dirt", BlockType.GROUND, 3, 0.2f);
        BAKED_POTATO_PLANKS = new PotatoFoodBlock("baked_potato_planks", BlockType.WOOD, 5, 0.5f);
        BAKED_POTATO_BLOCK = new PotatoFoodBlock("baked_potato_block", BlockType.POTATO, 10, 0.8f);

        // Normal Block
        POTATO_PLANKS = new PotatoBlock("potato_planks", BlockType.WOOD);
        POTATO_BLOCK = new PotatoBlock("potato_block", BlockType.POTATO);
        SWEET_POTATO_BLOCK = new PotatoBlock("sweet_potato_block", BlockType.METAL);
        SWEET_MACHINE_FRAME = new PotatoBlock("sweet_machine_frame", BlockType.METAL) {
            @Override
            public BlockRenderLayer getBlockLayer() {
                return BlockRenderLayer.CUTOUT;
            }
            public boolean isOpaqueCube(IBlockState state)
            {
                return false;
            }
            public boolean isFullCube(IBlockState state)
            {
                return false;
            }

        };
        GLOWING_POTATO_BLOCK = new PotatoBlock("glowing_potato_block", BlockType.GLASS) {
            @Override
            public Item getItemDropped(IBlockState state, Random rand, int fortune) {
                return PotatoItems.GLOWING_POTATO_DUST;
            }
        }.setLightLevel(1.0f);
        GLOWING_MANA_BLOCK = new PotatoBlock("glowing_mana_block", BlockType.GLASS) {
            @Override
            public Item getItemDropped(IBlockState state, Random rand, int fortune) {
                return PotatoItems.GLOWING_MANA_DUST;
            }
        }.setLightLevel(1.0f);
        GLOWING_MANA_STONE = new PotatoBlock("glowing_mana_stone", BlockType.STONE)
                .setLightLevel(0.7f).setResistance(6000);

        // Utility Block
        POTATO_DRIER = new PotatoDrier("potato_drier", BlockType.STONE);
        LIT_POTATO_DRIER = new PotatoDrier("lit_potato_drier", BlockType.STONE, true);
        SWEET_POTATO_GENERATOR = new SweetPotatoGenerator("sweet_potato_generator", BlockType.METAL);
        SWEET_FREEZER = new SweetFreezer("sweet_freezer", BlockType.METAL);
        SWEET_INFUSER = new SweetInfuser("sweet_infuser", BlockType.METAL);
        MANA_COLLECTOR = new ManaCollector("mana_collector", BlockType.GLASS).setLightLevel(0.5f);
        MANA_EXTRACTOR = new ManaExtractor("mana_extractor", BlockType.GLASS).setLightLevel(0.5f);
        MANA_CAULDRON = new ManaCauldron("mana_cauldron").setLightLevel(0.9f);

        // Bugged Feature
        DIMENSIONAL_GLASS = new PotatoBlock("dimensional_glass", BlockType.GLASS) {
            @SideOnly(Side.CLIENT)
            public BlockRenderLayer getBlockLayer()
            {
                return BlockRenderLayer.TRANSLUCENT;
            }
        }.setLightOpacity(0);
    }
}
