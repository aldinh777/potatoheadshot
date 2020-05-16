package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.energy.PotatoEnergyStorage;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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

import java.util.Objects;

public class TileEntityPotatoGenerator extends TileEntity implements ITickable {

    private final ItemStackHandler inputHandler = new ItemStackHandler(1);
    private final ItemStackHandler processHandler = new ItemStackHandler(1);
    private final ItemStackHandler outputHandler = new ItemStackHandler(1);

    private final PotatoEnergyStorage storage = new PotatoEnergyStorage(80000, 0, 60);
    private int energy = storage.getEnergyStored();
    private String customName;
    private int currentCookTime = 0;
    private int totalCookTime = 100;

    private boolean cookProcess() {
        ItemStack process = this.processHandler.getStackInSlot(0);
        ItemStack output = this.outputHandler.getStackInSlot(0);

        ItemStack result = getResult(process);

        if (output.isEmpty() && !result.isEmpty()) {
            this.outputHandler.setStackInSlot(0, result);
            return true;
        } else if (output.getCount() < output.getMaxStackSize() && result.getItem().equals(output.getItem())) {
            output.grow(1);
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            ItemStack input = this.inputHandler.getStackInSlot(0);
            ItemStack process = this.processHandler.getStackInSlot(0);

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
            } else if (!input.isEmpty() && isItemFuel(input)) {
                this.totalCookTime = getFuelValue(input);
                this.currentCookTime = 0;
                this.processHandler.setStackInSlot(0, new ItemStack(input.getItem()));
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
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null) {
                return (T)this.processHandler;
            } else if (facing == EnumFacing.DOWN) {
                return (T)this.outputHandler;
            } else if (facing == EnumFacing.UP) {
                return (T)this.inputHandler;
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return true;
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return facing == null || facing == EnumFacing.DOWN || facing == EnumFacing.UP;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("InventoryInput", this.inputHandler.serializeNBT());
        compound.setTag("InventoryProcess", this.processHandler.serializeNBT());
        compound.setTag("InventoryOutput", this.outputHandler.serializeNBT());
        compound.setInteger("GuiEnergy", this.energy);
        compound.setInteger("CurrentCookTime", (short)this.currentCookTime);
        compound.setInteger("TotalCookTime", (short)this.totalCookTime);
        compound.setString("Name", Objects.requireNonNull(getDisplayName()).toString());
        this.storage.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inputHandler.deserializeNBT(compound.getCompoundTag("InventoryInput"));
        this.processHandler.deserializeNBT(compound.getCompoundTag("InventoryProcess"));
        this.outputHandler.deserializeNBT(compound.getCompoundTag("InventoryOutput"));
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

    public static int getFuelValue(ItemStack stack) {
        if (stack.getItem() == Items.POTATO) return 200;
        if (stack.getItem() == PotatoItems.SWEET_POTATO) return 200;
        return 0;
    }

    public static ItemStack getResult(ItemStack stack) {
        if (stack.getItem() == Items.POTATO) return new ItemStack(Items.BAKED_POTATO);
        if (stack.getItem() == PotatoItems.SWEET_POTATO) return new ItemStack(PotatoItems.BAKED_SWEET_POTATO);
        return ItemStack.EMPTY;
    }

    public static boolean isItemFuel(ItemStack itemStack) {
        return getFuelValue(itemStack) > 0;
    }

    public static void doEnergyInteract(TileEntity source, TileEntity target, EnumFacing side, int maxTransfer) {
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
