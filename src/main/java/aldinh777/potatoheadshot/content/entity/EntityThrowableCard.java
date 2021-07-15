package aldinh777.potatoheadshot.content.entity;

import aldinh777.potatoheadshot.content.items.SwitchingCard;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityThrowableCard extends EntityEnderPearl {

    private ItemStack stack;
    private int remainingTick = 6000;

    public EntityThrowableCard(World worldIn) {
        super(worldIn);
    }

    public EntityThrowableCard(World worldIn, EntityLivingBase throwerIn, ItemStack stack) {
        this(worldIn, throwerIn, stack, false);
    }

    public EntityThrowableCard(World worldIn, EntityLivingBase throwerIn, ItemStack stack, boolean stay) {
        this(worldIn);
        double height = stay ? 0 : (double)throwerIn.getEyeHeight() - 0.10000000149011612D;
        this.setPosition(throwerIn.posX, throwerIn.posY + height, throwerIn.posZ);
        this.thrower = throwerIn;
        this.stack = stack;
    }

    protected void onImpact(RayTraceResult result) {
        EntityLivingBase entitylivingbase = this.getThrower();

        if (result.entityHit != null) {
            if (result.entityHit == this.thrower) {
                return;
            }

            if (entitylivingbase != null) {
                double throwerX = entitylivingbase.posX;
                double throwerY = entitylivingbase.posY;
                double throwerZ = entitylivingbase.posZ;

                if (thrower.isRiding()) {
                    thrower.dismountRidingEntity();
                }

                if (result.entityHit.isRiding()) {
                    result.entityHit.dismountRidingEntity();
                }

                thrower.setPositionAndUpdate(result.entityHit.posX, result.entityHit.posY, result.entityHit.posZ);
                thrower.fallDistance = 0.0F;
                world.playSound(null, thrower.posX, thrower.posY, thrower.posZ, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));

                result.entityHit.setPositionAndUpdate(throwerX, throwerY, throwerZ);
                result.entityHit.fallDistance = 0.0F;
                world.playSound(null, result.entityHit.posX, result.entityHit.posY, result.entityHit.posZ, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));

                thrower.attackEntityFrom(DamageSource.FALL, 0.0F);
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, entitylivingbase), 0.0F);

                this.setDead();
            }
        }

        if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = result.getBlockPos();
            TileEntity tileentity = this.world.getTileEntity(blockpos);

            if (tileentity instanceof TileEntityEndGateway) {
                TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)tileentity;

                if (entitylivingbase != null) {
                    if (entitylivingbase instanceof EntityPlayerMP) {
                        CriteriaTriggers.ENTER_BLOCK.trigger((EntityPlayerMP)entitylivingbase, this.world.getBlockState(blockpos));
                    }

                    tileentityendgateway.teleportEntity(entitylivingbase);
                    this.setDead();
                    return;
                }

                tileentityendgateway.teleportEntity(this);
                return;
            }

            this.setVelocity(0, 0, 0);
            this.setNoGravity(true);
        }

        for (int i = 0; i < 32; ++i) {
            this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0D, this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (remainingTick > 0) {
            remainingTick--;
            if (remainingTick <= 0) {
                setDead();
            }
            if (remainingTick % 20 == 0) {
                if (stack.isEmpty()) {
                    setDead();
                }
            }
        }
    }

    @Override
    public void setDead() {
        super.setDead();
        SwitchingCard.cards.remove(stack);
    }
}
