package aldinh777.potatoheadshot.block.guis;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.block.containers.ContainerVoidExchanger;
import aldinh777.potatoheadshot.block.tileentities.TileEntityVoidExchanger;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class GuiVoidExchanger extends GuiContainer {

    private static final String TEXTURE = PotatoHeadshot.MODID + ":textures/gui/container/void_exchanger.png";
    private static final ResourceLocation TEXTURES = new ResourceLocation(TEXTURE);
    private final InventoryPlayer player;
    private final TileEntityVoidExchanger tileEntity;

    public GuiVoidExchanger(InventoryPlayer player, TileEntityVoidExchanger tileEntity) {
        super(new ContainerVoidExchanger(player, tileEntity));
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
        this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2), 6, 4210752);
        this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 7, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        drawExchangeProgress();
    }

    private void drawExchangeProgress() {
        int progress = this.tileEntity.getField("progress");
        int cost = this.tileEntity.getField("cost");
        int i = (progress == 0 || cost == 0) ? 0 : progress * 24 / cost;

        this.drawTexturedModalRect(
                this.guiLeft + 75, this.guiTop + 47,
                176, 14,
                i + 1, 16
        );
    }
}
