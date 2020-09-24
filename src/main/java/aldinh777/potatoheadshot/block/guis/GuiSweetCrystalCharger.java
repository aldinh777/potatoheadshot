package aldinh777.potatoheadshot.block.guis;

import aldinh777.potatoheadshot.block.containers.ContainerSweetCrystalCharger;
import aldinh777.potatoheadshot.block.tileentities.TileEntitySweetCrystalCharger;
import java.util.Objects;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiSweetCrystalCharger extends GuiContainer {

	private static final String TEXTURE = "potatoheadshot:textures/gui/container/sweet_crystal_charger.png";
	private static final ResourceLocation TEXTURES = new ResourceLocation("potatoheadshot:textures/gui/container/sweet_crystal_charger.png");
	private final InventoryPlayer player;
	private final TileEntitySweetCrystalCharger tileEntity;
	
	public GuiSweetCrystalCharger(InventoryPlayer player, TileEntitySweetCrystalCharger tileEntity) {
		super(new ContainerSweetCrystalCharger(player, tileEntity));
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
		String tileName = ((ITextComponent)Objects.<ITextComponent>requireNonNull(this.tileEntity.getDisplayName())).getUnformattedText();
		this.fontRenderer.drawString(tileName, this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2 + 10, 6, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 7, this.ySize - 96 + 2, 4210752);
		this.fontRenderer.drawString("RF : " + this.tileEntity.getField("energy"), 100, 72, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		drawEnergyStored();
		drawChargeProgress();
	}
	
	private void drawEnergyStored() {
		int energy = this.tileEntity.getField("energy");
		int maxEnergy = this.tileEntity.getMaxEnergyStored();
		int i = (energy == 0 || maxEnergy == 0) ? 0 : (energy * 57 / maxEnergy);
		
		this.drawTexturedModalRect(
		        this.guiLeft + 10, this.guiTop + 11 + 56 - i,
                178, 89 - i,
                18, i + 1
        );
	}

	private void drawChargeProgress() {
		int currentCharged = this.tileEntity.getField("currentCharged");
		int fullCharge = this.tileEntity.getField("fullCharge");
		int i = (fullCharge != 0 && currentCharged != 0) ? (currentCharged * 124 / fullCharge) : 0;
		
		this.drawTexturedModalRect(
		        this.guiLeft + 39, this.guiTop + 52,
                0, 166,
                i + 1, 25
        );
	}
}
