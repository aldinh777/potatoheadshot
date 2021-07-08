package aldinh777.potatoheadshot.content.blocks.flower;

import aldinh777.potatoheadshot.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class FlamingFlower extends DoubleFlower {

    public FlamingFlower(String name) {
        super(name);
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        BlockHelper.getPosByRange(pos, 2, (targetPos) -> {
            IBlockState targetState = worldIn.getBlockState(targetPos);
            IBlockState targetUnder = worldIn.getBlockState(targetPos.down());
            if (targetState.getBlock() == Blocks.AIR) {
                Block blockUnder = targetUnder.getBlock();
                if (blockUnder != Blocks.AIR) {
                    worldIn.setBlockState(targetPos, Blocks.FIRE.getDefaultState());
                }
            }
        });
        super.updateTick(worldIn, pos, state, rand);
    }
}
