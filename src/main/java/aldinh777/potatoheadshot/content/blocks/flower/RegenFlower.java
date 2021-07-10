package aldinh777.potatoheadshot.content.blocks.flower;

import aldinh777.potatoheadshot.common.util.AreaHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class RegenFlower extends SingleFlower {

    public RegenFlower(String name) {
        super(name);
        setTickRandomly(true);
        setLightLevel(0.5f);
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        AreaHelper.getEntitiesByRange(EntityLivingBase.class, worldIn, pos, 2, (entityLivingBase ->
                entityLivingBase.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600))));

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
