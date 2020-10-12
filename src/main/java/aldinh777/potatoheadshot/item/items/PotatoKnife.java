package aldinh777.potatoheadshot.item.items;

import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import net.minecraft.block.BlockPotato;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class PotatoKnife extends ItemSword {

    public PotatoKnife(String name) {
        super(ToolMaterial.WOOD);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(PotatoTab.POTATO_TAB);
        this.setContainerItem(this);
        PotatoItems.LISTS.add(this);
    }

    @Override
    public boolean isRepairable() {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull ItemStack stack, @Nonnull World worldIn, IBlockState state, @Nonnull BlockPos pos, @Nonnull EntityLivingBase entityLiving) {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(2, entityLiving);
        }

        if (!worldIn.isRemote) {
            if (state.getBlock() == Blocks.POTATOES) {
                BlockPotato potato = (BlockPotato) state.getBlock();
                if (potato.isMaxAge(state)) {
                    ItemStack leaves = new ItemStack(PotatoItems.POTATO_LEAVES, 2);
                    EntityItem knifeDrop = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), leaves);
                    worldIn.spawnEntity(knifeDrop);
                    stack.damageItem(1, entityLiving);
                }
            }
        }

        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return blockIn.getBlock() == Blocks.POTATOES || blockIn.getBlock() == Blocks.WEB;
    }
}
