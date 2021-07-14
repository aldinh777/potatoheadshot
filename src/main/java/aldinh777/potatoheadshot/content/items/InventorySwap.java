package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.content.capability.item.SwapInventoryCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InventorySwap extends PotatoItem {

    public InventorySwap(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            IItemHandler itemHandler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
            if (itemHandler instanceof ItemStackHandler) {
                ItemStackHandler stackHandler = (ItemStackHandler) itemHandler;
                for (int i = 0; i < 27; i++) {
                    ItemStack temp = stackHandler.getStackInSlot(i);
                    stackHandler.setStackInSlot(i, playerIn.inventory.mainInventory.get(i + 9));
                    playerIn.inventory.mainInventory.set(i + 9, temp);
                }
                return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
            }
        }
        playerIn.playSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 1.0f, 1.0f);
        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new SwapInventoryCapability(stack);
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(@Nonnull ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();

        if (compound == null) {
            compound = new NBTTagCompound();
        }

        if (stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)) {
            IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
            if (handler instanceof ItemStackHandler) {
                compound.setTag("Inventory", ((ItemStackHandler) handler).serializeNBT());
            }
        }

        return compound;
    }

    @Override
    public void readNBTShareTag(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        super.readNBTShareTag(stack, nbt);
        if (nbt != null) {
            if (stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)) {
                IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
                if (handler instanceof ItemStackHandler) {
                    ((ItemStackHandler) handler).deserializeNBT(nbt.getCompoundTag("Inventory"));
                }
            }
        }
    }
}
