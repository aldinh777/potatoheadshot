package aldinh777.potatoheadshot.content.blocks.crops;

import aldinh777.potatoheadshot.common.lists.PotatoItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class Lokbomb extends PotatoCrops {

    public Lokbomb(String name) {
        super(name);
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (getAge(state) >= getMaxAge()) {
                worldIn.setBlockState(pos, state.withProperty(AGE, 4));

                NonNullList<ItemStack> drops = NonNullList.create();
                getDrops(drops, worldIn, pos, state, 0);

                for (ItemStack drop : drops) {
                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), drop));
                }
            }
        }
        return false;
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (getAge(state) >= getMaxAge()) {
            worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 8, true);
        }
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return BUSH_AABB.offset(state.getOffset(source, pos));
    }

    @Nonnull
    @Override
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XZ;
    }

    @Nonnull
    @Override
    protected Item getSeed() {
        return PotatoItems.LOKBOMB;
    }

    @Nonnull
    @Override
    protected Item getCrop() {
        return PotatoItems.LOKBOMB;
    }

    @Override
    public boolean isPlaceable(IBlockState state) {
        return state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.GRASS;
    }
}
