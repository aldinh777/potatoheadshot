package aldinh777.potatoheadshot.block.guis;

import aldinh777.potatoheadshot.block.containers.ContainerMana;
import aldinh777.potatoheadshot.block.tileentities.TileEntityManaCollector;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public abstract class GuiMana extends GuiContainer {
	
	private static final String TEXTURE = "potatoheadshot:textures/gui/container/mana_collector.png";
	private static final ResourceLocation TEXTURES = new ResourceLocation(TEXTURE);
	private final InventoryPlayer player;
	private final TileEntityManaCollector tileEntity;
	
	public GuiMana(InventoryPlayer player, TileEntityManaCollector tileEntity, ContainerMana container) {
		super(container);
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
		String tileName = Objects.requireNonNull(this.tileEntity.getDisplayName()).getFormattedText();
		this.fontRenderer.drawString(tileName, this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2 + 10, 6, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getFormattedText(), 7, this.ySize - 96 + 2, 4210752);
		this.fontRenderer.drawString("Mana : " + this.tileEntity.getField("manaSize"), 100, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		drawManaStored();
	}
	
	private void drawManaStored() {
		int manaSize = this.tileEntity.getField("manaSize");
		int manaMaxSize = this.tileEntity.getField("manaMaxSize");
		int i = (manaSize == 0 || manaMaxSize == 0) ? 0 : (manaSize * 146 / manaMaxSize);
		
		this.drawTexturedModalRect(
						this.guiLeft + 15, this.guiTop + 16,
						0, 166,
						i - 1, 18
		);
	}
}
