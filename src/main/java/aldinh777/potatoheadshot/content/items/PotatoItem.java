package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.common.lists.PotatoTab;
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

        switch (name) {
            case "sweet_potato_bucket":
                if (ConfigHandler.SWEET_BUCKET) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "splash_mana_fire":
            case "splash_mana_life":
                if (ConfigHandler.SPLASH_MANA) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "potato_chip":
                if (ConfigHandler.POTATO_CHIP) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "small_potato_planks":
            case "potato_stick":
                if (ConfigHandler.POTATO_PLANKS) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            default:
                PotatoItems.LISTS.add(this);
        }
    }

    @Override
    public int getItemBurnTime(@Nonnull ItemStack itemStack) {
        return this.burn_time;
    }
}
