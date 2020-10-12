package aldinh777.potatoheadshot.item.items;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class PotatoFoodItemBlock extends PotatoItemBlock {

    private final ItemFood food;

    public PotatoFoodItemBlock(Block block, int amount, float saturation) {
        super(block);
        this.food = new ItemFood(amount, saturation, false);
    }

    @Override
    public int getItemBurnTime(@Nonnull ItemStack itemStack) {
        return 0;
    }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entityLiving;
            entityplayer.getFoodStats().addStats(this.food, stack);
            worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
        }

        stack.shrink(1);
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack)
    {
        return 32;
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack stack)
    {
        return EnumAction.EAT;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.canEat(false)) {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        } else {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
    }
}
