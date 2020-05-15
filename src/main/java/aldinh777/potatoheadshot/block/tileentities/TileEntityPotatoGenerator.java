package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.energy.PotatoEnergyStorage;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityPotatoGenerator extends TileEntity implements ITickable {

    public ItemStackHandler handler = new ItemStackHandler(3);
    private final PotatoEnergyStorage storage = new PotatoEnergyStorage(80000, 0, 60);
    public int energy = storage.getEnergyStored();
    private String customName;
    public int currentCookTime = 0;
    public int totalCookTime = 100;

    private boolean cookProcess() {
        ItemStack process = this.handler.getStackInSlot(1);
        ItemStack output = this.handler.getStackInSlot(2);

        Item result = getResult(process);

        if (output.isEmpty()) {
            this.handler.setStackInSlot(2, new ItemStack(result));
            return true;
        } else if (output.getCount() < output.getMaxStackSize() && result.equals(output.getItem())) {
            output.grow(1);
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            ItemStack input = this.handler.getStackInSlot(0);
            ItemStack process = this.handler.getStackInSlot(1);

            if (!process.isEmpty()) {
                if (this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) {
                    if (this.currentCookTime < this.totalCookTime) {
                        ++this.currentCookTime;
                        this.storage.generateEnergy(10);
                    }
                }
                if (this.currentCookTime >= this.totalCookTime) {
                    if (this.cookProcess()) {
                        this.currentCookTime = 0;
                        process.shrink(1);
                    }
                }
            } else if (!input.isEmpty()) {
                this.totalCookTime = getFuelValue(input);
                this.currentCookTime = 0;
                this.handler.setStackInSlot(1, new ItemStack(input.getItem()));
                input.shrink(1);
            }

            if (this.energy > 0) {
                for (EnumFacing side : EnumFacing.values()) {
                    TileEntity neighbour = this.world.getTileEntity(this.pos.offset(side));
                    doEnergyInteract(this, neighbour, side, 60);
                }
            }

            if (this.energy != this.storage.getEnergyStored()) {
                this.energy = this.storage.getEnergyStored();
            }

            markDirty();
        }
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return (T)this.storage;
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T)this.handler;
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return true;
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Inventory", this.handler.serializeNBT());
        compound.setInteger("GuiEnergy", this.energy);
        compound.setInteger("CurrentCookTime", (short)this.currentCookTime);
        compound.setInteger("TotalCookTime", (short)this.totalCookTime);
        compound.setString("Name", getDisplayName().toString());
        this.storage.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
        this.energy = compound.getInteger("GuiEnergy");
        this.currentCookTime = compound.getInteger("CurrentCookTime");
        this.totalCookTime = compound.getInteger("TotalCookTime");
        this.customName = compound.getString("Name");
        this.storage.readFromNBT(compound);
    }

    @Override
    public ITextComponent getDisplayName() {
        if (this.customName != null && !this.customName.isEmpty()) {
            return new TextComponentString(this.customName);
        }
        return new TextComponentString("Sweet Potato Generator");
    }

    public int getEnergyStored() {
        return this.energy;
    }

    public int getMaxEnergyStored() {
        return this.storage.getMaxEnergyStored();
    }

    public int getField(String id) {
        switch(id) {
            case "energy":
                return this.energy;
            case "totalCookTime":
                return this.totalCookTime;
            case "currentCookTime":
                return this.currentCookTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value) {
        switch(id) {
            case 0:
                this.energy = value;
            case 1:
                this.totalCookTime = value;
            case 2:
                this.currentCookTime = value;
        }
    }

    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    private static int getFuelValue(ItemStack stack) {
        if (stack.getItem() == Items.POTATO) return 200;
        if (stack.getItem() == PotatoItems.SWEET_POTATO) return 200;
        return 0;
    }

    private static Item getResult(ItemStack stack) {
        if (stack.getItem() == Items.POTATO) return Items.BAKED_POTATO;
        if (stack.getItem() == PotatoItems.SWEET_POTATO) return PotatoItems.BAKED_SWEET_POTATO;
        return Items.BAKED_POTATO;
    }

    public static boolean isItemFuel(ItemStack itemStack) {
        return getFuelValue(itemStack) > 0;
    }

    private static void doEnergyInteract(TileEntity source, TileEntity target, EnumFacing side, int maxTransfer) {
        if (maxTransfer > 0) {
            EnumFacing interfaceSide = side == null ? EnumFacing.UP : side.getOpposite();
            if (source != null && target != null) {
                if (target.hasCapability(CapabilityEnergy.ENERGY, interfaceSide)) {
                    IEnergyStorage sourceCap = source.getCapability(CapabilityEnergy.ENERGY, side);
                    IEnergyStorage targetCap = target.getCapability(CapabilityEnergy.ENERGY, interfaceSide);
                    if (sourceCap != null && targetCap != null) {
                        if (sourceCap.getEnergyStored() == 0) return;
                        int availableToSend = sourceCap.extractEnergy(maxTransfer, true);
                        if (availableToSend > 0) {
                            int totalSent = targetCap.receiveEnergy(availableToSend, false);
                            sourceCap.extractEnergy(totalSent, false);
                        }
                    }
                }
            }
        }
    }
}
