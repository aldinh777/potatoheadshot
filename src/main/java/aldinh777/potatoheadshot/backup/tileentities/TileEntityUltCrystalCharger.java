package aldinh777.potatoheadshot.backup.tileentities;

import aldinh777.potatoheadshot.content.energy.PotatoEnergyStorage;

public class TileEntityUltCrystalCharger extends TileEntitySweetCrystalCharger {

    public TileEntityUltCrystalCharger() {
        this.storage = new PotatoEnergyStorage(32_000_000, 1600, 0);
    }

    @Override
    public boolean isUltimate() {
        return true;
    }
}
