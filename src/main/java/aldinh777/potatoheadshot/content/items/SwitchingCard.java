package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.content.entity.EntityThrowableCard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class SwitchingCard extends PotatoItem {

    public EntityThrowableCard card;

    public SwitchingCard(String name) {
        super(name);
        setMaxStackSize(1);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.isSneaking()) {
            card = null;
        } else {
            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ENDERPEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            EntityThrowableCard throwableCard = new EntityThrowableCard(worldIn, playerIn);

            if (card != null && !card.isDead) {
                throwableCard.setVelocity(0, 0, 0);
                throwableCard.setNoGravity(true);

                if (!worldIn.isRemote) {
                    playerIn.setPositionAndUpdate(card.posX, card.posY, card.posZ);
                    playerIn.fallDistance = 0.0F;
                    playerIn.attackEntityFrom(DamageSource.FALL, 0);
                    card.setDead();
                }

                playerIn.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);

            } else {
                throwableCard.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            }

            if (!worldIn.isRemote) {
                worldIn.spawnEntity(throwableCard);
                card = throwableCard;
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
}
