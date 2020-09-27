package aldinh777.potatoheadshot.block.blocks;

import aldinh777.potatoheadshot.block.tileentities.TileEntityPotatoDrier;
import aldinh777.potatoheadshot.item.items.PotatoItemBlock;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import aldinh777.potatoheadshot.util.BlockType;
import aldinh777.potatoheadshot.util.Constants;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class PotatoDrier extends PotatoMachine {

    public static PropertyBool ACTIVE = PropertyBool.create("active");
    public static boolean keepInventory;

    public PotatoDrier(String name, BlockType blockType) {
        super(name, blockType, Constants.DRIER);
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(ACTIVE, false));
        this.setCreativeTab(PotatoTab.POTATO_TAB);

        PotatoItems.LISTS.add(new PotatoItemBlock(this));
    }

    @Override
    protected void init(String name) {
        this.setUnlocalizedName(name);
        PotatoBlocks.LISTS.add(this);
    }

    @Override
    public void onBlockAdded(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState north = worldIn.getBlockState(pos.north());
            IBlockState south = worldIn.getBlockState(pos.south());
            IBlockState west = worldIn.getBlockState(pos.west());
            IBlockState east = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state
                    .withProperty(FACING, enumfacing)
                    .withProperty(ACTIVE, false));
        }

    }

    @Nonnull
    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, ACTIVE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int facing = state.getValue(FACING).getIndex();
        boolean active = state.getValue(ACTIVE);

        return facing + (active ? 8 : 0);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        int facing = meta;
        boolean active = false;

        if (meta >= 8) {
            active = true;
            facing = meta - 8;
        }

        IBlockState iblockstate = this.getDefaultState().withProperty(ACTIVE, active);

        switch (facing) {
            case 0:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.DOWN);
                break;
            case 1:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH);
                break;
            case 3:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST);
                break;
            case 5:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST);
                break;
        }

        return iblockstate;
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

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileEntityPotatoDrier();
    }

    @Override
    public void breakBlock(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (!worldIn.isRemote) {
            if (!keepInventory) {
                TileEntityPotatoDrier te = (TileEntityPotatoDrier) worldIn.getTileEntity(pos);
                if (te != null) {
                    ItemStackHandler fuelHandler = (ItemStackHandler) te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                    ItemStackHandler inputHandler = (ItemStackHandler) te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
                    ItemStackHandler outputHandler = (ItemStackHandler) te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
                    ItemStackHandler[] handlers = new ItemStackHandler[]{fuelHandler, inputHandler, outputHandler};

                    for (ItemStackHandler handler : handlers) {
                        for (int i = 0; i < Objects.requireNonNull(handler).getSlots(); ++i) {
                            ItemStack itemStack = handler.getStackInSlot(i);

                            if (!itemStack.isEmpty()) {
                                spawnAsEntity(worldIn, pos, itemStack);
                            }
                        }
                    }
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
    }
}
