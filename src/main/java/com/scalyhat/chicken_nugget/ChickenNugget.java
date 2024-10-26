package com.scalyhat.chicken_nugget;

import com.scalyhat.chicken_nugget.content.ItemInitializer;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class ChickenNugget implements ModInitializer {
	public static final String MOD_ID = "chicken_nugget";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final SoundEvent susNote = Registry.register(Registries.SOUND_EVENT,
			Identifier.of(MOD_ID, "sus_note"),
			SoundEvent.of(Identifier.of(MOD_ID, "sus_note")));

	@Override
	public void onInitialize() {
		ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, Config.SPEC);
		ModConfigEvents.loading(MOD_ID).register(Config::assignValues);
		ModConfigEvents.reloading(MOD_ID).register(Config::assignValues);

		ItemInitializer.registerAll();

		if (FabricLoader.getInstance().isModLoaded("create")) {
			try {
				Class<?> CreateFeatures = Class.forName("com.scalyhat.chicken_nugget.compat.CreateFeatures");
				CreateFeatures.getMethod("add").invoke(null);
			} catch (ClassNotFoundException e) {
				LOGGER.warn("Attempted loading Create Features without Create being present.");
			} catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
				LOGGER.warn("Loading Create Features failed.");
            }
        }
	}
}