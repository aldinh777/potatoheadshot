package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.common.lists.PotatoTab;
import net.minecraft.item.ItemSword;

public class PotatoKnife extends ItemSword {

    public PotatoKnife(String name) {
        super(ToolMaterial.WOOD);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(PotatoTab.POTATO_TAB);
        this.setContainerItem(this);
        PotatoItems.LISTS.add(this);
    }
}
