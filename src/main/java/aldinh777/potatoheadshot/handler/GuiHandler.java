package aldinh777.potatoheadshot.handler;

import aldinh777.potatoheadshot.block.containers.ContainerPotatoDrier;
import aldinh777.potatoheadshot.block.guis.GuiPotatoDrier;
import aldinh777.potatoheadshot.block.tileentities.TileEntityPotatoDrier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;
import java.util.Objects;

public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 1) {
            TileEntityPotatoDrier te = (TileEntityPotatoDrier) world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerPotatoDrier(player.inventory, Objects.requireNonNull(te));
        } else {
            return  null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 1) {
            TileEntityPotatoDrier te = (TileEntityPotatoDrier) world.getTileEntity(new BlockPos(x, y, z));
            return new GuiPotatoDrier(player.inventory, Objects.requireNonNull(te));
        } else {
            return  null;
        }
    }
}
