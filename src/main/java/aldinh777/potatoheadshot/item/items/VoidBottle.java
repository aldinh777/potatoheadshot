package aldinh777.potatoheadshot.item.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class VoidBottle extends PotatoItem {

    public static int maxVoid = 1000000;

    public VoidBottle(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add("Void : " + getVoidSize(stack) + "/" + maxVoid);
    }

    @Override
    public boolean showDurabilityBar(@Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(@Nonnull ItemStack stack) {
        return 1.0 - (double) getVoidSize(stack) / maxVoid;
    }

    public static int getVoidSize(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();

        if (compound == null) {
            compound = new NBTTagCompound();
            stack.setTagCompound(compound);
        }

        if (!compound.hasKey("Void")) {
            compound.setInteger("Void", 0);
        }

        return compound.getInteger("Void");
    }

    public static void setVoidSize(ItemStack stack, int value) {
        NBTTagCompound compound = stack.getTagCompound();

        if (compound == null) {
            compound = new NBTTagCompound();
            stack.setTagCompound(compound);
        }

        if (value > maxVoid) {
            value = maxVoid;
        }

        compound.setInteger("Void", value);
    }
}
