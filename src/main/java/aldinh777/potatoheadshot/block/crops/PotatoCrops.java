package aldinh777.potatoheadshot.block.crops;

import aldinh777.potatoheadshot.lists.PotatoBlocks;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

public abstract class PotatoCrops extends BlockCrops {

    private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.4375D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D)
    };

    public PotatoCrops(String name) {
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        PotatoBlocks.LISTS.add(this);
    }

    @Nonnull
    @Override
    protected abstract Item getSeed();

    @Nonnull
    @Override
    protected abstract Item getCrop();

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return CROPS_AABB[state.getValue(this.getAgeProperty())];
    }
}
