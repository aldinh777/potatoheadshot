package aldinh777.potatoheadshot.block.slots;

import aldinh777.potatoheadshot.block.tileentities.TileEntityPotatoGenerator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotGeneratorHandler extends SlotItemHandler {

    public SlotGeneratorHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return super.isItemValid(stack) && TileEntityPotatoGenerator.isItemFuel(stack);
    }
}
