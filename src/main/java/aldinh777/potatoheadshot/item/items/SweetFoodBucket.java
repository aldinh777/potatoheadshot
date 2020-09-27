package aldinh777.potatoheadshot.item.items;

import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SweetFoodBucket extends PotatoFood {

    public SweetFoodBucket(String name, int amount, float saturation) {
        super(name, amount, saturation);
        this.setMaxStackSize(16);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
        ActionResult<ItemStack> ret = ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
        if (ret != null) return ret;

        if (raytraceresult == null) {
            return eatItem(playerIn, itemstack, handIn);

        } else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return new ActionResult<>(EnumActionResult.PASS, itemstack);

        } else {
            BlockPos blockpos = raytraceresult.getBlockPos();

            if (!worldIn.isBlockModifiable(playerIn, blockpos)) {
                return new ActionResult<>(EnumActionResult.FAIL, itemstack);
            } else {
                if (!playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack)) {
                    return new ActionResult<>(EnumActionResult.FAIL, itemstack);

                } else {
                    IBlockState iblockstate = worldIn.getBlockState(blockpos);
                    Material material = iblockstate.getMaterial();

                    if (material == Material.WATER && iblockstate.getValue(BlockLiquid.LEVEL) == 0) {
                        worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                        playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                        return new ActionResult<>(EnumActionResult.SUCCESS, this.fillBucket(itemstack, playerIn, PotatoItems.SWEET_WATER_BUCKET));

                    } else if (material == Material.LAVA && iblockstate.getValue(BlockLiquid.LEVEL) == 0) {
                        playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1.0F, 1.0F);
                        worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                        return new ActionResult<>(EnumActionResult.SUCCESS, this.fillBucket(itemstack, playerIn, PotatoItems.SWEET_LAVA_BUCKET));

                    } else {
                        return eatItem(playerIn, itemstack, handIn);
                    }
                }
            }
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        if (this.getClass() == SweetFoodBucket.class) {
            return new FluidBucketWrapper(stack);
        } else {
            return super.initCapabilities(stack, nbt);
        }
    }

    private ActionResult<ItemStack> eatItem(EntityPlayer playerIn, ItemStack itemstack, EnumHand handIn) {
        if (playerIn.canEat(false)) {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        return new ActionResult<>(EnumActionResult.FAIL, itemstack);
    }

    private ItemStack fillBucket(ItemStack emptyBuckets, EntityPlayer player, Item fullBucket) {
        if (player.capabilities.isCreativeMode) {
            return emptyBuckets;
        } else {
            emptyBuckets.shrink(1);

            if (emptyBuckets.isEmpty()) {
                return new ItemStack(fullBucket);
            } else {
                if (!player.inventory.addItemStackToInventory(new ItemStack(fullBucket))) {
                    player.dropItem(new ItemStack(fullBucket), false);
                }

                return emptyBuckets;
            }
        }
    }
}
