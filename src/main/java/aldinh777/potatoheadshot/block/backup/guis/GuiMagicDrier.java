package aldinh777.potatoheadshot.block.backup.guis;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.block.backup.containers.ContainerMagicDrier;
import aldinh777.potatoheadshot.block.backup.tileentities.TileEntityMagicDrier;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class GuiMagicDrier extends GuiContainer {

    private static final String TEXTURE = PotatoHeadshot.MODID + ":textures/gui/container/magic_drier.png";
    private static final ResourceLocation TEXTURES = new ResourceLocation(TEXTURE);
    private final TileEntityMagicDrier tileEntity;

    public GuiMagicDrier(InventoryPlayer player, TileEntityMagicDrier tileEntity) {
        super(new ContainerMagicDrier(player, tileEntity));
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

        drawBurnLeft();
        drawWateringLeft();

        drawManaVolume();
        drawWaterVolume();
        drawDryProgress();
        drawWetProgress();
    }

    private void drawManaVolume() {
        int manaSize = this.tileEntity.getField("mana");
        int maxManaSize = this.tileEntity.getMaxManaStored();
        int i = manaSize == 0 ? 0 : manaSize * 69 / maxManaSize;

        this.drawTexturedModalRect(
                this.guiLeft + 13, this.guiTop + 8 + 68 - i,
                176, 102 + 68 - i,
                18, i + 1
        );
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
        int totalDryTime = this.tileEntity.getField("totalDryTime");
        int dryTime = this.tileEntity.getField("dryTime");
        int i = dryTime == 0 ? 12 : dryTime * 13 / totalDryTime;

        this.drawTexturedModalRect(
                this.guiLeft + 68, this.guiTop + 27 + i,
                176, i,
                14, 12 - i
        );
    }

    private void drawWateringLeft() {
        int totalWetTime = this.tileEntity.getField("totalWetTime");
        int wetTime = this.tileEntity.getField("wetTime");
        int i = wetTime == 0 ? 12 : wetTime * 13 / totalWetTime;

        this.drawTexturedModalRect(
                this.guiLeft + 67, this.guiTop + 60 + i,
                176, 33 + i,
                15, 12 - i
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
