package aldinh777.potatoheadshot.backup.blocks.machines;

import aldinh777.potatoheadshot.backup.tileentities.TileEntitySweetFreezer;
import aldinh777.potatoheadshot.other.util.BlockType;
import aldinh777.potatoheadshot.other.util.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SweetFreezer extends PotatoMachine {

    public SweetFreezer(String name, BlockType blockType) {
        super(name, blockType, Constants.FREEZER);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntitySweetFreezer();
    }

    @Override
    public void breakBlock(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (!worldIn.isRemote) {
            TileEntitySweetFreezer tileEntity = (TileEntitySweetFreezer) worldIn.getTileEntity(pos);
            if (tileEntity != null) {
                IItemHandler saltHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                IItemHandler inputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
                IItemHandler outputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

                if (saltHandler != null) {
                    spawnAsEntity(worldIn, pos, saltHandler.getStackInSlot(0));
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
