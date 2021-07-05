package aldinh777.potatoheadshot.content.blocks.crops;

import aldinh777.potatoheadshot.common.handler.ConfigHandler;
import aldinh777.potatoheadshot.common.lists.PotatoBlocks;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

public abstract class PotatoCrops extends BlockCrops {

    private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.4375D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D)
    };

    public PotatoCrops(String name) {
        this.setRegistryName(name);
        this.setUnlocalizedName(name);

        switch (name) {
            case "red_potatoes":
                if (ConfigHandler.RED_POTATO) {
                    PotatoBlocks.LISTS.add(this);
                }
                break;
            case "sticky_potatoes":
                if (ConfigHandler.STICKY_POTATO) {
                    PotatoBlocks.LISTS.add(this);
                }
                break;
            case "lava_potatoes":
                if (ConfigHandler.LAVA_POTATO) {
                    PotatoBlocks.LISTS.add(this);
                }
                break;
            case "water_potatoes":
                if (ConfigHandler.WATER_POTATO) {
                    PotatoBlocks.LISTS.add(this);
                }
                break;
            case "ice_potatoes":
                if (ConfigHandler.ICE_POTATO) {
                    PotatoBlocks.LISTS.add(this);
                }
                break;
            default:
                PotatoBlocks.LISTS.add(this);
        }
    }

    @Nonnull
    @Override
    protected abstract Item getSeed();

    @Nonnull
    @Override
    protected abstract Item getCrop();

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return CROPS_AABB[state.getValue(this.getAgeProperty())];
    }
}
