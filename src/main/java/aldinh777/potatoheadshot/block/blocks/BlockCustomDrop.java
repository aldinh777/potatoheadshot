package aldinh777.potatoheadshot.block.blocks;

import aldinh777.potatoheadshot.util.BlockType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockCustomDrop extends PotatoBlock {

    private final Item itemDropped;

    public BlockCustomDrop(String name, Item itemDropped) {
        super(name, BlockType.GLASS);
        this.itemDropped = itemDropped;
        this.setLightLevel(1.0f);
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return itemDropped;
    }
}
