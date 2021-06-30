package aldinh777.potatoheadshot.content.blocks.crops;

import aldinh777.potatoheadshot.other.lists.PotatoItems;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public class SweetPotatoCrops extends PotatoCrops {

    public SweetPotatoCrops(String name) {
        super(name);
    }

    @Nonnull
    @Override
    protected Item getSeed() {
        return PotatoItems.SWEET_POTATO;
    }

    @Nonnull
    @Override
    protected Item getCrop() {
        return PotatoItems.SWEET_POTATO;
    }
}
