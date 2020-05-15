package aldinh777.potatoheadshot.lists;

import aldinh777.potatoheadshot.block.*;
import aldinh777.potatoheadshot.util.BlockType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class PotatoBlocks {

    public static List<Block> LISTS = new ArrayList<>();

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
    }.setLightLevel(1.0f);

    public static Block COOKED_DIRT;
    public static Block BAKED_POTATO_PLANKS;
    public static Block BAKED_POTATO_BLOCK;

    public static Block POTATO_PLANKS;
    public static Block POTATO_BLOCK;
    public static Block SWEET_POTATO_BLOCK;
    public static Block SWEET_MACHINE_FRAME;

    public static Block POTATO_DRIER;
    public static Block LIT_POTATO_DRIER;
    public static Block SWEET_POTATO_GENERATOR;

    public static Block DIMENSIONAL_GLASS;

    public static void init() {

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

        // Utility Block
        POTATO_DRIER = new PotatoDrier("potato_drier", BlockType.STONE);
        LIT_POTATO_DRIER = new PotatoDrier("lit_potato_drier", BlockType.STONE, true);
        SWEET_POTATO_GENERATOR = new PotatoGenerator("sweet_potato_generator", BlockType.METAL);

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
