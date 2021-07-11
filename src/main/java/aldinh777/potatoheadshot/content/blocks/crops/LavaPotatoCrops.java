package aldinh777.potatoheadshot.content.blocks.crops;

import aldinh777.potatoheadshot.common.lists.PotatoBlocks;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nonnull;

public class LavaPotatoCrops extends GlowingPotatoCrops {

    public LavaPotatoCrops(String name) {
        super(name);
    }

    @Nonnull
    @Override
    protected Item getCrop() {
        return PotatoItems.LAVA_POTATO;
    }

    @Nonnull
    @Override
    protected Item getSeed() {
        return PotatoItems.LAVA_POTATO_SEED;
    }

    @Override
    public boolean isPlaceable(IBlockState state) {
        return state.getBlock() == PotatoBlocks.LAVA_FARMLAND;
    }
}
