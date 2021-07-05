package aldinh777.potatoheadshot.content.blocks.crops;

import aldinh777.potatoheadshot.common.lists.PotatoItems;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StickyPotatoCrops extends PotatoCrops {

    public StickyPotatoCrops(String name) {
        super(name);
        this.setSoundType(SoundType.SLIME);
    }

    @Nonnull
    @Override
    protected Item getSeed() {
        return PotatoItems.STICKY_POTATO;
    }

    @Nonnull
    @Override
    protected Item getCrop() {
        return PotatoItems.STICKY_POTATO;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(@Nonnull IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return getBoundingBox(blockState, worldIn, pos);
    }

    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isStickyBlock(@Nonnull IBlockState state) {
        return true;
    }

    public void onFallenUpon(@Nonnull World worldIn, @Nonnull BlockPos pos, Entity entityIn, float fallDistance) {
        if (entityIn.isSneaking()) {
            super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
        } else {
            entityIn.fall(fallDistance, 0.0F);
        }
    }

    public void onLanded(@Nonnull World worldIn, Entity entityIn) {
        if (entityIn.isSneaking()) {
            super.onLanded(worldIn, entityIn);
        } else if (entityIn.motionY < 0.0D) {
            entityIn.motionY = -entityIn.motionY;

            if (!(entityIn instanceof EntityLivingBase)) {
                entityIn.motionY *= 0.8D;
            }
        }
    }

    public void onEntityWalk(@Nonnull World worldIn, @Nonnull BlockPos pos, Entity entityIn) {
        if (Math.abs(entityIn.motionY) < 0.1D && !entityIn.isSneaking()) {
            double d0 = 0.4D + Math.abs(entityIn.motionY) * 0.2D;
            entityIn.motionX *= d0;
            entityIn.motionZ *= d0;
        }

        super.onEntityWalk(worldIn, pos, entityIn);
    }
}
