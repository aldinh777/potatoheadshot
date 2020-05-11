package aldinh777.potatoheadshot.handler;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = PotatoHeadshot.MODID)
public class EntityHandler {

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack itemStack = player.inventory.getCurrentItem();

        if (event.getTarget() instanceof EntityCow) {
            if (!itemStack.isEmpty() && itemStack.getItem() == PotatoItems.SWEET_EMPTY_BUCKET) {
                itemStack.shrink(1);
                player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                if (itemStack.getCount() <= 1) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(PotatoItems.SWEET_MILK_BUCKET));
                } else if (player.inventory.addItemStackToInventory(new ItemStack(PotatoItems.SWEET_MILK_BUCKET))) {
                    player.dropItem(new ItemStack(PotatoItems.SWEET_MILK_BUCKET), true, false);
                }
            }
        }
    }
}
