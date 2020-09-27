package aldinh777.potatoheadshot.block.blocks;

import aldinh777.potatoheadshot.block.tileentities.TileEntitySweetCrystalMaker;
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

public class SweetCrystalMaker extends PotatoMachine {

    public SweetCrystalMaker(String name, BlockType blockType) {
        super(name, blockType, Constants.CRYSTAL_MAKER);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntitySweetCrystalMaker();
    }

    @Override
    public void breakBlock(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (!worldIn.isRemote) {
            TileEntitySweetCrystalMaker tileEntity = (TileEntitySweetCrystalMaker) worldIn.getTileEntity(pos);
            if (tileEntity != null) {
                IItemHandler iceHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                IItemHandler inputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
                IItemHandler outputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

                if (iceHandler != null) {
                    spawnAsEntity(worldIn, pos, iceHandler.getStackInSlot(0));
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
