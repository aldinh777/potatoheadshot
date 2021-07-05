package aldinh777.potatoheadshot.common.handler;

import aldinh777.potatoheadshot.content.containers.ContainerDrier;
import aldinh777.potatoheadshot.content.containers.ContainerDrierUpgrade;
import aldinh777.potatoheadshot.content.containers.ContainerPocketCauldron;
import aldinh777.potatoheadshot.content.guis.GuiDrier;
import aldinh777.potatoheadshot.content.guis.GuiDrierUpgrade;
import aldinh777.potatoheadshot.content.guis.GuiPocketCauldron;
import aldinh777.potatoheadshot.content.tileentities.TileEntityDrier;
import aldinh777.potatoheadshot.common.util.Constants;
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
        switch (ID) {
            case Constants.DRIER:
                TileEntityDrier drier = (TileEntityDrier) tileEntity;
                return new ContainerDrier(player.inventory, Objects.requireNonNull(drier));
            case Constants.DRIER_UPGRADE:
                TileEntityDrier drierUpgrade = (TileEntityDrier) tileEntity;
                return new ContainerDrierUpgrade(player.inventory, Objects.requireNonNull(drierUpgrade));
            case Constants.POCKET_CAULDRON:
                return new ContainerPocketCauldron(player);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID) {
            case Constants.DRIER:
                TileEntityDrier drier = (TileEntityDrier) tileEntity;
                return new GuiDrier(player.inventory, Objects.requireNonNull(drier));
            case Constants.DRIER_UPGRADE:
                TileEntityDrier drierUpgrade = (TileEntityDrier) tileEntity;
                return new GuiDrierUpgrade(player.inventory, Objects.requireNonNull(drierUpgrade));
            case Constants.POCKET_CAULDRON:
                return new GuiPocketCauldron(player);
        }
        return null;
    }
}
