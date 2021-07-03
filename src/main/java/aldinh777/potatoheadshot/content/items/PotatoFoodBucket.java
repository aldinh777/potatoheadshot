package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.other.capability.FoodBucketCapability;
import aldinh777.potatoheadshot.other.lists.PotatoItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class PotatoFoodBucket extends PotatoFood {

    private final Block containedBlock;
    private SoundEvent soundEvent = SoundEvents.ITEM_BUCKET_EMPTY;
    private boolean explodeOnPlaced = false;
    private int burnTime = 0;

    public PotatoFoodBucket(String name, Block block, int hunger, float saturation) {
        super(name, hunger, saturation);
        this.containedBlock = block;
        this.setMaxStackSize(64);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        if (stack.getItem() == PotatoItems.WATER_POTATO || stack.getItem() == PotatoItems.LAVA_POTATO) {
            return new FoodBucketCapability(stack);
        }
        return null;
    }

    @Override
    public int getItemBurnTime(@Nonnull ItemStack itemStack) {
        return this.burnTime;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        // Bucket Operation
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
        ActionResult<ItemStack> ret = ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);

        if (ret != null) return ret;

        if (raytraceresult == null) {

            // Food Operation
            if (playerIn.canEat(false)) {
                playerIn.setActiveHand(handIn);
                return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
            }

            return new ActionResult<>(EnumActionResult.FAIL, itemstack);

        } else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return new ActionResult<>(EnumActionResult.PASS, itemstack);

        } else {
            BlockPos blockpos = raytraceresult.getBlockPos();

            if (!worldIn.isBlockModifiable(playerIn, blockpos)) {
                return new ActionResult<>(EnumActionResult.FAIL, itemstack);

            } else {
                boolean flag = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
                BlockPos placing_position = flag && raytraceresult.sideHit == EnumFacing.UP ? blockpos : blockpos.offset(raytraceresult.sideHit);

                if (!playerIn.canPlayerEdit(placing_position, raytraceresult.sideHit, itemstack)) {
                    return new ActionResult<>(EnumActionResult.FAIL, itemstack);

                } else if (this.tryPlaceContainedLiquid(playerIn, worldIn, placing_position)) {
                    if (!playerIn.capabilities.isCreativeMode) {
                        itemstack.setCount(itemstack.getCount() - 1);
                    }

                    return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);

                } else {
                    return new ActionResult<>(EnumActionResult.FAIL, itemstack);
                }
            }
        }
    }

    public PotatoFoodBucket setSoundEvent(SoundEvent soundEvent) {
        this.soundEvent = soundEvent;
        return this;
    }

    public void setExplodeOnPlaced() {
        this.explodeOnPlaced = true;
    }

    public PotatoFoodBucket setBurnTime(int burnTime) {
        this.burnTime = burnTime;
        return this;
    }

    private void explode(World worldIn, EntityPlayer player, BlockPos posIn) {
        worldIn.createExplosion(player, posIn.getX(), posIn.getY(), posIn.getZ(), 4.0f, true);
        Objects.requireNonNull(player).attackEntityFrom(new DamageSource("potato_explosion"), 8);
    }

    private boolean tryPlaceContainedLiquid(@Nullable EntityPlayer player, World worldIn, BlockPos posIn) {
        if (this.containedBlock == Blocks.AIR && !explodeOnPlaced) {
            return false;
        }

        IBlockState iblockstate = worldIn.getBlockState(posIn);
        Material material = iblockstate.getMaterial();
        boolean flag = !material.isSolid();
        boolean flag1 = iblockstate.getBlock().isReplaceable(worldIn, posIn);

        if (!worldIn.isAirBlock(posIn) && !flag && !flag1) {
            return false;
        } else {
            if (worldIn.provider.doesWaterVaporize() && this.containedBlock == Blocks.FLOWING_WATER) {
                int l = posIn.getX();
                int i = posIn.getY();
                int j = posIn.getZ();
                worldIn.playSound(player, posIn, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

                for (int k = 0; k < 8; ++k) {
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)l + Math.random(), (double)i + Math.random(), (double)j + Math.random(), 0.0D, 0.0D, 0.0D);
                }
            } else {
                if (!worldIn.isRemote && (flag || flag1) && !material.isLiquid()) {
                    worldIn.destroyBlock(posIn, true);
                }

                worldIn.playSound(player, posIn, this.soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlockState(posIn, this.containedBlock.getDefaultState(), 11);

                if (!worldIn.isRemote && explodeOnPlaced) {
                    explode(worldIn, player, posIn);
                }
            }

            return true;
        }
    }
}
