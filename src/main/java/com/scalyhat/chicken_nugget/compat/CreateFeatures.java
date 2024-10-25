package com.scalyhat.chicken_nugget.compat;

import com.scalyhat.chicken_nugget.ChickenNugget;
import com.scalyhat.chicken_nugget.content.ItemInitializer;
import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonProjectileType;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileTypeManager;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerInteractionBehaviour;
import net.minecraft.util.Identifier;

public class CreateFeatures {
    public static void add() {
        PotatoProjectileTypeManager.registerBuiltinType(
                Identifier.of(ChickenNugget.MOD_ID, "chicken_nugget_type"),
                new PotatoCannonProjectileType.Builder(
                        Identifier.of(ChickenNugget.MOD_ID, "chicken_nugget_projectile")
                ).damage(2).reloadTicks(5).renderTumbling()
                        .addItems(ItemInitializer.rawChickenNugget).register()
        );
    }
}
