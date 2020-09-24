package aldinh777.potatoheadshot.block.blocks;

import aldinh777.potatoheadshot.item.PotatoItemBlock;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ManaFlower extends BlockBush {

    protected static final AxisAlignedBB BUSH_AABB = new AxisAlignedBB(
            0.30000001192092896D, 0.0D,
            0.30000001192092896D, 0.699999988079071D,
            1.0D, 0.699999988079071D);

    public ManaFlower(String name) {
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setLightLevel(0.8f);
        this.setHardness(0.0f);
        this.setResistance(2000.0F);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(PotatoTab.POTATO_TAB);

        PotatoBlocks.LISTS.add(this);
        PotatoItems.LISTS.add(new PotatoItemBlock(this));
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState blockState) {
        if (blockState.getBlock() == this) {
            IBlockState roof = world.getBlockState(pos.up());
            IBlockState soil = world.getBlockState(pos.down());

            return !isAirOrFlower(roof) || !isAirOrFlower(soil);
        } else {
             return this.canSustainBush(world.getBlockState(pos.down()));
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BUSH_AABB;
    }

    private boolean isAirOrFlower(IBlockState blockState) {
        return blockState.getBlock() == Blocks.AIR || blockState.getBlock() instanceof ManaFlower;
    }
}
