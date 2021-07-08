package aldinh777.potatoheadshot.content.blocks.flower;

import aldinh777.potatoheadshot.common.util.BlockHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class MistFlower extends DoubleFlower {

    public MistFlower(String name) {
        super(name);
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        BlockHelper.getPosByRange(pos, 3, (targetPos) -> {
            IBlockState targetBlock = worldIn.getBlockState(targetPos);
            if (targetBlock.getBlock() == Blocks.WATER) {
                worldIn.setBlockState(targetPos, Blocks.FROSTED_ICE.getDefaultState());
            }
        });
        super.updateTick(worldIn, pos, state, rand);
    }

    @Override
    public boolean canPlaceBlockAt(@Nonnull World worldIn, @Nonnull BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos.down());
        return super.canPlaceBlockAt(worldIn, pos) && this.canSustainBush(state);
    }

    @Override
    protected boolean canSustainBush(@Nonnull IBlockState state) {
        return state.getBlock() == Blocks.GRASS
                || state.getBlock() == Blocks.DIRT
                || state.getBlock() == Blocks.SAND;
    }
}
