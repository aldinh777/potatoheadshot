package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.content.blocks.machines.ManaCauldron;
import aldinh777.potatoheadshot.other.handler.ConfigHandler;
import aldinh777.potatoheadshot.other.lists.PotatoItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSponge;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SplashManaPotion extends PotatoItem {

    private final ManaCauldron.Element element;

    public SplashManaPotion(String name, ManaCauldron.Element element) {
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

    static class EntityManaPotion extends EntityPotion {

        private static final int fireColor = 15473700;
        private static final int lifeColor = 16777215;

        private final ManaCauldron.Element element;

        public EntityManaPotion(World worldIn) {
            super(worldIn);
            this.element = ManaCauldron.Element.MANA;
        }

        public EntityManaPotion(World worldIn, EntityLivingBase throwerIn, ManaCauldron.Element element) {
            super(worldIn, throwerIn, createFakePotion(element));
            this.element = element;
        }

        @Override
        protected void onImpact(@Nonnull RayTraceResult result) {
            if (!this.world.isRemote) {
                if (this.element == ManaCauldron.Element.FIRE) {
                    applyFireSplash(result);
                    this.world.playEvent(2007, new BlockPos(this), fireColor);
                } else if (this.element == ManaCauldron.Element.LIFE) {
                    applyLifeSplash(result);
                    this.world.playEvent(2007, new BlockPos(this), lifeColor);
                }

                this.setDead();
            }
        }

        private void applyFireSplash(RayTraceResult result) {
            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos blockPos = result.getBlockPos();
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            BlockPos pos = blockPos.add(x, y, z);
                            IBlockState state = world.getBlockState(pos);
                            IBlockState upBlock = world.getBlockState(pos.up());

                            if (state.getBlock() == Blocks.GRASS) {
                                if (ConfigHandler.SPLASH_FIRE_COOK_GRASS) {
                                    PotatoFoodItemBlock cookedDirt = (PotatoFoodItemBlock) PotatoItems.COOKED_DIRT;
                                    world.setBlockState(pos, cookedDirt.getBlock().getDefaultState());
                                }

                            } else if (state.getBlock() == Blocks.SPONGE) {
                                world.setBlockState(pos, Blocks.SPONGE.getDefaultState().withProperty(BlockSponge.WET, false));

                            } else {
                                ItemStack res = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(state.getBlock()));
                                if (res.getItem() instanceof PotatoFoodItemBlock) {
                                    PotatoFoodItemBlock item = (PotatoFoodItemBlock) res.getItem();
                                    Block blockResult = item.getBlock();
                                    world.setBlockState(pos, blockResult.getDefaultState());
                                }
                                if (res.getItem() instanceof ItemBlock) {
                                    ItemBlock item = (ItemBlock) res.getItem();
                                    Block blockResult = item.getBlock();
                                    world.setBlockState(pos, blockResult.getDefaultState());
                                }
                            }

                            if (upBlock.getBlock() == Blocks.AIR) {
                                world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());
                            }
                        }
                    }
                }
            }

            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D);
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

            if (!list.isEmpty()) {
                for (EntityLivingBase entitylivingbase : list) {
                    if (entitylivingbase.canBeHitWithPotion()) {
                        double distance = this.getDistanceSq(entitylivingbase);

                        if (distance < 16.0D) {
                            entitylivingbase.setFire(8);
                        }
                    }
                }
            }
        }

        private void applyLifeSplash(RayTraceResult result) {
            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos blockPos = result.getBlockPos();
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            BlockPos pos = blockPos.add(x, y, z);
                            IBlockState state = world.getBlockState(pos);
                            IBlockState upBlock = world.getBlockState(pos.up());

                            if (upBlock.getBlock() == Blocks.AIR && state.getBlock() == Blocks.DIRT) {
                                world.setBlockState(pos, Blocks.GRASS.getDefaultState());
                            }

                            if (state.getBlock() instanceof IGrowable) {
                                IGrowable plant = (IGrowable) state.getBlock();
                                if (plant.canGrow(world, pos, state, true)) {
                                    plant.grow(world, new Random(), pos, state);
                                }
                            }
                        }
                    }
                }
            }

            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D);
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

            if (!list.isEmpty()) {
                for (EntityLivingBase entitylivingbase : list) {
                    if (entitylivingbase.canBeHitWithPotion()) {
                        double distance = this.getDistanceSq(entitylivingbase);

                        if (distance < 16.0D) {
                            if (entitylivingbase.isEntityUndead()) {
                                entitylivingbase.attackEntityFrom(DamageSource.MAGIC,6);
                            } else {
                                entitylivingbase.heal(6);
                            }
                        }
                    }
                }
            }
        }

        private static ItemStack createFakePotion(ManaCauldron.Element element) {
            ItemStack potion = new ItemStack(Items.SPLASH_POTION);
            NBTTagCompound compound = new NBTTagCompound();

            if (element == ManaCauldron.Element.FIRE) {
                compound.setInteger("CustomPotionColor", fireColor);
            } else if (element == ManaCauldron.Element.LIFE) {
                compound.setInteger("CustomPotionColor", lifeColor);
            }

            potion.setTagCompound(compound);
            return potion;
        }
    }
}
