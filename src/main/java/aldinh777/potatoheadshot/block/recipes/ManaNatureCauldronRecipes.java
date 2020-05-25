package aldinh777.potatoheadshot.block.recipes;

import aldinh777.potatoheadshot.lists.PotatoItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ManaNatureCauldronRecipes implements IManaRecipes {

    public static IManaRecipes INSTANCE = new ManaNatureCauldronRecipes();
    private final Map<Item, ItemStack> recipes = new HashMap<>();

    public ManaNatureCauldronRecipes() {
        Item redMushroom = Item.getItemFromBlock(Blocks.RED_MUSHROOM);
        Item brownMushroom = Item.getItemFromBlock(Blocks.BROWN_MUSHROOM);
        Item cactus = Item.getItemFromBlock(Blocks.CACTUS);
        Item leaves = Item.getItemFromBlock(Blocks.LEAVES);

        addRecipe(Items.WHEAT_SEEDS, new ItemStack(Items.MELON_SEEDS));
        addRecipe(Items.MELON_SEEDS, new ItemStack(Items.PUMPKIN_SEEDS));
        addRecipe(Items.PUMPKIN_SEEDS, new ItemStack(Items.BEETROOT_SEEDS));
        addRecipe(Items.BEETROOT_SEEDS, new ItemStack(Items.DYE, 1, 3));
        addRecipe(Items.NETHER_WART, new ItemStack(redMushroom));
        addRecipe(redMushroom, new ItemStack(brownMushroom));
        addRecipe(brownMushroom, new ItemStack(Items.NETHER_WART));
        addRecipe(cactus, new ItemStack(Items.REEDS));
        addRecipe(Items.REEDS, new ItemStack(cactus));
        addRecipe(PotatoItems.POTATO_LEAVES, new ItemStack(leaves));
    }

    private void addRecipe(Item input, ItemStack output) {
        this.recipes.put(input, output);
    }

    private ItemStack rotatePlant(ItemStack input) {
        Item redFlower = Item.getItemFromBlock(Blocks.RED_FLOWER);
        Item yellowFlower = Item.getItemFromBlock(Blocks.YELLOW_FLOWER);
        Item doublePlant = Item.getItemFromBlock(Blocks.DOUBLE_PLANT);
        Item sapling = Item.getItemFromBlock(Blocks.SAPLING);
        int meta = input.getMetadata();

        if (input.getItem().equals(redFlower)) {
            if (meta == 0) {
                return new ItemStack(yellowFlower);
            } if (meta == 8) {
                return new ItemStack(redFlower, 1, 0);
            } else {
                return new ItemStack(redFlower, 1, meta + 1);
            }
        } else if (input.getItem().equals(yellowFlower)) {
            return new ItemStack(redFlower, 1, 1);
        } else if (input.getItem().equals(sapling)) {
            if (meta == 5) {
                return new ItemStack(sapling);
            } else {
                return new ItemStack(sapling, 1, meta + 1);
            }
        } else if (input.getItem().equals(doublePlant)) {
            if (meta == 5) {
                return new ItemStack(doublePlant);
            } else {
                return new ItemStack(doublePlant, 1, meta + 1);
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack getResult(ItemStack input) {
        if (input.getItem() == Items.DYE && input.getMetadata() == 3) {
            return new ItemStack(Items.WHEAT_SEEDS);
        }

        ItemStack seedResult = recipes.get(input.getItem());
        if (seedResult != null && !seedResult.isEmpty()) {
            return seedResult;
        }

        ItemStack plantResult = rotatePlant(input);
        if (!plantResult.isEmpty()) {
            return plantResult;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public int getCost(ItemStack input) {
        return 100;
    }

}
