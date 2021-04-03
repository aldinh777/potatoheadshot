package aldinh777.potatoheadshot.util;

import aldinh777.potatoheadshot.block.tileentities.IManaStorage;
import aldinh777.potatoheadshot.compat.botania.BotaniaCompat;
import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.ForgeEventFactory;

public interface ItemStickHelper {

    static void debugBlock(RayTraceResult raytraceresult, World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (itemstack.getItem() != PotatoItems.POTATO_STICK && itemstack.getItem() != PotatoItems.FRIED_FRIES) {
            return;
        }

        ActionResult<ItemStack> ret = ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
        if (ret != null) return;

        if (raytraceresult != null) {
            if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos pos = raytraceresult.getBlockPos();

                IBlockState blockState = worldIn.getBlockState(pos);
                playerIn.sendMessage(new TextComponentString("Block : " + blockState.getBlock()));

                TileEntity tile = worldIn.getTileEntity(pos);
                if (tile != null) {
                    if (tile.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)) {
                        IEnergyStorage energy = tile.getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);
                        if (energy != null) {
                            int stored = energy.getEnergyStored();
                            playerIn.sendMessage(new TextComponentString("Energy : " + stored));
                        }
                    }

                    if (BotaniaCompat.isBotaniaAvailable()) {
                        if (BotaniaCompat.isInstanceOfManaPool(tile)) {
                            int stored = BotaniaCompat.getManaSize(tile);
                            playerIn.sendMessage(new TextComponentString("Mana : " + stored));
                        }
                    }

                    if (tile instanceof IManaStorage) {
                        PotatoManaStorage manaStorage = ((IManaStorage) tile).getManaStorage();
                        int stored = manaStorage.getManaStored();
                        playerIn.sendMessage(new TextComponentString("Mana : " + stored));
                    }
                }
            }
        }
    }
}
