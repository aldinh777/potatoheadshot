package aldinh777.potatoheadshot.block.blocks.crops;

import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RedPotatoCrops extends PotatoCrops {

    public RedPotatoCrops(String name) {
        super(name);
        this.setSoundType(SoundType.STONE);
    }

    @Nonnull
    @Override
    protected Item getSeed() {
        return PotatoItems.RED_POTATO;
    }

    @Nonnull
    @Override
    protected Item getCrop() {
        return PotatoItems.RED_POTATO;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(@Nonnull IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return super.getBoundingBox(blockState, worldIn, pos);
    }

    @Override
    public boolean canProvidePower(@Nonnull IBlockState state) {
        return true;
    }

    @Override
    public int getWeakPower(@Nonnull IBlockState blockState, @Nonnull IBlockAccess blockAccess, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
        return 8;
    }
}
