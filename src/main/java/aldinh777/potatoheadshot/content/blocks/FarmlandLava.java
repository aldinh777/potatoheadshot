package aldinh777.potatoheadshot.content.blocks;

import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.content.items.LavaPotatoSeed;
import aldinh777.potatoheadshot.content.items.PotatoItemBlock;
import aldinh777.potatoheadshot.common.lists.PotatoBlocks;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nonnull;
import java.util.Random;

public class FarmlandLava extends BlockFarmland {

    public FarmlandLava(String name) {
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHardness(0.5f);
        this.setLightLevel(0.50f);
        if (ConfigHandler.LAVA_POTATO) {
            PotatoBlocks.LISTS.add(this);
            PotatoItems.LISTS.add(new PotatoItemBlock(this));
        }
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, IBlockState state, @Nonnull Random rand) {
        int i = state.getValue(MOISTURE);

        if (!this.hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.up())) {
            if (i > 0) {
                worldIn.setBlockState(pos, state.withProperty(MOISTURE, i - 1), 2);
            } else if (!this.hasCrops(worldIn, pos)) {
                turnToDirt(worldIn, pos);
            }
        } else if (i < 7) {
            worldIn.setBlockState(pos, state.withProperty(MOISTURE, 7), 2);
        }
    }

    private boolean hasCrops(World worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        return block instanceof net.minecraftforge.common.IPlantable && canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable)block);
    }

    private boolean hasWater(World worldIn, BlockPos pos) {
        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
            if (worldIn.getBlockState(blockpos$mutableblockpos).getMaterial() == Material.LAVA) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canSustainPlant(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing direction, @Nonnull IPlantable plantable) {
        return plantable instanceof LavaPotatoSeed;
    }
}
