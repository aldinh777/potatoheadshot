package aldinh777.potatoheadshot.content.blocks;

import aldinh777.potatoheadshot.common.lists.PotatoBlocks;
import aldinh777.potatoheadshot.common.util.BlockType;
import aldinh777.potatoheadshot.content.tileentities.TileEntityCorrupted;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CorruptedBlock extends PotatoBlock {

    public static PropertyInteger STAGE = PropertyInteger.create("stage", 0, 5);

    public CorruptedBlock(String name) {
        super(name, BlockType.STONE);
        setBlockUnbreakable();
        setDefaultState(blockState.getBaseState()
                .withProperty(STAGE, 0)
        );
    }

    @Override
    protected void init(String name) {
        PotatoBlocks.LISTS.add(this);
    }

    @Override
    public boolean hasTileEntity(@Nonnull IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityCorrupted();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STAGE, meta);
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        return state.getValue(STAGE);
    }
}
