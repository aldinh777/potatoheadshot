package aldinh777.potatoheadshot.block.tileentities;

import aldinh777.potatoheadshot.block.blocks.machines.PotatoMachine;
import aldinh777.potatoheadshot.compat.botania.BotaniaCompat;
import aldinh777.potatoheadshot.energy.PotatoManaStorage;
import aldinh777.potatoheadshot.item.items.PocketCauldron;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.util.EnergyUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class TileEntityManaCollector extends TileEntityMana {

    private int tick = 0;

    // Override Methods

    @Override
    public void update() {
        if (!this.world.isRemote) {
            boolean flag = false;

            if (this.canCollectMana()) {
                if (this.tick >= 10) {
                    this.collectMana();
                    this.tick = 0;
                } else {
                    this.tick++;
                }
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
        IBlockState state = this.world.getBlockState(pos);
        if (state.getBlock() != PotatoBlocks.MANA_COLLECTOR) {
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
            return this.storage.getManaStored() < this.storage.getMaxManaStored();
        }

        return false;
    }

    private void takeMana() {
        IBlockState state = this.world.getBlockState(pos);
        if (state.getBlock() != PotatoBlocks.MANA_COLLECTOR) {
            return;
        }

        EnumFacing behind = state.getValue(PotatoMachine.FACING).getOpposite();
        TileEntity tileEntity = this.world.getTileEntity(pos.offset(behind));

        if (BotaniaCompat.isBotaniaAvailable()) {
            if (BotaniaCompat.isInstanceOfManaPool(tileEntity)) {
                BotaniaCompat.absorbMana(tileEntity, this.storage, 200);
                return;
            }
        }

        IManaStorage manaStorage = (IManaStorage) tileEntity;

        if (manaStorage != null) {
            PotatoManaStorage targetStorage = manaStorage.getManaStorage();

            if (targetStorage.getManaStored() <= 0) {
                return;
            }

            int targetMana = targetStorage.getManaStored();
            int manaLeftToFull = this.storage.getMaxManaStored() - this.storage.getManaStored();

            EnergyUtil.checkTransferableEnergy(targetMana, manaLeftToFull, 200, (transferable) -> {
                this.storage.collectMana(transferable);
                targetStorage.useMana(transferable);
            });
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
        }
        return ItemStack.EMPTY;
    }

    // Static Methods

    private static int getManaValue(ItemStack stack) {
        if (stack.getItem() == PotatoItems.GLOWING_POTATO_DUST) return 100;
        return 0;
    }
}
