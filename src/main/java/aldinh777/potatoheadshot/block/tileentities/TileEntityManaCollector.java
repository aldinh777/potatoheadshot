package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.machines.ManaExtractor;
import aldinh777.potatoheadshot.compat.botania.BotaniaCompat;
import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import aldinh777.potatoheadshot.item.items.PocketCauldron;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class TileEntityManaCollector extends TileEntityMana {

    // Override Methods

    @Override
    public void update() {
        if (!this.world.isRemote) {
            boolean flag = false;

            if (this.canCollectMana()) {
                this.collectMana();
            }

            if (this.canFuse()) {
                this.fuseItemWithMana();
                flag = true;
            }

            if (this.canTakeMana()) {
                this.takeMana();
            }

            this.chargeManaItem();

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
        return customOrDefaultName("Mana Collector");
    }

    // Custom Methods

    private boolean canTakeMana() {
        EnumFacing behind = this.world.getBlockState(pos).getValue(ManaExtractor.FACING).getOpposite();
        TileEntity cauldron = this.world.getTileEntity(pos.offset(behind));

        if (cauldron instanceof TileEntityManaCauldron) {
            return this.storage.getManaStored() < this.storage.getMaxManaStored();
        }
        return false;
    }

    private void takeMana() {
        EnumFacing behind = this.world.getBlockState(pos).getValue(ManaExtractor.FACING).getOpposite();
        TileEntity tileEntity = this.world.getTileEntity(pos.offset(behind));
        TileEntityManaCauldron cauldron = (TileEntityManaCauldron)tileEntity;

        if (cauldron != null) {
            PotatoManaStorage targetStorage = cauldron.getManaStorage();

            if (targetStorage.getManaStored() <= 0) {
                return;
            }

            int transferable = 200;
            int manaLeftToFull = this.storage.getMaxManaStored() - this.storage.getManaStored();
            if (targetStorage.getManaStored() < 200) {
                transferable = targetStorage.getManaStored();
            }
            if (manaLeftToFull < 200) {
                transferable = Math.min(manaLeftToFull, transferable);
            }

            this.storage.collectMana(transferable);
            targetStorage.useMana(transferable);
        }
    }

    @Override
    public boolean canCollectMana() {
        return true;
    }

    protected void collectMana() {
        this.storage.collectMana(2);
    }

    private boolean canFuse() {
        ItemStack input = this.inputHandler.getStackInSlot(0);
        if (input.isEmpty()) {
            return false;
        }

        int manaValue = getManaValue(input);

        if (manaValue <= 0 || this.storage.getManaStored() < manaValue) {
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

    private void fuseItemWithMana() {
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

        this.storage.useMana(manaValue);
        input.shrink(1);
    }

    private void chargeManaItem() {
        ItemStack stack = this.inputHandler.getStackInSlot(0);

        if (stack.getItem() instanceof PocketCauldron) {
            PocketCauldron.chargeCauldronMana(stack, this.storage, 200);
            return;
        }

        if (BotaniaCompat.isBotaniaAvailable()) {
            if (BotaniaCompat.isInstanceOfManaItem(stack.getItem())) {
                BotaniaCompat.chargeMana(stack, this.storage, 200);
            }
        }
    }

    public ItemStack getResult(ItemStack stack) {
        if (stack.getItem() == PotatoItems.GLOWING_POTATO_DUST) {
            return new ItemStack(PotatoItems.MANA_DUST);
        } else if (stack.getItem() == Item.getItemFromBlock(Blocks.RED_FLOWER)) {
            return new ItemStack(PotatoBlocks.MANA_FLOWER);
        } else if (stack.getItem() == Item.getItemFromBlock(Blocks.TORCH)) {
            return new ItemStack(PotatoBlocks.MANA_TORCH);
        } else if (stack.getItem() == Item.getItemFromBlock(Blocks.STONE)) {
            return new ItemStack(PotatoBlocks.MANA_STONE);
        }
        return ItemStack.EMPTY;
    }

    // Static Methods

    private static int getManaValue(ItemStack stack) {
        if (stack.getItem() == PotatoItems.GLOWING_POTATO_DUST) return 1000;
        if (stack.getItem() == Item.getItemFromBlock(Blocks.RED_FLOWER)) return 1000;
        if (stack.getItem() == Item.getItemFromBlock(Blocks.TORCH)) return 1000;
        if (stack.getItem() == Item.getItemFromBlock(Blocks.STONE)) return 1000;
        return 0;
    }
}
