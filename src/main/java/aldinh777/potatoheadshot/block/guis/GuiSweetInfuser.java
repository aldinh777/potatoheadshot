package aldinh777.potatoheadshot.block.guis;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.block.containers.ContainerSweetInfuser;
import aldinh777.potatoheadshot.block.tileentities.TileEntitySweetInfuser;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSweetInfuser extends GuiContainer {

    private static final String TEXTURE = PotatoHeadshot.MODID + ":textures/gui/container/sweet_Infuser.png";
    private static final ResourceLocation TEXTURES = new ResourceLocation(TEXTURE);
    private final InventoryPlayer player;
    private final TileEntitySweetInfuser tileEntity;

    public GuiSweetInfuser(InventoryPlayer player, TileEntitySweetInfuser tileEntity) {
        super(new ContainerSweetInfuser(player, tileEntity));
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
        this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 7, this.ySize - 96 + 2, 4210752);
        this.fontRenderer.drawString("RF : " + this.tileEntity.getField("energy"), 120, 72, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        drawEnergyStored();
        drawInfuseProgress();
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

    private void drawInfuseProgress() {
        int currentInfuseTime = this.tileEntity.getField("currentInfuseTime");
        int totalInfuseTime = this.tileEntity.getField("totalInfuseTime");
        int i = totalInfuseTime != 0 && currentInfuseTime != 0 ? currentInfuseTime * 44 / totalInfuseTime : 0;

        this.drawTexturedModalRect(
                this.guiLeft + 151, this.guiTop + 17 + 43 - i,
                200, 43 - i,
                15, i + 1
        );
    }
}
