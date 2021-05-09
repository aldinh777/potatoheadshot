package aldinh777.potatoheadshot.block.backup.slots;

import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotSaltHandler extends SlotItemHandler {

    public SlotSaltHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return super.isItemValid(stack) && stack.getItem() == PotatoItems.RAW_SALT;
    }
}
