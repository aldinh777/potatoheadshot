package aldinh777.potatoheadshot.handler;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.block.backup.tileentities.*;
import aldinh777.potatoheadshot.block.tileentities.TileEntityDrier;
import aldinh777.potatoheadshot.energy.CapabilityMana;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = PotatoHeadshot.MODID)
public class RegistryHandler {

    public static void preInit() {
        ConfigHandler.init();

        PotatoItems.init();
        PotatoBlocks.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(PotatoHeadshot.INSTANCE, new GuiHandler());
        CapabilityMana.register();
    }

    public static void init() {
        OreDictionaryHandler.register();
        CustomRecipeHandler.registerDrier();
        CustomRecipeHandler.registerInfuser();

        registerSmelting();
    }

    public static void registerSmelting() {
        GameRegistry.addSmelting(PotatoItems.SWEET_POTATO, new ItemStack(PotatoItems.BAKED_SWEET_POTATO), 0.35f);
        GameRegistry.addSmelting(PotatoItems.SWEET_POTATO_DUST, new ItemStack(PotatoItems.SWEET_POTATO_INGOT), 0.075f);

        if (ConfigHandler.HOT_POTATO) {
            GameRegistry.addSmelting(Items.POISONOUS_POTATO, new ItemStack(PotatoItems.HOT_POTATO), 0.075f);
            GameRegistry.addSmelting(PotatoItems.HOT_POTATO, new ItemStack(PotatoItems.EXTRA_HOT_POTATO), 0.075f);
            GameRegistry.addSmelting(PotatoItems.EXTRA_HOT_POTATO, new ItemStack(PotatoItems.EXTREME_HOT_POTATO), 0.075f);
            if (ConfigHandler.LAVA_POTATO) {
                GameRegistry.addSmelting(PotatoItems.EXTREME_HOT_POTATO, new ItemStack(PotatoItems.LAVA_POTATO), 0.075f);
            }
        }
        if (ConfigHandler.SWEET_BUCKET) {
            GameRegistry.addSmelting(PotatoItems.SWEET_POTATO_BUCKET, new ItemStack(PotatoItems.SWEET_EMPTY_BUCKET), 0.35f);
        }
        if (ConfigHandler.POTATO_CHIP) {
            GameRegistry.addSmelting(PotatoItems.POTATO_CHIP, new ItemStack(PotatoItems.BAKED_POTATO_CHIP), 0.15f);
        }
        if (ConfigHandler.RED_POTATO) {
            GameRegistry.addSmelting(PotatoItems.RED_POTATO, new ItemStack(Items.REDSTONE), 0.35f);
        }
        if (ConfigHandler.COOKED_DIRT) {
            GameRegistry.addSmelting(Blocks.DIRT, new ItemStack(PotatoItems.COOKED_DIRT), 0.15f);
        }
        if (ConfigHandler.COOKED_POTATO_VARIANT) {
            if (ConfigHandler.POTATO_PLANKS) {
                GameRegistry.addSmelting(PotatoItems.POTATO_STICK, new ItemStack(PotatoItems.FRIED_FRIES), 0.01f);
                GameRegistry.addSmelting(PotatoItems.SMALL_POTATO_PLANKS, new ItemStack(PotatoItems.BAKED_SMALL_POTATO_PLANKS), 0.15f);
                GameRegistry.addSmelting(PotatoBlocks.POTATO_PLANKS, new ItemStack(PotatoItems.BAKED_POTATO_PLANKS), 0.45f);
            }
            GameRegistry.addSmelting(PotatoBlocks.POTATO_BLOCK, new ItemStack(PotatoItems.BAKED_POTATO_BLOCK), 0.45f);
        }
    }

    public static void registerTileEntity() {
        ResourceLocation potatoDrier = new ResourceLocation("potatoheadshot:potato_drier");
        ResourceLocation sweetPotatoGenerator = new ResourceLocation("potatoheadshot:sweet_potato_generator");
        ResourceLocation sweetFreezer = new ResourceLocation("potatoheadshot:sweet_freezer");
        ResourceLocation sweetCrystalMaker = new ResourceLocation("potatoheadshot:sweet_crystal_maker");
        ResourceLocation sweetCrystalCharger = new ResourceLocation("potatoheadshot:sweet_crystal_charger");
        ResourceLocation sweetInfuser = new ResourceLocation("potatoheadshot:sweet_infuser");
        ResourceLocation manaCollector = new ResourceLocation("potatoheadshot:mana_collector");
        ResourceLocation manaExtractor = new ResourceLocation("potatoheadshot:mana_extractor");
        ResourceLocation manaCauldron = new ResourceLocation("potatoheadshot:mana_cauldron");
        ResourceLocation energyTransfer = new ResourceLocation("potatoheadshot:energyTransfer");
        ResourceLocation ultManaCauldron = new ResourceLocation("potatoheadshot:ultimate_mana_cauldron");
        ResourceLocation ultCrystalCharger = new ResourceLocation("potatoheadshot:ultimate_crystal_charger");
        ResourceLocation magicDrier = new ResourceLocation("potatoheadshot:magic_drier");

        GameRegistry.registerTileEntity(TileEntityDrier.class, potatoDrier);
        GameRegistry.registerTileEntity(TileEntityMagicDrier.class, magicDrier);
        GameRegistry.registerTileEntity(TileEntitySweetPotatoGenerator.class, sweetPotatoGenerator);
        GameRegistry.registerTileEntity(TileEntitySweetFreezer.class, sweetFreezer);
        GameRegistry.registerTileEntity(TileEntitySweetCrystalMaker.class, sweetCrystalMaker);
        GameRegistry.registerTileEntity(TileEntitySweetCrystalCharger.class, sweetCrystalCharger);
        GameRegistry.registerTileEntity(TileEntityUltCrystalCharger.class, ultCrystalCharger);
        GameRegistry.registerTileEntity(TileEntitySweetInfuser.class, sweetInfuser);
        GameRegistry.registerTileEntity(TileEntityManaCollector.class, manaCollector);
        GameRegistry.registerTileEntity(TileEntityManaExtractor.class, manaExtractor);
        GameRegistry.registerTileEntity(TileEntityManaCauldron.class, manaCauldron);
        GameRegistry.registerTileEntity(TileEntityEnergyTransfer.class, energyTransfer);
        GameRegistry.registerTileEntity(TileEntityUltManaCauldron.class, ultManaCauldron);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(PotatoItems.LISTS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(PotatoBlocks.LISTS.toArray(new Block[0]));

        registerTileEntity();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item : PotatoItems.LISTS) {
            registerModel(item);
        }
    }

    private static void registerModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(
                item, 0, new ModelResourceLocation(
                        Objects.requireNonNull(item.getRegistryName()).toString(),
                        "inventory"
                )
        );
    }
}
