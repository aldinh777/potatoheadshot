package aldinh777.potatoheadshot.block.recipes;

import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ManaLifeCauldronRecipes implements IManaRecipes {

    public static IManaRecipes INSTANCE = new ManaLifeCauldronRecipes();
    private final Map<Item, ItemStack> recipes = new HashMap<>();
    private final Map<Item, Integer> costs = new HashMap<>();

    private ManaLifeCauldronRecipes() {
        Item potatoPlanks = Item.getItemFromBlock(PotatoBlocks.POTATO_PLANKS);
        Item grass = Item.getItemFromBlock(Blocks.GRASS);
        Item dirt = Item.getItemFromBlock(Blocks.DIRT);
        Item sand = Item.getItemFromBlock(Blocks.SAND);
        Item log1 = Item.getItemFromBlock(Blocks.LOG);
        Item log2 = Item.getItemFromBlock(Blocks.LOG2);
        Item mycelium = Item.getItemFromBlock(Blocks.MYCELIUM);
        Item soulSand = Item.getItemFromBlock(Blocks.SOUL_SAND);
        Item endStone = Item.getItemFromBlock(Blocks.END_STONE);
        Item ice = Item.getItemFromBlock(Blocks.ICE);

        addRecipe(PotatoItems.LAVA_POTATO, new ItemStack(PotatoItems.LAVA_POTATO_SEED), 12000);
        addRecipe(Items.FISH, new ItemStack(Items.PRISMARINE_SHARD));
        addRecipe(Items.PRISMARINE_SHARD, new ItemStack(Items.PRISMARINE_CRYSTALS), 8000);
        addRecipe(Items.CLAY_BALL, new ItemStack(Items.SLIME_BALL), 8000);
        addRecipe(Items.STRING, new ItemStack(Blocks.VINE));
        addRecipe(potatoPlanks, new ItemStack(Blocks.SAPLING));
        addRecipe(grass, new ItemStack(Items.MELON_SEEDS));
        addRecipe(dirt, new ItemStack(Blocks.GRASS));
        addRecipe(sand, new ItemStack(Blocks.CACTUS));
        addRecipe(log1, new ItemStack(Blocks.SAPLING));
        addRecipe(log2, new ItemStack(Blocks.SAPLING));
        addRecipe(mycelium, new ItemStack(Blocks.BROWN_MUSHROOM));
        addRecipe(endStone, new ItemStack(Blocks.CHORUS_FLOWER));
        addRecipe(ice, new ItemStack(Blocks.SNOW));

        // Currently Not Working
        addRecipe(soulSand, new ItemStack(Blocks.NETHER_WART));
        addRecipe(Items.SUGAR, new ItemStack(Blocks.REEDS));
    }

    private void addRecipe(Item input, ItemStack result) {
        this.addRecipe(input, result, 2000);
    }

    private void addRecipe(Item input, ItemStack result, int cost) {
        this.recipes.put(input, result);
        this.costs.put(input, cost);
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
