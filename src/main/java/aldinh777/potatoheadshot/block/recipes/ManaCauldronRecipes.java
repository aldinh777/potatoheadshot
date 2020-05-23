package aldinh777.potatoheadshot.block.recipes;

import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;

public class ManaCauldronRecipes implements IManaRecipes {

    public static IManaRecipes INSTANCE = new ManaCauldronRecipes();
    private final Map<Item, ItemStack> recipes = new HashMap<>();
    private final Map<Item, Integer> costs = new HashMap<>();

    private ManaCauldronRecipes() {

        Item flower = Item.getItemFromBlock(Blocks.RED_FLOWER);
        Item torch = Item.getItemFromBlock(Blocks.TORCH);
        Item stone = Item.getItemFromBlock(Blocks.STONE);

        addRecipe(PotatoItems.GLOWING_POTATO_DUST, new ItemStack(PotatoItems.GLOWING_MANA_DUST), 1000);
        addRecipe(flower, new ItemStack(PotatoBlocks.GLOWING_MANA_FLOWER), 1000);
        addRecipe(torch, new ItemStack(PotatoBlocks.GLOWING_MANA_TORCH), 1000);
        addRecipe(stone, new ItemStack(PotatoBlocks.GLOWING_MANA_STONE), 1000);
    }

    public void addRecipe(Item input, ItemStack output, int manaCost) {
        this.recipes.put(input, output);
        this.costs.put(input, manaCost);
    }

    @Override
    public ItemStack getResult(Item input) {
        ItemStack result = this.recipes.get(input);

        if (result != null) {
            return result;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public int getCost(Item input) {
        Integer result = this.costs.get(input);

        if (result != null) {
            return result;
        } else {
            return 0;
        }
    }
}
