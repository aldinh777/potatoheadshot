package aldinh777.potatoheadshot.block.blocks.machines;

import aldinh777.potatoheadshot.block.backup.tileentities.TileEntityPotatoDrier;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.util.BlockType;
import aldinh777.potatoheadshot.util.Constants;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockDrier extends BlockMachine {

    public static PropertyBool ACTIVE = PropertyBool.create("active");
    public static PropertyBool WATER = PropertyBool.create("water");
    public static PropertyEnum<Mode> MODE = PropertyEnum.create("mode", Mode.class);

    public enum Mode implements IStringSerializable {
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

    public BlockDrier(String name, BlockType blockType) {
        super(name, blockType);
        this.setGuiId(Constants.DRIER);
        setDefaultState(blockState.getBaseState()
                .withProperty(ACTIVE, false)
                .withProperty(WATER, false)
                .withProperty(MODE, Mode.BASIC));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityPotatoDrier();
    }

    @Nonnull
    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, MODE, WATER, ACTIVE);
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return Item.getItemFromBlock(PotatoBlocks.POTATO_DRIER);
    }

    @Override
    public int getLightValue(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        boolean active = state.getValue(ACTIVE);
        return active ? 13 : 0;
    }
}
