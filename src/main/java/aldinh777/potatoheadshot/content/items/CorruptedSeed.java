package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.common.lists.PotatoBlocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class CorruptedSeed extends PotatoItem {

    public CorruptedSeed(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);
        if (player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
            if (state.getBlockHardness(worldIn, pos) != -1 && !state.getBlock().hasTileEntity(state)) {
                SoundType soundType = state.getBlock().getSoundType(state, worldIn, pos, null);
                worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), soundType.getBreakSound(), SoundCategory.BLOCKS, 1.0f, 1.0f, false);

                if (!worldIn.isRemote) {
                    state.getBlock().dropBlockAsItem(worldIn, pos, state, 0);
                    state.getBlock().breakBlock(worldIn, pos, state);
                    worldIn.setBlockState(pos, PotatoBlocks.CORRUPTED_BLOCK.getDefaultState());
                    itemstack.shrink(1);
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }
}
