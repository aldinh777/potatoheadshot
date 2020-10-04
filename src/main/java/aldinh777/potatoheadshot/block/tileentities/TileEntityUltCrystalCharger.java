package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.energy.PotatoEnergyStorage;

public class TileEntityUltCrystalCharger extends TileEntitySweetCrystalCharger {

    public TileEntityUltCrystalCharger() {
        this.storage = new PotatoEnergyStorage(32000000, 1600, 0);
    }

    @Override
    public boolean isUltimate() {
        return true;
    }


}
