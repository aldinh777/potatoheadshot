package aldinh777.potatoheadshot.content.blocks.crops;

import aldinh777.potatoheadshot.common.lists.PotatoItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class GlowingPotatoCrops extends PotatoCrops {

    public GlowingPotatoCrops(String name) {
        super(name);
    }

    @Nonnull
    @Override
    protected Item getCrop() {
        return PotatoItems.GLOWING_POTATO;
    }

    @Nonnull
    @Override
    protected Item getSeed() {
        return PotatoItems.GLOWING_POTATO;
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        this.checkAndDropBlock(worldIn, pos, state);

        if (!worldIn.isAreaLoaded(pos, 1)) return;
        if (worldIn.getLightFromNeighbors(pos.up()) >= 0) {
            int i = this.getAge(state);

            if (i < this.getMaxAge()) {
                float f = getGrowthChance(this, worldIn, pos);

                if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0)) {
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            }
        }
    }

    @Override
    public int getLightValue(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        int stage = state.getValue(AGE);

        switch (stage) {
            case 0:
            case 1:
                return 9;
            case 2:
            case 3:
                return 11;
            case 4:
            case 5:
            case 6:
                return 13;
            case 7:
                return 15;
            default:
                return 0;
        }
    }
}
