package aldinh777.potatoheadshot.other.util;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public enum BlockType {
    WOOD(Material.WOOD, SoundType.WOOD, 2.0f, 5.0f),
    GROUND(Material.GROUND, SoundType.GROUND, 0.5f, 1.0f),
    STONE(Material.ROCK, SoundType.STONE, 3.5f, 1.0f),
    METAL(Material.IRON, SoundType.METAL, 5.0f, 10.0f),
    GLASS(Material.GLASS, SoundType.GLASS, 0.3f, 1.0f),
    POTATO(Material.WOOD, SoundType.WOOD, 1.0f, 1.0f);

    private final Material material;
    private final SoundType soundType;
    private final float hardness;
    private final float resistance;

    BlockType(Material material, SoundType soundType, float hardness, float resistance) {
        this.material = material;
        this.soundType = soundType;
        this.hardness = hardness;
        this.resistance = resistance;
    }

    public Material getMaterial() {
        return material;
    }

    public SoundType getSoundType() {
        return soundType;
    }

    public float getHardness() {
        return hardness;
    }

    public float getResistance() {
        return resistance;
    }
}

