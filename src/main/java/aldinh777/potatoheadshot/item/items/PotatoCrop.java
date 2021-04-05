package aldinh777.potatoheadshot.item.items;

import aldinh777.potatoheadshot.handler.ConfigHandler;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeedFood;

public class PotatoCrop extends ItemSeedFood {

    public PotatoCrop(String name, int healAmount, float saturation, Block crops) {
        super(healAmount, saturation, crops, Blocks.FARMLAND);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(PotatoTab.POTATO_TAB);

        switch (name) {
            case "red_potato":
                if (ConfigHandler.RED_POTATO) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "sticky_potato":
                if (ConfigHandler.STICKY_POTATO) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            default:
                PotatoItems.LISTS.add(this);
                break;
        }
    }
}
