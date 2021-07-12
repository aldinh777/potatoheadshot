package aldinh777.potatoheadshot.content.blocks;

import aldinh777.potatoheadshot.common.lists.PotatoBlocks;
import aldinh777.potatoheadshot.common.util.BlockType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class PotatoFoodBlock extends PotatoBlock {

    private Item droppedItem;

    public PotatoFoodBlock(String name, BlockType blockType) {
        super(name, blockType);
    }

    @Override
    protected void init(String name) {
        PotatoBlocks.LISTS.add(this);
    }

    @Nonnull
    @Override
    public ItemStack getItem(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return this.droppedItem != null ? new ItemStack(this.droppedItem) : super.getItem(worldIn, pos, state);
    }

    public void setDroppedItem(Item droppedItem) {
        this.droppedItem = droppedItem;
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return this.droppedItem != null ? this.droppedItem : super.getItemDropped(state, rand, fortune);
    }
}
