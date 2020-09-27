package aldinh777.potatoheadshot.jei.infuser;

import aldinh777.potatoheadshot.lists.PotatoBlocks;
import aldinh777.potatoheadshot.lists.PotatoItems;
import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class InfuserRecipe implements IRecipeWrapper {
	
	private final List<ItemStack> inputs;
	private final ItemStack output;
	
	private static final Item LAPIS = new Item();
	private static final Item DYE_LIGHT_BLUE = new Item();
	private static final Item DYE_RED = new Item();
	private static final Item DYE_LIME = new Item();
	private static final Item DYE_WHITE = new Item();
	
	public InfuserRecipe(List<ItemStack> inputs, ItemStack output) {
		this.inputs = inputs;
		this.output = output;
	}
	
	public static List<InfuserRecipe> getRecipes() {
		List<InfuserRecipe> jeiRecipes = Lists.newArrayList();
		
		Item lava = Items.LAVA_BUCKET;
		Item water = Items.WATER_BUCKET;
		Item enderPearl = Items.ENDER_PEARL;
		Item string = Items.STRING;
		Item coal = Items.COAL;
		Item potatoLeaves = PotatoItems.POTATO_LEAVES;

		Item crystal = PotatoItems.CRYSTAL;
		Item chargedCrystal = PotatoItems.CHARGED_CRYSTAL;
		Item concentratedCrystal = PotatoItems.CONCENTRATED_CRYSTAL;
		Item manaFlower = Item.getItemFromBlock(PotatoBlocks.MANA_FLOWER);
		Item ultimateCrystal = PotatoItems.ULTIMATE_CRYSTAL;
		Item ultimateChargedCrystal = PotatoItems.ULTIMATE_CHARGED_CRYSTAL;
		Item ultimateConcentratedCrystal = PotatoItems.ULTIMATE_CONCENTRATED_CRYSTAL;
		Item ultimateBrokenFuel = PotatoItems.ULTIMATE_BROKEN_FUEL;
		Item ultimateManaFlower = Item.getItemFromBlock(PotatoBlocks.ULTIMATE_FLOWER);
		
		Item grass = Item.getItemFromBlock(Blocks.GRASS);
		Item mycelium = Item.getItemFromBlock(Blocks.MYCELIUM);
		Item dirt = Item.getItemFromBlock(Blocks.DIRT);
		Item stone = Item.getItemFromBlock(Blocks.STONE);
		Item gravel = Item.getItemFromBlock(Blocks.GRAVEL);
		Item rack = Item.getItemFromBlock(Blocks.NETHERRACK);
		Item cobble = Item.getItemFromBlock(Blocks.COBBLESTONE);
		Item sand = Item.getItemFromBlock(Blocks.SAND);
		Item brownMushroom = Item.getItemFromBlock(Blocks.BROWN_MUSHROOM);
		Item glowStone = Item.getItemFromBlock(Blocks.GLOWSTONE);
		Item endStone = Item.getItemFromBlock(Blocks.END_STONE);
		Item ice = Item.getItemFromBlock(Blocks.ICE);
		Item packedIce = Item.getItemFromBlock(Blocks.PACKED_ICE);
		Item cobweb = Item.getItemFromBlock(Blocks.WEB);
		
		basicOreRecipes(jeiRecipes);
		customOreRecipes(jeiRecipes);
		gemRecipes(jeiRecipes);
		
		addRecipe(jeiRecipes, new ItemStack(gravel), stone,
						cobble, cobble, cobble,
						sand, sand, sand);
		addRecipe(jeiRecipes, new ItemStack(rack), stone,
						cobble, lava, cobble,
						cobble, lava, cobble);
		addRecipe(jeiRecipes, new ItemStack(grass), dirt,
						potatoLeaves, potatoLeaves, potatoLeaves,
						dirt, water, dirt);
		addRecipe(jeiRecipes, new ItemStack(mycelium), dirt,
						brownMushroom, brownMushroom, brownMushroom,
						dirt, water, dirt);
		addRecipe(jeiRecipes, new ItemStack(glowStone), stone,
						lava, lava, lava,
						lava, lava, lava);
		addRecipe(jeiRecipes, new ItemStack(endStone), stone,
						enderPearl, enderPearl, enderPearl,
						enderPearl, enderPearl, enderPearl);
		addRecipe(jeiRecipes, new ItemStack(packedIce), ice,
						ice, ice, ice,
						ice, ice, ice);
		addRecipe(jeiRecipes, new ItemStack(cobweb), string,
						string, string, string,
						string, string, string);
		addRecipe(jeiRecipes, new ItemStack(ultimateCrystal), crystal,
				chargedCrystal, chargedCrystal, chargedCrystal,
				concentratedCrystal, concentratedCrystal, concentratedCrystal);
		addRecipe(jeiRecipes, new ItemStack(ultimateBrokenFuel), coal,
				ultimateCrystal, ultimateChargedCrystal, ultimateCrystal,
				ultimateCrystal, ultimateConcentratedCrystal, ultimateCrystal);
		addRecipe(jeiRecipes, new ItemStack(ultimateManaFlower), manaFlower,
				ultimateCrystal, ultimateChargedCrystal, ultimateCrystal,
				ultimateCrystal, ultimateConcentratedCrystal, ultimateCrystal);

		return jeiRecipes;
	}
	
	private static void basicOreRecipes(List<InfuserRecipe> list) {
		Item ironOre = Item.getItemFromBlock(Blocks.IRON_ORE);
		Item goldOre = Item.getItemFromBlock(Blocks.GOLD_ORE);
		Item diamondOre = Item.getItemFromBlock(Blocks.DIAMOND_ORE);
		Item emeraldOre = Item.getItemFromBlock(Blocks.EMERALD_ORE);
		Item coalOre = Item.getItemFromBlock(Blocks.COAL_ORE);
		Item redstoneOre = Item.getItemFromBlock(Blocks.REDSTONE_ORE);
		Item quartzOre = Item.getItemFromBlock(Blocks.QUARTZ_ORE);
		Item lapisOre = Item.getItemFromBlock(Blocks.LAPIS_ORE);

		Item stone = Item.getItemFromBlock(Blocks.STONE);
		Item rack = Item.getItemFromBlock(Blocks.NETHERRACK);
		
		addRecipe(list, new ItemStack(ironOre), stone,
				Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT,
				Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT);
		addRecipe(list, new ItemStack(goldOre), stone,
				Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT,
				Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT);
		addRecipe(list, new ItemStack(diamondOre), stone,
				Items.DIAMOND, Items.DIAMOND, Items.DIAMOND,
				Items.DIAMOND, Items.DIAMOND, Items.DIAMOND);
		addRecipe(list, new ItemStack(emeraldOre), stone,
				Items.EMERALD, Items.EMERALD, Items.EMERALD,
				Items.EMERALD, Items.EMERALD, Items.EMERALD);
		addRecipe(list, new ItemStack(coalOre), stone,
				Items.COAL, Items.COAL, Items.COAL,
				Items.COAL, Items.COAL, Items.COAL);
		addRecipe(list, new ItemStack(redstoneOre), stone,
				Items.REDSTONE, Items.REDSTONE, Items.REDSTONE,
				Items.REDSTONE, Items.REDSTONE, Items.REDSTONE);
		addRecipe(list, new ItemStack(quartzOre), rack,
				Items.QUARTZ, Items.QUARTZ, Items.QUARTZ,
				Items.QUARTZ, Items.QUARTZ, Items.QUARTZ);
		addRecipe(list, new ItemStack(lapisOre), stone,
				LAPIS, LAPIS, LAPIS,
				LAPIS, LAPIS, LAPIS);
	}
	
	private static void customOreRecipes(List<InfuserRecipe> list) {
		Item lava = Items.LAVA_BUCKET;
		Item water = Items.WATER_BUCKET;
		Item driedSweet = PotatoItems.DRIED_SWEET_POTATO;

		Item stone = Item.getItemFromBlock(Blocks.STONE);
		Item gravel = Item.getItemFromBlock(Blocks.GRAVEL);
		Item rack = Item.getItemFromBlock(Blocks.NETHERRACK);
		Item cobble = Item.getItemFromBlock(Blocks.COBBLESTONE);

		Item ironOre = Item.getItemFromBlock(Blocks.IRON_ORE);
		Item goldOre = Item.getItemFromBlock(Blocks.GOLD_ORE);
		Item redstoneOre = Item.getItemFromBlock(Blocks.REDSTONE_ORE);
		
		addRecipe(list, new ItemStack(ironOre), stone,
				gravel, lava, gravel,
				cobble, water, cobble);
		addRecipe(list, new ItemStack(goldOre), stone,
				rack, lava, rack,
				cobble, water, cobble);
		addRecipe(list, new ItemStack(redstoneOre), stone,
				DYE_RED, driedSweet, DYE_RED,
				DYE_RED, driedSweet, DYE_RED);
	}
	
	private static void gemRecipes(List<InfuserRecipe> list) {
		Item diamond = Items.DIAMOND;
		Item emerald = Items.EMERALD;
		Item quartz = Items.QUARTZ;
		Item dye = Items.DYE;
		
		Item crystalShard = PotatoItems.CRYSTAL_SHARD;
		Item crystal = PotatoItems.CRYSTAL;
		Item chargedCrystalShard = PotatoItems.CHARGED_CRYSTAL_SHARD;
		Item concentratedCrystalShard = PotatoItems.CONCENTRATED_CRYSTAL_SHARD;
		Item chargedCrystal = PotatoItems.CHARGED_CRYSTAL;
		Item concentratedCrystal = PotatoItems.CONCENTRATED_CRYSTAL;
		
		addRecipe(list, new ItemStack(crystal), crystalShard,
				crystalShard, crystalShard, crystalShard,
				crystalShard, crystalShard, crystalShard);
		addRecipe(list, new ItemStack(quartz), crystalShard,
				DYE_WHITE, DYE_WHITE, DYE_WHITE,
				DYE_WHITE, DYE_WHITE, DYE_WHITE);
		addRecipe(list, new ItemStack(dye, 1, 4), crystalShard,
				DYE_LIGHT_BLUE, DYE_LIGHT_BLUE, DYE_LIGHT_BLUE,
				DYE_LIGHT_BLUE, DYE_LIGHT_BLUE, DYE_LIGHT_BLUE);
		addRecipe(list, new ItemStack(diamond), crystal, crystalShard,
				DYE_LIGHT_BLUE, crystalShard, DYE_LIGHT_BLUE,
				crystalShard, DYE_LIGHT_BLUE);
		addRecipe(list, new ItemStack(emerald), crystal,
				crystalShard, DYE_LIME, crystalShard,
				DYE_LIME, crystalShard, DYE_LIME);
		addRecipe(list, new ItemStack(chargedCrystal), crystalShard,
				chargedCrystalShard, chargedCrystalShard, chargedCrystalShard,
				chargedCrystalShard, chargedCrystalShard, chargedCrystalShard);
		addRecipe(list, new ItemStack(concentratedCrystal), crystalShard,
				concentratedCrystalShard, concentratedCrystalShard, concentratedCrystalShard,
				concentratedCrystalShard, concentratedCrystalShard, concentratedCrystalShard);
	}
	
	private static ItemStack stackify(Item item) {
		if (item == LAPIS) {
			return new ItemStack(Items.DYE, 1, 4);
		}
		if (item == DYE_LIGHT_BLUE) {
			return new ItemStack(Items.DYE, 1, 12);
		}
		if (item == DYE_RED) {
			return new ItemStack(Items.DYE, 1, 1);
		}
		if (item == DYE_LIME) {
			return new ItemStack(Items.DYE, 1, 10);
		}
		if (item == DYE_WHITE) {
			return new ItemStack(Items.DYE, 1, 15);
		}

		return new ItemStack(item);
	}

	private static void addRecipe(List<InfuserRecipe> list, ItemStack output, Item middle, Item... fuses) {
		List<ItemStack> inputs = Lists.newArrayList(new ItemStack(middle));
		
		for (Item fuse : fuses) {
			inputs.add(stackify(fuse));
		}
		list.add(new InfuserRecipe(inputs, output));
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, this.inputs);
		ingredients.setOutput(ItemStack.class, this.output);
	}
}
