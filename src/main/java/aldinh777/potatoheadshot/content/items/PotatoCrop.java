package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.content.blocks.crops.IPlaceablePlant;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class PotatoCrop extends PotatoFood {

    private final Block crops;

    public PotatoCrop(String name, int healAmount, float saturation, Block crops) {
        super(name, healAmount, saturation);
        this.crops = crops;
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && worldIn.isAirBlock(pos.up())) {
            if (crops instanceof IPlaceablePlant) {
                IPlaceablePlant potatoCrops = (IPlaceablePlant) crops;

                if (potatoCrops.isPlaceable(state)) {
                    worldIn.setBlockState(pos.up(), crops.getDefaultState());

                    itemstack.shrink(1);
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.FAIL;
    }
}
