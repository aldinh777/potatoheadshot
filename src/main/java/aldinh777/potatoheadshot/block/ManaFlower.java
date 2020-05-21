package aldinh777.potatoheadshot.block;

import aldinh777.potatoheadshot.item.PotatoItemBlock;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ManaFlower extends BlockBush {

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
}
