package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.blocks.machines.BlockDrier;
import aldinh777.potatoheadshot.block.inventory.InventoryDrier;
import aldinh777.potatoheadshot.block.inventory.InventoryDrierUpgrade;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityDrier extends AbstractMachine {

    private final ItemStackHandler inventoryDrier = new InventoryDrier();
    private final InventoryDrierUpgrade inventoryUpgradeDrier = new InventoryDrierUpgrade();

    public InventoryDrierUpgrade getUpgrade() {
        return inventoryUpgradeDrier;
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString("Potato Drier");
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) inventoryDrier;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventoryDrier.deserializeNBT(compound.getCompoundTag("Inventory"));
        inventoryUpgradeDrier.deserializeNBT(compound.getCompoundTag("Upgrade"));
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Inventory", inventoryDrier.serializeNBT());
        compound.setTag("Upgrade", inventoryUpgradeDrier.serializeNBT());
        return compound;
    }

    @Override
    public void update() {
        boolean dryFlag = false;

        if (!world.isRemote) {
            IBlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof BlockDrier) {
                boolean water = state.getValue(BlockDrier.WATER);
                BlockDrier.Mode mode = state.getValue(BlockDrier.MODE);
                if (water != inventoryUpgradeDrier.hasWaterCapacity()) {
                    world.setBlockState(pos, state.withProperty(BlockDrier.WATER, inventoryUpgradeDrier.hasWaterCapacity()));
                }
                if (mode != inventoryUpgradeDrier.getMode()) {
                    world.setBlockState(pos, state.withProperty(BlockDrier.MODE, inventoryUpgradeDrier.getMode()));
                }
            }
        }

        if (dryFlag) {
            markDirty();
        }
    }

    private boolean canDry() {
        return false;
    }

    private void dryItem() {

    }
}
