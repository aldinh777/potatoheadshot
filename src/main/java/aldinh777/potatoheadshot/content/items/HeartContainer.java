package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.content.capability.CapabilityBlood;
import aldinh777.potatoheadshot.content.capability.IBloodStorage;
import aldinh777.potatoheadshot.content.capability.PotatoBloodStorage;
import aldinh777.potatoheadshot.content.capability.item.HeartContainerCapability;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class HeartContainer extends PotatoItem {

    public HeartContainer(String name) {
        super(name);
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        IBloodStorage bloodStorage = stack.getCapability(CapabilityBlood.BLOOD, EnumFacing.UP);
        if (bloodStorage instanceof PotatoBloodStorage) {
            tooltip.add("Heart : " + (int)bloodStorage.getBloodQuantity() + "/" + bloodStorage.getMaxQuantity());
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean showDurabilityBar(@Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(@Nonnull ItemStack stack) {
        IBloodStorage bloodStorage = stack.getCapability(CapabilityBlood.BLOOD, EnumFacing.UP);
        if (bloodStorage instanceof PotatoBloodStorage) {
            return 1.0 - (double) bloodStorage.getBloodQuantity() / (double) bloodStorage.getMaxQuantity();
        }
        return 0;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            IBloodStorage bloodStorage = stack.getCapability(CapabilityBlood.BLOOD, EnumFacing.UP);
            if (bloodStorage instanceof PotatoBloodStorage) {
                if (playerIn.getHealth() > 1 && bloodStorage.getBloodQuantity() < bloodStorage.getMaxQuantity()) {
                    float totalHeart = playerIn.getHealth() - 1;
                    float extractedHeart = Math.min(totalHeart, bloodStorage.getMaxQuantity() - bloodStorage.getBloodQuantity());
                    playerIn.attackEntityFrom(new DamageSource("heart_extraction"), extractedHeart);
                    bloodStorage.increaseBlood(extractedHeart);
                    return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
                }
            }
        }
        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new HeartContainerCapability(stack);
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(@Nonnull ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();

        if (compound == null) {
            compound = new NBTTagCompound();
        }

        IBloodStorage bloodStorage = stack.getCapability(CapabilityBlood.BLOOD, EnumFacing.UP);
        NBTTagCompound bloodCompound = new NBTTagCompound();

        if (bloodStorage instanceof PotatoBloodStorage) {
            ((PotatoBloodStorage) bloodStorage).writeToNBT(bloodCompound);
            compound.setTag("Heart", bloodCompound);
        }

        return compound;
    }

    @Override
    public void readNBTShareTag(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        super.readNBTShareTag(stack, nbt);
        if (nbt != null) {
            if (stack.hasCapability(CapabilityBlood.BLOOD, EnumFacing.UP)) {
                IBloodStorage storage = stack.getCapability(CapabilityBlood.BLOOD, EnumFacing.UP);
                if (storage instanceof PotatoBloodStorage) {
                    ((PotatoBloodStorage) storage).readFromNBT(nbt.getCompoundTag("Heart"));
                }
            }
        }
    }

    public static ItemStack findHeartContainer(EntityPlayer player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (stack.hasCapability(CapabilityBlood.BLOOD, EnumFacing.UP)) {
                IBloodStorage bloodStorage = stack.getCapability(CapabilityBlood.BLOOD, EnumFacing.UP);
                if (bloodStorage instanceof PotatoBloodStorage) {
                    if (bloodStorage.getBloodQuantity() > 0) {
                        return stack;
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }
}
