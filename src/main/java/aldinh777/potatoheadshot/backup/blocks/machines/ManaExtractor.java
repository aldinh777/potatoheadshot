package aldinh777.potatoheadshot.backup.blocks.machines;

import aldinh777.potatoheadshot.backup.tileentities.TileEntityManaExtractor;
import aldinh777.potatoheadshot.other.util.BlockType;
import aldinh777.potatoheadshot.other.util.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManaExtractor extends ManaCollector {

    public ManaExtractor(String name, BlockType blockType) {
        super(name, blockType, Constants.EXTRACTOR);
        this.setResistance(6000);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityManaExtractor();
    }
}
