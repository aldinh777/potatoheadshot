package aldinh777.potatoheadshot.handler;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.recipes.category.PotatoDrierRecipes;
import aldinh777.potatoheadshot.recipes.custom.CustomDryRecipe;
import aldinh777.potatoheadshot.recipes.custom.CustomWetRecipe;
import aldinh777.potatoheadshot.recipes.recipe.PotatoDrierRecipe;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.*;
import java.util.List;

public class CustomRecipeHandler {

    public static List<PotatoDrierRecipe> DRY_RECIPES = Lists.newArrayList();
    public static List<PotatoDrierRecipe> WET_RECIPES = Lists.newArrayList();

    public static void registerDrier() {
        File configDirectory = PotatoHeadshot.CONFIG_DIR;

        File drierRecipeDirectory = new File(configDirectory.getPath(), "drier");

        try {
            drierRecipeDirectory.mkdirs();
            registerDryRecipes(drierRecipeDirectory);
            registerWetRecipes(drierRecipeDirectory);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void registerDryRecipes(File recipeDirectory) throws IOException {
        File dryRecipeFile = new File(recipeDirectory.getPath(), "dry_recipe.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (dryRecipeFile.exists()) {
            Reader fileReader = new FileReader(dryRecipeFile);
            CustomDryRecipe[] recipes = gson.fromJson(fileReader, CustomDryRecipe[].class);

            for (CustomDryRecipe recipe : recipes) {
                Item inputItem = Item.getByNameOrId(recipe.input.item);
                Item outputItem = Item.getByNameOrId(recipe.output.item);
                if (inputItem != null && outputItem != null) {
                    int inputMeta = recipe.input.meta;
                    int outputMeta = recipe.output.meta;
                    int water = recipe.output.water;

                    ItemStack input = new ItemStack(inputItem, 1, inputMeta);
                    ItemStack output = new ItemStack(outputItem, 1, outputMeta);
                    PotatoDrierRecipe recipeResult = new PotatoDrierRecipe(input, output, water);

                    DRY_RECIPES.add(recipeResult);
                    PotatoDrierRecipes.INSTANCE.addDryRecipe(inputItem, recipeResult);
                }
            }
            fileReader.close();

        } else {
            if (dryRecipeFile.createNewFile()) {
                List<PotatoDrierRecipe> defaultRecipes = PotatoDrierRecipe.getDefaultDryRecipes();
                List<CustomDryRecipe> customRecipes = Lists.newArrayList();

                for (PotatoDrierRecipe recipe : defaultRecipes) {
                    PotatoDrierRecipes.INSTANCE.addDryRecipe(recipe.getInput().getItem(), recipe);
                    customRecipes.add(recipe.toCustomDryRecipe());
                    DRY_RECIPES.add(recipe);
                }

                Writer fileWriter = new FileWriter(dryRecipeFile);
                gson.toJson(customRecipes.toArray(), CustomDryRecipe[].class, fileWriter);
                fileWriter.close();
            }
        }
    }

    private static void registerWetRecipes(File recipeDirectory) throws IOException {
        File wetRecipeFile = new File(recipeDirectory.getPath(), "wet_recipe.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (wetRecipeFile.exists()) {
            Reader fileReader = new FileReader(wetRecipeFile);
            CustomWetRecipe[] recipes = gson.fromJson(fileReader, CustomWetRecipe[].class);

            for (CustomWetRecipe recipe : recipes) {
                Item inputItem = Item.getByNameOrId(recipe.input.item);
                Item outputItem = Item.getByNameOrId(recipe.output.item);
                if (inputItem != null && outputItem != null) {
                    int inputMeta = recipe.input.meta;
                    int outputMeta = recipe.output.meta;

                    ItemStack input = new ItemStack(inputItem, 1, inputMeta);
                    ItemStack output = new ItemStack(outputItem, 1, outputMeta);
                    PotatoDrierRecipe recipeResult = new PotatoDrierRecipe(input, output, 0);

                    WET_RECIPES.add(recipeResult);
                    PotatoDrierRecipes.INSTANCE.addWetRecipe(inputItem, recipeResult);
                }
            }
            fileReader.close();

        } else {
            if (wetRecipeFile.createNewFile()) {
                List<PotatoDrierRecipe> defaultRecipes = PotatoDrierRecipe.getDefaultWetRecipes();
                List<CustomWetRecipe> customRecipes = Lists.newArrayList();

                for (PotatoDrierRecipe recipe : defaultRecipes) {
                    PotatoDrierRecipes.INSTANCE.addWetRecipe(recipe.getInput().getItem(), recipe);
                    customRecipes.add(recipe.toCustomWetRecipe());
                    WET_RECIPES.add(recipe);
                }

                Writer fileWriter = new FileWriter(wetRecipeFile);
                gson.toJson(customRecipes.toArray(), CustomWetRecipe[].class, fileWriter);
                fileWriter.close();
            }
        }
    }
}
