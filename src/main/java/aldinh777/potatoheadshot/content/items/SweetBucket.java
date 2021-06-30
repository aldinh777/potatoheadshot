package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.other.handler.ConfigHandler;
import aldinh777.potatoheadshot.other.lists.PotatoItems;
import aldinh777.potatoheadshot.other.lists.PotatoTab;
import aldinh777.potatoheadshot.other.util.FoodEffects;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SweetBucket extends Item {

    private final Block containedBlock;
    private final List<FoodEffects> effects = new ArrayList<>();

    public SweetBucket(String name) {
        this(name, Blocks.AIR);
    }

    public SweetBucket(String name, Block containedBlockIn) {
        this.containedBlock = containedBlockIn;
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setMaxStackSize(1);
        this.setCreativeTab(PotatoTab.POTATO_TAB);
        this.setContainerItem(PotatoItems.SWEET_EMPTY_BUCKET);
        if (ConfigHandler.SWEET_BUCKET) {
            PotatoItems.LISTS.add(this);
        }
    }

    @Override
    public int getItemBurnTime(@Nonnull ItemStack itemStack) {
        if (itemStack.getItem() == PotatoItems.SWEET_LAVA_BUCKET) {
            return 20000;
        }
        return super.getItemBurnTime(itemStack);
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
        return 32;
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        return EnumAction.DRINK;
    }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, World worldIn, @Nonnull EntityLivingBase entityLiving) {
        if (!worldIn.isRemote) {
            if (entityLiving instanceof EntityPlayer) {
                onDrink(new ItemStack(Items.MILK_BUCKET), worldIn, (EntityPlayer) entityLiving);
            }
        }

        if (entityLiving instanceof EntityPlayer && !((EntityPlayer)entityLiving).capabilities.isCreativeMode) {
            stack.shrink(1);
        }

        return stack.isEmpty() ? new ItemStack(PotatoItems.SWEET_EMPTY_BUCKET) : stack;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new BucketCapability(stack);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {

        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);
        ActionResult<ItemStack> ret = ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
        if (ret != null) return ret;

        if (raytraceresult == null) {
            return drink(playerIn, itemstack, handIn);

        } else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return new ActionResult<>(EnumActionResult.PASS, itemstack);

        } else {
            BlockPos blockpos = raytraceresult.getBlockPos();

            if (!worldIn.isBlockModifiable(playerIn, blockpos)) {
                return new ActionResult<>(EnumActionResult.FAIL, itemstack);

            } else {
                boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
                BlockPos sidePos = flag1 && raytraceresult.sideHit == EnumFacing.UP ? blockpos : blockpos.offset(raytraceresult.sideHit);

                if (!playerIn.canPlayerEdit(sidePos, raytraceresult.sideHit, itemstack)) {
                    return new ActionResult<>(EnumActionResult.FAIL, itemstack);

                } else if (this.tryPlaceContainedLiquid(playerIn, worldIn, sidePos)) {
                    if (!playerIn.capabilities.isCreativeMode) {
                        return new ActionResult<>(EnumActionResult.SUCCESS, new ItemStack(PotatoItems.SWEET_EMPTY_BUCKET));
                    }

                    return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
                } else {
                    return drink(playerIn, itemstack, handIn);
                }
            }
        }
    }

    public void addEffects(FoodEffects... effects) {
        this.effects.addAll(Arrays.asList(effects));
    }

    private boolean tryPlaceContainedLiquid(@Nullable EntityPlayer player, World worldIn, BlockPos posIn) {
        if (this.containedBlock == Blocks.AIR) {
            return false;
        } else {
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

                    SoundEvent soundevent = this.containedBlock == Blocks.FLOWING_LAVA ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
                    worldIn.playSound(player, posIn, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    worldIn.setBlockState(posIn, this.containedBlock.getDefaultState(), 11);
                }

                return true;
            }
        }
    }

    private void onDrink(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            for (FoodEffects effect : this.effects) {
                effect.applyEffects(stack, worldIn, player);
            }
        }
    }

    private ActionResult<ItemStack> drink(EntityPlayer playerIn, ItemStack itemstack, EnumHand handIn) {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    public static class BucketCapability extends FluidBucketWrapper {
        public BucketCapability(@Nonnull ItemStack container) {
            super(container);
        }

        @Nullable
        @Override
        public FluidStack getFluid() {
            Item item = container.getItem();
            if (item == PotatoItems.SWEET_WATER_BUCKET) {
                return new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME);
            }
            else if (item == PotatoItems.SWEET_LAVA_BUCKET) {
                return new FluidStack(FluidRegistry.LAVA, Fluid.BUCKET_VOLUME);
            }
            else if (item == PotatoItems.SWEET_MILK_BUCKET) {
                return FluidRegistry.getFluidStack("milk", Fluid.BUCKET_VOLUME);
            }

            return null;
        }

        @Override
        protected void setFluid(@Nullable FluidStack fluidStack) {
            if (fluidStack == null) {
                container = new ItemStack(PotatoItems.SWEET_EMPTY_BUCKET);
            } else {
                Fluid fluid = fluidStack.getFluid();

                if (fluidStack.tag == null || fluidStack.tag.hasNoTags()) {
                    if (fluid == FluidRegistry.WATER) {
                        container = new ItemStack(PotatoItems.SWEET_WATER_BUCKET);
                    }
                    else if (fluid == FluidRegistry.LAVA) {
                        container = new ItemStack(PotatoItems.SWEET_LAVA_BUCKET);
                    }
                    else if (fluid.getName().equals("milk")) {
                        container = new ItemStack(PotatoItems.SWEET_MILK_BUCKET);
                    }
                    else {
                        container = ItemStack.EMPTY;
                    }
                }
            }
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            if (container.getCount() != 1
                    || resource == null
                    || resource.amount < Fluid.BUCKET_VOLUME
                    || container.getItem() == PotatoItems.SWEET_MILK_BUCKET
                    || getFluid() != null
                    || !canFillFluidType(resource)) {
                return 0;
            }

            if (doFill) {
                setFluid(resource);
            }

            return Fluid.BUCKET_VOLUME;
        }

        @Override
        public boolean canFillFluidType(FluidStack fluidStack) {
            Fluid fluid = fluidStack.getFluid();
            return fluid == FluidRegistry.WATER || fluid == FluidRegistry.LAVA || fluid.getName().equals("milk");
        }
    }
}
