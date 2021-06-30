package aldinh777.potatoheadshot.content.guis;

import aldinh777.potatoheadshot.content.containers.ContainerDrierUpgrade;
import aldinh777.potatoheadshot.content.tileentities.TileEntityDrier;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiDrierUpgrade extends GuiMachine {

    private static final String TEXTURE = ":textures/gui/upgrade/potato_drier.png";

    public GuiDrierUpgrade(InventoryPlayer inventoryPlayer, TileEntityDrier tileEntity) {
        super(new ContainerDrierUpgrade(inventoryPlayer, tileEntity), tileEntity, TEXTURE);
    }
}
