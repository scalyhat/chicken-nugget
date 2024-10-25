package com.scalyhat.chicken_nugget;

import com.scalyhat.chicken_nugget.compat.CreateFeatures;
import com.scalyhat.chicken_nugget.content.ItemInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundEngine;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChickenNugget implements ModInitializer {
	public static final String MOD_ID = "chicken_nugget";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final SoundEvent susNote = Registry.register(Registries.SOUND_EVENT,
			Identifier.of(MOD_ID, "sus_note"),
			SoundEvent.of(Identifier.of(MOD_ID, "sus_note")));

	@Override
	public void onInitialize() {
		ItemInitializer.registerAll();

		if (FabricLoader.getInstance().isModLoaded("create")) {
			CreateFeatures.add();
		}
	}
}