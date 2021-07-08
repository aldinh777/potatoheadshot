package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.common.lists.PotatoTab;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeedFood;

public class PotatoCrop extends ItemSeedFood {

    public PotatoCrop(String name, int healAmount, float saturation, Block crops) {
        super(healAmount, saturation, crops, Blocks.FARMLAND);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(PotatoTab.POTATO_TAB);

        PotatoItems.LISTS.add(this);
    }
}
