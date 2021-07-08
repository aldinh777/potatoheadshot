package aldinh777.potatoheadshot.common.util;

import net.minecraft.util.math.BlockPos;

import java.util.function.Consumer;

public interface BlockHelper {

    static void getPosByRange(BlockPos pos, int range, Consumer<BlockPos> stateConsumer) {
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    if (!isInEdge(x, y, z, range)) {
                        stateConsumer.accept(pos.add(x, y, z));
                    }
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
