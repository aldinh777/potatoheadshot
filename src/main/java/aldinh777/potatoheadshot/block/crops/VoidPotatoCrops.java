package aldinh777.potatoheadshot.block.crops;

import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;

import javax.annotation.Nonnull;

public class VoidPotatoCrops extends PotatoCrops {

    public VoidPotatoCrops(String name) {
        super(name);
    }

    @Nonnull
    @Override
    protected Item getSeed() {
        return PotatoItems.VOID_POTATO;
    }

    @Nonnull
    @Override
    protected Item getCrop() {
        return PotatoItems.VOID_POTATO;
    }

    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
