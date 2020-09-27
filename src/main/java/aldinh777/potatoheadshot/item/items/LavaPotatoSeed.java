package aldinh777.potatoheadshot.item.items;

import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class LavaPotatoSeed extends ItemSeeds {

    private final Block crops;
    private final Block soil;

    public LavaPotatoSeed(String name) {
        super(PotatoBlocks.LAVA_POTATOES, PotatoBlocks.LAVA_FARMLAND);
        this.crops = PotatoBlocks.LAVA_POTATOES;
        this.soil = PotatoBlocks.LAVA_FARMLAND;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(PotatoTab.POTATO_TAB);

        PotatoItems.LISTS.add(this);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
        net.minecraft.block.state.IBlockState state = worldIn.getBlockState(pos);
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && state.getBlock() == this.soil && worldIn.isAirBlock(pos.up())) {
            worldIn.setBlockState(pos.up(), this.crops.getDefaultState());

            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;

        } else {
            return EnumActionResult.FAIL;
        }
    }
}
