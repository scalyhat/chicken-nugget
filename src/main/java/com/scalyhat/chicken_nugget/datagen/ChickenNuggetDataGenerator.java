package com.scalyhat.chicken_nugget.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

import static com.scalyhat.chicken_nugget.content.ItemInitializer.*;

public class ChickenNuggetDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(AdvancementsProvider::new);
		pack.addProvider(RecipesProvider::new);
		pack.addProvider(BlockLootTableProvider::new);
		pack.addProvider(ModelGenerator::new);
		pack.addProvider(LanguageGenerator::new);
	}

	private static class AdvancementsProvider extends FabricAdvancementProvider {
		protected AdvancementsProvider(FabricDataOutput dataGenerator) {
			super(dataGenerator);
		}

		@Override
		public void generateAdvancement(Consumer<Advancement> consumer) {
			Advancement.Builder.create().display(
					rawChickenIngot,
					Text.literal("Fixed point in history"),
					Text.literal("Attempt to unnugget a chicken; despair in your inability to"),
					new Identifier("textures/gui/advancements/backgrounds/adventure.png"),
					AdvancementFrame.GOAL,
					false,
					true,
					true
			).criterion("got_chicken_ingot", InventoryChangedCriterion.Conditions.items(rawChickenIngot)).build(consumer, "chicken_nugget_chicken_ingot");
		}
	}

	private static class RecipesProvider extends FabricRecipeProvider {
		private RecipesProvider(FabricDataOutput generator) {
			super(generator);
		}

		@Override
		public void generate(Consumer<RecipeJsonProvider> exporter) {
			FabricRecipeProvider.offerCompactingRecipe(exporter, RecipeCategory.FOOD, rawChickenIngot, rawChickenNugget);
			FabricRecipeProvider.offerCompactingRecipe(exporter, RecipeCategory.FOOD, chickenBlockItem, rawChickenIngot);
			FabricRecipeProvider.offerShapelessRecipe(exporter, rawChickenNugget, rawChickenIngot, "main", 9);
			FabricRecipeProvider.offerShapelessRecipe(exporter, rawChickenIngot, chickenBlockItem, "main", 9);

			ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, amogusChickenNugget).pattern("nen")
					.input('n', cookedChickenNugget).criterion(FabricRecipeProvider.hasItem(cookedChickenNugget),
							FabricRecipeProvider.conditionsFromItem(cookedChickenNugget))
					.input('e', Items.ENDER_PEARL).criterion(FabricRecipeProvider.hasItem(Items.ENDER_PEARL),
							FabricRecipeProvider.conditionsFromItem(Items.ENDER_PEARL))
					.offerTo(exporter);

			FabricRecipeProvider.offerFoodCookingRecipe(
					exporter, "smoker", RecipeSerializer.SMOKING, 100, rawChickenNugget, cookedChickenNugget, 0.1f
			);
			FabricRecipeProvider.offerFoodCookingRecipe(
					exporter, "smoker", RecipeSerializer.SMOKING, 100, rawChickenIngot, cookedChickenIngot, 0.1f
			);
			FabricRecipeProvider.offerFoodCookingRecipe(
					exporter, "furnace", RecipeSerializer.SMELTING, 200, rawChickenNugget, cookedChickenNugget, 0.1f
			);
			FabricRecipeProvider.offerFoodCookingRecipe(
					exporter, "furnace", RecipeSerializer.SMELTING, 200, rawChickenIngot, cookedChickenIngot, 0.1f
			);
			FabricRecipeProvider.offerFoodCookingRecipe(
					exporter, "campfire", RecipeSerializer.CAMPFIRE_COOKING, 600, rawChickenNugget, cookedChickenNugget, 0.1f
			);
			FabricRecipeProvider.offerFoodCookingRecipe(
					exporter, "campfire", RecipeSerializer.CAMPFIRE_COOKING, 600, rawChickenIngot, cookedChickenIngot, 0.1f
			);
		}
	}

	private static class BlockLootTableProvider extends FabricBlockLootTableProvider {
		public BlockLootTableProvider(FabricDataOutput dataOutput) {
			super(dataOutput);
		}

		@Override
		public void generate() {
			addDrop(chickenBlock, chickenBlockItem);
		}
	}

}
