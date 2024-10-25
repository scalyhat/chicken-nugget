package com.scalyhat.chicken_nugget.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import static com.scalyhat.chicken_nugget.ChickenNugget.susNote;
import static com.scalyhat.chicken_nugget.content.ItemInitializer.*;

public class LanguageGenerator extends FabricLanguageProvider {
    protected LanguageGenerator(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(rawChickenNugget, "Raw Chicken Nugget");
        translationBuilder.add(cookedChickenNugget, "Cooked Chicken Nugget");
        translationBuilder.add(amogusChickenNugget, "Suspicious Chicken Nugget");
        translationBuilder.add(rawChickenIngot, "Raw Chicken \"Ingot\"");
        translationBuilder.add(cookedChickenIngot, "Cooked Chicken \"Ingot\"");
        translationBuilder.add(chickenBlockItem, "Chicken \"Block\"");
        translationBuilder.add(susNote.getId(), "Suspicious Sound");
    }
}
