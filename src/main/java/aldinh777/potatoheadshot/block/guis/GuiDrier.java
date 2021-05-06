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
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String tileName = Objects.requireNonNull(tileEntity.getDisplayName()).getUnformattedText();
        fontRenderer.drawString(tileName, (xSize / 2 - fontRenderer.getStringWidth(tileName) / 2) + 3, 8, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(TEXTURES);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (tileEntity.isBurning()) {
            drawBurnLeft();
        }

        drawDryProgress();
    }

    private void drawBurnLeft() {
        int currentBurnTime = tileEntity.burnProgress;
        int burnTime = tileEntity.burnTime;
        int i = currentBurnTime == 0 ? 0 : burnTime * 13 / currentBurnTime;

        drawTexturedModalRect(
                guiLeft + 68, guiTop + 27 + 12 - i,
                176, 12 - i,
                14, i + 1
        );
    }

    private void drawDryProgress() {
        int dryTime = tileEntity.dryProgress;
        int totalDryTime = tileEntity.maxDryProgress;
        int i = totalDryTime != 0 && dryTime != 0 ? dryTime * 24 / totalDryTime : 0;

        drawTexturedModalRect(
                guiLeft + 108, guiTop + 27,
                176, 14,
                i + 1, 16
        );
    }
}
