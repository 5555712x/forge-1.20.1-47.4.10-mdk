package net.john.tutorialmod.item.custom;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "tutoriolMod")
public class ChainmailHelmetFireResistance {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = null;
        if (event.phase == TickEvent.Phase.START && !event.player.level().isClientSide()) {
            player = event.player;
        }

        boolean hasChainHelmet = player.getItemBySlot(EquipmentSlot.HEAD).is(Items.CHAINMAIL_HELMET);
        boolean hasChainChestplate = player.getItemBySlot(EquipmentSlot.CHEST).is(Items.CHAINMAIL_CHESTPLATE);
        boolean hasChainLeggings = player.getItemBySlot(EquipmentSlot.LEGS).is(Items.CHAINMAIL_LEGGINGS);
        boolean hasChainBoots = player.getItemBySlot(EquipmentSlot.FEET).is(Items.CHAINMAIL_BOOTS);
        // 如果頭、胸、腿、腳都是鎖鏈裝

        if (hasChainBoots && hasChainChestplate && hasChainHelmet && hasChainLeggings) {
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 40, 0, true, true));
        }
        //第 4 個參數（true）—— Ambient（環境效果）意思：代表這個效果是否為「環境周遭產生的」（例如像走進烽火台 Beacon 範圍那樣）。影響：如果設為 true，玩家身上的藥水粒子特效會變得比較淡、呈半透明狀，不會干擾視線。
        // 第 5 個參數（false）—— Visible（是否顯示圖示或粒子）意思：代表是否要在畫面右上方或背包介面顯示這個效果的圖示（Icon）。影響：如果設為 false，玩家身上雖然確實有抗火能力，但畫面上不會看到一 閃一 閃的圖示。

    }
}
