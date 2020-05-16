package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.PotatoDrier;
import aldinh777.potatoheadshot.block.recipes.PotatoDrierRecipes;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityPotatoDrier extends TileEntityPotatoMachine {

    private final ItemStackHandler fuelHandler = new ItemStackHandler(1) {
        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return isItemFuel(stack) ? super.insertItem(slot, stack, simulate) : stack;
        }
    };
    private final ItemStackHandler inputHandler = new ItemStackHandler(2);
    private final ItemStackHandler outputHandler = new ItemStackHandler(2);

    private int waterSize;
    private int wateringTime;
    private int burnTime;
    private int currentBurnTime;
    private int currentWateringTime;

    private int dryTime;
    private int wetTime;
    private int totalDryTime = 200;
    private int totalWetTime = 1000;

    // Override Methods

    @Override
    public void update() {
        boolean flagBurning = this.isBurning();
        boolean dryingFlag = false;
        boolean wettingFlag = false;

        if (this.isBurning()) {
            --this.burnTime;
        }
        if (this.isWatering()) {
            --this.wateringTime;
        }

        if (!this.world.isRemote) {
            ItemStack fuel = this.fuelHandler.getStackInSlot(0);
            ItemStack dryInput = this.inputHandler.getStackInSlot(0);
            ItemStack wetInput = this.inputHandler.getStackInSlot(1);

            // Burning Section
            if (this.isBurning() || !fuel.isEmpty() && !dryInput.isEmpty()) {
                if (!this.isBurning() && this.canDry()) {
                    this.burnTime = getItemBurnTime(fuel);
                    this.currentBurnTime = this.burnTime;

                    if (this.isBurning()) {
                        dryingFlag = true;

                        if (!fuel.isEmpty()) {
                            Item item = fuel.getItem();
                            fuel.shrink(1);

                            if (fuel.isEmpty()) {
                                ItemStack containerItem = item.getContainerItem(fuel);
                                this.fuelHandler.setStackInSlot(0, containerItem);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canDry()) {
                    ++this.dryTime;

                    if (this.dryTime == this.totalDryTime) {
                        this.dryTime = 0;
                        this.totalDryTime = 200;
                        this.dryItem();
                        dryingFlag = true;
                    }
                }
                else {
                    this.dryTime = 0;
                }

            }
            else if (!this.isBurning() && this.dryTime > 0) {
                this.dryTime = MathHelper.clamp(this.dryTime - 2, 0, this.totalDryTime);
            }

            // Wetting Section
            if (this.isWatering() || this.waterSize >= 1000 && !wetInput.isEmpty()) {
                if (!this.isWatering() && this.canWet()) {
                    this.wateringTime = 1000;
                    this.currentWateringTime = this.wateringTime;

                    if (this.isWatering()) {
                        wettingFlag = true;

                        if (this.waterSize >= 1000) {
                            this.waterSize -= 1000;
                        }
                    }
                }

                if (this.isWatering() && this.canWet()) {
                    ++this.wetTime;

                    if (this.wetTime == this.totalWetTime) {
                        this.wetTime = 0;
                        this.totalWetTime = 1000;
                        this.wetItem();
                        wettingFlag = true;
                    }
                }
                else {
                    this.wetTime = 0;
                }
            }
            else if (!this.isWatering() && this.wetTime > 0) {
                this.wetTime = MathHelper.clamp(this.wetTime - 2, 0, this.totalWetTime);
            }

            if (flagBurning != this.isBurning()) {
                dryingFlag = true;
                setState(this.isBurning(), this.world, this.pos);
            }
        }

        if (dryingFlag || wettingFlag) {
            this.markDirty();
        }
    }

    @Nullable
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.UP) {
                return (T)this.inputHandler;
            } else if (facing == EnumFacing.DOWN) {
                return (T)this.outputHandler;
            } else {
                return (T)this.fuelHandler;
            }
        }
        return super.getCapability(capability, facing);
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return this.customOrDefaultName("Potato Drier");
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.fuelHandler.deserializeNBT(compound.getCompoundTag("InventoryFuel"));
        this.inputHandler.deserializeNBT(compound.getCompoundTag("InventoryInput"));
        this.outputHandler.deserializeNBT(compound.getCompoundTag("InventoryOutput"));
        this.waterSize = compound.getInteger("WaterVolume");
        this.burnTime = compound.getInteger("BurnTime");
        this.wateringTime = compound.getInteger("WateringTime");
        this.dryTime = compound.getInteger("DryTime");
        this.wetTime = compound.getInteger("WetTime");
        this.totalDryTime = compound.getInteger("DryTimeTotal");
        this.totalWetTime = compound.getInteger("WetTimeTotal");
        this.currentBurnTime = getItemBurnTime(this.fuelHandler.getStackInSlot(0));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("InventoryFuel", this.fuelHandler.serializeNBT());
        compound.setTag("InventoryInput", this.inputHandler.serializeNBT());
        compound.setTag("InventoryOutput", this.outputHandler.serializeNBT());
        compound.setInteger("WaterVolume", (short)this.waterSize);
        compound.setInteger("BurnTime", (short)this.burnTime);
        compound.setInteger("WateringTime", (short)this.wateringTime);
        compound.setInteger("DryTime", (short)this.dryTime);
        compound.setInteger("WetTime", (short)this.wetTime);
        compound.setInteger("DryTimeTotal", (short)this.totalDryTime);
        compound.setInteger("WetTimeTotal", (short)this.totalWetTime);

        return compound;
    }

    @Override
    public int getField(String id) {
        switch(id) {
            case "waterSize":
                return this.waterSize;
            case "burnTime":
                return this.burnTime;
            case "wateringTime":
                return this.wateringTime;
            case "dryTime":
                return this.dryTime;
            case "wetTime":
                return this.wetTime;
            case "currentBurnTime":
                return this.currentBurnTime;
            case "currentWateringTime":
                return this.currentWateringTime;
            case "totalDryTime":
                return this.totalDryTime;
            case "totalWetTime":
                return this.totalWetTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.waterSize = value;
                break;
            case 1:
                this.burnTime = value;
                break;
            case 2:
                this.wateringTime = value;
                break;
            case 3:
                this.dryTime = value;
                break;
            case 4:
                this.wetTime = value;
                break;
            case 5:
                this.currentBurnTime = value;
                break;
            case 6:
                this.currentWateringTime = value;
                break;
            case 7:
                this.totalDryTime = value;
                break;
            case 8:
                this.totalWetTime = value;
                break;
        }
    }

    // Custom Method

    public int getMaxWaterSize() {
        return 4000;
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    public boolean isWatering() {
        return this.wateringTime > 0;
    }

    private boolean canDry() {

        ItemStack fuel = this.fuelHandler.getStackInSlot(0);
        ItemStack dryInput = this.inputHandler.getStackInSlot(0);
        ItemStack dryOutput = this.outputHandler.getStackInSlot(0);

        if (fuel.isEmpty() && dryInput.isEmpty()) {
            return false;

        } else {
            ItemStack result = PotatoDrierRecipes.INSTANCE.getDryResult(dryInput.getItem());

            if (result.isEmpty()) {
                return false;

            } else {
                if (dryOutput.isEmpty()) {
                    return true;

                } else {
                    if (dryOutput.isItemEqual(result)) {
                        int res = dryOutput.getCount() + result.getCount();
                        return res <= dryOutput.getMaxStackSize();

                    } else {
                        return false;
                    }
                }
            }
        }
    }

    private boolean canWet() {

        ItemStack wetInput = this.inputHandler.getStackInSlot(1);
        ItemStack wetOutput = this.outputHandler.getStackInSlot(1);

        if (this.waterSize < 1000 && wetInput.isEmpty()) {
            return false;

        } else {
            ItemStack result = PotatoDrierRecipes.INSTANCE.getWetResult(wetInput.getItem());

            if (result.isEmpty()) {
                return false;

            } else {
                if (wetOutput.isEmpty()) {
                    return true;

                } else {
                    if (wetOutput.isItemEqual(result)) {
                        int res = wetOutput.getCount() + result.getCount();
                        return res <= wetOutput.getMaxStackSize();

                    } else {
                        return false;
                    }
                }
            }
        }
    }

    private void dryItem() {
        if (this.canDry()) {
            ItemStack dryInput = this.inputHandler.getStackInSlot(0);
            ItemStack dryOutput = this.outputHandler.getStackInSlot(0);
            ItemStack result = PotatoDrierRecipes.INSTANCE.getDryResult(dryInput.getItem());

            if (dryOutput.isEmpty()) {
                this.outputHandler.setStackInSlot(0, result.copy());
            }
            else if (dryOutput.getItem() == result.getItem()) {
                dryOutput.grow(result.getCount());
            }

            this.waterSize += getWaterValue(dryInput);
            if (this.waterSize > this.getMaxWaterSize()) {
                this.waterSize = this.getMaxWaterSize();
            }
            dryInput.shrink(1);
        }
    }

    private void wetItem() {
        if (this.canWet()) {
            ItemStack wetInput = this.inputHandler.getStackInSlot(1);
            ItemStack wetOutput = this.outputHandler.getStackInSlot(1);
            ItemStack result = PotatoDrierRecipes.INSTANCE.getWetResult(wetInput.getItem());

            if (wetOutput.isEmpty()) {
                this.outputHandler.setStackInSlot(1, result.copy());
            }
            else if (wetOutput.getItem() == result.getItem()) {
                wetOutput.grow(result.getCount());
            }

            wetInput.shrink(1);
        }
    }

    // Static Method

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(TileEntityPotatoDrier te) {
        return te.getField("burnTime") > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isWatering(TileEntityPotatoDrier te) {
        return te.getField("wateringTime") > 0;
    }

    private static int getWaterValue(ItemStack stack) {
        if (!stack.isEmpty()) {
            Item item = stack.getItem();

            if (item == Items.POTATO || item == PotatoItems.SWEET_POTATO) return 100;
            if (item == PotatoItems.WET_POTATO) return 200;
            if (item == PotatoItems.SUPER_WET_POTATO) return 400;
            if (item == Items.WATER_BUCKET || item == PotatoItems.WATER_POTATO) return 800;
        }
        return 0;
    }

    private static int getItemBurnTime(ItemStack fuel) {

        if (fuel.isEmpty()) {
            return 0;
        } else {
            Item item = fuel.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.WOODEN_SLAB) return 150;
                if (block.getDefaultState().getMaterial() == Material.WOOD) return 300;
                if (block == Blocks.COAL_BLOCK) return 16000;
            }

            if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())) return 200;
            if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())) return 200;
            if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())) return 200;
            if (item == Items.STICK) return 100;
            if (item == Items.COAL) return 1600;
            if (item == Items.LAVA_BUCKET) return 20000;
            if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
            if (item == Items.BLAZE_ROD) return 2400;

            return ForgeEventFactory.getItemBurnTime(fuel);
        }
    }

    private static void setState(boolean active, World worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        PotatoDrier.keepInventory = true;

        if (active) {
            worldIn.setBlockState(pos, PotatoBlocks.LIT_POTATO_DRIER.getDefaultState().withProperty(PotatoDrier.FACING, iblockstate.getValue(PotatoDrier.FACING)), 3);
            worldIn.setBlockState(pos, PotatoBlocks.LIT_POTATO_DRIER.getDefaultState().withProperty(PotatoDrier.FACING, iblockstate.getValue(PotatoDrier.FACING)), 3);
        }
        else {
            worldIn.setBlockState(pos, PotatoBlocks.POTATO_DRIER.getDefaultState().withProperty(PotatoDrier.FACING, iblockstate.getValue(PotatoDrier.FACING)), 3);
            worldIn.setBlockState(pos, PotatoBlocks.POTATO_DRIER.getDefaultState().withProperty(PotatoDrier.FACING, iblockstate.getValue(PotatoDrier.FACING)), 3);
        }

        PotatoDrier.keepInventory = false;

        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    public static boolean isItemFuel(ItemStack fuel) {
        return getItemBurnTime(fuel) > 0;
    }
}
