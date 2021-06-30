package aldinh777.potatoheadshot.content.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UpgradeDrierWater extends PotatoItem {

    public UpgradeDrierWater(String name) {
        super(name);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new WaterCapability(stack);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        FluidHandlerItemStack water = (FluidHandlerItemStack) stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, EnumFacing.UP);
        if (water != null) {
            FluidStack fluidStack = water.getFluid();
            if (fluidStack != null) {
                return fluidStack.amount > 0 ? 1 : super.getItemStackLimit(stack);
            }
        }
        return super.getItemStackLimit(stack);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        FluidHandlerItemStack water = (FluidHandlerItemStack) stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, EnumFacing.UP);
        if (water != null) {
            FluidStack fluidStack = water.getFluid();
            if (fluidStack != null) {
                return fluidStack.amount > 0;
            }
        }
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        WaterCapability water = (WaterCapability) stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, EnumFacing.UP);
        if (water != null) {
            FluidStack fluidStack = water.getFluid();
            if (fluidStack != null) {
                return 1.0 - (double) fluidStack.amount / (double) water.getMaxVolume();
            }
        }
        return super.getDurabilityForDisplay(stack);
    }

    public static class WaterCapability extends FluidHandlerItemStack {

        public WaterCapability(ItemStack container) {
            super(container, 8000);
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return fluid.getFluid() == FluidRegistry.WATER;
        }

        public int getMaxVolume() {
            return 8000;
        }
    }
}
