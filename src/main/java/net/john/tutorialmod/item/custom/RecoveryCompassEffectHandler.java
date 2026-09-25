package net.john.tutorialmod.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "tutorialmod")
public class RecoveryCompassEffectHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // 確保只在伺服器端執行，且事件剛好在 START 階段觸發（避免重複計算）
        if (event.phase == TickEvent.Phase.START && !event.player.level().isClientSide()) {
            Player player = event.player;

            // 檢查玩家主手或副手是否拿著原版的回生羅盤
            boolean holdingCompassMainHand = player.getMainHandItem().is(Items.RECOVERY_COMPASS);
            boolean holdingCompassOffHand = player.getOffhandItem().is(Items.RECOVERY_COMPASS);

            if (holdingCompassMainHand) {
                // 給予回復效果 (Resistance II，持續 80 ticks)
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 30, 1, true, true));
                // 給予抗性提升效果 (Resistance IV，持續 80 ticks)
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 80, 3, true, true));
                // 給予緩速效果 (SLOWDOWN II，持續 160 ticks)
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,160, 1, true, true));
            }
            if (holdingCompassOffHand){
                // 給予抗性提升效果 (Resistance II，持續 80 ticks)
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 80, 1, true, true));
                // 給予虛弱效果 (Weakness II，持續 40 ticks)
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 1, true, true));
                // 給予速度效果 (SPEED III，持續 20 ticks)
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 2, true, true));

            }
        }
    }
}