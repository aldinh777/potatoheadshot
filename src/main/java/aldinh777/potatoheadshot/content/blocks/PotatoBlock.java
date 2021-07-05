package aldinh777.potatoheadshot.content.blocks;

import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.content.items.PotatoItemBlock;
import aldinh777.potatoheadshot.common.lists.PotatoBlocks;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
import aldinh777.potatoheadshot.common.lists.PotatoTab;
import aldinh777.potatoheadshot.common.util.BlockType;
import net.minecraft.block.Block;

public class PotatoBlock extends Block {

    public PotatoBlock(String name, BlockType blockType) {
        super(blockType.getMaterial());
        this.setRegistryName(name);
        this.setHardness(blockType.getHardness());
        this.setResistance(blockType.getResistance());
        this.setSoundType(blockType.getSoundType());
        this.setUnlocalizedName(name);
        this.setCreativeTab(PotatoTab.POTATO_TAB);

        if ("potato_planks".equals(name)) {
            if (ConfigHandler.POTATO_PLANKS) {
                init(name);
            }
        } else {
            init(name);
        }
    }

    protected void init(String name) {
        PotatoBlocks.LISTS.add(this);
        PotatoItems.LISTS.add(new PotatoItemBlock(this));
    }
}
