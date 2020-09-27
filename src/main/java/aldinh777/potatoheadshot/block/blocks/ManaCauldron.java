package aldinh777.potatoheadshot.block.blocks;

import aldinh777.potatoheadshot.block.tileentities.TileEntityManaCauldron;
import aldinh777.potatoheadshot.item.items.PotatoItemBlock;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ManaCauldron extends Block {

    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 2);
    public static final PropertyEnum<Element> ELEMENT = PropertyEnum.create("element", Element.class);

    protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

    private boolean ultimate = false;

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
    public boolean hasTileEntity(@Nonnull IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        if (this.ultimate) {
            return new TileEntityManaCauldron().setUltimate();
        } else {
            return new TileEntityManaCauldron();
        }
    }

    @Override
    public void addCollisionBoxToList(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB entityBox, @Nonnull List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state) {
        return false;
    }

    public boolean isFullCube(@Nonnull IBlockState state) {
        return false;
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        if (this.ultimate) {
            return Item.getItemFromBlock(PotatoBlocks.ULTIMATE_CAULDRON);
        } else {
            return Item.getItemFromBlock(PotatoBlocks.MANA_CAULDRON);
        }
    }

    @Nonnull
    @Override
    public ItemStack getItem(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (this.ultimate) {
            return new ItemStack(PotatoBlocks.ULTIMATE_CAULDRON);
        } else {
            return new ItemStack(PotatoBlocks.MANA_CAULDRON);
        }
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        int elementValue = meta / 3;
        int level = meta % 3;

        return this.getDefaultState()
                .withProperty(ELEMENT, Element.withValue(elementValue))
                .withProperty(LEVEL, level);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        Element element = state.getValue(ELEMENT);
        int level = state.getValue(LEVEL);
        int elementValue = element.getValue();

        return (elementValue * 3) + level;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LEVEL, ELEMENT);
    }

    @Override
    public boolean isPassable(@Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return true;
    }

    @Nonnull
    @Override
    public BlockFaceShape getBlockFaceShape(@Nonnull IBlockAccess worldIn, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EnumFacing face) {
        if (face == EnumFacing.UP) {
            return BlockFaceShape.BOWL;
        } else {
            return face == EnumFacing.DOWN ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
        }
    }

    public ManaCauldron setUltimate() {
        this.ultimate = true;
        this.setHardness(50.0f);
        return this;
    }

    public enum Element implements IStringSerializable {
        MANA("mana", 0),
        LIFE("life", 1),
        NATURE("nature", 2),
        FIRE("fire", 3),
        VOID("void", 4);

        private final String name;
        private final int value;

        Element(String name, int value) {
            this.name = name;
            this.value = value;
        }

        @Nonnull
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
                case 4: return VOID;
                default: return MANA;
            }
        }
    }
}
