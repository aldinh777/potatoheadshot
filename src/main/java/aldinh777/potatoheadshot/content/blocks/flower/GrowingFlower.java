package aldinh777.potatoheadshot.content.blocks.flower;

import aldinh777.potatoheadshot.common.util.AreaHelper;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class GrowingFlower extends DoubleFlower {

    public GrowingFlower(String name) {
        super(name);
        setTickRandomly(true);
        setLightLevel(0.2f);
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        AreaHelper.getStateByRange(worldIn, pos, 5, (targetPos, targetState) -> {
            if (state.getBlock() == Blocks.DIRT) {
                IBlockState upBlock = worldIn.getBlockState(pos.up());
                if (upBlock.getBlock() == Blocks.AIR) {
                    worldIn.setBlockState(pos, Blocks.GRASS.getDefaultState());
                }

            } else if (state.getBlock() instanceof IGrowable) {
                IGrowable plant = (IGrowable) state.getBlock();
                if (plant.canGrow(worldIn, pos, state, true)) {
                    plant.grow(worldIn, new Random(), pos, state);
                }
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
                || state.getBlock() == Blocks.SAND
                || state.getBlock() == Blocks.LOG
                || state.getBlock() == Blocks.LOG2;
    }
}
