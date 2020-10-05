package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.item.items.VoidBottle;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityVoidExchanger extends TileEntityPotatoMachine {

    private final ItemStackHandler bottleHandler = new ItemStackHandler(1) {
        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return stack.getItem() == PotatoItems.VOID_BOTTLE ? super.insertItem(slot, stack, simulate) : stack;
        }
    };
    private final ItemStackHandler inputHandler = new ItemStackHandler(1);
    private final ItemStackHandler outputHandler = new ItemStackHandler(1);

    private static final int speed = 8;
    private int progress = 0;
    private int cost = 0;

    // Override Methods

    @Override
    public void update() {
        if (!this.world.isRemote) {
            boolean flag = false;

            if (this.canExchange()) {
                if (this.progress >= this.cost) {
                    this.exchangeItem();
                    this.progress = 0;

                } else {
                    this.progress += this.progressExchange();
                }

                flag = true;

            } else if (progress > 0) {
                this.progress--;
                flag = true;
            }

            if (flag) {
                this.markDirty();
            }
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.UP) {
                return (T)this.bottleHandler;
            } else if (facing == EnumFacing.DOWN) {
                return (T)this.outputHandler;
            } else {
                return (T)this.inputHandler;
            }
        }

        return super.getCapability(capability, facing);
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return customOrDefaultName("Void Exchanger");
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.bottleHandler.deserializeNBT(compound.getCompoundTag("InventoryBottle"));
        this.inputHandler.deserializeNBT(compound.getCompoundTag("InventoryInput"));
        this.outputHandler.deserializeNBT(compound.getCompoundTag("InventoryOutput"));
        this.progress = compound.getInteger("Progress");
        this.cost = compound.getInteger("Cost");
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("InventoryInput", this.inputHandler.serializeNBT());
        compound.setTag("InventoryBottle", this.bottleHandler.serializeNBT());
        compound.setTag("InventoryOutput", this.outputHandler.serializeNBT());
        compound.setInteger("Progress", this.progress);
        compound.setInteger("Cost", this.cost);
        return compound;
    }

    @Override
    public int getField(String id) {
        switch (id) {
            case "progress":
                return this.progress;
            case "cost":
                return this.cost;
        }
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.progress = value;
                break;
            case 1:
                this.cost = value;
                break;
        }
    }

    // Custom Methods

    private boolean canExchange() {
        ItemStack bottle = bottleHandler.getStackInSlot(0);
        ItemStack input = inputHandler.getStackInSlot(0);
        ItemStack output = outputHandler.getStackInSlot(0);

        if (bottle.isEmpty() || input.isEmpty()) {
            return false;
        } else if (bottle.getItem() != PotatoItems.VOID_BOTTLE) {
            return false;
        } else if (VoidBottle.getVoidSize(bottle) <= 0) {
            return false;
        } else {
            ItemStack result = getResult(input);
            int cost = getCost(input);

            if (result.isEmpty()) {
                return false;
            } else {
                if (output.isEmpty()) {
                    this.cost = cost;
                    return true;

                } else {
                    if (output.isItemEqual(result)) {
                        int res = output.getCount() + result.getCount();
                        if (res <= output.getMaxStackSize()) {
                            this.cost = cost;
                            return true;
                        }
                    }
                    return false;
                }
            }
        }
    }

    private void exchangeItem() {
        ItemStack input = inputHandler.getStackInSlot(0);
        ItemStack output = outputHandler.getStackInSlot(0);
        ItemStack result = getResult(input);

        if (output.isEmpty()) {
            this.outputHandler.setStackInSlot(0, result.copy());
        } else if (output.isItemEqual(result)) {
            output.grow(1);
        }

        input.shrink(1);
    }

    private int progressExchange() {
        ItemStack bottle = bottleHandler.getStackInSlot(0);
        int voidSize = VoidBottle.getVoidSize(bottle);

        if (voidSize > 0) {
            if (voidSize >= speed) {
                VoidBottle.setVoidSize(bottle, voidSize - speed);
                return speed;
            } else {
                VoidBottle.setVoidSize(bottle, 0);
                return voidSize;
            }
        }

        return 0;
    }

    // Static Methods

    public static ItemStack getResult(ItemStack stack) {
        Item dirt = Item.getItemFromBlock(Blocks.DIRT);
        Item stone = Item.getItemFromBlock(Blocks.STONE);

        if (stack.getItem() == dirt) return new ItemStack(Items.DIAMOND);
        if (stack.getItem() == stone) return new ItemStack(Blocks.BEDROCK);

        return ItemStack.EMPTY;
    }

    public static int getCost(ItemStack stack) {
        Item dirt = Item.getItemFromBlock(Blocks.DIRT);
        Item stone = Item.getItemFromBlock(Blocks.STONE);

        if (stack.getItem() == dirt) return 8000;
        if (stack.getItem() == stone) return 128000;

        return 0;
    }
}
