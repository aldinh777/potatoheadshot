package aldinh777.potatoheadshot.content.tileentities;

import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.common.lists.PotatoBlocks;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.common.recipes.category.IManaRecipes;
import aldinh777.potatoheadshot.common.recipes.recipe.CauldronRecipe;
import aldinh777.potatoheadshot.common.util.AreaHelper;
import aldinh777.potatoheadshot.common.util.Element;
import aldinh777.potatoheadshot.common.util.InventoryHelper;
import aldinh777.potatoheadshot.content.blocks.crops.PotatoCrops;
import aldinh777.potatoheadshot.content.blocks.machines.ManaCauldron;
import aldinh777.potatoheadshot.content.capability.PotatoManaStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicBoolean;

public class TileEntityManaCauldron extends TileEntity implements ITickable, IManaMachine {

    protected PotatoManaStorage storage;
    protected Element element = Element.MANA;
    protected IManaRecipes recipes = IManaRecipes.getRecipeByElement(Element.MANA);
    protected int level = 0;
    protected int checkMana = 0;

    private static final int MANA_COLLECT_DEFAULT = 200;
    private int tick = 0;

    public TileEntityManaCauldron() {
        storage = new PotatoManaStorage(ConfigHandler.MANA_CAULDRON_CAPACITY);
    }

    // Override Methods

    @Override
    public void update() {
        if (!world.isRemote) {

            tick++;
            if (tick >= 20) {
                tick = 0;
                absorbSurroundingMana();
            }
            
            boolean flag = false;
            boolean flagItem = transformItemsInside();
            boolean flagEntity = affectEntityInside();

            detectLevelChange();

            if (checkMana != storage.getManaStored()) {
                checkMana = storage.getManaStored();
                flag = true;
            }

            if (flag && flagItem && flagEntity) {
                markDirty();
            }
        }
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        element = Element.withValue(compound.getInteger("Element"));
        recipes = IManaRecipes.getRecipeByElement(element);
        storage.readFromNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("Element", element.getValue());
        storage.writeToNBT(compound);
        return compound;
    }

    @Override
    public boolean shouldRefresh(@Nonnull World world, @Nonnull BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    // Custom Methods

    public PotatoManaStorage getManaStorage() {
        return storage;
    }

    public void absorbSurroundingMana() {
        if (storage.getManaStored() < storage.getMaxManaStored()) {
            AreaHelper.getStateByRange(world, pos, 3, (targetPos, targetState) -> {
                if (targetState.getBlock() == PotatoBlocks.GLOWING_POTATOES) {
                    if (targetState.getValue(PotatoCrops.AGE) == 7) {
                        world.setBlockState(targetPos, PotatoBlocks.GLOWING_POTATOES.getDefaultState()
                                .withProperty(PotatoCrops.AGE, 0));
                        storage.collectMana(MANA_COLLECT_DEFAULT);
                    }
                }
            }, () -> storage.getManaStored() >= storage.getMaxManaStored());
        }
    }

    public boolean transformItemsInside() {
        AtomicBoolean flag = new AtomicBoolean(false);
        AxisAlignedBB bb = new AxisAlignedBB(pos);
        AreaHelper.getEntitiesByRange(EntityItem.class, world, bb, (entityItem) -> {
            ItemStack stack = entityItem.getItem();

            if (stack.getItem() == PotatoItems.ESSENCE_MANA) {
                setElement(Element.MANA);
                stack.shrink(1);
                flag.set(true);

            } else if (stack.getItem() == PotatoItems.ESSENCE_LIFE) {
                setElement(Element.LIFE);
                stack.shrink(1);
                flag.set(true);

            } else if (stack.getItem() == PotatoItems.ESSENCE_NATURE) {
                setElement(Element.NATURE);
                stack.shrink(1);
                flag.set(true);

            } else if (stack.getItem() == PotatoItems.ESSENCE_FIRE) {
                setElement(Element.FIRE);
                stack.shrink(1);
                flag.set(true);

            } else if (stack.getItem() == PotatoItems.GLOWING_POTATO_DUST) {
                if (storage.getManaStored() < storage.getMaxManaStored()) {
                    storage.collectMana(MANA_COLLECT_DEFAULT);
                    stack.shrink(1);
                }
                flag.set(true);

            } else if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.GLOWING_POTATO_BLOCK)) {
                if (storage.getManaStored() < storage.getMaxManaStored()) {
                    storage.collectMana(MANA_COLLECT_DEFAULT * 4);
                    stack.shrink(1);
                }
                flag.set(true);

            } else {
                CauldronRecipe recipe = recipes.getResult(stack);
                ItemStack result = recipe.getOutput().copy();
                int cost = recipe.getCost();

                if (!result.isEmpty() && storage.getManaStored() >= cost) {
                    float posX = pos.getX() + 0.5f;
                    float posY = pos.getY() + 0.5f;
                    float posZ = pos.getZ() + 0.5f;

                    boolean success = false;

                    for (EnumFacing facing : EnumFacing.VALUES) {
                        if (facing == EnumFacing.UP) {
                            continue;
                        }

                        if (success) {
                            break;
                        }

                        TileEntity te = world.getTileEntity(pos.offset(facing));

                        if (InventoryHelper.transferItem(te, facing, result)) {
                            success = true;
                        }
                    }

                    if (!success) {
                        EntityItem entityResult = new EntityItem(world, posX, posY, posZ, result);
                        world.spawnEntity(entityResult);
                    }

                    storage.useMana(cost);
                    stack.shrink(1);

                    flag.set(true);
                }
            }
        });

        return flag.get();
    }

    public boolean affectEntityInside() {
        AtomicBoolean flag = new AtomicBoolean(false);
        AxisAlignedBB bb = new AxisAlignedBB(pos);
        AreaHelper.getEntitiesByRange(EntityLivingBase.class, world, bb, (entityLivingBase) -> {
            int cost = 2;
            if (storage.getManaStored() > cost) {
                if (element == Element.FIRE) {
                    if (!entityLivingBase.isBurning()) {
                        entityLivingBase.setFire(4);
                        storage.useMana(cost);
                        flag.set(true);
                    }
                } else if (element == Element.LIFE) {
                    if (entityLivingBase.isEntityUndead()) {
                        entityLivingBase.attackEntityFrom(DamageSource.MAGIC,2f);
                        storage.useMana(cost);
                        flag.set(true);
                    } else if (entityLivingBase.getHealth() < entityLivingBase.getMaxHealth()) {
                        entityLivingBase.heal(0.2f);
                        storage.useMana(cost);
                        flag.set(true);
                    }
                }
            }
        });
        return flag.get();
    }

    public void setManaLevel(int level) {
        world.setBlockState(pos, world.getBlockState(pos)
                .withProperty(ManaCauldron.LEVEL, level));
    }

    public void setElement(Element element) {
        this.element = element;
        recipes = IManaRecipes.getRecipeByElement(element);

        world.setBlockState(pos, world.getBlockState(pos)
                .withProperty(ManaCauldron.ELEMENT, element));
    }

    public void detectLevelChange() {
        int manaStored = storage.getManaStored();
        int currentLevel;

        if (manaStored <= 0) {
            currentLevel = 0;
        } else if (manaStored < storage.getMaxManaStored() / 2) {
            currentLevel = 1;
        } else if (manaStored < storage.getMaxManaStored()) {
            currentLevel = 2;
        } else {
            currentLevel = 3;
        }

        if (level != currentLevel) {
            level = currentLevel;
            setManaLevel(currentLevel);
        }
    }
}
