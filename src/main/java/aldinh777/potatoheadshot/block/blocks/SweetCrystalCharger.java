package aldinh777.potatoheadshot.block.blocks;

import aldinh777.potatoheadshot.block.tileentities.TileEntitySweetCrystalCharger;
import aldinh777.potatoheadshot.util.BlockType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import aldinh777.potatoheadshot.util.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class SweetCrystalCharger extends PotatoMachine {

	public SweetCrystalCharger(String name, BlockType blockType) {
		super(name, blockType, Constants.CRYSTAL_CHARGER);
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
		return new TileEntitySweetCrystalCharger();
	}

	@Override
	public void breakBlock(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		TileEntitySweetCrystalCharger tileEntity = (TileEntitySweetCrystalCharger)worldIn.getTileEntity(pos);
		if (tileEntity != null) {
			IItemHandler inputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
			IItemHandler outputHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
			
			if (inputHandler != null) {
				spawnAsEntity(worldIn, pos, inputHandler.getStackInSlot(0));
			}
			if (outputHandler != null) {
				spawnAsEntity(worldIn, pos, outputHandler.getStackInSlot(0));
			}
		} 
		super.breakBlock(worldIn, pos, state);
	}
}
