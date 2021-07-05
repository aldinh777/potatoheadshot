package aldinh777.potatoheadshot;

import aldinh777.potatoheadshot.common.handler.RegistryHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = PotatoHeadshot.MODID, name = PotatoHeadshot.NAME, version = PotatoHeadshot.VERSION)
public class PotatoHeadshot {
    public static final String MODID = "potatoheadshot";
    public static final String NAME = "Potato Headshot";
    public static final String VERSION = "1.7.2";

    public static File CONFIG_DIR;
    public static Logger LOGGER;

    @Mod.Instance
    public static PotatoHeadshot INSTANCE;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        CONFIG_DIR = new File(event.getModConfigurationDirectory() + "/" + PotatoHeadshot.MODID);

        RegistryHandler.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        RegistryHandler.init();
    }
}
