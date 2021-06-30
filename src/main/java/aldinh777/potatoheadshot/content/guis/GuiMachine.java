package aldinh777.potatoheadshot.content.guis;

import aldinh777.potatoheadshot.PotatoHeadshot;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class GuiMachine extends GuiContainer {

    protected TileEntity tileEntity;
    protected ResourceLocation TEXTURES;

    public GuiMachine(Container inventorySlotsIn, TileEntity tileEntity, String texture) {
        super(inventorySlotsIn);
        this.tileEntity = tileEntity;
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
    }
}
