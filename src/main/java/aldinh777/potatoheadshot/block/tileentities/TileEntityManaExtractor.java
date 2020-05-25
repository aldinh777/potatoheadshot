package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.ManaExtractor;
import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class TileEntityManaExtractor extends TileEntityManaCollector {

    // Override Method

    @Override
    public void update() {
        if (!this.world.isRemote) {
            boolean flag = false;

            if (this.canExtract()) {
                this.extractMana();
            }

            if (this.canCollect()) {
                this.storage.collectMana(1);
            }

            if (this.canTransferMana()) {
                this.transferMana();
            }

            if (this.manaSize != this.storage.getManaStored()) {
                this.manaSize = this.storage.getManaStored();
                flag = true;
            }

            if (flag) {
                this.markDirty();
            }
        }
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return customOrDefaultName("Mana Extractor");
    }

    // Custom Methods

    private boolean canTransferMana() {
        EnumFacing behind = this.world.getBlockState(pos).getValue(ManaExtractor.FACING).getOpposite();
        TileEntity cauldron = this.world.getTileEntity(pos.offset(behind));

        if (cauldron instanceof TileEntityManaCauldron) {
            return this.storage.getManaStored() > 0;
        }
        return false;
    }

    private void transferMana() {
        EnumFacing behind = this.world.getBlockState(pos).getValue(ManaExtractor.FACING).getOpposite();
        TileEntity tileEntity = this.world.getTileEntity(pos.offset(behind));
        TileEntityManaCauldron cauldron = (TileEntityManaCauldron)tileEntity;

        if (cauldron != null) {
            PotatoManaStorage targetStorage = cauldron.getManaStorage();

            if (targetStorage.getManaStored() >= targetStorage.getMaxManaStored()) {
                return;
            }

            int transferable = 200;
            int manaLeftToFull = targetStorage.getMaxManaStored() - targetStorage.getManaStored();
            if (this.storage.getManaStored() < 200) {
                transferable = this.storage.getManaStored();
            }
            if (manaLeftToFull < 200) {
                transferable = Math.min(manaLeftToFull, transferable);
            }

            this.storage.useMana(transferable);
            targetStorage.collectMana(transferable);
        }
    }

    private boolean canCollect() {
        Block blockTop = this.world.getBlockState(this.pos.up()).getBlock();
        return blockTop.equals(PotatoBlocks.GLOWING_MANA_FLOWER);
    }

    private boolean canExtract() {
        ItemStack input = this.inputHandler.getStackInSlot(0);
        if (input.isEmpty()) {
            return false;
        }

        int manaValue = getManaValue(input);

        if (manaValue <= 0 || this.storage.getManaStored() >= this.storage.getMaxManaStored()) {
            return false;
        }

        ItemStack output = this.outputHandler.getStackInSlot(0);
        ItemStack result = getResult(input);

        if (output.isEmpty()) {
            return true;
        } else if (output.getItem().equals(result.getItem())) {
            return output.getCount() < output.getMaxStackSize();
        } else {
            return false;
        }
    }

    private void extractMana() {
        ItemStack input = this.inputHandler.getStackInSlot(0);
        ItemStack output = this.outputHandler.getStackInSlot(0);
        ItemStack result = getResult(input);
        int manaValue = getManaValue(input);

        if (output.isEmpty()) {
            this.outputHandler.setStackInSlot(0, result);
        } else if (output.getItem().equals(result.getItem())) {
            if (output.getCount() < output.getMaxStackSize()) {
                output.grow(1);
            }
        }

        this.storage.collectMana(manaValue);
        input.shrink(1);
    }

    @Override
    public ItemStack getResult(ItemStack stack) {
        if (stack.getItem() == PotatoItems.GLOWING_MANA_DUST) {
            return new ItemStack(PotatoItems.GLOWING_POTATO_DUST);
        } else if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.GLOWING_MANA_FLOWER)) {
            return new ItemStack(Blocks.RED_FLOWER);
        } else if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.GLOWING_MANA_TORCH)) {
            return new ItemStack(Blocks.TORCH);
        } else if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.GLOWING_MANA_STONE)) {
            return new ItemStack(Blocks.STONE);
        }
        return ItemStack.EMPTY;
    }

    // Static Methods

    private static int getManaValue(ItemStack stack) {
        if (stack.getItem() == PotatoItems.GLOWING_MANA_DUST) return 1000;
        if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.GLOWING_MANA_FLOWER)) return 1000;
        if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.GLOWING_MANA_TORCH)) return 1000;
        if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.GLOWING_MANA_STONE)) return 1000;
        return 0;
    }
}
