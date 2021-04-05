package aldinh777.potatoheadshot.block.blocks;

import aldinh777.potatoheadshot.handler.ConfigHandler;
import aldinh777.potatoheadshot.item.items.PotatoItemBlock;
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
        this.setUnlocalizedName(name);
        this.setCreativeTab(PotatoTab.POTATO_TAB);

        switch (name) {
            case "potato_planks":
                if (ConfigHandler.POTATO_PLANKS) {
                    init(name);
                }
                break;
            case "potato_drier":
                if (ConfigHandler.POTATO_DRIER) {
                    init(name);
                }
                break;
            case "sweet_potato_generator":
                if (ConfigHandler.SWEET_POTATO_GENERATOR) {
                    init(name);
                }
                break;
            case "sweet_freezer":
                if (ConfigHandler.SWEET_FREEZER) {
                    init(name);
                }
                break;
            case "sweet_crystal_maker":
                if (ConfigHandler.SWEET_CRYSTAL_MAKER) {
                    init(name);
                }
                break;
            case "sweet_crystal_charger":
            case "ultimate_crystal_charger":
                if (ConfigHandler.ULTIMATE_CRYSTALS && ConfigHandler.SWEET_CRYSTAL_CHARGER) {
                    init(name);
                }
                break;
            case "sweet_infuser":
                if (ConfigHandler.SWEET_INFUSER) {
                    init(name);
                }
                break;
            case "mana_collector":
                if (ConfigHandler.MANA_COLLECTOR) {
                    init(name);
                }
                break;
            case "mana_extractor":
                if (ConfigHandler.MANA_EXTRACTOR) {
                    init(name);
                }
                break;
            case "energy_transfer":
                if (ConfigHandler.ENERGY_TRANSFER) {
                    init(name);
                }
                break;
            case "mana_cauldron":
            case "ultimate_mana_cauldron":
                if (ConfigHandler.MANA_CAULDRON) {
                    if (name.equals("ultimate_mana_cauldron")) {
                        if (ConfigHandler.ULTIMATE_CRYSTALS && ConfigHandler.ULTIMATE_CAULDRON) {
                            init(name);
                        }
                    } else {
                        init(name);
                    }
                }
                break;
            default:
                init(name);
        }
    }

    protected void init(String name) {
        PotatoBlocks.LISTS.add(this);
        PotatoItems.LISTS.add(new PotatoItemBlock(this));
    }
}
