package net.john.tutorialmod.events;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "tutorialmod")
public class AtomicComboEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // 只在伺服器端、且在 Phase 結束時執行，避免重複觸發
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) {
            return;
        }

        Player player = event.player;
        CompoundTag playerTag = player.getPersistentData();

        // 檢查玩家身上是否有剩餘的連擊次數
        if (playerTag.contains("AtomicRemainingHits") && playerTag.getInt("AtomicRemainingHits") > 0) {

            // 1. 強制定身：把玩家位置鎖死在施放瞬間的座標
            if (playerTag.contains("AtomicLockX")) {
                player.setPos(
                        playerTag.getDouble("AtomicLockX"),
                        playerTag.getDouble("AtomicLockY"),
                        playerTag.getDouble("AtomicLockZ")
                );
                player.setDeltaMovement(0, player.getDeltaMovement().y, 0);
            }

            int delay = playerTag.getInt("AtomicComboDelay");

            if (delay > 0) {
                playerTag.putInt("AtomicComboDelay", delay - 1);
            } else {
                // 執行單次攻擊
                executeComboHit(player);

                // 更新剩餘次數與間隔
                int remaining = playerTag.getInt("AtomicRemainingHits") - 1;
                playerTag.putInt("AtomicRemainingHits", remaining);
                playerTag.putInt("AtomicComboDelay", 1); // 每 1 Tick 砍一次
            }
        }
    }

    private static void executeComboHit(Player player) {
        ServerLevel level = (ServerLevel) player.level();
        Vec3 look = player.getLookAngle();

        // 計算攻擊範圍
        AABB area = player.getBoundingBox()
                .inflate(1.0, 0.5, 1.0)
                .move(look.scale(2.0));

        // 視覺特效：拳風粒子
        Vec3 spawnPos = player.position().add(look.scale(1.5)).add(0, player.getEyeHeight() * 0.5, 0);
        level.sendParticles(ParticleTypes.SWEEP_ATTACK, spawnPos.x, spawnPos.y, spawnPos.z, 1, 0, 0, 0, 0);
        level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, spawnPos.x, spawnPos.y, spawnPos.z, 3, 0.2, 0.2, 0.2, 0.02);

        // 播放揮擊音效
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.6f, 0.8f);

        // 搜尋並傷害範圍內目標
        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class,
                area,
                entity -> entity != player && entity.isAlive()
        );

        for (LivingEntity target : targets) {
            target.hurt(player.damageSources().playerAttack(player), 1.5f);

            // 附加緩速與虛弱
            target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 10, 4, false, false, true
            ));
            target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.WEAKNESS, 10, 4, false, false, true
            ));

            // 命中暴擊粒子
            level.sendParticles(ParticleTypes.CRIT,
                    target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(),
                    8, 0.3, 0.3, 0.3, 0.2);
        }
    }
}