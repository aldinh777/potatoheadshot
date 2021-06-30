package aldinh777.potatoheadshot.backup.tileentities;

import aldinh777.potatoheadshot.content.energy.PotatoManaStorage;
import aldinh777.potatoheadshot.other.handler.ConfigHandler;

public class TileEntityUltManaCauldron extends TileEntityManaCauldron {

    public TileEntityUltManaCauldron() {
        this.storage = new PotatoManaStorage(ConfigHandler.ULTIMATE_CAULDRON_CAPACITY);
    }

    @Override
    public boolean isUltimate() {
        return true;
    }
}
