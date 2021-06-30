package aldinh777.potatoheadshot.backup.guis;

import aldinh777.potatoheadshot.backup.containers.ContainerManaCollector;
import aldinh777.potatoheadshot.backup.tileentities.TileEntityManaCollector;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiManaCollector extends GuiMana {

    public GuiManaCollector(InventoryPlayer player, TileEntityManaCollector tileEntity) {
        super(player, tileEntity, new ContainerManaCollector(player, tileEntity));
    }
}
