package aldinh777.potatoheadshot.block.machines;

import aldinh777.potatoheadshot.block.tileentities.TileEntityManaExtractor;
import aldinh777.potatoheadshot.util.BlockType;
import aldinh777.potatoheadshot.util.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManaExtractor extends ManaCollector {

    public ManaExtractor(String name, BlockType blockType) {
        super(name, blockType, Constants.EXTRACTOR);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityManaExtractor();
    }
}
