package aldinh777.potatoheadshot.common.lists;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class PotatoTab {

    public static CreativeTabs POTATO_TAB = new CreativeTabs("potatoheadshot") {
        @Nonnull
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(PotatoBlocks.POTATO_BLOCK);
        }
    };

}
