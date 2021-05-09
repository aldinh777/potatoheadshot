package aldinh777.potatoheadshot.block.blocks.machines;

import aldinh777.potatoheadshot.block.inventory.InventoryDrier;
import aldinh777.potatoheadshot.block.inventory.InventoryDrierUpgrade;
import aldinh777.potatoheadshot.block.tileentities.TileEntityDrier;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.util.BlockType;
import aldinh777.potatoheadshot.util.Constants;
import aldinh777.potatoheadshot.util.InventoryHelper;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockDrier extends BlockMachine implements IBlockUpgradable {

    public static PropertyBool ACTIVE = PropertyBool.create("active");
    public static PropertyBool WATER = PropertyBool.create("water");
    public static PropertyEnum<Mode> MODE = PropertyEnum.create("mode", Mode.class);

    public BlockDrier(String name, BlockType blockType) {
        super(name, blockType);
        setDefaultState(blockState.getBaseState()
                .withProperty(ACTIVE, false)
                .withProperty(WATER, false)
                .withProperty(MODE, Mode.BASIC));
    }

    @Override
    public int getGuiId() {
        return Constants.DRIER;
    }

    @Override
    public int getUpgradeGuiId() {
        return Constants.DRIER_UPGRADE;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityDrier();
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
        return active ? 12 : 0;
    }

    @Nonnull
    @Override
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, @Nonnull BlockPos pos) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileEntityDrier) {
            TileEntityDrier drier = (TileEntityDrier) tileEntity;
            InventoryDrierUpgrade upgrade = drier.getUpgrade();
            return state.withProperty(MODE, upgrade.getMode()).withProperty(WATER, upgrade.hasWaterUpgrade());
        }
        return state;
    }

    @Override
    public void breakBlock(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (tileEntity instanceof TileEntityDrier) {
            TileEntityDrier drier = (TileEntityDrier) tileEntity;

            IItemHandler itemStackHandler = drier.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            IItemHandler upgradeHandler = drier.getUpgrade();

            if (itemStackHandler instanceof InventoryDrier) {
                InventoryHelper.spawnAllSlotAsEntity(itemStackHandler, worldIn, pos);
            }

            InventoryHelper.spawnAllSlotAsEntity(upgradeHandler, worldIn, pos);
        }
        super.breakBlock(worldIn, pos, state);
    }
}
