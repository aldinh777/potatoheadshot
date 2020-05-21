package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class TileEntityManaExtractor extends TileEntityManaCollector {

    // Override Method

    @Override
    public void update() {
        if (!this.world.isRemote) {
            if (canExtract()) {
                extractMana();
            }

            markDirty();
        }
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return customOrDefaultName("Mana Extractor");
    }

    // Custom Methods

    private boolean canExtract() {
        ItemStack input = this.inputHandler.getStackInSlot(0);
        if (input.isEmpty()) {
            return false;
        }

        int manaValue = getManaValue(input);

        if (manaValue <= 0 || this.manaSize >= this.manaMaxSize) {
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

        this.manaSize += manaValue;
        input.shrink(1);

        if (this.manaSize > this.manaMaxSize) {
            this.manaSize = this.manaMaxSize;
        }
    }

    @Override
    public ItemStack getResult(ItemStack stack) {
        if (stack.getItem() == PotatoItems.GLOWING_MANA_DUST) {
            return new ItemStack(PotatoItems.GLOWING_POTATO_DUST);
        } else if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.GLOWING_MANA_FLOWER)) {
            return new ItemStack(Blocks.RED_FLOWER);
        } else if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.GLOWING_MANA_TORCH)) {
            return new ItemStack(Blocks.TORCH);
        }
        return ItemStack.EMPTY;
    }

    // Static Methods

    private static int getManaValue(ItemStack stack) {
        if (stack.getItem() == PotatoItems.GLOWING_MANA_DUST) return 1000;
        if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.GLOWING_MANA_FLOWER)) return 1000;
        if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.GLOWING_MANA_TORCH)) return 1000;
        return 0;
    }
}
