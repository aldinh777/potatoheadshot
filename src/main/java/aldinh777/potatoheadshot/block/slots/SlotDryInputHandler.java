package aldinh777.potatoheadshot.block.slots;

import aldinh777.potatoheadshot.block.recipes.PotatoDrierRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotDryInputHandler extends SlotItemHandler {

    public SlotDryInputHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return super.isItemValid(stack) && PotatoDrierRecipes.INSTANCE.isDryRecipeExists(stack.getItem());
    }
}
