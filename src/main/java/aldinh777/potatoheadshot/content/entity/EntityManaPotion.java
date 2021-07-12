package aldinh777.potatoheadshot.content.entity;

import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.common.util.AreaHelper;
import aldinh777.potatoheadshot.common.util.Element;
import aldinh777.potatoheadshot.content.items.PotatoFoodItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSponge;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class EntityManaPotion extends EntityPotion {

        private static final int fireColor = 0xEC1C24;
        private static final int lifeColor = 0xFFFFFF;

        private Element element;

        public EntityManaPotion(World worldIn) {
            super(worldIn);
        }

        public EntityManaPotion(World worldIn, EntityLivingBase throwerIn, Element element) {
            this(worldIn);
            this.setPosition(throwerIn.posX, throwerIn.posY + (double)throwerIn.getEyeHeight() - 0.10000000149011612D, throwerIn.posZ);
            this.thrower = throwerIn;
            this.setItem(createFakePotion(element));
            this.element = element;
        }

        @Override
        protected void onImpact(@Nonnull RayTraceResult result) {
            if (!this.world.isRemote) {
                if (this.element == Element.FIRE) {
                    applyFireSplash(result);
                    this.world.playEvent(2007, new BlockPos(this), fireColor);
                } else if (this.element == Element.LIFE) {
                    applyLifeSplash(result);
                    this.world.playEvent(2007, new BlockPos(this), lifeColor);
                }

                this.setDead();
            }
        }

        private void applyFireSplash(RayTraceResult result) {
            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos blockPos = result.getBlockPos();

                AreaHelper.getStateByRange(world, blockPos, 2, (pos, state) -> {
                    if (state.getBlock() == Blocks.AIR) {
                        IBlockState targetUnder = world.getBlockState(pos.down());
                        Block blockUnder = targetUnder.getBlock();

                        if (blockUnder != Blocks.AIR) {
                            world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                        }

                    } else if (state.getBlock() == Blocks.GRASS) {
                        if (ConfigHandler.SPLASH_FIRE_COOK_GRASS && ConfigHandler.COOKED_DIRT) {
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
                });
            }

            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D);
            AreaHelper.getEntitiesByRange(EntityLivingBase.class, world, axisalignedbb, (entityLivingBase -> {
                if (entityLivingBase.canBeHitWithPotion()) {
                    double distance = this.getDistanceSq(entityLivingBase);

                    if (distance < 16.0D) {
                        entityLivingBase.setFire(8);
                    }
                }
            }));
        }

        private void applyLifeSplash(RayTraceResult result) {
            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos blockPos = result.getBlockPos();

                AreaHelper.getStateByRange(world, blockPos, 1, (pos, state) -> {
                    if (state.getBlock() == Blocks.DIRT) {
                        IBlockState upBlock = world.getBlockState(pos.up());
                        if (upBlock.getBlock() == Blocks.AIR) {
                            world.setBlockState(pos, Blocks.GRASS.getDefaultState());
                        }

                    } else if (state.getBlock() instanceof IGrowable) {
                        IGrowable plant = (IGrowable) state.getBlock();
                        if (plant.canGrow(world, pos, state, true)) {
                            plant.grow(world, new Random(), pos, state);
                        }
                    }
                });
            }

            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D);
            AreaHelper.getEntitiesByRange(EntityLivingBase.class, world, axisalignedbb, (entityLivingBase) -> {
                if (entityLivingBase.canBeHitWithPotion()) {
                    double distance = this.getDistanceSq(entityLivingBase);

                    if (distance < 16.0D) {
                        if (entityLivingBase.isEntityUndead()) {
                            entityLivingBase.attackEntityFrom(DamageSource.MAGIC,6);
                        } else {
                            entityLivingBase.heal(6);
                        }
                    }
                }
            });
        }

        private static ItemStack createFakePotion(Element element) {
            ItemStack potion = new ItemStack(Items.SPLASH_POTION);
            NBTTagCompound compound = new NBTTagCompound();

            if (element == Element.FIRE) {
                compound.setInteger("CustomPotionColor", fireColor);
            } else if (element == Element.LIFE) {
                compound.setInteger("CustomPotionColor", lifeColor);
            }

            potion.setTagCompound(compound);
            return potion;
        }
    }
