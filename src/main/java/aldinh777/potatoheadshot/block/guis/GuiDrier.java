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
        if (upgrade.hasEnergyUpgrade()) {
            if (upgrade.hasWaterUpgrade()) {
                texture = TEXTURE_WATER_ENERGIZED;
            } else {
                texture = TEXTURE_BASIC_ENERGIZED;
            }
        } else {
            if (upgrade.hasWaterUpgrade()) {
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

        InventoryDrierUpgrade upgrade = tileEntity.getUpgrade();

        if (tileEntity.isBurning()) {
            drawBurnLeft();
        } else if (tileEntity.activeStateLimit > 10) {
            justDrawBurnIcon();
        }
        if (upgrade.hasWaterUpgrade() && tileEntity.waterProgress > 0) {
            drawWaterProgress();
            justDrawWaterIcon();
        }
        if (upgrade.hasWaterUpgrade()) {
            drawWaterVolume();
        }
        if (upgrade.hasFluxUpgrade()) {
            drawFluxStored();
        }
        if (upgrade.hasManaUpgrade()) {
            drawManaStored();
        }
        drawDryProgress();
    }

    private void justDrawBurnIcon() {
        drawTexturedModalRect(
                guiLeft + 68, guiTop + 27,
                176, 0,
                14, 13
        );
    }

    private void justDrawWaterIcon() {
        this.drawTexturedModalRect(
                this.guiLeft + 67, this.guiTop + 60,
                176, 33,
                15, 12
        );
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

    private void drawWaterProgress() {
        int wetTime = tileEntity.waterProgress;
        int totalWetTime = tileEntity.maxWaterProgress;
        int i = totalWetTime != 0 && wetTime != 0 ? wetTime * 24 / totalWetTime : 0;
        this.drawTexturedModalRect(
                this.guiLeft + 108, this.guiTop + 59,
                176, 14,
                i + 1, 16
        );
    }

    private void drawWaterVolume() {
        int waterSize = tileEntity.waterVolume;
        int maxWaterSize = tileEntity.maxWaterVolume;
        int i = waterSize == 0 ? 0 : waterSize * 53 / maxWaterSize;
        this.drawTexturedModalRect(
                this.guiLeft + 44, this.guiTop + 21 + 52 - i,
                176, 48 + 52 - i,
                11, i + 1
        );
    }

    private void drawFluxStored() {
        int energy = tileEntity.fluxStored;
        int maxEnergy = tileEntity.maxFluxStored;
        int i = (energy == 0 || maxEnergy == 0) ? 0 : energy * 68 / maxEnergy;
        this.drawTexturedModalRect(
                this.guiLeft + 14, this.guiTop + 9 + 68 - i,
                195, 103 + 68 - i,
                16, i + 1
        );
    }

    private void drawManaStored() {
        int energy = tileEntity.manaStored;
        int maxEnergy = tileEntity.maxManaStored;
        int i = (energy == 0 || maxEnergy == 0) ? 0 : energy * 68 / maxEnergy;
        this.drawTexturedModalRect(
                this.guiLeft + 14, this.guiTop + 9 + 68 - i,
                177, 103 + 68 - i,
                16, i + 1
        );
    }
}
