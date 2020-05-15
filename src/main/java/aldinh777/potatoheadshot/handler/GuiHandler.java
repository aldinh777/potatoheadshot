package aldinh777.potatoheadshot.handler;

import aldinh777.potatoheadshot.block.containers.ContainerPotatoDrier;
import aldinh777.potatoheadshot.block.containers.ContainerPotatoGenerator;
import aldinh777.potatoheadshot.block.guis.GuiPotatoDrier;
import aldinh777.potatoheadshot.block.guis.GuiPotatoGenerator;
import aldinh777.potatoheadshot.block.tileentities.TileEntityPotatoDrier;
import aldinh777.potatoheadshot.block.tileentities.TileEntityPotatoGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;
import java.util.Objects;

public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (ID == 1) {
            TileEntityPotatoDrier te = (TileEntityPotatoDrier) tileEntity;
            return new ContainerPotatoDrier(player.inventory, Objects.requireNonNull(te));
        } else if (ID == 2) {
            TileEntityPotatoGenerator te = (TileEntityPotatoGenerator) tileEntity;
            return new ContainerPotatoGenerator(player.inventory, Objects.requireNonNull(te));
        } else {
            return  null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (ID == 1) {
            TileEntityPotatoDrier te = (TileEntityPotatoDrier) tileEntity;
            return new GuiPotatoDrier(player.inventory, Objects.requireNonNull(te));
        } else if (ID == 2) {
            TileEntityPotatoGenerator te = (TileEntityPotatoGenerator) tileEntity;
            return new GuiPotatoGenerator(player.inventory, Objects.requireNonNull(te));
        } else {
            return  null;
        }
    }
}
