package aldinh777.potatoheadshot.handler;

import aldinh777.potatoheadshot.block.containers.*;
import aldinh777.potatoheadshot.block.guis.*;
import aldinh777.potatoheadshot.block.tileentities.*;
import aldinh777.potatoheadshot.item.container.ContainerPocketCauldron;
import aldinh777.potatoheadshot.item.guis.GuiPocketCauldron;
import aldinh777.potatoheadshot.util.Constants;
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
                if (ConfigHandler.POTATO_DRIER) {
                    TileEntityPotatoDrier drier = (TileEntityPotatoDrier) tileEntity;
                    return new ContainerPotatoDrier(player.inventory, Objects.requireNonNull(drier));
                }
                break;
            case Constants.MAGIC_DRIER:
                if (ConfigHandler.POTATO_DRIER) {
                    TileEntityMagicDrier drier = (TileEntityMagicDrier) tileEntity;
                    return new ContainerMagicDrier(player.inventory, Objects.requireNonNull(drier));
                }
                break;
            case Constants.POTATO_GEN:
                if (ConfigHandler.SWEET_POTATO_GENERATOR) {
                    TileEntitySweetPotatoGenerator gen = (TileEntitySweetPotatoGenerator) tileEntity;
                    return new ContainerSweetPotatoGenerator(player.inventory, Objects.requireNonNull(gen));
                }
                break;
            case Constants.FREEZER:
                if (ConfigHandler.SWEET_FREEZER) {
                    TileEntitySweetFreezer freezer = (TileEntitySweetFreezer) tileEntity;
                    return new ContainerSweetFreezer(player.inventory, Objects.requireNonNull(freezer));
                }
                break;
            case Constants.CRYSTAL_MAKER:
                if (ConfigHandler.SWEET_CRYSTAL_MAKER) {
                    TileEntitySweetCrystalMaker crystalMaker = (TileEntitySweetCrystalMaker) tileEntity;
                    return new ContainerSweetCrystalMaker(player.inventory, Objects.requireNonNull(crystalMaker));
                }
                break;
            case Constants.CRYSTAL_CHARGER:
                if (ConfigHandler.SWEET_CRYSTAL_CHARGER) {
                    TileEntitySweetCrystalCharger crystalCharger = (TileEntitySweetCrystalCharger) tileEntity;
                    return new ContainerSweetCrystalCharger(player.inventory, Objects.requireNonNull(crystalCharger));
                }
                break;
            case Constants.INFUSER:
                if (ConfigHandler.SWEET_INFUSER) {
                    TileEntitySweetInfuser infuser = (TileEntitySweetInfuser) tileEntity;
                    return new ContainerSweetInfuser(player.inventory, Objects.requireNonNull(infuser));
                }
                break;
            case Constants.COLLECTOR:
                if (ConfigHandler.MANA_COLLECTOR) {
                    TileEntityManaCollector collector = (TileEntityManaCollector) tileEntity;
                    return new ContainerManaCollector(player.inventory, Objects.requireNonNull(collector));
                }
                break;
            case Constants.EXTRACTOR:
                if (ConfigHandler.MANA_EXTRACTOR) {
                    TileEntityManaExtractor extractor = (TileEntityManaExtractor) tileEntity;
                    return new ContainerManaExtractor(player.inventory, Objects.requireNonNull(extractor));
                }
                break;
            case Constants.POCKET_CAULDRON:
                if (ConfigHandler.MANA_CAULDRON) {
                    return new ContainerPocketCauldron(player);
                }
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID) {
            case Constants.DRIER:
                if (ConfigHandler.POTATO_DRIER) {
                    TileEntityPotatoDrier drier = (TileEntityPotatoDrier) tileEntity;
                    return new GuiPotatoDrier(player.inventory, Objects.requireNonNull(drier));
                }
                break;
            case Constants.MAGIC_DRIER:
                if (ConfigHandler.POTATO_DRIER) {
                    TileEntityMagicDrier drier = (TileEntityMagicDrier) tileEntity;
                    return new GuiMagicDrier(player.inventory, Objects.requireNonNull(drier));
                }
                break;
            case Constants.POTATO_GEN:
                if (ConfigHandler.SWEET_POTATO_GENERATOR) {
                    TileEntitySweetPotatoGenerator gen = (TileEntitySweetPotatoGenerator) tileEntity;
                    return new GuiSweetPotatoGenerator(player.inventory, Objects.requireNonNull(gen));
                }
                break;
            case Constants.FREEZER:
                if (ConfigHandler.SWEET_FREEZER) {
                    TileEntitySweetFreezer freezer = (TileEntitySweetFreezer) tileEntity;
                    return new GuiSweetFreezer(player.inventory, Objects.requireNonNull(freezer));
                }
                break;
            case Constants.CRYSTAL_MAKER:
                if (ConfigHandler.SWEET_CRYSTAL_MAKER) {
                    TileEntitySweetCrystalMaker crystalMaker = (TileEntitySweetCrystalMaker) tileEntity;
                    return new GuiSweetCrystalMaker(player.inventory, Objects.requireNonNull(crystalMaker));
                }
                break;
            case Constants.CRYSTAL_CHARGER:
                if (ConfigHandler.SWEET_CRYSTAL_CHARGER) {
                    TileEntitySweetCrystalCharger crystalCharger = (TileEntitySweetCrystalCharger) tileEntity;
                    return new GuiSweetCrystalCharger(player.inventory, Objects.requireNonNull(crystalCharger));
                }
                break;
            case Constants.INFUSER:
                if (ConfigHandler.SWEET_INFUSER) {
                    TileEntitySweetInfuser infuser = (TileEntitySweetInfuser) tileEntity;
                    return new GuiSweetInfuser(player.inventory, Objects.requireNonNull(infuser));
                }
                break;
            case Constants.COLLECTOR:
                if (ConfigHandler.MANA_COLLECTOR) {
                    TileEntityManaCollector collector = (TileEntityManaCollector) tileEntity;
                    return new GuiManaCollector(player.inventory, Objects.requireNonNull(collector));
                }
                break;
            case Constants.EXTRACTOR:
                if (ConfigHandler.MANA_EXTRACTOR) {
                    TileEntityManaExtractor extractor = (TileEntityManaExtractor) tileEntity;
                    return new GuiManaExtractor(player.inventory, Objects.requireNonNull(extractor));
                }
                break;
            case Constants.POCKET_CAULDRON:
                if (ConfigHandler.MANA_CAULDRON) {
                    return new GuiPocketCauldron(player);
                }
        }
        return null;
    }
}
