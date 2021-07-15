package aldinh777.potatoheadshot.common.handler;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.content.blocks.MagicBlock;
import aldinh777.potatoheadshot.content.capability.CapabilityBlood;
import aldinh777.potatoheadshot.content.capability.IBloodStorage;
import aldinh777.potatoheadshot.content.capability.PotatoBloodStorage;
import aldinh777.potatoheadshot.content.entity.EntityFloatingItem;
import aldinh777.potatoheadshot.content.items.HeartContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = PotatoHeadshot.MODID)
public class InteractionHandler {

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!event.getWorld().isRemote) {
            EntityPlayer player = event.getEntityPlayer();
            ItemStack currentItem = player.getHeldItem(event.getHand());

            if (!currentItem.isEmpty() && currentItem.getItem() == PotatoItems.SWEET_EMPTY_BUCKET) {
                if (event.getTarget() instanceof EntityCow) {
                    currentItem.shrink(1);
                    player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                    if (currentItem.getCount() < 1) {
                        player.setHeldItem(event.getHand(), new ItemStack(PotatoItems.SWEET_MILK_BUCKET));
                    } else if (!player.inventory.addItemStackToInventory(new ItemStack(PotatoItems.SWEET_MILK_BUCKET))) {
                        player.dropItem(new ItemStack(PotatoItems.SWEET_MILK_BUCKET), true, false);
                    }
                }
            }

            if (!currentItem.isEmpty() && currentItem.getItem() == PotatoItems.POTATO_MANA_KNIFE) {
                if (event.getTarget() instanceof EntityAnimal) {
                    EntityAnimal target = (EntityAnimal) event.getTarget();
                    World world = event.getWorld();
                    float x = target.getPosition().getX() + 0.5f;
                    float y = target.getPosition().getY() + 0.5f;
                    float z = target.getPosition().getZ() + 0.5f;
                    if (!world.isRemote) {
                        ItemStack lifeStack = new ItemStack(PotatoItems.ESSENCE_LIFE);
                        EntityItem lifeEssence = new EntityFloatingItem(world, x, y, z, lifeStack);
                        MagicBlock.floatEntity(lifeEssence, 4_000);
                        world.spawnEntity(lifeEssence);
                        world.removeEntity(target);
                        if (!player.capabilities.isCreativeMode) {
                            currentItem.damageItem(1, player);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        EntityLivingBase entityIn = event.getEntityLiving();
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;
            if (!event.getSource().damageType.equals("heart_extraction")) {
                ItemStack heartContainer = HeartContainer.findHeartContainer(player);
                if (heartContainer.hasCapability(CapabilityBlood.BLOOD, EnumFacing.UP)) {
                    IBloodStorage bloodStorage = heartContainer.getCapability(CapabilityBlood.BLOOD, EnumFacing.UP);
                    if (bloodStorage instanceof PotatoBloodStorage) {
                        float reducedDamage = Math.max(event.getAmount() - bloodStorage.getBloodQuantity(), 0);
                        bloodStorage.useBlood(event.getAmount());
                        if (reducedDamage > 0) {
                            heartContainer.shrink(1);
                            event.setAmount(reducedDamage);
                            onEntityHurt(event);
                        } else {
                            if (bloodStorage.getBloodQuantity() <= 0) {
                                heartContainer.shrink(1);
                            }
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityUpdate(EntityEvent event) {
        Entity entity = event.getEntity();
        if (entity != null) {
            NBTTagCompound compound = entity.getEntityData();
            if (compound.hasKey("GravityTick")) {
                int gravityTick = compound.getInteger("GravityTick");
                if (gravityTick > 0) {
                    if (!entity.hasNoGravity()) {
                        entity.setNoGravity(true);
                    }
                    compound.setInteger("GravityTick", --gravityTick);
                } else {
                    entity.setNoGravity(false);
                    compound.removeTag("GravityTick");
                }
            }
        }
    }
}
