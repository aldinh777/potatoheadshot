package aldinh777.potatoheadshot.block.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import javax.annotation.Nonnull;

public class ContainerDrier extends Container {

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        return false;
    }
}
