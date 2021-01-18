package aldinh777.potatoheadshot.item.items;

import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;

public class PotatoManaKnife extends PotatoKnife {

    public PotatoManaKnife(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityLivingBase entityLiving) {
        this.setDamage(stack, this.getDamage(stack) - 50);
        return stack;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
        ActionResult<ItemStack> ret = ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
        if (ret != null) return ret;

        if (raytraceresult == null) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);

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
                    float x = blockpos.getX() + 0.5f;
                    float y = blockpos.getY() + 0.5f;
                    float z = blockpos.getZ() + 0.5f;

                    if (iblockstate.getBlock() == Blocks.GRASS) {
                        if (!playerIn.capabilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }

                        if (!worldIn.isRemote) {
                            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                            ItemStack natureStack = new ItemStack(PotatoItems.ESSENCE_NATURE);
                            EntityItem natureEssence = new EntityItem(worldIn, x, y, z, natureStack);
                            worldIn.spawnEntity(natureEssence);
                        }

                        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);

                    } else if (material == Material.LAVA && iblockstate.getValue(BlockLiquid.LEVEL) == 0) {
                        if (!playerIn.capabilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }

                        if (!worldIn.isRemote) {
                            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                            ItemStack fireStack = new ItemStack(PotatoItems.ESSENCE_FIRE);
                            EntityItem fireEssence = new EntityItem(worldIn, x, y, z, fireStack);
                            worldIn.spawnEntity(fireEssence);
                        }

                        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);

                    } else {
                        return new ActionResult<>(EnumActionResult.FAIL, itemstack);
                    }
                }
            }
        }
    }
}
