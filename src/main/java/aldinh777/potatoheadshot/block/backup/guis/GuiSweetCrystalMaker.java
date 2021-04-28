package aldinh777.potatoheadshot.block.backup.guis;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.block.backup.containers.ContainerSweetCrystalMaker;
import aldinh777.potatoheadshot.block.backup.tileentities.TileEntitySweetCrystalMaker;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class GuiSweetCrystalMaker extends GuiContainer {

    private static final String TEXTURE = PotatoHeadshot.MODID + ":textures/gui/container/sweet_crystal_maker.png";
    private static final ResourceLocation TEXTURES = new ResourceLocation(TEXTURE);
    private final InventoryPlayer player;
    private final TileEntitySweetCrystalMaker tileEntity;

    public GuiSweetCrystalMaker(InventoryPlayer player, TileEntitySweetCrystalMaker tileEntity) {
        super(new ContainerSweetCrystalMaker(player, tileEntity));
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
        drawCrystalProgress();
        drawCrystalization();
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

    private void drawCrystalProgress() {
        int crystalTime = this.tileEntity.getField("crystalTime");
        int totalCrystalTime = this.tileEntity.getField("totalCrystalTime");
        int i = totalCrystalTime != 0 && crystalTime != 0 ? crystalTime * 21 / totalCrystalTime : 0;

        this.drawTexturedModalRect(
                this.guiLeft + 61, this.guiTop + 27,
                176, 91,
                27, i + 1
        );
    }

    private void drawCrystalization() {
        int crystalization = this.tileEntity.getField("crystalization");
        int totalCrystalization = this.tileEntity.getField("totalCrystalization");
        int i = totalCrystalization != 0 && crystalization != 0 ? crystalization * 103 / totalCrystalization : 0;

        this.drawTexturedModalRect(
                this.guiLeft + 63, this.guiTop + 52,
                0, 166,
                i + 1, 14
        );
    }
}
