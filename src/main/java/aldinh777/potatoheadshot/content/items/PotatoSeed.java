package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
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

public class PotatoSeed extends PotatoItem {

    private final Block crops;

    public PotatoSeed(String name, Block crops) {
        super(name);
        this.crops = crops;

        switch (name) {
            case "lava_potato_seed":
                if (ConfigHandler.LAVA_POTATO) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "water_potato_seed":
                if (ConfigHandler.WATER_POTATO) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            case "ice_potato_seed":
                if (ConfigHandler.ICE_POTATO) {
                    PotatoItems.LISTS.add(this);
                }
                break;
            default:
                PotatoItems.LISTS.add(this);
        }
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
