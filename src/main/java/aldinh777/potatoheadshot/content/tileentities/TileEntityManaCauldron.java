package aldinh777.potatoheadshot.content.tileentities;

import aldinh777.potatoheadshot.content.blocks.crops.PotatoCrops;
import aldinh777.potatoheadshot.content.blocks.machines.ManaCauldron;
import aldinh777.potatoheadshot.other.capability.PotatoManaStorage;
import aldinh777.potatoheadshot.other.handler.ConfigHandler;
import aldinh777.potatoheadshot.other.lists.PotatoBlocks;
import aldinh777.potatoheadshot.other.lists.PotatoItems;
import aldinh777.potatoheadshot.other.recipes.category.IManaRecipes;
import aldinh777.potatoheadshot.other.util.Element;
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
    protected Element element = Element.MANA;
    protected IManaRecipes recipes = IManaRecipes.getRecipeById(0);
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
                for (int x = -3; x <= 3; x++) {
                    for (int z = -3; z <= 3; z++) {
                        BlockPos statePos = pos.add(x, 0, z);
                        IBlockState state = world.getBlockState(statePos);

                        if (state.getBlock() == PotatoBlocks.GLOWING_POTATOES) {
                            if (state.getValue(PotatoCrops.AGE) == 7) {
                                IBlockState glowPotato = PotatoBlocks.GLOWING_POTATOES.getDefaultState()
                                        .withProperty(PotatoCrops.AGE, 0);
                                world.setBlockState(statePos, glowPotato);
                                storage.collectMana(MANA_COLLECT_DEFAULT);
                            }
                        }
                    }
                }
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
        recipes = IManaRecipes.getRecipeById(element.getValue());
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

    public boolean transformItemsInside() {
        AxisAlignedBB bb = new AxisAlignedBB(pos);
        List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, bb);
        for (EntityItem entity : entities) {
            ItemStack stack = entity.getItem();

            if (stack.getItem().equals(PotatoItems.ESSENCE_MANA)) {
                setElement(Element.MANA);
                stack.shrink(1);
                return true;

            } else if (stack.getItem().equals(PotatoItems.ESSENCE_LIFE)) {
                setElement(Element.LIFE);
                stack.shrink(1);
                return true;

            } else if (stack.getItem().equals(PotatoItems.ESSENCE_NATURE)) {
                setElement(Element.NATURE);
                stack.shrink(1);
                return true;

            } else if (stack.getItem().equals(PotatoItems.ESSENCE_FIRE)) {
                setElement(Element.FIRE);
                stack.shrink(1);
                return true;

            } else if (stack.getItem().equals(PotatoItems.GLOWING_POTATO_DUST)) {
                if (storage.getManaStored() < storage.getMaxManaStored()) {
                    storage.collectMana(MANA_COLLECT_DEFAULT);
                    stack.shrink(1);
                }
                return true;

            } else {
                ItemStack result = recipes.getResult(stack).copy();
                int cost = recipes.getCost(stack);

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
                        EntityItem entityResult = new EntityItem(world, posX, posY, posZ, result);
                        world.spawnEntity(entityResult);
                    }

                    storage.useMana(cost);
                    stack.shrink(1);

                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean affectEntityInside() {
        AxisAlignedBB bb = new AxisAlignedBB(pos);
        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
        for (EntityLivingBase entity : entities) {
            int cost = 2;
            if (storage.getManaStored() > cost) {
                if (element == Element.FIRE) {
                    if (!entity.isBurning()) {
                        entity.setFire(4);
                        storage.useMana(cost);
                        return true;
                    }
                } else if (element == Element.LIFE) {
                    if (entity.isEntityUndead()) {
                        entity.attackEntityFrom(DamageSource.MAGIC,2f);
                        storage.useMana(cost);
                        return true;
                    } else if (entity.getHealth() < entity.getMaxHealth()) {
                        entity.heal(0.2f);
                        storage.useMana(cost);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setManaLevel(int level) {
        IBlockState newState = world.getBlockState(pos)
                .withProperty(ManaCauldron.LEVEL, level);

        world.setBlockState(pos, newState);
    }

    public void setElement(Element element) {
        this.element = element;
        recipes = IManaRecipes.getRecipeById(element.getValue());

        IBlockState newState = world.getBlockState(pos)
                .withProperty(ManaCauldron.ELEMENT, element);

        world.setBlockState(pos, newState);
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
