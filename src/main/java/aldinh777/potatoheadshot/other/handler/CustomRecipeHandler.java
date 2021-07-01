package aldinh777.potatoheadshot.other.handler;

import aldinh777.potatoheadshot.PotatoHeadshot;
import aldinh777.potatoheadshot.other.recipes.category.PotatoDrierRecipes;
import aldinh777.potatoheadshot.other.recipes.custom.CustomDryRecipe;
import aldinh777.potatoheadshot.other.recipes.custom.CustomWetRecipe;
import aldinh777.potatoheadshot.other.recipes.recipe.PotatoDrierRecipe;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.util.TriConsumer;

import java.io.*;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class CustomRecipeHandler {

    public static List<PotatoDrierRecipe> DRY_RECIPES = Lists.newArrayList();
    public static List<PotatoDrierRecipe> WET_RECIPES = Lists.newArrayList();

    public static void registerDrier() {
        File configDirectory = PotatoHeadshot.CONFIG_DIR;
        File drierDirectory = new File(configDirectory.getPath(), "drier");
        drierDirectory.mkdirs();

        registerRecipe(drierDirectory, "dry_recipes.json", Drier::readDryRecipes, Drier::writeDryRecipes, PotatoDrierRecipe::getDefaultDryRecipes);
        registerRecipe(drierDirectory, "wet_recipes.json", Drier::readWetRecipes, Drier::writeWetRecipes, PotatoDrierRecipe::getDefaultWetRecipes);
    }

    public static <T> void registerRecipe(File recipeDir, String filename, BiConsumer<Reader, Gson> readFunction, TriConsumer<Writer, Gson, Supplier<T>> writeFunction, Supplier<T> recipes) {
        File recipeFile = new File(recipeDir.getPath(), filename);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            if (recipeFile.exists()) {
                Reader fileReader = new FileReader(recipeFile);
                readFunction.accept(fileReader, gson);
                fileReader.close();
            } else if (recipeFile.createNewFile()) {
                Writer fileWriter = new FileWriter(recipeFile);
                writeFunction.accept(fileWriter, gson, recipes);
                fileWriter.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    interface Drier {

        static void readDryRecipes(Reader fileReader, Gson gson) {
            CustomDryRecipe[] recipes = gson.fromJson(fileReader, CustomDryRecipe[].class);

            for (CustomDryRecipe recipe : recipes) {
                ItemStack input = recipe.input.toItemStack();
                ItemStack output = recipe.output.toItemStack();

                if (!input.isEmpty() && !output.isEmpty()) {
                    PotatoDrierRecipe recipeResult = new PotatoDrierRecipe(input, output, recipe.output.water);
                    DRY_RECIPES.add(recipeResult);
                    PotatoDrierRecipes.INSTANCE.addDryRecipe(input.getItem(), recipeResult);
                }
            }
        }

        static void readWetRecipes(Reader fileReader, Gson gson) {
            CustomWetRecipe[] recipes = gson.fromJson(fileReader, CustomWetRecipe[].class);

            for (CustomWetRecipe recipe : recipes) {
                ItemStack input = recipe.input.toItemStack();
                ItemStack output = recipe.output.toItemStack();

                if (!input.isEmpty() && !output.isEmpty()) {
                    PotatoDrierRecipe recipeResult = new PotatoDrierRecipe(input, output, 0);
                    WET_RECIPES.add(recipeResult);
                    PotatoDrierRecipes.INSTANCE.addWetRecipe(input.getItem(), recipeResult);
                }
            }
        }

        static void writeDryRecipes(Writer fileWriter, Gson gson, Supplier<List<PotatoDrierRecipe>> recipes) {
            List<PotatoDrierRecipe> defaultRecipes = recipes.get();
            List<CustomDryRecipe> customRecipes = Lists.newArrayList();

            for (PotatoDrierRecipe recipe : defaultRecipes) {
                PotatoDrierRecipes.INSTANCE.addDryRecipe(recipe.getInput().getItem(), recipe);
                customRecipes.add(recipe.toCustomDryRecipe());
                DRY_RECIPES.add(recipe);
            }

            gson.toJson(customRecipes.toArray(), CustomDryRecipe[].class, fileWriter);
        }

        static void writeWetRecipes(Writer fileWriter, Gson gson, Supplier<List<PotatoDrierRecipe>> recipes) {
            List<PotatoDrierRecipe> defaultRecipes = recipes.get();
            List<CustomWetRecipe> customRecipes = Lists.newArrayList();

            for (PotatoDrierRecipe recipe : defaultRecipes) {
                PotatoDrierRecipes.INSTANCE.addWetRecipe(recipe.getInput().getItem(), recipe);
                customRecipes.add(recipe.toCustomWetRecipe());
                WET_RECIPES.add(recipe);
            }

            gson.toJson(customRecipes.toArray(), CustomWetRecipe[].class, fileWriter);
        }
    }
}
