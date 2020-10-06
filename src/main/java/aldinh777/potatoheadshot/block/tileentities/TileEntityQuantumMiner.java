package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityQuantumMiner extends TileEntityPotatoMachine {

    int coolDown = 8;
    int currentCoolDown = 0;

    @Override
    public int getField(String id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public void update() {
        if (!this.world.isRemote) {
            if (this.currentCoolDown >= this.coolDown) {
                this.mineVoid();
                this.markDirty();
                this.currentCoolDown = 0;

            } else {
                this.currentCoolDown++;
            }
        }
    }

    public void mineVoid() {
        IBlockState minedBlock = this.world.getBlockState(this.pos.down());
        if (minedBlock.getBlock() != Blocks.BEDROCK) {
            return;
        }

        TileEntity storage = this.world.getTileEntity(this.pos.up());
        if (storage != null) {
            if (storage.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)) {
                IItemHandler handler = storage.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

                if (handler != null) {
                    this.mineVoid(handler);
                }
            }
        }
    }

    public void mineVoid(IItemHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);

            if (stack.isEmpty()) {
                handler.insertItem(i, new ItemStack(PotatoItems.VOID_QUANTUM), false);
                return;
            }

            if (stack.getItem() == PotatoItems.VOID_QUANTUM && stack.getCount() < stack.getMaxStackSize()) {
                stack.grow(1);
                return;
            }
        }
    }
}
