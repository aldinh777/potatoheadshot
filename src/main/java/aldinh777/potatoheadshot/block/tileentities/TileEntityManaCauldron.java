package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.ManaCauldron;
import aldinh777.potatoheadshot.block.recipes.IManaRecipes;
import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class TileEntityManaCauldron extends TileEntity implements ITickable {

    private final PotatoManaStorage storage = new PotatoManaStorage(64000);
    private ManaCauldron.Element element = ManaCauldron.Element.MANA;
    private IManaRecipes recipes = IManaRecipes.getRecipeById(0);
    private int level = 0;

//     Override Methods

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
        super.readFromNBT(compound);
        this.element = ManaCauldron.Element.withValue(compound.getInteger("Element"));
        this.recipes = IManaRecipes.getRecipeById(this.element.getValue());
        storage.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("Element", this.element.getValue());
        storage.writeToNBT(compound);
        return compound;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
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

            if (stack.getItem().equals(Items.LAVA_BUCKET)) {
                this.setElement(ManaCauldron.Element.FIRE);
                stack.shrink(1);
            } else if (stack.getItem().equals(PotatoItems.POTATO_LEAVES)) {
                this.setElement(ManaCauldron.Element.NATURE);
                stack.shrink(1);
            } else if (stack.getItem().equals(Items.ROTTEN_FLESH)) {
                this.setElement(ManaCauldron.Element.LIFE);
                stack.shrink(1);
            } else {
                ItemStack result = recipes.getResult(stack.getItem());

                if (!result.isEmpty()) {
                    int manaCost = recipes.getCost(stack.getItem());
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
    }

    public void setManaLevel(int level) {
        IBlockState newState = this.world.getBlockState(this.pos)
                .withProperty(ManaCauldron.LEVEL, level);

        this.world.setBlockState(pos, newState);
    }

    public void setElement(ManaCauldron.Element element) {
        this.element = element;
        this.recipes = IManaRecipes.getRecipeById(element.getValue());

        IBlockState newState = this.world.getBlockState(this.pos)
                .withProperty(ManaCauldron.ELEMENT, element);

        this.world.setBlockState(pos, newState);
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
