package aldinh777.potatoheadshot.block.guis;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.block.containers.ContainerDrier;
import aldinh777.potatoheadshot.block.inventory.InventoryDrierUpgrade;
import aldinh777.potatoheadshot.block.tileentities.TileEntityDrier;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class GuiDrier extends GuiContainer {

    private static final String TEXTURE_BASIC = ":textures/gui/container/potato_drier_basic.png";
    private static final String TEXTURE_WATER = ":textures/gui/container/potato_drier_water.png";
    private static final String TEXTURE_BASIC_ENERGIZED = ":textures/gui/container/potato_drier_basic_energized.png";
    private static final String TEXTURE_WATER_ENERGIZED = ":textures/gui/container/potato_drier_water_energized.png";

    private final ResourceLocation TEXTURES;
    private final TileEntityDrier tileEntity;

    public GuiDrier(InventoryPlayer inventoryPlayer, TileEntityDrier tileEntity) {
        super(new ContainerDrier(inventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        InventoryDrierUpgrade upgrade = tileEntity.getUpgrade();
        String texture;
        if (upgrade.hasEnergyCapacity()) {
            if (upgrade.hasWaterCapacity()) {
                texture = TEXTURE_WATER_ENERGIZED;
            } else {
                texture = TEXTURE_BASIC_ENERGIZED;
            }
        } else {
            if (upgrade.hasWaterCapacity()) {
                texture = TEXTURE_WATER;
            } else {
                texture = TEXTURE_BASIC;
            }
        }
        TEXTURES = new ResourceLocation(PotatoHeadshot.MODID + texture);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String tileName = Objects.requireNonNull(this.tileEntity.getDisplayName()).getUnformattedText();
        this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) + 3, 8, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }
}
