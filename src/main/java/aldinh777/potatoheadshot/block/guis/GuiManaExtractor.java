package aldinh777.potatoheadshot.block.guis;

import aldinh777.potatoheadshot.block.containers.ContainerManaExtractor;
import aldinh777.potatoheadshot.block.tileentities.TileEntityManaExtractor;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiManaExtractor extends GuiMana {

	public GuiManaExtractor(InventoryPlayer player, TileEntityManaExtractor tileEntity) {
		super(player, tileEntity, new ContainerManaExtractor(player, tileEntity));
	}
}
