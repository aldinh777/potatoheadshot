package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.content.capability.item.SwapArmorCapability;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ArmorSwap extends PotatoItem {

    public ArmorSwap(String name) {
        super(name);
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        IItemHandler itemHandler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        if (itemHandler instanceof ItemStackHandler) {
            for (int i = 3; i >= 0; i--) {
                ItemStack itemStack = itemHandler.getStackInSlot(i);
                String display = itemStack.isEmpty() ? "EMPTY" : itemStack.getDisplayName();
                tooltip.add("- " + display);
            }
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        IItemHandler itemHandler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        if (itemHandler instanceof ItemStackHandler) {
            ItemStackHandler stackHandler = (ItemStackHandler) itemHandler;
            for (int i = 0; i < 4; i++) {
                ItemStack armor = stackHandler.getStackInSlot(i);
                stackHandler.setStackInSlot(i, playerIn.inventory.armorInventory.get(i));
                playerIn.inventory.armorInventory.set(i, armor);
                playEquipSound(playerIn, armor);
            }
        }

        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    private void playEquipSound(EntityPlayer player, ItemStack stack) {
        if (!stack.isEmpty()) {
            SoundEvent soundevent = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
            Item item = stack.getItem();

            if (item instanceof ItemArmor) {
                soundevent = ((ItemArmor)item).getArmorMaterial().getSoundEvent();
            } else if (item == Items.ELYTRA) {
                soundevent = SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA;
            }

            player.playSound(soundevent, 1.0F, 1.0F);
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new SwapArmorCapability(stack);
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
