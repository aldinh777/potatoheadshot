package aldinh777.potatoheadshot.block;

import aldinh777.potatoheadshot.block.tileentities.TileEntityManaCauldron;
import aldinh777.potatoheadshot.item.PotatoItemBlock;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ManaCauldron extends Block {

    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 3);
    public static final PropertyEnum<Element> ELEMENT = PropertyEnum.create("element", Element.class);

    protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

    public ManaCauldron(String name) {
        super(Material.ROCK);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHardness(3.0f);
        this.setResistance(6000.0f);
        this.setCreativeTab(PotatoTab.POTATO_TAB);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(LEVEL, 0)
                .withProperty(ELEMENT, Element.MANA));

        PotatoBlocks.LISTS.add(this);
        PotatoItems.LISTS.add(new PotatoItemBlock(this));
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityManaCauldron();
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(PotatoBlocks.MANA_CAULDRON);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(PotatoBlocks.MANA_CAULDRON);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        int elementValue = meta / 4;
        int level = meta - (4 * elementValue);

        return this.getDefaultState()
                .withProperty(LEVEL, level)
                .withProperty(ELEMENT, Element.withValue(elementValue));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int level = state.getValue(LEVEL);
        Element element = state.getValue(ELEMENT);

        int elementValue = element.getValue();

        return (elementValue * 4) + level;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LEVEL, ELEMENT);
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        if (face == EnumFacing.UP) {
            return BlockFaceShape.BOWL;
        } else {
            return face == EnumFacing.DOWN ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
        }
    }

    public enum Element implements IStringSerializable {
        MANA("mana", 0),
        LIFE("life", 1),
        NATURE("nature", 2),
        FIRE("fire", 3);

        private final String name;
        private final int value;

        Element(String name, int value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public int getValue() {
            return this.value;
        }

        public static Element withValue(int value) {
            switch (value) {
                case 1: return LIFE;
                case 2: return NATURE;
                case 3: return FIRE;
                default: return MANA;
            }
        }
    }
}
