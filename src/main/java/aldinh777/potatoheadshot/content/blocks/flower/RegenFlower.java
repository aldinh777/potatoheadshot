package aldinh777.potatoheadshot.content.blocks.flower;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class RegenFlower extends DoubleFlower {

    public RegenFlower(String name) {
        super(name);
        setTickRandomly(true);
        setLightLevel(0.5f);
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
                entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600));
            }
        }
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
