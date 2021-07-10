package aldinh777.potatoheadshot.content.blocks.flower;

import aldinh777.potatoheadshot.common.lists.PotatoBlocks;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.common.lists.PotatoTab;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

public class SingleFlower extends BlockBush {

    public SingleFlower(String name) {
        this(name, Material.PLANTS);
    }

    public SingleFlower(String name, Material material) {
        super(material);
        this.setDefaultState(this.getBlockState().getBaseState());
        this.setHardness(0.0f);
        this.setSoundType(SoundType.PLANT);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(PotatoTab.POTATO_TAB);

        ItemBlock item = new ItemBlock(this);
        item.setRegistryName(name);
        item.setUnlocalizedName(name);

        PotatoBlocks.LISTS.add(this);
        PotatoItems.LISTS.add(item);
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return super.getBoundingBox(state, source, pos).offset(state.getOffset(source, pos));
    }

    @Nonnull
    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XZ;
    }
}
