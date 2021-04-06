package aldinh777.potatoheadshot.block.blocks.machines;

import aldinh777.potatoheadshot.block.tileentities.TileEntityMagicDrier;
import aldinh777.potatoheadshot.block.tileentities.TileEntityPotatoDrier;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.util.BlockType;
import aldinh777.potatoheadshot.util.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class MagicDrier extends PotatoDrier {

    public MagicDrier(String name, BlockType blockType) {
        super(name, blockType, Constants.MAGIC_DRIER);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityMagicDrier();
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return Item.getItemFromBlock(PotatoBlocks.MAGIC_DRIER);
    }

    @Override
    public void breakBlock(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te != null) {
                ItemStackHandler inputHandler = (ItemStackHandler) te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
                ItemStackHandler outputHandler = (ItemStackHandler) te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
                ItemStackHandler[] handlers = new ItemStackHandler[]{inputHandler, outputHandler};

                for (ItemStackHandler handler : handlers) {
                    for (int i = 0; i < Objects.requireNonNull(handler).getSlots(); ++i) {
                        ItemStack itemStack = handler.getStackInSlot(i);

                        if (!itemStack.isEmpty()) {
                            spawnAsEntity(worldIn, pos, itemStack);
                        }
                    }
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
    }
}
