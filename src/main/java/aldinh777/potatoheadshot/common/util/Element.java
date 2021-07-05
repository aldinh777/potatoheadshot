package aldinh777.potatoheadshot.common.util;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public enum Element implements IStringSerializable {
        MANA("mana", 0),
        LIFE("life", 1),
        NATURE("nature", 2),
        FIRE("fire", 3);

        private final String name;
        private final int value;

        Element(String name, int value) {
            this.name = name;
            this.value = value;
        }

        @Nonnull
        @Override
        public String getName() {
            return this.name;
        }

        public int getValue() {
            return this.value;
        }

        public static Element withValue(int value) {
            switch (value) {
                case 1: return LIFE;
                case 2: return NATURE;
                case 3: return FIRE;
                default: return MANA;
            }
        }
    }
