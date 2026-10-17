package net.john.tutorialmod.events;

import net.john.tutorialmod.item.custom.PunchItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "tutorialmod")
public class ClientItemLockHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) {
            return;
        }

        Player player = event.player;
        CompoundTag playerTag = player.getPersistentData();

        if (playerTag.contains("AtomicRemainingHits") && playerTag.getInt("AtomicRemainingHits") > 0) {

            // 1. 【絕對物品欄鎖定】強制把選取格釘在施放技能的那一格！
            if (playerTag.contains("LockedSlot")) {
                int lockedSlot = playerTag.getInt("LockedSlot");
                if (player.getInventory().selected != lockedSlot) {
                    player.getInventory().selected = lockedSlot;
                    // 同步給客戶端，強行把畫面上的框框彈回來
                    if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.connection.send(new net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket(lockedSlot));
                    }
                }
            }

            // 2. 連擊計時與執行
            int delay = playerTag.getInt("AtomicComboDelay");

            if (delay > 0) {
                playerTag.putInt("AtomicComboDelay", delay - 1);
            } else {
                // 執行單次攻擊
                performComboHit(player);

                int remaining = playerTag.getInt("AtomicRemainingHits") - 1;
                playerTag.putInt("AtomicRemainingHits", remaining);
                playerTag.putInt("AtomicComboDelay", 1);
            }
        }
    }

    private static void performComboHit(Player player) {
        ServerLevel level = (ServerLevel) player.level();
        Vec3 look = player.getLookAngle();
        AABB area = player.getBoundingBox().inflate(1.0, 0.5, 1.0).move(look.scale(2.0));

        // 粒子特效
        Vec3 spawnPos = player.position().add(look.scale(1.5)).add(0, player.getEyeHeight() * 0.5, 0);
        level.sendParticles(ParticleTypes.SWEEP_ATTACK, spawnPos.x, spawnPos.y, spawnPos.z, 1, 0, 0, 0, 0);
        level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, spawnPos.x, spawnPos.y, spawnPos.z, 3, 0.2, 0.2, 0.2, 0.02);

        // 音效
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.6f, 0.8f);

        // 傷害與效果
        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class, area, entity -> entity != player && entity.isAlive()
        );

        for (LivingEntity target : targets) {
            target.hurt(player.damageSources().playerAttack(player), 1.5f);
            target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 10, 4, false, false, true
            ));
            target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.WEAKNESS, 10, 4, false, false, true
            ));

            level.sendParticles(ParticleTypes.CRIT,
                    target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(),
                    8, 0.3, 0.3, 0.3, 0.2);
        }
    }
}