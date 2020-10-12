package aldinh777.potatoheadshot.block.machines;

import aldinh777.potatoheadshot.block.tileentities.TileEntityVoidCharger;
import aldinh777.potatoheadshot.util.BlockType;
import aldinh777.potatoheadshot.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class VoidCharger extends PotatoMachine {

    public VoidCharger(String name, BlockType blockType) {
        super(name, blockType, Constants.VOID_CHARGER);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityVoidCharger();
    }

    @Override
    public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        TileEntityVoidCharger tileEntity = (TileEntityVoidCharger)worldIn.getTileEntity(pos);
        if (tileEntity != null) {
            IItemHandler inputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
            IItemHandler outputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

            if (inputHandler != null) {
                Block.spawnAsEntity(worldIn, pos, inputHandler.getStackInSlot(0));
            }
            if (outputHandler != null) {
                Block.spawnAsEntity(worldIn, pos, outputHandler.getStackInSlot(0));
            }
        }
        super.breakBlock(worldIn, pos, state);
    }
}
