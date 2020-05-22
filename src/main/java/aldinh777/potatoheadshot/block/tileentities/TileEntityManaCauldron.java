package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.ManaCauldron;
import aldinh777.potatoheadshot.block.recipes.ManaCauldronRecipes;
import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class TileEntityManaCauldron extends TileEntity implements ITickable {

    private final PotatoManaStorage storage = new PotatoManaStorage(64000);
    private int level = 0;

    // Override Methods

    @Override
    public void update() {
        if (!this.world.isRemote) {
            if (this.storage.getManaStored() >= 1000) {
                this.transformItemsInside();
            }

            this.detectLevelChange();

            this.markDirty();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        storage.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        storage.writeToNBT(compound);
        return compound;
    }

    // Custom Methods

    public PotatoManaStorage getManaStorage() {
        return this.storage;
    }

    public void transformItemsInside() {
        AxisAlignedBB bb = new AxisAlignedBB(pos);
        List<EntityItem> entities = this.world.getEntitiesWithinAABB(EntityItem.class, bb);
        for (EntityItem entity : entities) {
            ItemStack stack = entity.getItem();
            ItemStack result = ManaCauldronRecipes.INSTANCE.getResult(stack.getItem());

            if (!result.isEmpty()) {
                int manaCost = ManaCauldronRecipes.INSTANCE.getCost(stack.getItem());
                float posX = this.pos.getX() + 0.5f;
                float posY = this.pos.getY() + 0.5f;
                float posZ = this.pos.getZ() + 0.5f;

                EntityItem entityResult = new EntityItem(this.world, posX, posY, posZ, result.copy());
                this.world.spawnEntity(entityResult);
                stack.shrink(1);
                this.storage.useMana(manaCost);
            }
        }
    }

    public void setManaLevel(int level) {
        IBlockState newState = this.world.getBlockState(this.pos)
                .withProperty(ManaCauldron.LEVEL, level);

        this.world.setBlockState(pos, newState);

        this.validate();
        this.world.setTileEntity(pos, this);
    }

    public void detectLevelChange() {
        int manaStored = this.storage.getManaStored();
        int currentLevel;

        if (manaStored <= 0) {
            currentLevel = 0;
        } else if (manaStored <= this.storage.getMaxManaStored() / 3) {
            currentLevel = 1;
        } else if (manaStored <= this.storage.getMaxManaStored() / 3 * 2) {
            currentLevel = 2;
        } else {
            currentLevel = 3;
        }

        if (this.level != currentLevel) {
            this.level = currentLevel;
            this.setManaLevel(currentLevel);
        }
    }
}
