package aldinh777.potatoheadshot.common.util;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.function.Consumer;

public interface InventoryHelper {

    static boolean transferItem(TileEntity tileEntity, EnumFacing facing, ItemStack stack) {
        boolean flag = false;
        if (tileEntity != null) {
            if (tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
                IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                if (itemHandler != null) {
                    int maxSlot = itemHandler.getSlots();
                    for (int i = 0; i < maxSlot; i++) {
                        ItemStack insert = itemHandler.insertItem(i, stack, true);
                        if (insert.isEmpty()) {
                            itemHandler.insertItem(i, stack, false);
                            flag = true;
                            break;
                        }
                    }
                }
            }
        }
        return flag;
    }

    static void spawnAllSlotAsEntity(IItemHandler handler, World world, BlockPos pos) {
        for (int i = 0; i < handler.getSlots(); ++i) {
            ItemStack itemStack = handler.getStackInSlot(i);

            if (!itemStack.isEmpty()) {
                Block.spawnAsEntity(world, pos, itemStack);
            }
        }
    }

    static boolean isOutputOverflow(ItemStack output, ItemStack result) {
        return output.getCount() + result.getCount() > output.getMaxStackSize();
    }

    static void shrinkIntoContainer(ItemStackHandler itemHandler, int slot, ItemStack input) {
        ItemStack container = input.getItem().getContainerItem(input);
        input.shrink(1);

        if (input.isEmpty() && !container.isEmpty()) {
            itemHandler.setStackInSlot(slot, container);
        }
    }

    static boolean setOutputSlot(ItemStackHandler itemHandler, int outputSlot, ItemStack result) {
        ItemStack output = itemHandler.getStackInSlot(outputSlot);

        if (output.isEmpty()) {
            itemHandler.setStackInSlot(outputSlot, result.copy());
            return true;
        } else if (output.isItemEqual(result) && !isOutputOverflow(output, result)) {
            output.grow(result.getCount());
            return true;
        }

        return false;
    }

    static void addSlotPlayer(Consumer<Slot> addSlotFunction, InventoryPlayer inventoryPlayer) {
        for (int y = 0; y < 3; y++) {
            for(int x = 0; x < 9; x++) {
                addSlotFunction.accept(new Slot(inventoryPlayer, x + y*9 + 9, 8 + x*18, 84 + y*18));
            }
        }

        for (int x = 0; x < 9; x++) {
            addSlotFunction.accept(new Slot(inventoryPlayer, x, 8 + x * 18, 142));
        }
    }
}
