package aldinh777.potatoheadshot.block.crops;

import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;

import javax.annotation.Nonnull;

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

    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
