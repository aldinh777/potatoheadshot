package aldinh777.potatoheadshot.handler;

import aldinh777.potatoheadshot.block.containers.*;
import aldinh777.potatoheadshot.block.guis.*;
import aldinh777.potatoheadshot.block.tileentities.*;
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
            case 1:
                TileEntityPotatoDrier drier = (TileEntityPotatoDrier) tileEntity;
                return new ContainerPotatoDrier(player.inventory, Objects.requireNonNull(drier));
            case 2:
                TileEntitySweetPotatoGenerator gen = (TileEntitySweetPotatoGenerator) tileEntity;
                return new ContainerSweetPotatoGenerator(player.inventory, Objects.requireNonNull(gen));
            case 3:
                TileEntitySweetFreezer freezer = (TileEntitySweetFreezer) tileEntity;
                return new ContainerSweetFreezer(player.inventory, Objects.requireNonNull(freezer));
            case 4:
                TileEntitySweetCrystalMaker crystalMaker = (TileEntitySweetCrystalMaker) tileEntity;
                return new ContainerSweetCrystalMaker(player.inventory, Objects.requireNonNull(crystalMaker));
            case 5:
                TileEntitySweetCrystalCharger crystalCharger = (TileEntitySweetCrystalCharger) tileEntity;
                return new ContainerSweetCrystalCharger(player.inventory, Objects.requireNonNull(crystalCharger));
            case 6:
                TileEntitySweetInfuser infuser = (TileEntitySweetInfuser) tileEntity;
                return new ContainerSweetInfuser(player.inventory, Objects.requireNonNull(infuser));
            case 7:
                TileEntityManaCollector collector = (TileEntityManaCollector) tileEntity;
                return new ContainerManaCollector(player.inventory, Objects.requireNonNull(collector));
            case 8:
                TileEntityManaExtractor extractor = (TileEntityManaExtractor) tileEntity;
                return new ContainerManaExtractor(player.inventory, Objects.requireNonNull(extractor));
            default:
                return  null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID) {
            case 1:
                TileEntityPotatoDrier drier = (TileEntityPotatoDrier) tileEntity;
                return new GuiPotatoDrier(player.inventory, Objects.requireNonNull(drier));
            case 2:
                TileEntitySweetPotatoGenerator gen = (TileEntitySweetPotatoGenerator) tileEntity;
                return new GuiSweetPotatoGenerator(player.inventory, Objects.requireNonNull(gen));
            case 3:
                TileEntitySweetFreezer freezer = (TileEntitySweetFreezer) tileEntity;
                return new GuiSweetFreezer(player.inventory, Objects.requireNonNull(freezer));
            case 4:
                TileEntitySweetCrystalMaker crystalMaker = (TileEntitySweetCrystalMaker) tileEntity;
                return new GuiSweetCrystalMaker(player.inventory, Objects.requireNonNull(crystalMaker));
            case 5:
                TileEntitySweetCrystalCharger crystalCharger = (TileEntitySweetCrystalCharger) tileEntity;
                return new GuiSweetCrystalCharger(player.inventory, Objects.requireNonNull(crystalCharger));
            case 6:
                TileEntitySweetInfuser infuser = (TileEntitySweetInfuser) tileEntity;
                return new GuiSweetInfuser(player.inventory, Objects.requireNonNull(infuser));
            case 7:
                TileEntityManaCollector collector = (TileEntityManaCollector) tileEntity;
                return new GuiManaCollector(player.inventory, Objects.requireNonNull(collector));
            case 8:
                TileEntityManaExtractor extractor = (TileEntityManaExtractor) tileEntity;
                return new GuiManaExtractor(player.inventory, Objects.requireNonNull(extractor));
            default:
                return  null;
        }
    }
}
