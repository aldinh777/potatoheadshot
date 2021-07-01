package aldinh777.potatoheadshot.content.capability;

import aldinh777.potatoheadshot.other.lists.PotatoItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BucketCapability extends FluidBucketWrapper {
        public BucketCapability(@Nonnull ItemStack container) {
            super(container);
        }

        @Nullable
        @Override
        public FluidStack getFluid() {
            Item item = container.getItem();
            if (item == PotatoItems.SWEET_WATER_BUCKET) {
                return new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME);
            }
            else if (item == PotatoItems.SWEET_LAVA_BUCKET) {
                return new FluidStack(FluidRegistry.LAVA, Fluid.BUCKET_VOLUME);
            }
            else if (item == PotatoItems.SWEET_MILK_BUCKET) {
                return FluidRegistry.getFluidStack("milk", Fluid.BUCKET_VOLUME);
            }

            return null;
        }

        @Override
        protected void setFluid(@Nullable FluidStack fluidStack) {
            if (fluidStack == null) {
                container = new ItemStack(PotatoItems.SWEET_EMPTY_BUCKET);
            } else {
                Fluid fluid = fluidStack.getFluid();

                if (fluidStack.tag == null || fluidStack.tag.hasNoTags()) {
                    if (fluid == FluidRegistry.WATER) {
                        container = new ItemStack(PotatoItems.SWEET_WATER_BUCKET);
                    }
                    else if (fluid == FluidRegistry.LAVA) {
                        container = new ItemStack(PotatoItems.SWEET_LAVA_BUCKET);
                    }
                    else if (fluid.getName().equals("milk")) {
                        container = new ItemStack(PotatoItems.SWEET_MILK_BUCKET);
                    }
                    else {
                        container = ItemStack.EMPTY;
                    }
                }
            }
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            if (container.getCount() != 1
                    || resource == null
                    || resource.amount < Fluid.BUCKET_VOLUME
                    || container.getItem() == PotatoItems.SWEET_MILK_BUCKET
                    || getFluid() != null
                    || !canFillFluidType(resource)) {
                return 0;
            }

            if (doFill) {
                setFluid(resource);
            }

            return Fluid.BUCKET_VOLUME;
        }

        @Override
        public boolean canFillFluidType(FluidStack fluidStack) {
            Fluid fluid = fluidStack.getFluid();
            return fluid == FluidRegistry.WATER || fluid == FluidRegistry.LAVA || fluid.getName().equals("milk");
        }
    }
