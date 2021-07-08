package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.common.util.Element;
import aldinh777.potatoheadshot.content.entity.EntityManaPotion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Objects;

public class SplashManaPotion extends PotatoItem {

    private final Element element;

    public SplashManaPotion(String name, Element element) {
        super(name);
        this.element = element;
        this.setMaxStackSize(1);
    }

    @Override
    public boolean hasEffect(@Nonnull ItemStack stack) {
        return true;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote) {
            EntityManaPotion entityPotion = new EntityManaPotion(worldIn, playerIn, this.element);
            entityPotion.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 0.5F, 1.0F);
            worldIn.spawnEntity(entityPotion);
        }

        playerIn.addStat(Objects.requireNonNull(StatList.getObjectUseStats(this)));
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
}
