package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.content.blocks.MagicBlock;
import aldinh777.potatoheadshot.common.lists.PotatoBlocks;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.content.entity.EntityFloatingItem;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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

                    if (iblockstate.getBlock() instanceof BlockLeaves) {
                        if (!playerIn.capabilities.isCreativeMode) {
                            itemstack.damageItem(1, playerIn);
                        }

                        if (!worldIn.isRemote) {
                            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                            ItemStack natureStack = new ItemStack(PotatoItems.ESSENCE_NATURE);
                            EntityItem natureEssence = new EntityFloatingItem(worldIn, x, y, z, natureStack);
                            MagicBlock.floatEntity(natureEssence, 4_000);
                            worldIn.spawnEntity(natureEssence);
                            itemstack.damageItem(1, playerIn);
                        }

                        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);

                    } else if (iblockstate.getBlock() == PotatoBlocks.GLOWING_POTATO_BLOCK) {
                        if (!playerIn.capabilities.isCreativeMode) {
                            itemstack.damageItem(1, playerIn);
                        }

                        if (!worldIn.isRemote) {
                            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                            ItemStack manaStack = new ItemStack(PotatoItems.ESSENCE_MANA);
                            EntityItem pureEssence = new EntityFloatingItem(worldIn, x, y, z, manaStack);
                            MagicBlock.floatEntity(pureEssence, 4_000);
                            worldIn.spawnEntity(pureEssence);
                            itemstack.damageItem(1, playerIn);
                        }

                        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);

                    } else if (material == Material.LAVA && iblockstate.getValue(BlockLiquid.LEVEL) == 0) {
                        if (!playerIn.capabilities.isCreativeMode) {
                            itemstack.damageItem(1, playerIn);
                        }

                        if (!worldIn.isRemote) {
                            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                            ItemStack fireStack = new ItemStack(PotatoItems.ESSENCE_FIRE);
                            EntityItem fireEssence = new EntityFloatingItem(worldIn, x, y, z, fireStack);
                            MagicBlock.floatEntity(fireEssence, 4_000);
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
