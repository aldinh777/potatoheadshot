package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import aldinh777.potatoheadshot.handler.ConfigHandler;

public class TileEntityUltManaCauldron extends TileEntityManaCauldron {

    public TileEntityUltManaCauldron() {
        this.storage = new PotatoManaStorage(ConfigHandler.ULTIMATE_CAULDRON_CAPACITY);
    }

    @Override
    public boolean isUltimate() {
        return true;
    }
}
