package aldinh777.potatoheadshot.content.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityFloatingItem extends EntityItem {

    public EntityFloatingItem(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityFloatingItem(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
    }

    public EntityFloatingItem(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote) {
            NBTTagCompound compound = getEntityData();
            if (compound.hasKey("GravityTick")) {
                int gravityTick = compound.getInteger("GravityTick");
                if (gravityTick > 0) {
                    if (!hasNoGravity()) {
                        setNoGravity(true);
                    }
                    compound.setInteger("GravityTick", --gravityTick);
                } else {
                    setNoGravity(false);
                    compound.removeTag("GravityTick");
                }
            }
        }
    }
}
