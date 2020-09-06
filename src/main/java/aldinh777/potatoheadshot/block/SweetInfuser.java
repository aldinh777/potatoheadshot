package aldinh777.potatoheadshot.block;

import aldinh777.potatoheadshot.block.tileentities.TileEntitySweetInfuser;
import aldinh777.potatoheadshot.util.BlockType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Objects;

public class SweetInfuser extends PotatoMachine {

    public SweetInfuser(String name, BlockType blockType) {
        super(name, blockType, 6);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntitySweetInfuser();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            TileEntitySweetInfuser te = (TileEntitySweetInfuser) worldIn.getTileEntity(pos);
            if (te != null) {
                ItemStackHandler inputHandler = te.getHandler("input");
                ItemStackHandler middleHandler = te.getHandler("middle");
                ItemStackHandler[] handlers = new ItemStackHandler[]{inputHandler, middleHandler};

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
