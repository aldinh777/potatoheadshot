package aldinh777.potatoheadshot.content.blocks.flower;

import aldinh777.potatoheadshot.common.util.AreaHelper;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class GrowingFlower extends SingleFlower {

    public GrowingFlower(String name) {
        super(name);
        setTickRandomly(true);
        setLightLevel(0.2f);
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        AreaHelper.getStateByRange(worldIn, pos, 5, (targetPos, targetState) -> {
            if (targetState.getBlock() == Blocks.DIRT) {
                IBlockState upBlock = worldIn.getBlockState(targetPos.up());
                if (upBlock.getBlock() == Blocks.AIR) {
                    worldIn.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                }

            } else if (targetState.getBlock() instanceof IGrowable) {
                IGrowable plant = (IGrowable) targetState.getBlock();
                if (plant.canGrow(worldIn, targetPos, targetState, true)) {
                    plant.grow(worldIn, new Random(), targetPos, targetState);
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
