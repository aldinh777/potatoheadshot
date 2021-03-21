package aldinh777.potatoheadshot.block.blocks.crops;

import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;

import javax.annotation.Nonnull;

public class WaterPotatoCrops extends PotatoCrops {

    public WaterPotatoCrops(String name) {
        super(name);
    }

    @Nonnull
    @Override
    protected Item getSeed() {
        return PotatoItems.WATER_POTATO_SEED;
    }

    @Nonnull
    @Override
    protected Item getCrop() {
        return PotatoItems.WATER_POTATO;
    }

    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
