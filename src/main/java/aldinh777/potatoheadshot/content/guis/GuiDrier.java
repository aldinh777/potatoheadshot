package aldinh777.potatoheadshot.content.guis;

import aldinh777.potatoheadshot.content.containers.ContainerDrier;
import aldinh777.potatoheadshot.content.tileentities.TileEntityDrier;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiDrier extends GuiMachine {

    private static final String TEXTURE = ":textures/gui/container/potato_drier.png";

    public GuiDrier(InventoryPlayer inventoryPlayer, TileEntityDrier tileEntity) {
        super(new ContainerDrier(inventoryPlayer, tileEntity), tileEntity, TEXTURE);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        if (tileEntity instanceof TileEntityDrier) {
            TileEntityDrier tileEntity = (TileEntityDrier) this.tileEntity;

            if (tileEntity.isBurning()) {
                drawBurnLeft(tileEntity);
            }

            drawWaterVolume(tileEntity);
            drawWaterProgress(tileEntity);
            drawDryProgress(tileEntity);
        }
    }

    private void drawBurnLeft(TileEntityDrier tileEntity) {
        int currentBurnTime = tileEntity.burnProgress;
        int burnTime = tileEntity.burnTime;
        int i = currentBurnTime == 0 ? 0 : burnTime * 13 / currentBurnTime;

        drawTexturedModalRect(
                guiLeft + 68, guiTop + 27 + 12 - i,
                176, 12 - i,
                14, i + 1
        );
    }

    private void drawDryProgress(TileEntityDrier tileEntity) {
        int dryTime = tileEntity.dryProgress;
        int totalDryTime = tileEntity.maxDryProgress;
        int i = totalDryTime != 0 && dryTime != 0 ? dryTime * 24 / totalDryTime : 0;
        drawTexturedModalRect(
                guiLeft + 108, guiTop + 27,
                176, 14,
                i + 1, 16
        );
    }

    private void drawWaterProgress(TileEntityDrier tileEntity) {
        int wetTime = tileEntity.waterProgress;
        int totalWetTime = tileEntity.maxWaterProgress;
        int i = totalWetTime != 0 && wetTime != 0 ? wetTime * 24 / totalWetTime : 0;
        this.drawTexturedModalRect(
                this.guiLeft + 108, this.guiTop + 59,
                176, 14,
                i + 1, 16
        );
    }

    private void drawWaterVolume(TileEntityDrier tileEntity) {
        int waterSize = tileEntity.waterVolume;
        int maxWaterSize = tileEntity.maxWaterVolume;
        int i = waterSize == 0 ? 0 : waterSize * 53 / maxWaterSize;
        this.drawTexturedModalRect(
                this.guiLeft + 44, this.guiTop + 21 + 52 - i,
                176, 48 + 52 - i,
                11, i + 1
        );
    }
}
