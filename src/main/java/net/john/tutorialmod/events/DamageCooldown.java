package net.john.tutorialmod.events;

import net.john.tutorialmod.TutoriolMod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = TutoriolMod.MOD_ID)
public class DamageCooldown {

    // 記錄每個實體最近被打的次數
    private static final Map<UUID, Integer> recentHitCount = new HashMap<>();
    private static final Map<UUID, Long> lastHurtTime = new HashMap<>();

    // ===== 可調整參數 =====
    private static final int TIME_WINDOW = 20;        // 檢查最近幾 tick（40 = 2秒）
    private static final int HIT_THRESHOLD = 3;       // 被打超過幾次後開始減少擊退
    private static final float MIN_KNOCKBACK = 0.25f; // 最低擊退倍率（不會完全沒有）

    // 記錄被打次數
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        UUID id = entity.getUUID();
        long currentTime = entity.level().getGameTime();

        // 超過時間窗口就重置次數
        if (lastHurtTime.containsKey(id) && currentTime - lastHurtTime.get(id) > TIME_WINDOW) {
            recentHitCount.put(id, 0);
        }

        // 次數 +1
        int count = recentHitCount.getOrDefault(id, 0) + 1;
        recentHitCount.put(id, count);
        lastHurtTime.put(id, currentTime);

        // 原本的無敵時間設定（可保留）
        entity.invulnerableTime = 15;
    }

    // 根據被打次數減少擊退
    @SubscribeEvent
    public static void onKnockBack(LivingKnockBackEvent event) {
        LivingEntity entity = event.getEntity();
        UUID id = entity.getUUID();

        int hitCount = recentHitCount.getOrDefault(id, 0);

        if (hitCount > HIT_THRESHOLD) {
            // 被打次數越多，擊退越弱
            // 例如：第4次開始減弱，之後每次再更弱
            float factor = Math.max(MIN_KNOCKBACK, 1.0f - (hitCount - HIT_THRESHOLD) * 0.2f);

            event.setStrength(event.getStrength() * factor);
        }
    }
}