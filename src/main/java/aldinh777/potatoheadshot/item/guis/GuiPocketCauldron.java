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
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String tileName = "Pocket Cauldron";
        this.fontRenderer.drawString(tileName, this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2 + 10, 6, 4210752);
        this.fontRenderer.drawString(this.player.getDisplayName().getFormattedText(), 7, this.ySize - 96 + 2, 4210752);
        this.fontRenderer.drawString("Mana : " + PocketCauldron.getEnergy(stack).getManaStored(), 100, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        drawManaStored();
    }

    private void drawManaStored() {
        int manaSize = PocketCauldron.getEnergy(stack).getManaStored();
        int manaMaxSize = PocketCauldron.getEnergy(stack).getMaxManaStored();
        int i = (manaSize == 0 || manaMaxSize == 0) ? 0 : (manaSize * 146 / manaMaxSize);

        this.drawTexturedModalRect(
                this.guiLeft + 15, this.guiTop + 16,
                0, 166,
                i - 1, 18
        );
    }
}
