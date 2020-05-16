package aldinh777.potatoheadshot.block.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

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

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("CustomName", 8)) {
            this.customName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (this.hasCustomName()) {
            compound.setString("CustomName", this.customName);
        }
        return compound;
    }
}
