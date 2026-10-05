package net.john.tutorialmod.events;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "tutorialmod") // 確保這裡和小寫的 mods.toml 一致
public class ChainmailHelmetFireResistance {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // 確保在伺服器端且是 Tick 開始時才執行
        if (event.phase == TickEvent.Phase.START && !event.player.level().isClientSide()) {
            Player player = event.player;

            // 檢查玩家是否穿著全套鎖鏈裝
            boolean hasChainHelmet = player.getItemBySlot(EquipmentSlot.HEAD).is(Items.CHAINMAIL_HELMET);
            boolean hasChainChestplate = player.getItemBySlot(EquipmentSlot.CHEST).is(Items.CHAINMAIL_CHESTPLATE);
            boolean hasChainLeggings = player.getItemBySlot(EquipmentSlot.LEGS).is(Items.CHAINMAIL_LEGGINGS);
            boolean hasChainBoots = player.getItemBySlot(EquipmentSlot.FEET).is(Items.CHAINMAIL_BOOTS);

            // 如果頭、胸、腿、腳都是鎖鏈裝
            if (hasChainHelmet && hasChainChestplate && hasChainLeggings && hasChainBoots) {
                // 給予抗火效果：效果、持續時間(Ticks)、等級、環境特效、是否顯示圖示
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 40, 0, true, true));
            }
        }
    }
}