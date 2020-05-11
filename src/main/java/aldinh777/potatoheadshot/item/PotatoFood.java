package aldinh777.potatoheadshot.item;

import aldinh777.potatoheadshot.lists.PotatoItems;
import aldinh777.potatoheadshot.lists.PotatoTab;
import aldinh777.potatoheadshot.util.FoodEffects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PotatoFood extends ItemFood {

    private final List<FoodEffects> effects = new ArrayList<>();

    public PotatoFood(String name, int amount, float saturation) {
        super(amount, saturation, false);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(PotatoTab.POTATO_TAB);
        PotatoItems.LISTS.add(this);
    }

    public void addEffects(FoodEffects... effects) {
        this.effects.addAll(Arrays.asList(effects));
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            for (FoodEffects effect : this.effects) {
                effect.applyEffects(stack, worldIn, player);
            }
        }
    }
}
