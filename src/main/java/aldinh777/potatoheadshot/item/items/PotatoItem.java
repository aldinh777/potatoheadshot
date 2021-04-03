package aldinh777.potatoheadshot.item.items;

import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import aldinh777.potatoheadshot.util.ItemStickHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
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
        PotatoItems.LISTS.add(this);
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
