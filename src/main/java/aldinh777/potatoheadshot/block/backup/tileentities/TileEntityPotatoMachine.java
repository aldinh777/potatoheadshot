package aldinh777.potatoheadshot.block.backup.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class TileEntityPotatoMachine extends TileEntity implements ITickable {

    protected String customName;

    protected boolean hasCustomName() {
        return this.customName != null && !this.customName.isEmpty();
    }

    protected ITextComponent customOrDefaultName(String defaultName) {
        if (this.hasCustomName()) {
            return new TextComponentString(this.customName);
        }
        return new TextComponentString(defaultName);
    }

    public abstract int getField(String id);

    public abstract void setField(int id, int value);

    public boolean isUsableByPlayer(EntityPlayer player) {
        if (this.world.getTileEntity(this.pos) == this){
            double posX = (double) this.pos.getX() + 0.5D;
            double posY = (double) this.pos.getY() + 0.5D;
            double posZ = (double) this.pos.getZ() + 0.5D;

            return player.getDistanceSq(posX, posY, posZ) <= 64.0D;
        }
        return false;
    }

    @Nonnull
    @Override
    public final NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public boolean shouldRefresh(@Nonnull World world, @Nonnull BlockPos pos, IBlockState state, IBlockState newState) {
        return state.getBlock() != newState.getBlock();
    }

    @Override
    public ITextComponent getDisplayName() {
        return super.getDisplayName();
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (this.hasCustomName()) {
            compound.setString("CustomName", this.customName);
        }
        return compound;
    }
}
