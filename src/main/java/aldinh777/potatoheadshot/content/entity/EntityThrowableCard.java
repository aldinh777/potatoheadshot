package aldinh777.potatoheadshot.content.entity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class EntityThrowableCard extends EntityEnderPearl {

    private int remainingTick = 100;

    public EntityThrowableCard(World worldIn) {
        super(worldIn);
    }

    public EntityThrowableCard(World worldIn, EntityLivingBase throwerIn) {
        this(worldIn);
        this.setPosition(throwerIn.posX, throwerIn.posY + (double)throwerIn.getEyeHeight() - 0.10000000149011612D, throwerIn.posZ);
        this.thrower = throwerIn;
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
                thrower.setPositionAndUpdate(result.entityHit.posX, result.entityHit.posY, result.entityHit.posZ);
                thrower.fallDistance = 0.0F;

                result.entityHit.setPositionAndUpdate(throwerX, throwerY, throwerZ);
                result.entityHit.fallDistance = 0.0F;

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
        }

        for (int i = 0; i < 32; ++i) {
            this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0D, this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
        }

        if (!this.world.isRemote) {
            if (entitylivingbase instanceof EntityPlayerMP) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP)entitylivingbase;

                if (entityplayermp.connection.getNetworkManager().isChannelOpen() && entityplayermp.world == this.world && !entityplayermp.isPlayerSleeping()) {
                    EnderTeleportEvent event = new EnderTeleportEvent(entityplayermp, this.posX, this.posY, this.posZ, 5.0F);
                    if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) {
                        // Don't indent to lower patch size
                        if (entitylivingbase.isRiding()) {
                            entitylivingbase.dismountRidingEntity();
                        }

                        this.setVelocity(0, 0, 0);
                        this.setNoGravity(true);
                    }
                }
            } else if (entitylivingbase != null) {
                this.setVelocity(0, 0, 0);
                this.setNoGravity(true);
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        remainingTick--;
        if (remainingTick <= 0) {
            setDead();
        }
    }
}
