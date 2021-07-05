package aldinh777.potatoheadshot.common.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.function.Consumer;

public interface EnergyUtil {

    static void doEnergyInteract(TileEntity source, TileEntity target, EnumFacing side, int maxTransfer) {
        if (maxTransfer > 0) {
            EnumFacing interfaceSide = side == null ? EnumFacing.UP : side.getOpposite();
            if (source != null && target != null) {
                if (target.hasCapability(CapabilityEnergy.ENERGY, interfaceSide)) {
                    IEnergyStorage sourceCap = source.getCapability(CapabilityEnergy.ENERGY, side);
                    IEnergyStorage targetCap = target.getCapability(CapabilityEnergy.ENERGY, interfaceSide);
                    if (sourceCap != null && targetCap != null) {
                        if (sourceCap.getEnergyStored() == 0) return;
                        int availableToSend = sourceCap.extractEnergy(maxTransfer, true);
                        if (availableToSend > 0) {
                            int totalSent = targetCap.receiveEnergy(availableToSend, false);
                            sourceCap.extractEnergy(totalSent, false);
                        }
                    }
                }
            }
        }
    }

    static void checkTransferableEnergy(int energyLeft, int energyNeededToFull, int transferRate, Consumer<Integer> callback) {
        int transferable = Math.min(transferRate, energyLeft);
        transferable = Math.min(transferable, energyNeededToFull);

        if (transferable > 0) {
            callback.accept(transferable);
        }
    }
}
