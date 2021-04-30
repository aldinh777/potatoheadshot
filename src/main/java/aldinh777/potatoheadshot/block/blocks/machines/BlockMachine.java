package aldinh777.potatoheadshot.block.blocks.machines;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.block.blocks.PotatoBlock;
import aldinh777.potatoheadshot.util.BlockType;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class BlockMachine extends PotatoBlock {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockMachine(String name, BlockType blockType) {
        super(name, blockType);
        setDefaultState(blockState.getBaseState()
                .withProperty(FACING, EnumFacing.NORTH));
    }

    public int getGuiId() {
        return -1;
    }

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

    @Override
    public boolean hasTileEntity(@Nonnull IBlockState state) {
        return true;
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (playerIn.isSneaking()) {
            if (this instanceof IBlockUpgradable) {
                int upgradeGuiId = ((IBlockUpgradable) this).getUpgradeGuiId();
                if (!worldIn.isRemote) {
                    playerIn.openGui(PotatoHeadshot.INSTANCE, upgradeGuiId, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
                return true;
            }
        } else {
            int guiId = getGuiId();
            if (guiId != -1) {
                if (!worldIn.isRemote) {
                    playerIn.openGui(PotatoHeadshot.INSTANCE, guiId, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBlockAdded(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState north = worldIn.getBlockState(pos.north());
            IBlockState south = worldIn.getBlockState(pos.south());
            IBlockState west = worldIn.getBlockState(pos.west());
            IBlockState east = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing));
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, EntityLivingBase placer, @Nonnull ItemStack stack) {
        worldIn.setBlockState(pos, getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = getDefaultState();
        EnumFacing propertyFacing = EnumFacing.NORTH;

        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            if (facing.getIndex() == meta) {
                propertyFacing = facing;
                break;
            }
        }

        return state.withProperty(FACING, propertyFacing);
    }
}
