package aldinh777.potatoheadshot.block;

import aldinh777.potatoheadshot.item.PotatoFoodItemBlock;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import aldinh777.potatoheadshot.util.BlockType;
import net.minecraft.block.Block;

public class PotatoFoodBlock extends Block {

    public PotatoFoodBlock(String name, BlockType blockType, int amount, float saturation) {
        super(blockType.getMaterial());
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHardness(blockType.getHardness());
        this.setResistance(blockType.getResistance());
        this.setSoundType(blockType.getSoundType());
        this.setCreativeTab(PotatoTab.POTATO_TAB);

        PotatoBlocks.LISTS.add(this);
        PotatoItems.LISTS.add(new PotatoFoodItemBlock(this, amount, saturation));
    }
}
