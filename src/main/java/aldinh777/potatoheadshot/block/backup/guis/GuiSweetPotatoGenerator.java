package aldinh777.potatoheadshot.block.backup.guis;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.block.backup.containers.ContainerSweetPotatoGenerator;
import aldinh777.potatoheadshot.block.backup.tileentities.TileEntitySweetPotatoGenerator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class GuiSweetPotatoGenerator extends GuiContainer {

    private static final String TEXTURE = PotatoHeadshot.MODID + ":textures/gui/container/sweet_potato_generator.png";
    private static final ResourceLocation TEXTURES = new ResourceLocation(TEXTURE);
    private final InventoryPlayer player;
    private final TileEntitySweetPotatoGenerator tileEntity;

    public GuiSweetPotatoGenerator(InventoryPlayer player, TileEntitySweetPotatoGenerator tileEntity) {
        super(new ContainerSweetPotatoGenerator(player, tileEntity));
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
        drawCookProgress();
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

    private void drawCookProgress() {
        int currentCookTime = this.tileEntity.getField("currentCookTime");
        int totalCookTime = this.tileEntity.getField("totalCookTime");
        int i = totalCookTime != 0 && currentCookTime != 0 ? currentCookTime * 24 / totalCookTime : 0;

        if (i > 0) {
            this.drawBurning();
        }

        this.drawTexturedModalRect(
                this.guiLeft + 90, this.guiTop + 35,
                176, 14,
                i + 1, 16
        );
    }

    private void drawBurning() {
        this.drawTexturedModalRect(
                this.guiLeft + 63, this.guiTop + 53,
                176, 0,
                14, 14
        );
    }
}