package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.content.entity.EntityThrowableCard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class SwitchingCard extends PotatoItem {

    public static Map<ItemStack, EntityThrowableCard> cards = new HashMap<>();

    public SwitchingCard(String name) {
        super(name);
        setMaxStackSize(1);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        EntityThrowableCard card = cards.get(itemstack);

        if (playerIn.isSneaking()) {
            if (card != null && !card.isDead) {
                card.setDead();
            }

        } else {
            EntityThrowableCard throwableCard;

            if (card != null && !card.isDead) {
                throwableCard = new EntityThrowableCard(worldIn, playerIn, itemstack, true);
                throwableCard.setVelocity(0, 0, 0);
                throwableCard.setNoGravity(true);

                if (!worldIn.isRemote) {
                    if (playerIn.isRiding()) {
                        playerIn.dismountRidingEntity();
                    }
                    playerIn.setPositionAndUpdate(card.posX, card.posY, card.posZ);
                    playerIn.fallDistance = 0.0F;
                    playerIn.attackEntityFrom(DamageSource.FALL, 0);
                    card.setDead();
                }

                worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            } else {
                throwableCard = new EntityThrowableCard(worldIn, playerIn, itemstack);
                throwableCard.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 3.0F, 1.0F);
                worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ENDERPEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            }

            if (!worldIn.isRemote) {
                worldIn.spawnEntity(throwableCard);
                cards.put(itemstack, throwableCard);
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
}
