package aldinh777.potatoheadshot.common.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.*;

public interface AreaHelper {

    static <T extends Entity> void getEntitiesByRange(Class<T> clazz, World world, BlockPos pos, int range, Consumer<T> entityConsumer) {
        BlockPos pos1 = pos.add(-range, -range, -range);
        BlockPos pos2 = pos.add(range, range, range);

        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(pos1, pos2);
        getEntitiesByRange(clazz, world, axisAlignedBB, entityConsumer);
    }

    static <T extends Entity> void getEntitiesByRange(Class<T> clazz, World world, AxisAlignedBB axisAlignedBB, Consumer<T> entityConsumer) {
        List<T> list = world.getEntitiesWithinAABB(clazz, axisAlignedBB);

        if (!list.isEmpty()) {
            for (T entity : list) {
                entityConsumer.accept(entity);
            }
        }
    }

    static void getStateByRange(World world, BlockPos pos, int range, BiConsumer<BlockPos, IBlockState> stateBiConsumer) {
        getStateByRange(world, pos, range, stateBiConsumer, () -> false);
    }

    static void getStateByRange(World world, BlockPos pos, int range, BiConsumer<BlockPos, IBlockState> stateBiConsumer, BooleanSupplier stopCondition) {
        getPosByRange(pos, range, (blockPos -> {
            IBlockState state = world.getBlockState(blockPos);
            stateBiConsumer.accept(blockPos, state);
        }), stopCondition, (blockPos) -> isInEdge(blockPos.getX(), blockPos.getY(), blockPos.getZ(), range));
    }

    static void getSurroundingState(World world, BlockPos pos, BiConsumer<BlockPos, IBlockState> stateBiConsumer) {
        getPosByRange(pos, 1, (blockPos -> {
            IBlockState state = world.getBlockState(blockPos);
            stateBiConsumer.accept(blockPos, state);
        }), () -> false, (blockPos) -> false);
    }

    static void getPosByRange(BlockPos pos, int range, Consumer<BlockPos> stateConsumer, BooleanSupplier stopCondition, Function<BlockPos, Boolean> skipCondition) {
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    if (stopCondition.getAsBoolean()) {
                        return;
                    }
                    if (skipCondition.apply(pos.add(x, y, z))) {
                        continue;
                    }
                    stateConsumer.accept(pos.add(x, y, z));
                }
            }
        }
    }

    static boolean isInEdge(int x, int y, int z, int range) {
        int intersection = 0;

        if (isInEdge(x, range)) {
            intersection++;
        }
        if (isInEdge(y, range)) {
            intersection++;
        }
        if (isInEdge(z, range)) {
            intersection++;
        }

        return intersection >= 2;
    }

    static boolean isInEdge(int num, int range) {
        return num == range || num == -range;
    }
}
