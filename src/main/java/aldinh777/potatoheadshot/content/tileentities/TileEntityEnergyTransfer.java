package aldinh777.potatoheadshot.content.tileentities;

import aldinh777.potatoheadshot.content.blocks.machines.EnergyTransfer;
import aldinh777.potatoheadshot.common.compat.botania.BotaniaCompat;
import aldinh777.potatoheadshot.content.capability.PotatoEnergyStorage;
import aldinh777.potatoheadshot.content.capability.PotatoManaStorage;
import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.common.util.EnergyUtil;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TileEntityEnergyTransfer extends TileEntity implements ITickable, IManaMachine {

    protected EnergyTransfer.Mode mode = EnergyTransfer.Mode.EXTRACT;
    protected PotatoManaStorage manaStorage = new PotatoManaStorage(8000);
    protected PotatoEnergyStorage energyStorage = new PotatoEnergyStorage(80_000, 1000, 1000);

    // Override Method

    @Override
    public void update() {
        if (!this.world.isRemote) {
            List<TileEntity> targets = this.getStorageAround(ConfigHandler.ENERGY_TRANSFER_RANGE);
            this.shareEnergy(targets);
            this.spreadRF();

            this.markDirty();
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return true;
        }

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T)this.energyStorage;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean shouldRefresh(@Nonnull World world, @Nonnull BlockPos pos, IBlockState state, IBlockState newState) {
        return state.getBlock() != newState.getBlock();
    }

    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        manaStorage.readFromNBT(compound);
        energyStorage.readFromNBT(compound);
        this.mode = EnergyTransfer.Mode.withValue(compound.getInteger("mode"));
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        manaStorage.writeToNBT(compound);
        energyStorage.writeToNBT(compound);
        compound.setInteger("mode", this.mode.getValue());
        return compound;
    }

    @Override
    public PotatoManaStorage getManaStorage() {
        return this.manaStorage;
    }

    // Custom Method

    public void switchMode() {
        EnergyTransfer.Mode newMode;

        if (this.mode == EnergyTransfer.Mode.EXTRACT) {
            newMode = EnergyTransfer.Mode.ABSORB;
        } else {
            newMode = EnergyTransfer.Mode.EXTRACT;
        }

        this.mode = newMode;
        IBlockState newState = this.world.getBlockState(this.pos)
                .withProperty(EnergyTransfer.MODE, newMode);

        this.world.setBlockState(this.pos, newState);
    }

    public void spreadRF() {
        for (EnumFacing facing : EnumFacing.VALUES) {
            TileEntity targetTile = this.world.getTileEntity(this.pos.offset(facing));
            if (targetTile != null) {
                EnergyUtil.doEnergyInteract(this, targetTile, facing, 1000);
            }
        }
    }

    public List<TileEntity> getStorageAround(int area) {
        List<TileEntity> storages = Lists.newArrayList();

        for (EnumFacing facing : EnumFacing.VALUES) {
            for (int i = 1; i <= area; i++) {
                BlockPos pos = this.getPos().offset(facing, i);
                TileEntity tile = this.world.getTileEntity(pos);

                if (BotaniaCompat.isBotaniaAvailable()) {
                    if (BotaniaCompat.isInstanceOfManaPool(tile)) {
                        storages.add(tile);
                        break;
                    }
                }

                if (tile instanceof IManaMachine) {
                    storages.add(tile);
                    break;
                }
            }
        }

        return storages;
    }

    private void shareEnergy(List<TileEntity> targets) {
        for (TileEntity tileEntity : targets) {

            if (BotaniaCompat.isBotaniaAvailable()) {
                if (BotaniaCompat.isInstanceOfManaPool(tileEntity)) {
                    if (this.mode == EnergyTransfer.Mode.EXTRACT) {
                        BotaniaCompat.spreadMana(tileEntity, this.manaStorage, 100);
                    } else {
                        BotaniaCompat.absorbMana(tileEntity, this.manaStorage, 100);
                    }
                }
            }

            if (tileEntity instanceof IManaMachine) {
                IManaMachine storage = (IManaMachine) tileEntity;
                PotatoManaStorage targetStorage = storage.getManaStorage();
                if (storage instanceof TileEntityManaCauldron) {
                    if (this.mode == EnergyTransfer.Mode.EXTRACT) {
                        spreadMana(targetStorage);
                    } else {
                        absorbMana(targetStorage);
                    }

                } else if (storage instanceof TileEntityEnergyTransfer) {
                    PotatoEnergyStorage targetEnergyTransfer = ((TileEntityEnergyTransfer) storage).energyStorage;
                    int sourceMana = this.manaStorage.getManaStored();
                    int sourceEnergy = this.energyStorage.getEnergyStored();
                    int targetMana = targetStorage.getManaStored();
                    int targetEnergy = targetEnergyTransfer.getEnergyStored();

                    if (sourceMana > targetMana + 100) {
                        spreadMana(targetStorage);

                    } else if (sourceMana < targetMana - 100) {
                        absorbMana(targetStorage);
                    }

                    if (sourceEnergy > targetEnergy + 1000) {
                        spreadEnergy(targetEnergyTransfer);

                    } else if (sourceEnergy < targetEnergy - 1000) {
                        absorbEnergy(targetEnergyTransfer);
                    }
                } else {
                    spreadMana(targetStorage);
                }
            }
        }
    }

    private void absorbMana(PotatoManaStorage targetStorage) {
        if (targetStorage.getManaStored() <= 0) {
            return;
        }

        int targetMana = targetStorage.getManaStored();
        int manaLeftToFull = this.manaStorage.getMaxManaStored() - this.manaStorage.getManaStored();

        EnergyUtil.checkTransferableEnergy(targetMana, manaLeftToFull, 100, (transferable) -> {
            this.manaStorage.collectMana(transferable);
            targetStorage.useMana(transferable);
        });
    }

    private void spreadMana(PotatoManaStorage targetStorage) {
        if (targetStorage.getManaStored() >= targetStorage.getMaxManaStored()) {
            return;
        }

        int mana = this.manaStorage.getManaStored();
        int manaLeftToFull = targetStorage.getMaxManaStored() - targetStorage.getManaStored();

        EnergyUtil.checkTransferableEnergy(mana, manaLeftToFull, 100, (transferable) -> {
            this.manaStorage.useMana(transferable);
            targetStorage.collectMana(transferable);
        });
    }

    private void absorbEnergy(PotatoEnergyStorage targetStorage) {
        if (targetStorage.getEnergyStored() <= 0) {
            return;
        }

        int targetEnergy = targetStorage.getEnergyStored();
        int energyToFillSource = this.energyStorage.getMaxEnergyStored() - this.energyStorage.getEnergyStored();

        EnergyUtil.checkTransferableEnergy(targetEnergy, energyToFillSource, 1000, (transferable) -> {
            this.energyStorage.generateEnergy(transferable);
            targetStorage.useEnergy(transferable);
        });
    }

    private void spreadEnergy(PotatoEnergyStorage targetStorage) {
        if (targetStorage.getEnergyStored() >= targetStorage.getMaxEnergyStored()) {
            return;
        }

        int energy = this.energyStorage.getEnergyStored();
        int energyToFillTarget = targetStorage.getMaxEnergyStored() - targetStorage.getEnergyStored();

        EnergyUtil.checkTransferableEnergy(energy, energyToFillTarget,1000, (transferable) -> {
            this.energyStorage.useEnergy(transferable);
            targetStorage.generateEnergy(transferable);
        });
    }
}
