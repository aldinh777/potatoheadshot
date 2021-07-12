package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.content.capability.item.RandomPlacerCapability;
import aldinh777.potatoheadshot.content.inventory.InventoryRandomPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RandomPlacer extends PotatoItem {

    private int randomSlot;

    public RandomPlacer(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);
        IBlockState target = worldIn.getBlockState(pos.offset(facing));
        if (!state.getBlock().hasTileEntity(state)) {
            if (state.getBlock() != Blocks.AIR && target.getBlock() == Blocks.AIR && player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
                if (itemstack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)) {
                    IItemHandler handler = itemstack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
                    if (handler instanceof InventoryRandomPlacer) {
                        InventoryRandomPlacer placerHandler = (InventoryRandomPlacer) handler;
                        if (worldIn.isRemote) {
                            randomSlot = placerHandler.getRandomAvailableSlot();
                        }
                        if (randomSlot == -1) {
                            return EnumActionResult.FAIL;
                        }
                        ItemStack randomStack = placerHandler.getStackInSlot(randomSlot);
                        ItemBlock itemBlock = (ItemBlock) randomStack.getItem();
                        Block block = itemBlock.getBlock();
                        int meta = randomStack.getMetadata();
                        if (!worldIn.isRemote) {
                            worldIn.setBlockState(pos.offset(facing), block.getStateForPlacement(worldIn, pos.offset(facing), facing, hitX, hitY, hitZ, meta, player, hand));
                            randomStack.shrink(1);
                        }
                        IBlockState blockState = worldIn.getBlockState(pos.offset(facing));
                        SoundEvent soundEvent = block.getSoundType(blockState, worldIn, pos.offset(facing), player).getPlaceSound();
                        worldIn.playSound(player, pos.offset(facing), soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        } else {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity != null) {
                if (tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing) && itemstack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)) {
                    IItemHandler itemHandlerTile = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                    IItemHandler itemHandlerStack = itemstack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
                    if (itemHandlerTile != null && itemHandlerStack instanceof InventoryRandomPlacer) {
                        for (int i = 0; i < itemHandlerStack.getSlots(); i++) {
                            for (int j = 0; j < itemHandlerTile.getSlots(); j++) {
                                ItemStack extracted = itemHandlerTile.extractItem(j, itemHandlerTile.getSlotLimit(j), false);
                                ItemStack result = itemHandlerStack.insertItem(i, extracted, false);
                                itemHandlerTile.insertItem(j, result, false);
                            }
                        }
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        }
        return EnumActionResult.FAIL;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new RandomPlacerCapability(stack);
    }
}
