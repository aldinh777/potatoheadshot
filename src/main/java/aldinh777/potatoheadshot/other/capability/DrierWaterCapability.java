package aldinh777.potatoheadshot.other.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class DrierWaterCapability implements IFluidHandler {

    public int water = 0;
    public int capacity = 8000;

    public FluidStack getFluid() {
        return new FluidStack(FluidRegistry.WATER, water);
    }

    public void setFluid(FluidStack fluid) {
        this.water = fluid.amount;
    }

    public boolean canFillFluidType(FluidStack fluid) {
        return fluid.getFluid() == FluidRegistry.WATER;
    }

    public boolean canDrainFluidType(FluidStack fluid) {
        return fluid.getFluid() == FluidRegistry.WATER;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new FluidTankProperties[] { new FluidTankProperties(getFluid(), capacity) };
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource == null || resource.amount <= 0 || !canFillFluidType(resource)) {
            return 0;
        }

        FluidStack contained = getFluid();
        if (contained == null) {
            int fillAmount = Math.min(capacity, resource.amount);

            if (doFill) {
                FluidStack filled = resource.copy();
                filled.amount = fillAmount;
                setFluid(filled);
            }

            return fillAmount;
        } else {
            if (contained.isFluidEqual(resource)) {
                int fillAmount = Math.min(capacity - contained.amount, resource.amount);

                if (doFill && fillAmount > 0) {
                    contained.amount += fillAmount;
                    setFluid(contained);
                }

                return fillAmount;
            }

            return 0;
        }
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null || resource.amount <= 0 || !resource.isFluidEqual(getFluid())) {
            return null;
        }
        return drain(resource.amount, doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (maxDrain <= 0) {
            return null;
        }

        FluidStack contained = getFluid();
        if (contained == null || contained.amount <= 0 || !canDrainFluidType(contained)) {
            return null;
        }

        final int drainAmount = Math.min(contained.amount, maxDrain);

        FluidStack drained = contained.copy();
        drained.amount = drainAmount;

        if (doDrain) {
            contained.amount -= drainAmount;
            setFluid(contained);
        }

        return drained;
    }

    public void readFromNBT(NBTTagCompound compound) {
        water = compound.getInteger("Water");
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("Water", water);
    }
}
