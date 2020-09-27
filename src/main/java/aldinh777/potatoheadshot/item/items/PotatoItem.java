package aldinh777.potatoheadshot.item.items;

import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class PotatoItem extends Item {

    private final int burn_time;

    public PotatoItem(String name) {
        this(name, -1);
    }

    public PotatoItem(String name, int burn_time) {
        this.burn_time = burn_time;
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(PotatoTab.POTATO_TAB);
        PotatoItems.LISTS.add(this);
    }

    @Override
    public int getItemBurnTime(@Nonnull ItemStack itemStack) {
        return this.burn_time;
    }
}
