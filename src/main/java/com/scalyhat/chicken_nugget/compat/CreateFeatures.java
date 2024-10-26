package com.scalyhat.chicken_nugget.compat;

import com.scalyhat.chicken_nugget.ChickenNugget;
import com.scalyhat.chicken_nugget.content.ItemInitializer;
import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonProjectileType;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileTypeManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class CreateFeatures {
    private static final Random chickenCreateRandom = new Random();
    public static void sprayChickenShrapnel(World world, Vec3d source, int projectileCount) {
        for (int i = 0; i < projectileCount; i++) {
            PotatoProjectileEntity projectile = AllEntityTypes.POTATO_PROJECTILE.create(world);
            projectile.setItem(new ItemStack(ItemInitializer.rawChickenNugget));

            projectile.setPosition(source.x, source.y, source.z);
            projectile.setVelocity(new Vec3d(chickenCreateRandom.nextFloat()-0.5f, chickenCreateRandom.nextFloat()-0.5f, chickenCreateRandom.nextFloat()-0.5f).multiply(1.2));
            world.spawnEntity(projectile);
        }
    }

    public static void add() {
        PotatoProjectileTypeManager.registerBuiltinType(
                Identifier.of(ChickenNugget.MOD_ID, "chicken_nugget_type"),
                new PotatoCannonProjectileType.Builder(
                        Identifier.of(ChickenNugget.MOD_ID, "chicken_nugget_projectile")
                ).damage(2).reloadTicks(5).renderTumbling()
                        .onEntityHit((entityHitResult -> {
                            if (entityHitResult.getEntity() instanceof LivingEntity target) {
                                if (chickenCreateRandom.nextFloat() > 0.7) {
                                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 40));
                                }
                            }
                            return false;
                        }))
                        .addItems(ItemInitializer.rawChickenNugget).register()
        );
        PotatoProjectileTypeManager.registerBuiltinType(
                Identifier.of(ChickenNugget.MOD_ID, "chicken_ingot_type"),
                new PotatoCannonProjectileType.Builder(
                        Identifier.of(ChickenNugget.MOD_ID, "chicken_ingot_projectile")
                ).damage(8).reloadTicks(20).renderTumbling()
                        .onEntityHit((entityHitResult) -> {
                            Entity target = entityHitResult.getEntity();
                            sprayChickenShrapnel(target.getWorld(), target.getPos(), 18);
                            return false;
                        })
                        .onBlockHit((world, ray) -> {
                            sprayChickenShrapnel((World) world, ray.getPos(), 18);
                            return false;
                        })
                        .addItems(ItemInitializer.rawChickenIngot).register()
        );
    }
}
