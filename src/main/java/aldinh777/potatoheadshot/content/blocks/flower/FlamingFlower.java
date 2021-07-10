package aldinh777.potatoheadshot.content.blocks.flower;

import aldinh777.potatoheadshot.common.util.AreaHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class FlamingFlower extends DoubleFlower {

    public FlamingFlower(String name) {
        super(name);
        setTickRandomly(true);
        setLightLevel(1.0f);
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        AxisAlignedBB axisAlignedBB = getBoundingBox(state, worldIn, pos).grow(2.0D, 2.0D, 2.0d);
        AreaHelper.getEntitiesByRange(EntityLivingBase.class, worldIn, axisAlignedBB, (entityLivingBase -> {
            entityLivingBase.setFire(8);
        }));

        AreaHelper.getStateByRange(worldIn, pos, 2, (targetPos, targetState) -> {
            if (targetState.getBlock() == Blocks.AIR) {
                IBlockState targetUnder = worldIn.getBlockState(targetPos.down());
                Block blockUnder = targetUnder.getBlock();

                if (blockUnder != Blocks.AIR) {
                    worldIn.setBlockState(targetPos, Blocks.FIRE.getDefaultState());
                }
            }
        });

        super.updateTick(worldIn, pos, state, rand);
    }
}
