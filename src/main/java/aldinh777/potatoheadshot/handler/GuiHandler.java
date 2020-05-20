package aldinh777.potatoheadshot.handler;

import aldinh777.potatoheadshot.block.containers.ContainerPotatoDrier;
import aldinh777.potatoheadshot.block.containers.ContainerSweetFreezer;
import aldinh777.potatoheadshot.block.containers.ContainerSweetPotatoGenerator;
import aldinh777.potatoheadshot.block.containers.ContainerSweetInfuser;
import aldinh777.potatoheadshot.block.guis.GuiPotatoDrier;
import aldinh777.potatoheadshot.block.guis.GuiSweetFreezer;
import aldinh777.potatoheadshot.block.guis.GuiSweetPotatoGenerator;
import aldinh777.potatoheadshot.block.guis.GuiSweetInfuser;
import aldinh777.potatoheadshot.block.tileentities.TileEntityPotatoDrier;
import aldinh777.potatoheadshot.block.tileentities.TileEntitySweetFreezer;
import aldinh777.potatoheadshot.block.tileentities.TileEntitySweetPotatoGenerator;
import aldinh777.potatoheadshot.block.tileentities.TileEntitySweetInfuser;
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
            TileEntitySweetPotatoGenerator te = (TileEntitySweetPotatoGenerator) tileEntity;
            return new ContainerSweetPotatoGenerator(player.inventory, Objects.requireNonNull(te));
        } else if (ID == 3) {
            TileEntitySweetFreezer te = (TileEntitySweetFreezer) tileEntity;
            return new ContainerSweetFreezer(player.inventory, Objects.requireNonNull(te));
        } else if (ID == 4) {
            TileEntitySweetInfuser te = (TileEntitySweetInfuser) tileEntity;
            return new ContainerSweetInfuser(player.inventory, Objects.requireNonNull(te));
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
            TileEntitySweetPotatoGenerator te = (TileEntitySweetPotatoGenerator) tileEntity;
            return new GuiSweetPotatoGenerator(player.inventory, Objects.requireNonNull(te));
        } else if (ID == 3) {
            TileEntitySweetFreezer te = (TileEntitySweetFreezer) tileEntity;
            return new GuiSweetFreezer(player.inventory, Objects.requireNonNull(te));
        } else if (ID == 4) {
            TileEntitySweetInfuser te = (TileEntitySweetInfuser) tileEntity;
            return new GuiSweetInfuser(player.inventory, Objects.requireNonNull(te));
        } else {
            return  null;
        }
    }
}
