package aldinh777.potatoheadshot.content.items;

import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.common.lists.PotatoTab;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class PotatoSeed extends ItemSeeds {

    private final Block crops;
    private final Block soil;

    public PotatoSeed(String name, Block crops) {
        this(name, crops, Blocks.FARMLAND);
    }

    public PotatoSeed(String name, Block crops, Block soil) {
        super(crops, soil);
        this.crops = crops;
        this.soil = soil;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(PotatoTab.POTATO_TAB);

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
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && state.getBlock() == this.soil && worldIn.isAirBlock(pos.up())) {
            worldIn.setBlockState(pos.up(), this.crops.getDefaultState());

            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }
}
