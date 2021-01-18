package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.blocks.ManaCauldron;
import aldinh777.potatoheadshot.block.recipes.IManaRecipes;
import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class TileEntityManaCauldron extends TileEntity implements ITickable {

    protected PotatoManaStorage storage;
    protected ManaCauldron.Element element = ManaCauldron.Element.MANA;
    protected IManaRecipes recipes = IManaRecipes.getRecipeById(0);
    protected int level = 0;
    protected int checkMana = 0;

    public TileEntityManaCauldron() {
        this.storage = new PotatoManaStorage(800000);
    }

    // Override Methods

    @Override
    public void update() {
        if (!this.world.isRemote) {

            boolean flag = this.transformItemsInside();

            this.detectLevelChange();

            if (this.checkMana != this.storage.getManaStored()) {
                this.checkMana = this.storage.getManaStored();
                flag = true;
            }

            if (flag) {
                this.markDirty();
            }
        }
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.element = ManaCauldron.Element.withValue(compound.getInteger("Element"));
        this.recipes = IManaRecipes.getRecipeById(this.element.getValue());
        storage.readFromNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("Element", this.element.getValue());
        storage.writeToNBT(compound);
        return compound;
    }

    @Override
    public boolean shouldRefresh(@Nonnull World world, @Nonnull BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    // Custom Methods

    public PotatoManaStorage getManaStorage() {
        return this.storage;
    }

    public boolean transformItemsInside() {
        AxisAlignedBB bb = new AxisAlignedBB(pos);
        List<EntityItem> entities = this.world.getEntitiesWithinAABB(EntityItem.class, bb);
        for (EntityItem entity : entities) {
            ItemStack stack = entity.getItem();

            if (stack.getItem().equals(PotatoItems.ROD_MANA)) {
                this.setElement(ManaCauldron.Element.MANA);
                stack.shrink(1);
                return true;

            } else if (stack.getItem().equals(PotatoItems.ROD_LIFE)) {
                this.setElement(ManaCauldron.Element.LIFE);
                stack.shrink(1);
                return true;

            } else if (stack.getItem().equals(PotatoItems.ROD_NATURE)) {
                this.setElement(ManaCauldron.Element.NATURE);
                stack.shrink(1);
                return true;

            } else if (stack.getItem().equals(PotatoItems.ROD_FIRE)) {
                this.setElement(ManaCauldron.Element.FIRE);
                stack.shrink(1);
                return true;

            } else if (this.element == ManaCauldron.Element.VOID) {
                stack.shrink(stack.getCount());
                return false;

            } else {
                ItemStack result = recipes.getResult(stack);
                int cost = recipes.getCost(stack);

                if (!result.isEmpty() && this.storage.getManaStored() >= cost) {
                    float posX = this.pos.getX() + 0.5f;
                    float posY = this.pos.getY() + 0.5f;
                    float posZ = this.pos.getZ() + 0.5f;

                    if (result.getItem() == PotatoItems.ULTIMATE_CONCENTRATED_CRYSTAL && !this.isUltimate()) {
                        world.createExplosion(null,
                                (this.pos.getX() + 0.5f), (this.pos.getY() + 1.0f), (pos.getZ() + 0.5f),
                                5.0f, true);
                    }

                    EntityItem entityResult = new EntityItem(this.world, posX, posY, posZ, result.copy());
                    this.world.spawnEntity(entityResult);
                    this.storage.useMana(cost);
                    stack.shrink(1);
                }
                return false;
            }
        }
        return false;
    }

    public boolean isUltimate() {
        return false;
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
        } else if (manaStored < this.storage.getMaxManaStored()) {
            currentLevel = 1;
        } else {
            currentLevel = 2;
        }

        if (this.level != currentLevel) {
            this.level = currentLevel;
            this.setManaLevel(currentLevel);
        }
    }
}
