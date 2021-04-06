package aldinh777.potatoheadshot.item.items;

import aldinh777.potatoheadshot.handler.ConfigHandler;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import aldinh777.potatoheadshot.util.FoodEffects;
import aldinh777.potatoheadshot.util.ItemStickHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PotatoFood extends ItemFood {

    private final List<FoodEffects> effects = new ArrayList<>();

    public PotatoFood(String name, int amount, float saturation) {
        super(amount, saturation, false);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(PotatoTab.POTATO_TAB);

        switch (name) {
            case "sweet_bucket_empty":
                if (ConfigHandler.SWEET_BUCKET) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "salt_potato":
                if (ConfigHandler.SALT_POTATO) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "baked_potato_chip":
                if (ConfigHandler.POTATO_CHIP) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "baked_small_potato_planks":
            case "fried_fries":
                if (ConfigHandler.POTATO_PLANKS && ConfigHandler.COOKED_POTATO_VARIANT) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "hot_potato":
            case "extra_hot_potato":
            case "extreme_hot_potato":
                if (ConfigHandler.HOT_POTATO) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "lava_potato":
                if (ConfigHandler.LAVA_POTATO_SEED) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "wet_potato":
            case "super_wet_potato":
                if (ConfigHandler.WET_POTATO) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "water_potato":
                if (ConfigHandler.WATER_POTATO_SEED) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "frozen_potato":
                if (ConfigHandler.FROZEN_POTATO) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            default:
                PotatoItems.LISTS.add(this);
        }
    }

    @Override
    protected void onFoodEaten(@Nonnull ItemStack stack, World worldIn, @Nonnull EntityPlayer player) {
        if (!worldIn.isRemote) {
            for (FoodEffects effect : this.effects) {
                effect.applyEffects(stack, worldIn, player);
            }
        }
    }

    public void addEffects(FoodEffects... effects) {
        this.effects.addAll(Arrays.asList(effects));
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
