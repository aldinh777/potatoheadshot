package aldinh777.potatoheadshot.backup.guis;

import aldinh777.potatoheadshot.backup.containers.ContainerManaExtractor;
import aldinh777.potatoheadshot.backup.tileentities.TileEntityManaExtractor;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiManaExtractor extends GuiMana {

	public GuiManaExtractor(InventoryPlayer player, TileEntityManaExtractor tileEntity) {
		super(player, tileEntity, new ContainerManaExtractor(player, tileEntity));
	}
}
