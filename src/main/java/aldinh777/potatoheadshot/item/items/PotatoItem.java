package aldinh777.potatoheadshot.item.items;

import aldinh777.potatoheadshot.handler.ConfigHandler;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import aldinh777.potatoheadshot.util.ItemStickHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

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
            case "charged_crystal_shard":
            case "concentrated_crystal_shard":
            case "charged_crystal":
            case "concentrated_crystal":
            case "ultimate_crystal":
            case "ultimate_charged_crystal":
            case "ultimate_concentrated_crystal":
                if (ConfigHandler.ULTIMATE_CRYSTALS) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "sweet_potato_bucket":
                if (ConfigHandler.SWEET_BUCKET) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "ultimate_broken_fuel":
                if (ConfigHandler.ULTIMATE_BROKEN_FUEL) {
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
            case "ice_potato":
                if (ConfigHandler.FROZEN_POTATO) {
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

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);
        if (!worldIn.isRemote) {
            ItemStickHelper.debugBlock(raytraceresult, worldIn, playerIn, handIn);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
