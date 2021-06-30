package aldinh777.potatoheadshot.other.handler;

import aldinh777.potatoheadshot.backup.containers.*;
import aldinh777.potatoheadshot.backup.guis.*;
import aldinh777.potatoheadshot.backup.tileentities.*;
import aldinh777.potatoheadshot.content.containers.ContainerDrier;
import aldinh777.potatoheadshot.content.containers.ContainerDrierUpgrade;
import aldinh777.potatoheadshot.content.guis.GuiDrier;
import aldinh777.potatoheadshot.content.guis.GuiDrierUpgrade;
import aldinh777.potatoheadshot.content.tileentities.TileEntityDrier;
import aldinh777.potatoheadshot.content.containers.ContainerPocketCauldron;
import aldinh777.potatoheadshot.content.guis.GuiPocketCauldron;
import aldinh777.potatoheadshot.other.util.Constants;
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
            case Constants.POTATO_GEN:
                TileEntitySweetPotatoGenerator gen = (TileEntitySweetPotatoGenerator) tileEntity;
                return new ContainerSweetPotatoGenerator(player.inventory, Objects.requireNonNull(gen));
            case Constants.FREEZER:
                TileEntitySweetFreezer freezer = (TileEntitySweetFreezer) tileEntity;
                return new ContainerSweetFreezer(player.inventory, Objects.requireNonNull(freezer));
            case Constants.CRYSTAL_MAKER:
                TileEntitySweetCrystalMaker crystalMaker = (TileEntitySweetCrystalMaker) tileEntity;
                return new ContainerSweetCrystalMaker(player.inventory, Objects.requireNonNull(crystalMaker));
            case Constants.CRYSTAL_CHARGER:
                TileEntitySweetCrystalCharger crystalCharger = (TileEntitySweetCrystalCharger) tileEntity;
                return new ContainerSweetCrystalCharger(player.inventory, Objects.requireNonNull(crystalCharger));
            case Constants.INFUSER:
                TileEntitySweetInfuser infuser = (TileEntitySweetInfuser) tileEntity;
                return new ContainerSweetInfuser(player.inventory, Objects.requireNonNull(infuser));
            case Constants.COLLECTOR:
                TileEntityManaCollector collector = (TileEntityManaCollector) tileEntity;
                return new ContainerManaCollector(player.inventory, Objects.requireNonNull(collector));
            case Constants.EXTRACTOR:
                TileEntityManaExtractor extractor = (TileEntityManaExtractor) tileEntity;
                return new ContainerManaExtractor(player.inventory, Objects.requireNonNull(extractor));
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
            case Constants.POTATO_GEN:
                TileEntitySweetPotatoGenerator gen = (TileEntitySweetPotatoGenerator) tileEntity;
                return new GuiSweetPotatoGenerator(player.inventory, Objects.requireNonNull(gen));
            case Constants.FREEZER:
                TileEntitySweetFreezer freezer = (TileEntitySweetFreezer) tileEntity;
                return new GuiSweetFreezer(player.inventory, Objects.requireNonNull(freezer));
            case Constants.CRYSTAL_MAKER:
                TileEntitySweetCrystalMaker crystalMaker = (TileEntitySweetCrystalMaker) tileEntity;
                return new GuiSweetCrystalMaker(player.inventory, Objects.requireNonNull(crystalMaker));
            case Constants.CRYSTAL_CHARGER:
                TileEntitySweetCrystalCharger crystalCharger = (TileEntitySweetCrystalCharger) tileEntity;
                return new GuiSweetCrystalCharger(player.inventory, Objects.requireNonNull(crystalCharger));
            case Constants.INFUSER:
                TileEntitySweetInfuser infuser = (TileEntitySweetInfuser) tileEntity;
                return new GuiSweetInfuser(player.inventory, Objects.requireNonNull(infuser));
            case Constants.COLLECTOR:
                TileEntityManaCollector collector = (TileEntityManaCollector) tileEntity;
                return new GuiManaCollector(player.inventory, Objects.requireNonNull(collector));
            case Constants.EXTRACTOR:
                TileEntityManaExtractor extractor = (TileEntityManaExtractor) tileEntity;
                return new GuiManaExtractor(player.inventory, Objects.requireNonNull(extractor));
            case Constants.POCKET_CAULDRON:
                return new GuiPocketCauldron(player);
        }
        return null;
    }
}
