package aldinh777.potatoheadshot.block.backup.guis;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.block.backup.containers.ContainerSweetFreezer;
import aldinh777.potatoheadshot.block.backup.tileentities.TileEntitySweetFreezer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class GuiSweetFreezer extends GuiContainer {

    private static final String TEXTURE = PotatoHeadshot.MODID + ":textures/gui/container/sweet_freezer.png";
    private static final ResourceLocation TEXTURES = new ResourceLocation(TEXTURE);
    private final InventoryPlayer player;
    private final TileEntitySweetFreezer tileEntity;

    public GuiSweetFreezer(InventoryPlayer player, TileEntitySweetFreezer tileEntity) {
        super(new ContainerSweetFreezer(player, tileEntity));
        this.player = player;
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
        this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) + 10, 6, 4210752);
        this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 7, this.ySize - 96 + 2, 4210752);
        this.fontRenderer.drawString("RF : " + this.tileEntity.getField("energy"), 120, 72, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        drawEnergyStored();
        drawFreezeProgress();
    }

    private void drawEnergyStored() {
        int energy = this.tileEntity.getField("energy");
        int maxEnergy = this.tileEntity.getMaxEnergyStored();
        int i = (energy == 0 || maxEnergy == 0) ? 0 : energy * 57 / maxEnergy;

        this.drawTexturedModalRect(
                this.guiLeft + 10, this.guiTop + 11 + 56 - i,
                178, 33 + 56 - i,
                18, i + 1
        );
    }

    private void drawFreezeProgress() {
        int currentFreezeTime = this.tileEntity.getField("currentFreezeTime");
        int totalFreezeTime = this.tileEntity.getField("totalFreezeTime");
        int i = totalFreezeTime != 0 && currentFreezeTime != 0 ? currentFreezeTime * 24 / totalFreezeTime : 0;

        this.drawTexturedModalRect(
                this.guiLeft + 90, this.guiTop + 35,
                176, 14,
                i + 1, 16
        );
    }
}
