package aldinh777.potatoheadshot.other.lists;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class PotatoTab {

    public static CreativeTabs POTATO_TAB = new CreativeTabs("potatoheadshot") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(PotatoBlocks.POTATO_BLOCK);
        }
    };

}
