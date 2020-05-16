package aldinh777.potatoheadshot.block;

import aldinh777.potatoheadshot.item.PotatoItemBlock;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import aldinh777.potatoheadshot.util.BlockType;
import net.minecraft.block.Block;

public class PotatoBlock extends Block {

    public PotatoBlock(String name, BlockType blockType) {
        super(blockType.getMaterial());
        this.setRegistryName(name);
        this.setHardness(blockType.getHardness());
        this.setResistance(blockType.getResistance());
        this.setSoundType(blockType.getSoundType());
        init(name);
    }

    protected void init(String name) {
        this.setUnlocalizedName(name);
        this.setCreativeTab(PotatoTab.POTATO_TAB);

        PotatoBlocks.LISTS.add(this);
        PotatoItems.LISTS.add(new PotatoItemBlock(this));
    }
}
