package com.scalyhat.chicken_nugget.content;

import com.scalyhat.chicken_nugget.ChickenNugget;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ItemInitializer {

    public static final Item rawChickenNugget = Registry.register(Registries.ITEM,
            Identifier.of(ChickenNugget.MOD_ID, "raw_chicken_nugget"),
            new Item(new Item.Settings().food(new FoodComponent.Builder().meat().hunger(1).saturationModifier(0).snack()
                    .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600), 0.3f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200), 0.3f)
                    .build()))
    );
    public static final Item cookedChickenNugget = Registry.register(Registries.ITEM,
            Identifier.of(ChickenNugget.MOD_ID, "cooked_chicken_nugget"),
            new Item(new Item.Settings().food(new FoodComponent.Builder().meat().hunger(1).saturationModifier(1).snack().build()))
    );
    public static final Item amogusChickenNugget = Registry.register(Registries.ITEM,
            Identifier.of(ChickenNugget.MOD_ID, "amogus_chicken_nugget"),
            new SuspiciousNuggetItem(new Item.Settings().food(new FoodComponent.Builder().meat().hunger(2).saturationModifier(2).alwaysEdible().snack().build()))
    );

    public static final Item rawChickenIngot = Registry.register(Registries.ITEM,
            Identifier.of(ChickenNugget.MOD_ID, "raw_chicken_ingot"),
            new Item(new Item.Settings().food(new FoodComponent.Builder().meat().hunger(3).saturationModifier(0)
                    .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600), 1f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600), 1f)
                    .build()))
    );
    public static final Item cookedChickenIngot = Registry.register(Registries.ITEM,
            Identifier.of(ChickenNugget.MOD_ID, "cooked_chicken_ingot"),
            new Item(new Item.Settings().food(new FoodComponent.Builder().meat().hunger(6).saturationModifier(1).build()))
    );

    public static final Block chickenBlock = Registry.register(Registries.BLOCK,
            Identifier.of(ChickenNugget.MOD_ID, "chicken_block"),
            new FallingBlock(AbstractBlock.Settings.create().slipperiness(0.95f).sounds(BlockSoundGroup.SLIME).jumpVelocityMultiplier(0.9f).hardness(0.5f))
    );
    public static final BlockItem chickenBlockItem = Registry.register(Registries.ITEM,
            Identifier.of(ChickenNugget.MOD_ID, "chicken_block"),
            new BlockItem(chickenBlock, new Item.Settings()));

    public static void registerAll() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> {
                    itemGroup.add(rawChickenNugget);
                    itemGroup.add(cookedChickenNugget);
                    itemGroup.add(amogusChickenNugget);
                    itemGroup.add(rawChickenIngot);
                    itemGroup.add(cookedChickenIngot);
                });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register((itemGroup) -> itemGroup.add(chickenBlockItem));
    }
}
