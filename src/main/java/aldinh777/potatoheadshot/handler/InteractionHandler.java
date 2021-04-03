package aldinh777.potatoheadshot.handler;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = PotatoHeadshot.MODID)
public class InteractionHandler {

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!event.getWorld().isRemote) {
            EntityPlayer player = event.getEntityPlayer();
            ItemStack currentItem = player.inventory.getCurrentItem();

            if (!currentItem.isEmpty() && currentItem.getItem() == PotatoItems.SWEET_EMPTY_BUCKET) {
                if (event.getTarget() instanceof EntityCow) {
                    currentItem.shrink(1);
                    player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                    if (currentItem.getCount() <= 1) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(PotatoItems.SWEET_MILK_BUCKET));
                    } else if (player.inventory.addItemStackToInventory(new ItemStack(PotatoItems.SWEET_MILK_BUCKET))) {
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
                        EntityItem lifeEssence = new EntityItem(world, x, y, z, lifeStack);
                        world.spawnEntity(lifeEssence);
                        world.removeEntity(target);
                        if (!player.capabilities.isCreativeMode) {
                            currentItem.shrink(1);
                        }
                    }
                }
            }
        }
    }
}
