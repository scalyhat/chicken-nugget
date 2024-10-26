package com.scalyhat.chicken_nugget.content;

import com.scalyhat.chicken_nugget.Config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import static com.scalyhat.chicken_nugget.ChickenNugget.susNote;

public class SuspiciousNuggetItem extends Item {
    public SuspiciousNuggetItem(Settings settings) {super(settings);}

    private boolean canTeleportTo(LivingEntity teleportee, BlockPos targetPosition) {
        World world = teleportee.getWorld();
        Box potentialBounds = teleportee.getBoundingBox().offset(targetPosition);
        return world.isChunkLoaded(targetPosition)
                && world.isSpaceEmpty(potentialBounds)
                && world.getBlockState(targetPosition.down()).blocksMovement();
    }

    private final TargetPredicate playerTargetingPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(16);

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);

        if (!world.isClient) {
            Vec3d oriPosition = user.getPos();
            double oriX = user.getX();
            double oriY = user.getY();
            double oriZ = user.getZ();

            boolean doRandomTeleport = true;

            PlayerEntity potentialTarget = world.getClosestPlayer(playerTargetingPredicate, user);
            if (potentialTarget != null && Config.doSuspiciousTeleport.get()) {
                double targetDirectionRad = Math.toRadians(potentialTarget.getHeadYaw()+90);
                Vec3d targetPosition = potentialTarget.getPos().subtract(new Vec3d(Math.cos(targetDirectionRad), 0, Math.sin(targetDirectionRad))); // position 1 block behind target, irrespective of pitch
                Vec3d targetRotation = potentialTarget.getPos().subtract(targetPosition).normalize(); // rotation from user to target
                double newX = targetPosition.getX();
                double newZ = targetPosition.getZ();
                for (double offset = -1; offset <= 1; offset = offset+0.25) {
                    if (!canTeleportTo(user, BlockPos.ofFloored(targetPosition.add(0,offset,0)))){continue;}
                    double newY = targetPosition.getY()+offset;
                    if (user.hasVehicle()) {
                        user.stopRiding();
                    }

                    if (user.teleport(newX, newY, newZ, false)) {
                        world.emitGameEvent(GameEvent.TELEPORT, oriPosition, GameEvent.Emitter.of(user));
                        world.playSound((PlayerEntity)null, oriX, oriY, oriZ, susNote, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        world.playSoundFromEntity((PlayerEntity)null, user, susNote, SoundCategory.PLAYERS, 1.0f, 1.0f);

                        float yaw = (float) (Math.atan2(targetRotation.getZ(), targetRotation.getX()) * (180 / Math.PI)) - 90;
                        float pitch = (float) -(Math.atan2(targetRotation.getY(), Math.sqrt(Math.pow(targetRotation.getX(),2) + Math.pow(targetRotation.getZ(),2))) * (180 / Math.PI));
                        user.setHeadYaw(yaw);
                        user.setPitch(pitch);
                        doRandomTeleport = false;
                        break;
                    }
                }
            }
            if (doRandomTeleport) {
                for(int i = 0; i < 16; ++i) {
                    double newX = user.getX() + (user.getRandom().nextDouble() - 0.5) * 16.0;
                    double newY = MathHelper.clamp(user.getY() + (double)(user.getRandom().nextInt(16) - 8), (double)world.getBottomY(), (double)(world.getBottomY() + ((ServerWorld)world).getLogicalHeight() - 1));
                    double newZ = user.getZ() + (user.getRandom().nextDouble() - 0.5) * 16.0;
                    if (user.hasVehicle()) {
                        user.stopRiding();
                    }

                    if (user.teleport(newX, newY, newZ, false)) {
                        world.emitGameEvent(GameEvent.TELEPORT, oriPosition, GameEvent.Emitter.of(user));
                        world.playSound((PlayerEntity)null, oriX, oriY, oriZ, susNote, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        user.playSound(susNote, 1.0F, 1.0F);
                        break;
                    }
                }
            }

            if (user instanceof PlayerEntity) {
                ((PlayerEntity)user).getItemCooldownManager().set(this, 40);
            }
        }

        return itemStack;
    }
}
