package com.scalyhat.chicken_nugget.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

import static com.scalyhat.chicken_nugget.content.ItemInitializer.*;

public class ModelGenerator extends FabricModelProvider {

    public ModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(chickenBlock);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(rawChickenNugget, Models.GENERATED);
        itemModelGenerator.register(rawChickenIngot, Models.GENERATED);
        itemModelGenerator.register(cookedChickenNugget, Models.GENERATED);
        itemModelGenerator.register(cookedChickenIngot, Models.GENERATED);
        itemModelGenerator.register(amogusChickenNugget, Models.GENERATED);
    }
}
