package aldinh777.potatoheadshot.block;

import aldinh777.potatoheadshot.block.tileentities.TileEntityPotatoDrier;
import aldinh777.potatoheadshot.item.PotatoItemBlock;
import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import aldinh777.potatoheadshot.util.BlockType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Objects;

public class PotatoDrier extends PotatoMachine {

    public static boolean keepInventory;

    public PotatoDrier(String name, BlockType blockType) {
        this(name, blockType, false);
    }

    public PotatoDrier(String name, BlockType blockType, boolean isBurning) {
        super(name, blockType, 1);
        initBurning(isBurning);
    }

    @Override
    protected void init(String name) {
        this.setUnlocalizedName(name);
        PotatoBlocks.LISTS.add(this);
    }

    protected void initBurning(boolean isBurning) {
        if (!isBurning) {
            PotatoItems.LISTS.add(new PotatoItemBlock(this));
            this.setCreativeTab(PotatoTab.POTATO_TAB);
        } else {
            this.setLightLevel(0.875f);
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityPotatoDrier();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            if (!keepInventory) {
                TileEntityPotatoDrier te = (TileEntityPotatoDrier) worldIn.getTileEntity(pos);
                if (te != null) {
                    ItemStackHandler handler = (ItemStackHandler) te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

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
}
