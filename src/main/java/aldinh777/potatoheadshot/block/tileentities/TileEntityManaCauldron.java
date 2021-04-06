package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.blocks.ManaCauldron;
import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import aldinh777.potatoheadshot.handler.ConfigHandler;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.recipes.category.IManaRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class TileEntityManaCauldron extends TileEntity implements ITickable, IManaStorage {

    protected PotatoManaStorage storage;
    protected ManaCauldron.Element element = ManaCauldron.Element.MANA;
    protected IManaRecipes recipes = IManaRecipes.getRecipeById(0);
    protected int level = 0;
    protected int checkMana = 0;

    public TileEntityManaCauldron() {
        this.storage = new PotatoManaStorage(ConfigHandler.MANA_CAULDRON_CAPACITY);
    }

    // Override Methods

    @Override
    public void update() {
        if (!this.world.isRemote) {

            boolean flag = false;
            boolean flagItem = this.transformItemsInside();
            boolean flagEntity = this.affectEntityInside();

            this.detectLevelChange();

            if (this.checkMana != this.storage.getManaStored()) {
                this.checkMana = this.storage.getManaStored();
                flag = true;
            }

            if (flag && flagItem && flagEntity) {
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

            } else {
                ItemStack result = recipes.getResult(stack);
                int cost = recipes.getCost(stack);

                if (!result.isEmpty() && this.storage.getManaStored() >= cost) {
                    float posX = this.pos.getX() + 0.5f;
                    float posY = this.pos.getY() + 0.5f;
                    float posZ = this.pos.getZ() + 0.5f;

                    if (result.getItem() == PotatoItems.ULTIMATE_CONCENTRATED_CRYSTAL && !this.isUltimate()) {
                        if (ConfigHandler.CONCENTRATED_CRYSTAL_EXPLOSION) {
                            world.createExplosion(null,
                                    (this.pos.getX() + 0.5f), (this.pos.getY() + 1.0f), (pos.getZ() + 0.5f),
                                    5.0f, true);
                        }
                    }

                    boolean success = false;

                    for (EnumFacing facing : EnumFacing.VALUES) {
                        if (facing == EnumFacing.UP) {
                            continue;
                        }

                        if (success) {
                            break;
                        }

                        TileEntity te = this.world.getTileEntity(this.pos.offset(facing));
                        if (te != null) {
                            if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
                                IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                                if (itemHandler != null) {
                                    int maxSlot = itemHandler.getSlots();
                                    for (int i = 0; i < maxSlot; i++) {
                                        ItemStack insert = itemHandler.insertItem(i, result, true);
                                        if (insert.isEmpty()) {
                                            itemHandler.insertItem(i, result, false);
                                            success = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (!success) {
                        EntityItem entityResult = new EntityItem(this.world, posX, posY, posZ, result.copy());
                        this.world.spawnEntity(entityResult);
                    }

                    this.storage.useMana(cost);
                    stack.shrink(1);
                }
                return false;
            }
        }
        return false;
    }

    public boolean affectEntityInside() {
        AxisAlignedBB bb = new AxisAlignedBB(pos);
        List<EntityLivingBase> entities = this.world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
        for (EntityLivingBase entity : entities) {
            int cost = 200;
            if (this.storage.getManaStored() > cost) {
                if (this.element == ManaCauldron.Element.FIRE) {
                    if (!entity.isBurning()) {
                        entity.setFire(4);
                        this.storage.useMana(cost);
                        return true;
                    }
                } else if (this.element == ManaCauldron.Element.LIFE) {
                    if (entity.isEntityUndead()) {
                        entity.attackEntityFrom(DamageSource.MAGIC,2f);
                        this.storage.useMana(cost);
                        return true;
                    } else if (entity.getHealth() < entity.getMaxHealth()) {
                        entity.heal(0.2f);
                        this.storage.useMana(cost);
                        return true;
                    }
                }
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
        } else if (manaStored < this.storage.getMaxManaStored() / 2) {
            currentLevel = 1;
        } else if (manaStored < this.storage.getMaxManaStored()) {
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
