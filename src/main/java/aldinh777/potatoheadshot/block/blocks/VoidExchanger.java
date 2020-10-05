package aldinh777.potatoheadshot.block.blocks;

import aldinh777.potatoheadshot.block.tileentities.TileEntityVoidExchanger;
import aldinh777.potatoheadshot.util.BlockType;
import aldinh777.potatoheadshot.util.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class VoidExchanger extends PotatoMachine {

    public VoidExchanger(String name, BlockType blockType) {
        super(name, blockType, Constants.VOID_EXCHANGER);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityVoidExchanger();
    }

    @Override
    public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (!worldIn.isRemote) {
            TileEntityVoidExchanger tileEntity = (TileEntityVoidExchanger) worldIn.getTileEntity(pos);
            if (tileEntity != null) {
                IItemHandler bottleHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
                IItemHandler inputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                IItemHandler outputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

                if (bottleHandler != null) {
                    spawnAsEntity(worldIn, pos, bottleHandler.getStackInSlot(0));
                }
                if (inputHandler != null) {
                    spawnAsEntity(worldIn, pos, inputHandler.getStackInSlot(0));
                }
                if (outputHandler != null) {
                    spawnAsEntity(worldIn, pos, outputHandler.getStackInSlot(0));
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
    }
}
