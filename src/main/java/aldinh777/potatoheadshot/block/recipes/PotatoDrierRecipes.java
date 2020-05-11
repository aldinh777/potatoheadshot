package aldinh777.potatoheadshot.block.recipes;

import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PotatoDrierRecipes {

    public static final PotatoDrierRecipes INSTANCE = new PotatoDrierRecipes();
    private final Map<Item, ItemStack> dryList = new HashMap<>();
    private final Map<Item, ItemStack> wetList = new HashMap<>();

    private PotatoDrierRecipes() {
        addDryRecipe(Items.POTATO, new ItemStack(PotatoItems.DRIED_POTATO));
        addDryRecipe(PotatoItems.DRIED_POTATO, new ItemStack(PotatoItems.POTATO_STARCH));
        addDryRecipe(PotatoItems.SWEET_POTATO, new ItemStack(PotatoItems.DRIED_SWEET_POTATO));
        addDryRecipe(PotatoItems.DRIED_SWEET_POTATO, new ItemStack(PotatoItems.SWEET_POTATO_DUST));
        addDryRecipe(PotatoItems.WET_POTATO, new ItemStack(Items.CLAY_BALL));
        addDryRecipe(PotatoItems.SUPER_WET_POTATO, new ItemStack(PotatoItems.SALT_POTATO));
        addDryRecipe(PotatoItems.WATER_POTATO, new ItemStack(PotatoItems.SALT));
        addDryRecipe(Items.WATER_BUCKET, new ItemStack(PotatoItems.SALT));

        addWetRecipe(Items.POTATO, new ItemStack(PotatoItems.WET_POTATO));
        addWetRecipe(PotatoItems.WET_POTATO, new ItemStack(PotatoItems.SUPER_WET_POTATO));
        addWetRecipe(PotatoItems.SUPER_WET_POTATO, new ItemStack(PotatoItems.WATER_POTATO));
        addWetRecipe(Items.BUCKET, new ItemStack(Items.WATER_BUCKET));
        addWetRecipe(PotatoItems.DRIED_POTATO, new ItemStack(Items.POTATO));
        addWetRecipe(PotatoItems.EXTREME_HOT_POTATO, new ItemStack(Items.COAL));
        addWetRecipe(PotatoItems.LAVA_POTATO, new ItemStack(Item.getItemFromBlock(Blocks.OBSIDIAN)));
    }

    public void addDryRecipe(Item input, ItemStack result) {
        this.dryList.put(input, result);
    }

    public void addWetRecipe(Item input, ItemStack result) {
        this.wetList.put(input, result);
    }

    public ItemStack getDryResult(Item input) {
        ItemStack result = dryList.get(input);
        return result == null ? ItemStack.EMPTY : result;
    }

    public ItemStack getWetResult(Item input) {
        ItemStack result = wetList.get(input);
        return result == null ? ItemStack.EMPTY : result;
    }

    public boolean isDryRecipeExists(Item input) {
        return !this.getDryResult(input).isEmpty();
    }

    public boolean isWetRecipeExists(Item input) {
        return !this.getWetResult(input).isEmpty();
    }
}
