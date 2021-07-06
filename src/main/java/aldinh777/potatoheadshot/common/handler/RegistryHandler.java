package aldinh777.potatoheadshot.common.handler;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.content.capability.CapabilityBlood;
import aldinh777.potatoheadshot.content.tileentities.TileEntityDrier;
import aldinh777.potatoheadshot.content.capability.CapabilityMana;
import aldinh777.potatoheadshot.content.tileentities.TileEntityEnergyTransfer;
import aldinh777.potatoheadshot.content.tileentities.TileEntityManaCauldron;
import aldinh777.potatoheadshot.common.compat.OreDictionaryCompat;
import aldinh777.potatoheadshot.common.lists.PotatoBlocks;
import aldinh777.potatoheadshot.common.lists.PotatoItems;
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
        CapabilityBlood.register();
    }

    public static void init() {
        OreDictionaryCompat.register();
        CustomRecipeHandler.registerDrier();

        registerSmelting();
    }

    public static void registerSmelting() {
        GameRegistry.addSmelting(PotatoItems.SWEET_POTATO, new ItemStack(PotatoItems.BAKED_SWEET_POTATO), 0.35f);
        GameRegistry.addSmelting(PotatoItems.SWEET_POTATO_DUST, new ItemStack(PotatoItems.SWEET_POTATO_INGOT), 0.075f);

        if (ConfigHandler.HOT_POTATO) {
            GameRegistry.addSmelting(Items.POISONOUS_POTATO, new ItemStack(PotatoItems.HOT_POTATO), 0.075f);
            if (ConfigHandler.LAVA_POTATO) {
                GameRegistry.addSmelting(PotatoItems.HOT_POTATO, new ItemStack(PotatoItems.LAVA_POTATO), 0.075f);
            }
        }
        if (ConfigHandler.SWEET_BUCKET) {
            GameRegistry.addSmelting(PotatoItems.SWEET_POTATO_BUCKET, new ItemStack(PotatoItems.SWEET_EMPTY_BUCKET), 0.35f);
        }
        if (ConfigHandler.POTATO_CHIP) {
            GameRegistry.addSmelting(PotatoItems.POTATO_CHIP, new ItemStack(PotatoItems.BAKED_POTATO_CHIP), 0.15f);
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
        ResourceLocation manaCauldron = new ResourceLocation("potatoheadshot:mana_cauldron");
        ResourceLocation energyTransfer = new ResourceLocation("potatoheadshot:energyTransfer");

        GameRegistry.registerTileEntity(TileEntityDrier.class, potatoDrier);
        GameRegistry.registerTileEntity(TileEntityManaCauldron.class, manaCauldron);
        GameRegistry.registerTileEntity(TileEntityEnergyTransfer.class, energyTransfer);
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
