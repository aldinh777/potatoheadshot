package aldinh777.potatoheadshot.content.blocks.machines;

import aldinh777.potatoheadshot.content.tileentities.TileEntityEnergyTransfer;
import aldinh777.potatoheadshot.content.blocks.PotatoBlock;
import aldinh777.potatoheadshot.common.util.BlockType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnergyTransfer extends PotatoBlock {

    public static final PropertyEnum<Mode> MODE = PropertyEnum.create("mode", Mode.class);

    public EnergyTransfer(String name, BlockType blockType) {
        super(name, blockType);
        this.setLightLevel(8);
        this.setResistance(6000);
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (playerIn.isSneaking()) {
            TileEntityEnergyTransfer energyTransfer = (TileEntityEnergyTransfer) worldIn.getTileEntity(pos);
            if (energyTransfer != null) {
                if (!worldIn.isRemote) {
                    energyTransfer.switchMode();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasTileEntity(@Nonnull IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityEnergyTransfer();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, MODE);
    }

    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(@Nonnull IBlockState state) {
        return false;
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(MODE, Mode.withValue(meta));
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        return state.getValue(MODE).getValue();
    }

    public enum Mode implements IStringSerializable {
        EXTRACT("extract", 0),
        ABSORB("absorb", 1);

        private final String name;
        private final int value;

        Mode(String name, int value) {
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

        public static Mode withValue(int value) {
            if (value == 1) {
                return ABSORB;
            } else {
                return EXTRACT;
            }
        }
    }
}
