package aldinh777.potatoheadshot.content.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

import java.util.Objects;

public class PotatoItemBlock extends ItemBlock {

    public PotatoItemBlock(Block block) {
        super(block);
        this.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
    }
}
