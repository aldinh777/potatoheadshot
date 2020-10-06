package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.blocks.QuantumBlock;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class TileEntityQuantumBlock extends TileEntity implements ITickable {

    private int stage = 0;
    private int progress = 0;
    private final static int duration = 20;

    // Override Methods

    @Override
    public void update() {
        if (!this.world.isRemote) {
            List<BlockPos> freePos = this.getFreePos();

            if (freePos.size() > 0 && this.stage == 0) {
                if (this.progress >= duration) {
                    this.spreadQuantum(freePos);

                    this.progress = 0;
                    this.decay();

                } else {
                    this.progress++;
                }
            } else {
                if (this.progress >= duration) {
                    this.progress = 0;
                    this.decay();

                } else {
                    this.progress++;
                }
            }

            this.markDirty();
        }
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.stage = compound.getInteger("Stage");
        this.progress = compound.getInteger("Progress");
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("Stage", this.stage);
        compound.setInteger("Progress", this.progress);
        return compound;
    }

    @Override
    public boolean shouldRefresh(@Nonnull World world, @Nonnull BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    // Custom Methods

    private List<BlockPos> getFreePos() {
        List<BlockPos> freePos = Lists.newArrayList();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    int posX = this.pos.getX() + x;
                    int posY = this.pos.getY() + y;
                    int posZ = this.pos.getZ() + z;
                    BlockPos position = new BlockPos(posX, posY, posZ);

                    IBlockState state = this.world.getBlockState(position);

                    if (state.getBlock().isAir(state, this.world, position)) {
                        freePos.add(position);
                    }
                }
            }
        }

        return freePos;
    }

    private void spreadQuantum(List<BlockPos> freePos) {
        int random = (int) Math.floor(Math.random() * freePos.size());

        this.world.setBlockState(freePos.get(random), PotatoBlocks.QUANTUM_BLOCK.getDefaultState());
    }

    private void decay() {
        IBlockState state = this.world.getBlockState(this.pos);
        this.stage++;

        if (this.stage == 4) {
            this.world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
        } else {
            this.world.setBlockState(this.pos, state.withProperty(QuantumBlock.STAGE, this.stage));
        }
    }
}
