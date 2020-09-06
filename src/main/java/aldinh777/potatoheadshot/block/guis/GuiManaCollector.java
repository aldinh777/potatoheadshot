package aldinh777.potatoheadshot.block.guis;

import aldinh777.potatoheadshot.block.containers.ContainerManaCollector;
import aldinh777.potatoheadshot.block.tileentities.TileEntityManaCollector;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiManaCollector extends GuiMana {

    public GuiManaCollector(InventoryPlayer player, TileEntityManaCollector tileEntity) {
        super(player, tileEntity, new ContainerManaCollector(player, tileEntity));
    }
}
