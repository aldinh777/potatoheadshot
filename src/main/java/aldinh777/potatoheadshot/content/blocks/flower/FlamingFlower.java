package aldinh777.potatoheadshot.content.blocks.flower;

import aldinh777.potatoheadshot.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class FlamingFlower extends DoubleFlower {

    public FlamingFlower(String name) {
        super(name);
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        float x = pos.getX();
        float y = pos.getY();
        float z = pos.getZ();

        AxisAlignedBB axisalignedbb = new AxisAlignedBB(x-2, y-2, z-2, x+2, y+2, z+2);
        List<EntityLivingBase> list = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

        if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
                entitylivingbase.setFire(8);
            }
        }

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
