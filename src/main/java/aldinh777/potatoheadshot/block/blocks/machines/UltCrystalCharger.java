package aldinh777.potatoheadshot.block.blocks.machines;

import aldinh777.potatoheadshot.block.tileentities.TileEntityUltCrystalCharger;
import aldinh777.potatoheadshot.util.BlockType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class UltCrystalCharger extends SweetCrystalCharger {

    public UltCrystalCharger(String name, BlockType blockType) {
        super(name, blockType);
        this.setHardness(50.0f);
        this.setResistance(6000.0f);
    }

    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityUltCrystalCharger();
    }
}
