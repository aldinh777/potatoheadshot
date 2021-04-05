package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.energy.PotatoEnergyStorage;
import aldinh777.potatoheadshot.handler.ConfigHandler;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.util.EnergyUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntitySweetPotatoGenerator extends TileEntityPotatoMachine {

    private final ItemStackHandler inputHandler = new ItemStackHandler(1);
    private final ItemStackHandler processHandler = new ItemStackHandler(1);
    private final ItemStackHandler outputHandler = new ItemStackHandler(1);

    private final PotatoEnergyStorage storage = new PotatoEnergyStorage(80000, 0, ConfigHandler.GENERATOR_RATE);
    private final int generationRate = ConfigHandler.GENERATOR_RATE;
    private int energy = storage.getEnergyStored();
    private int currentCookTime = 0;
    private int totalCookTime = 100;

    // Override Methods

    @Override
    public void update() {
        if (!world.isRemote) {
            boolean flag = false;
            ItemStack input = this.inputHandler.getStackInSlot(0);
            ItemStack process = this.processHandler.getStackInSlot(0);

            if (!process.isEmpty()) {
                if (this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) {
                    if (this.currentCookTime < this.totalCookTime) {
                        ++this.currentCookTime;
                        this.storage.generateEnergy(generationRate);
                        flag = true;
                    }
                }
                if (this.currentCookTime >= this.totalCookTime) {
                    if (this.cookProcess()) {
                        this.currentCookTime = 0;
                        process.shrink(1);
                        flag = true;
                    }
                }
            } else if (!input.isEmpty() && isItemFuel(input)) {
                this.totalCookTime = getFuelValue(input);
                this.currentCookTime = 0;
                this.processHandler.setStackInSlot(0, new ItemStack(input.getItem()));
                input.shrink(1);
                flag = true;
            }

            if (this.energy > 0) {
                for (EnumFacing side : EnumFacing.values()) {
                    TileEntity neighbour = this.world.getTileEntity(this.pos.offset(side));
                    EnergyUtil.doEnergyInteract(this, neighbour, side, 60);
                }
            }

            if (this.energy != this.storage.getEnergyStored()) {
                this.energy = this.storage.getEnergyStored();
                flag = true;
            }

            if (flag) {
                this.markDirty();
            }
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return true;
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return facing == null || facing == EnumFacing.DOWN || facing == EnumFacing.UP;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) this.storage;
        }

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

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return this.customOrDefaultName("Sweet Potato Generator");
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inputHandler.deserializeNBT(compound.getCompoundTag("InventoryInput"));
        this.processHandler.deserializeNBT(compound.getCompoundTag("InventoryProcess"));
        this.outputHandler.deserializeNBT(compound.getCompoundTag("InventoryOutput"));
        this.energy = compound.getInteger("GuiEnergy");
        this.currentCookTime = compound.getInteger("CurrentCookTime");
        this.totalCookTime = compound.getInteger("TotalCookTime");
        this.storage.readFromNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("InventoryInput", this.inputHandler.serializeNBT());
        compound.setTag("InventoryProcess", this.processHandler.serializeNBT());
        compound.setTag("InventoryOutput", this.outputHandler.serializeNBT());
        compound.setInteger("GuiEnergy", this.energy);
        compound.setInteger("CurrentCookTime", (short)this.currentCookTime);
        compound.setInteger("TotalCookTime", (short)this.totalCookTime);
        this.storage.writeToNBT(compound);
        return compound;
    }

    @Override
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

    @Override
    public void setField(int id, int value) {
        switch(id) {
            case 0:
                this.energy = value;
                break;
            case 1:
                this.totalCookTime = value;
                break;
            case 2:
                this.currentCookTime = value;
                break;
        }
    }

    // Custom Methods

    public int getMaxEnergyStored() {
        return this.storage.getMaxEnergyStored();
    }

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

    // Static Methods

    public static ItemStack getResult(ItemStack stack) {
        Item potatoPlanks = Item.getItemFromBlock(PotatoBlocks.POTATO_PLANKS);
        Item potatoBlock = Item.getItemFromBlock(PotatoBlocks.POTATO_BLOCK);

        if (stack.getItem() == Items.POTATO) return new ItemStack(Items.BAKED_POTATO);
        if (stack.getItem() == PotatoItems.SWEET_POTATO) return new ItemStack(PotatoItems.BAKED_SWEET_POTATO);
        if (stack.getItem() == PotatoItems.POTATO_STICK) return new ItemStack(PotatoItems.FRIED_FRIES);
        if (stack.getItem() == PotatoItems.SMALL_POTATO_PLANKS) return new ItemStack(PotatoItems.BAKED_SMALL_POTATO_PLANKS);
        if (stack.getItem() == potatoPlanks) return new ItemStack(PotatoItems.BAKED_POTATO_PLANKS);
        if (stack.getItem() == potatoBlock) return new ItemStack(PotatoItems.BAKED_POTATO_BLOCK);

        return ItemStack.EMPTY;
    }

    public static int getFuelValue(ItemStack stack) {
        Item potatoPlanks = Item.getItemFromBlock(PotatoBlocks.POTATO_PLANKS);
        Item potatoBlock = Item.getItemFromBlock(PotatoBlocks.POTATO_BLOCK);

        if (stack.getItem() == Items.POTATO) return 200;
        if (stack.getItem() == PotatoItems.SWEET_POTATO) return 200;
        if (stack.getItem() == PotatoItems.POTATO_STICK) return 100;
        if (stack.getItem() == PotatoItems.SMALL_POTATO_PLANKS) return 50;
        if (stack.getItem() == potatoPlanks) return 200;
        if (stack.getItem() == potatoBlock) return 1800;

        return 0;
    }

    public static boolean isItemFuel(ItemStack itemStack) {
        return getFuelValue(itemStack) > 0;
    }
}
