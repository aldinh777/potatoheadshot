package aldinh777.potatoheadshot.block;

import aldinh777.potatoheadshot.item.PotatoFoodItemBlock;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import aldinh777.potatoheadshot.util.BlockType;

public class PotatoFoodBlock extends PotatoBlock {

    public PotatoFoodBlock(String name, BlockType blockType, int amount, float saturation) {
        super(name, blockType);
        initFood(amount, saturation);
    }

    @Override
    protected void init(String name) {
        this.setUnlocalizedName(name);
        this.setCreativeTab(PotatoTab.POTATO_TAB);

        PotatoBlocks.LISTS.add(this);
    }

    protected void initFood(int amount, float saturation) {
        PotatoItems.LISTS.add(new PotatoFoodItemBlock(this, amount, saturation));
    }
}
