package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.energy.PotatoManaStorage;

public class TileEntityUltManaCauldron extends TileEntityManaCauldron {

    public TileEntityUltManaCauldron() {
        this.storage = new PotatoManaStorage(32000000);
    }

    @Override
    public boolean isUltimate() {
        return true;
    }
}
