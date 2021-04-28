package aldinh777.potatoheadshot.block.backup.tileentities;

import aldinh777.potatoheadshot.block.blocks.ManaFlower;
import aldinh777.potatoheadshot.block.backup.blocks.machines.PotatoMachine;
import aldinh777.potatoheadshot.compat.botania.BotaniaCompat;
import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import aldinh777.potatoheadshot.handler.ConfigHandler;
import aldinh777.potatoheadshot.item.items.PocketCauldron;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.util.EnergyUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class TileEntityManaExtractor extends TileEntityMana {

    private final int manaFlowerRate = ConfigHandler.MANA_FLOWER_RATE;
    private final int ultFlowerRate = ConfigHandler.ULTIMATE_FLOWER_RATE;
    private int tick = 0;

    // Override Method

    @Override
    public void update() {
        if (!this.world.isRemote) {
            boolean flag = false;

            if (this.canExtract()) {
                this.extractMana();
            }

            if (this.canCollectMana()) {
                if (this.tick >= 10) {
                    this.collectMana();
                    this.tick = 0;
                } else {
                    this.tick++;
                }
            }

            if (this.canTransferMana()) {
                this.transferMana();
            }

            this.extractManaItem();

            if (this.manaSize != this.storage.getManaStored()) {
                this.manaSize = this.storage.getManaStored();
                flag = true;
            }

            if (flag) {
                this.markDirty();
            }
        }
    }

    public boolean canCollectMana() {
        Block blockTop = this.world.getBlockState(this.pos.up()).getBlock();
        return blockTop instanceof ManaFlower;
    }

    @Override
    protected void collectMana() {
        Block flower = this.world.getBlockState(this.pos.up()).getBlock();
        if (flower.equals(PotatoBlocks.MANA_FLOWER)) {
            this.storage.collectMana(manaFlowerRate);
        } else if (flower.equals(PotatoBlocks.ULTIMATE_FLOWER)) {
            this.storage.collectMana(ultFlowerRate);
        }
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return customOrDefaultName("Mana Extractor");
    }

    // Custom Methods

    private boolean canTransferMana() {
        IBlockState state = this.world.getBlockState(pos);
        if (state.getBlock() != PotatoBlocks.MANA_EXTRACTOR) {
            return false;
        }

        EnumFacing behind = state.getValue(PotatoMachine.FACING).getOpposite();
        TileEntity storage = this.world.getTileEntity(pos.offset(behind));

        if (BotaniaCompat.isBotaniaAvailable()) {
            if (BotaniaCompat.isInstanceOfManaPool(storage)) {
                return true;
            }
        }

        if (storage instanceof IManaStorage) {
            return this.storage.getManaStored() > 0;
        }

        return false;
    }

    private void transferMana() {
        IBlockState state = this.world.getBlockState(pos);
        if (state.getBlock() != PotatoBlocks.MANA_EXTRACTOR) {
            return;
        }

        EnumFacing behind = state.getValue(PotatoMachine.FACING).getOpposite();
        TileEntity tileEntity = this.world.getTileEntity(pos.offset(behind));

        if (BotaniaCompat.isBotaniaAvailable()) {
            if (BotaniaCompat.isInstanceOfManaPool(tileEntity)) {
                BotaniaCompat.spreadMana(tileEntity, this.storage, 200);
                return;
            }
        }

        IManaStorage manaStorage = (IManaStorage)tileEntity;

        if (manaStorage != null) {
            PotatoManaStorage targetStorage = manaStorage.getManaStorage();

            if (targetStorage.getManaStored() >= targetStorage.getMaxManaStored()) {
                return;
            }

            int mana = this.storage.getManaStored();
            int manaLeftToFull = targetStorage.getMaxManaStored() - targetStorage.getManaStored();

            EnergyUtil.checkTransferableEnergy(mana, manaLeftToFull, 200, (transferable) -> {
                this.storage.useMana(transferable);
                targetStorage.collectMana(transferable);
            });
        }
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

    private void extractManaItem() {
        ItemStack stack = this.inputHandler.getStackInSlot(0);

        if (stack.getItem() instanceof PocketCauldron) {
            PocketCauldron.extractCauldronMana(stack, this.storage, 200);
            return;
        }

        if (BotaniaCompat.isBotaniaAvailable()) {
            if (BotaniaCompat.isInstanceOfManaItem(stack.getItem())) {
                BotaniaCompat.extractMana(stack, this.storage, 200);
            }
        }
    }

    @Override
    public ItemStack getResult(ItemStack stack) {
        if (stack.getItem() == PotatoItems.MANA_DUST) {
            return new ItemStack(PotatoItems.GLOWING_POTATO_DUST);
        } else if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.MANA_FLOWER)) {
            return new ItemStack(Blocks.RED_FLOWER);
        } else if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.MANA_TORCH)) {
            return new ItemStack(Blocks.TORCH);
        } else if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.MANA_STONE)) {
            return new ItemStack(Blocks.STONE);
        }
        return ItemStack.EMPTY;
    }

    // Static Methods

    private static int getManaValue(ItemStack stack) {
        if (stack.getItem() == PotatoItems.MANA_DUST) return 100;
        if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.MANA_TORCH)) return 100;
        if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.MANA_FLOWER)) return 800;
        if (stack.getItem() == Item.getItemFromBlock(PotatoBlocks.MANA_STONE)) return 800;
        return 0;
    }
}
