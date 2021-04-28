package aldinh777.potatoheadshot.block.backup.guis;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.block.backup.containers.ContainerPotatoDrier;
import aldinh777.potatoheadshot.block.backup.tileentities.TileEntityPotatoDrier;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class GuiPotatoDrier extends GuiContainer {

    private static final String TEXTURE = PotatoHeadshot.MODID + ":textures/gui/container/potato_drier.png";
    private static final ResourceLocation TEXTURES = new ResourceLocation(TEXTURE);
    private final TileEntityPotatoDrier tileEntity;

    public GuiPotatoDrier(InventoryPlayer player, TileEntityPotatoDrier tileEntity) {
        super(new ContainerPotatoDrier(player, tileEntity));
        this.tileEntity = tileEntity;
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

        if (TileEntityPotatoDrier.isBurning(tileEntity)) {
            drawBurnLeft();
        }
        if (TileEntityPotatoDrier.isWatering(tileEntity)) {
            drawWateringLeft();
        }
        drawWaterVolume();
        drawDryProgress();
        drawWetProgress();
    }

    private void drawWaterVolume() {
        int waterSize = this.tileEntity.getField("waterSize");
        int maxWaterSize = this.tileEntity.getMaxWaterSize();
        int i = waterSize == 0 ? 0 : waterSize * 53 / maxWaterSize;

        this.drawTexturedModalRect(
                this.guiLeft + 44, this.guiTop + 21 + 52 - i,
                176, 48 + 52 - i,
                11, i + 1
        );
    }

    private void drawBurnLeft() {
        int currentBurnTime = this.tileEntity.getField("currentBurnTime");
        int burnTime = this.tileEntity.getField("burnTime");
        int i = currentBurnTime == 0 ? 0 : burnTime * 13 / currentBurnTime;

        this.drawTexturedModalRect(
                this.guiLeft + 68, this.guiTop + 27 + 12 - i,
                176, 12 - i,
                14, i + 1
        );
    }

    private void drawWateringLeft() {
        int currentWateringTime = this.tileEntity.getField("currentWateringTime");
        int wateringTime = this.tileEntity.getField("wateringTime");
        int i = currentWateringTime == 0 ? 0 : wateringTime * 13 / currentWateringTime;

        this.drawTexturedModalRect(
                this.guiLeft + 67, this.guiTop + 60 + 12 - i,
                176, 33 + 12 - i,
                15, i + 1
        );
    }

    private void drawDryProgress() {
        int dryTime = this.tileEntity.getField("dryTime");
        int totalDryTime = this.tileEntity.getField("totalDryTime");
        int i = totalDryTime != 0 && dryTime != 0 ? dryTime * 24 / totalDryTime : 0;

        this.drawTexturedModalRect(
                this.guiLeft + 108, this.guiTop + 27,
                176, 14,
                i + 1, 16
        );
    }

    private void drawWetProgress() {
        int wetTime = this.tileEntity.getField("wetTime");
        int totalWetTime = this.tileEntity.getField("totalWetTime");
        int i = totalWetTime != 0 && wetTime != 0 ? wetTime * 24 / totalWetTime : 0;

        this.drawTexturedModalRect(
                this.guiLeft + 108, this.guiTop + 59,
                176, 14,
                i + 1, 16
        );
    }
}
