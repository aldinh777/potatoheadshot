package aldinh777.potatoheadshot.common.util;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public interface FoodEffects {

    FoodEffects curePlayer = (stack, worldIn, player) ->
            player.curePotionEffects(stack);
    FoodEffects burnPlayer = (stack, worldIn, player) ->
            player.setFire(8);
    FoodEffects unBurnPlayer =(stack, worldIn, player) -> {
        if (player.isBurning()) {
            player.extinguish();
        }
    };
    FoodEffects poisonPlayer = (stack, worldIn, player) ->
            player.addPotionEffect(new PotionEffect(MobEffects.POISON, 600, 2));
    FoodEffects confusePlayer = (stack, worldIn, player) ->
            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 300, 2));
    FoodEffects weakenPlayer = (stack, worldIn, player) ->
            player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 150, 1));
    FoodEffects slowPlayer = (stack, worldIn, player) ->
            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 150, 1));
    FoodEffects blindPlayer = (stack, worldIn, player) ->
            player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 150, 1));
    FoodEffects visionPlayer = (stack, worldIn, player) ->
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 600, 1));
    FoodEffects tirePlayer = (stack, worldIn, player) ->
            player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 600, 1));
    FoodEffects fireResistPlayer = (stack, worldIn, player) ->
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 900));
    FoodEffects explodePlayer = (stack, worldIn, player) -> {
        if (!worldIn.isRemote) {
            worldIn.createExplosion(player, player.posX, player.posY, player.posZ, 4.0f, true);
            player.attackEntityFrom(new DamageSource("potato_explosion"), 8);
        }
    };
    FoodEffects struckPlayer = (stack, worldIn, player) -> {
        if (!worldIn.isRemote) {
            EntityLightningBolt lightning = new EntityLightningBolt(worldIn, player.posX, player.posY, player.posZ, false);
            worldIn.spawnEntity(lightning);
        }
    };

    static FoodEffects combine(FoodEffects... effects) {
        return (stack, worldIn, player) -> {
            for (FoodEffects effect : effects) {
                effect.applyEffects(stack, worldIn, player);
            }
        };
    }
    void applyEffects(ItemStack stack, World worldIn, EntityPlayer player);
}
