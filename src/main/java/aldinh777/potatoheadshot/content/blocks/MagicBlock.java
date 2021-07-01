package aldinh777.potatoheadshot.content.blocks;

import aldinh777.potatoheadshot.other.util.BlockType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.List;

public class MagicBlock extends PotatoBlock {

    public MagicBlock(String name, BlockType blockType) {
        super(name, blockType);
        setLightLevel(1.0f);
    }

    @Override
    public void onEntityCollidedWithBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Entity entityIn) {
        if (!entityIn.hasNoGravity()) {
            entityIn.setNoGravity(true);
            Timer timer = new Timer(0, (e -> {
                if (entityIn.hasNoGravity()) {
                    entityIn.setNoGravity(false);
                }
            }));
            timer.setInitialDelay(3_000);
            timer.setRepeats(false);
            timer.restart();
        }
    }

    @Nonnull
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(@Nonnull IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return NULL_AABB;
    }

    public boolean isPassable(@Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return false;
    }

    public boolean isFullCube(@Nonnull IBlockState state) {
        return false;
    }

    public boolean isOpaqueCube(@Nonnull IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(@Nonnull IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, @Nonnull EnumFacing side) {
        if (blockAccess.getBlockState(pos.offset(side)).getMaterial() == this.blockMaterial) {
            return false;
        } else {
            return side == EnumFacing.UP || super.shouldSideBeRendered(blockState, blockAccess, pos, side);
        }
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public void dropBlockAsItemWithChance(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, float chance, int fortune) {
        if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) {
            List<ItemStack> drops = getDrops(worldIn, pos, state, fortune);
            chance = net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(drops, worldIn, pos, state, fortune, chance, false, harvesters.get());

            for (ItemStack drop : drops)
            {
                if (worldIn.rand.nextFloat() <= chance)
                {
                    spawnAsEntity(worldIn, pos, drop);
                }
            }
        }
    }

    public static void spawnAsEntity(World worldIn, @Nonnull BlockPos pos, @Nonnull ItemStack stack) {
        if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean("doTileDrops")&& !worldIn.restoringBlockSnapshots) {
            if (captureDrops.get()) {
                capturedDrops.get().add(stack);
                return;
            }
            double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            EntityItem entityitem = new EntityItem(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, stack);
            entityitem.setDefaultPickupDelay();
            entityitem.setNoGravity(true);
            Timer timer = new Timer(0, (e -> {
                if (entityitem.hasNoGravity()) {
                    entityitem.setNoGravity(false);
                }
            }));
            timer.setInitialDelay(9_000);
            timer.setRepeats(false);
            timer.restart();
            worldIn.spawnEntity(entityitem);
        }
    }
}
