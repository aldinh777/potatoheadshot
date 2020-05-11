package aldinh777.potatoheadshot.lists;

import aldinh777.potatoheadshot.block.PotatoBlock;
import aldinh777.potatoheadshot.block.PotatoCrops;
import aldinh777.potatoheadshot.block.PotatoDrier;
import aldinh777.potatoheadshot.block.PotatoFoodBlock;
import aldinh777.potatoheadshot.util.BlockType;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class PotatoBlocks {

    public static List<Block> LISTS = new ArrayList<>();

    public static Block SWEET_POTATOES = new PotatoCrops("sweet_potatoes");

    public static Block COOKED_DIRT;
    public static Block BAKED_POTATO_PLANKS;
    public static Block BAKED_POTATO_BLOCK;

    public static Block POTATO_PLANKS;
    public static Block POTATO_BLOCK;
    public static Block SWEET_POTATO_BLOCK;

    public static Block POTATO_DRIER;
    public static Block LIT_POTATO_DRIER;

    public static void init() {

        // Food Block
        COOKED_DIRT = new PotatoFoodBlock("cooked_dirt", BlockType.GROUND, 3, 0.2f);
        BAKED_POTATO_PLANKS = new PotatoFoodBlock("baked_potato_planks", BlockType.WOOD, 5, 0.5f);
        BAKED_POTATO_BLOCK = new PotatoFoodBlock("baked_potato_block", BlockType.POTATO, 10, 0.8f);

        // Normal Block
        POTATO_PLANKS = new PotatoBlock("potato_planks", BlockType.WOOD);
        POTATO_BLOCK = new PotatoBlock("potato_block", BlockType.POTATO);
        SWEET_POTATO_BLOCK = new PotatoBlock("sweet_potato_block", BlockType.METAL);

        // Utility Block
        POTATO_DRIER = new PotatoDrier("potato_drier", BlockType.STONE);
        LIT_POTATO_DRIER = new PotatoDrier("lit_potato_drier", BlockType.STONE, true);
    }
}
