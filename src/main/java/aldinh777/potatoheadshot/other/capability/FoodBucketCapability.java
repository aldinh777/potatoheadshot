package aldinh777.potatoheadshot.other.capability;

import aldinh777.potatoheadshot.other.lists.PotatoItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FoodBucketCapability extends FluidBucketWrapper {
    public FoodBucketCapability(@Nonnull ItemStack container) {
        super(container);
    }

    @Nullable
    @Override
    public FluidStack getFluid() {
        Item item = container.getItem();
        if (item == PotatoItems.WATER_POTATO) {
            return new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME);
        }
        else if (item == PotatoItems.LAVA_POTATO) {
            return new FluidStack(FluidRegistry.LAVA, Fluid.BUCKET_VOLUME);
        }

        return null;
    }

    @Override
    protected void setFluid(@Nullable FluidStack fluidStack) {
        container = ItemStack.EMPTY;
    }
}
