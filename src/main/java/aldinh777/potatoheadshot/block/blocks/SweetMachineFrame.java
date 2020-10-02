package aldinh777.potatoheadshot.block.blocks;

import aldinh777.potatoheadshot.util.BlockType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;

import javax.annotation.Nonnull;

public class SweetMachineFrame extends PotatoBlock {

    public SweetMachineFrame(String name, BlockType blockType) {
        super(name, blockType);
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
}
