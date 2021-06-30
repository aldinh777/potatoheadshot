package aldinh777.potatoheadshot.content.blocks.machines;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public interface IBlockUpgradable {

    enum Mode implements IStringSerializable {
        BASIC("basic"), FLUX("flux"), MANA("mana");

        private final String name;

        Mode(String name) {
            this.name = name;
        }

        @Nonnull
        @Override
        public String getName() {
            return name;
        }
    }

    int getUpgradeGuiId();
}
