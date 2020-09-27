package aldinh777.potatoheadshot.item.guis;

import aldinh777.potatoheadshot.item.PocketCauldron;
import aldinh777.potatoheadshot.item.container.ContainerPocketCauldron;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiPocketCauldron extends GuiContainer {

    private static final String TEXTURE = "potatoheadshot:textures/gui/container/pocket_cauldron.png";
    private static final ResourceLocation TEXTURES = new ResourceLocation(TEXTURE);
    private final EntityPlayer player;
    private final ItemStack stack;

    public GuiPocketCauldron(EntityPlayer player) {
        super(new ContainerPocketCauldron(player));
        this.player = player;
        this.stack = PocketCauldron.findPocketCauldron(player);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String tileName = "Pocket Cauldron";
        this.fontRenderer.drawString(tileName, this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2 + 10, 6, 4210752);
        this.fontRenderer.drawString(this.player.getDisplayName().getFormattedText(), 7, this.ySize - 96 + 2, 4210752);
        this.fontRenderer.drawString("Mana : " + PocketCauldron.getManaSize(stack), 100, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        drawManaStored();
    }

    private void drawManaStored() {
        int manaSize = PocketCauldron.getManaSize(stack);
        int manaMaxSize = PocketCauldron.getMaxManaSize();
        int i = (manaSize == 0 || manaMaxSize == 0) ? 0 : (manaSize * 146 / manaMaxSize);

        this.drawTexturedModalRect(
                this.guiLeft + 15, this.guiTop + 16,
                0, 166,
                i - 1, 18
        );
    }
}
