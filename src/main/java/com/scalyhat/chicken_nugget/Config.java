package com.scalyhat.chicken_nugget;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Double> ammoNauseaChance = BUILDER
            .comment("Chance for the raw chicken nugget, when used as Potato Cannon ammo, to cause Nausea. 0 ~ 1, set to 0 to disable")
            .define("ammo_nausea_chance", 0.3);

    public static final ForgeConfigSpec.BooleanValue doSuspiciousTeleport = BUILDER
            .comment("Whether the Suspicious nugget should attempt to teleport the consumer behind the nearest player")
            .define("do_suspicious_teleport", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();
    static void assignValues(ModConfig config) {
        ammoNauseaChance.set(config.getConfigData().getOrElse("ammo_nausea_chance", 0.3));
        doSuspiciousTeleport.set(config.getConfigData().getOrElse("do_suspicious_teleport", true));
    }
}
