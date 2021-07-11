package aldinh777.potatoheadshot.content.tileentities;

import aldinh777.potatoheadshot.content.blocks.CorruptedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class TileEntityCorrupted extends TileEntity implements ITickable {

    int tick = 0;

    @Override
    public void update() {
        tick++;
        if (tick > 10) {
            spreadCorruption();
            tick = 0;
        }
    }

    @Override
    public boolean shouldRefresh(@Nonnull World world, @Nonnull BlockPos pos, IBlockState state, IBlockState newState) {
        return state.getBlock() != newState.getBlock();
    }

    public void spreadCorruption() {
        IBlockState state = world.getBlockState(pos);
        int stage = state.getValue(CorruptedBlock.STAGE);
        if (stage < 5) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos nextPos = pos.offset(facing);
                IBlockState nextState = world.getBlockState(nextPos);
                if (nextState.getBlock() != Blocks.AIR && !(nextState.getBlock() instanceof CorruptedBlock)) {
                    if (nextState.getBlockHardness(world, nextPos) != -1) {
                        Block block = nextState.getBlock();
                        SoundType soundType = block.getSoundType(nextState, world, nextPos, null);
                        world.playSound(nextPos.getX(), nextPos.getY(), nextPos.getZ(), soundType.getBreakSound(), SoundCategory.BLOCKS, 1.0f, 1.0f, false);

                        if (!world.isRemote) {
                            NonNullList<ItemStack> drops = NonNullList.create();
                            block.getDrops(drops, world, nextPos, nextState, 0);

                            for (ItemStack drop : drops) {
                                world.spawnEntity(new EntityItem(world, nextPos.getX(), nextPos.getY(), nextPos.getZ(), drop));
                            }

                            block.breakBlock(world, nextPos, nextState);
                            world.setBlockState(nextPos, state.withProperty(CorruptedBlock.STAGE, stage + 1));
                        }
                    }
                }
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        } else {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }
}
