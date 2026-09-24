package net.john.tutorialmod.events;

import com.google.common.collect.ImmutableMap;
import net.john.tutorialmod.item.custom.ModArmorItem;
import net.john.tutorialmod.item.custom.ModArmorMaterials;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

// 用 TickEvent.PlayerTickEvent 取代已 deprecated 的 onArmorTick
// @Mod.EventBusSubscriber 會自動把這個 class 裡所有 @SubscribeEvent 方法註冊到 Forge 的 EVENT_BUS
@Mod.EventBusSubscriber(modid = "tutorialmod")
public class ArmorEffectHandler {

    // 盔甲材質 → 對應效果的對照表
    // 用 MobEffect（效果類型）而非 MobEffectInstance（效果實例），
    // 因為 MobEffectInstance 有剩餘時間等狀態，不能靜態共用
    private static final Map<ArmorMaterial, MobEffect> MATERIAL_TO_EFFECT_MAP =
            new ImmutableMap.Builder<ArmorMaterial, MobEffect>()
                    .put(ModArmorMaterials.SAPPHIRE, MobEffects.NIGHT_VISION)
                    .build();

    // 效果持續時間（單位：ticks，20 ticks = 1 秒）
    // 設為 260 ticks，讓剩餘時間低於 80 ticks 時能提前刷新，避免效果閃爍
    private static final int EFFECT_DURATION = 260;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // Phase.END 確保每個 tick 只執行一次（START 和 END 各觸發一次）
        if (event.phase != TickEvent.Phase.END) return;

        // 只在伺服器端執行，避免客戶端和伺服器各跑一次
        if (event.player.level().isClientSide()) return;

        Player player = event.player;

        // 必須穿著全套盔甲才檢查效果
        if (!hasFullSuitOfArmorOn(player)) return;

        // 遍歷對照表，對符合材質的玩家套用對應效果
        for (Map.Entry<ArmorMaterial, MobEffect> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            if (hasCorrectArmorOn(entry.getKey(), player)) {
                applyEffect(player, entry.getValue());
            }
        }
    }

    // 套用效果，只在效果不存在或剩餘時間低於 80 ticks（4 秒）時才重新套用
    // 這樣效果會在到期前自動續上，不會出現夜視閃爍的空窗期
    private static void applyEffect(Player player, MobEffect effect) {
        MobEffectInstance current = player.getEffect(effect);

        if (current == null || current.getDuration() < 80) {
            player.addEffect(new MobEffectInstance(
                    effect,
                    EFFECT_DURATION, // 持續時間
                    1,               // 效果等級（0 = I，1 = II）
                    false,           // 是否為環境效果（信標/藥水雲產生的樣式）
                    false,           // 是否顯示粒子效果
                    true             // 是否在畫面右上角顯示效果圖示
            ));
        }
    }

    // 檢查玩家是否穿著完整的一套盔甲（四個槽位都不為空）
    // getArmor(0) = 靴子, (1) = 護腿, (2) = 胸甲, (3) = 頭盔
    private static boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots       = player.getInventory().getArmor(0);
        ItemStack leggings    = player.getInventory().getArmor(1);
        ItemStack breastplate = player.getInventory().getArmor(2);
        ItemStack helmet      = player.getInventory().getArmor(3);

        return !boots.isEmpty() && !leggings.isEmpty()
                && !breastplate.isEmpty() && !helmet.isEmpty();
    }

    // 檢查玩家穿的是否全套指定材質的盔甲
    private static boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        // 先確認所有槽位都是 ArmorItem，
        // 避免後面強轉型（cast）時發生 ClassCastException
        for (ItemStack armorStack : player.getInventory().armor) {
            if (!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots       = (ArmorItem) player.getInventory().getArmor(0).getItem();
        ArmorItem leggings    = (ArmorItem) player.getInventory().getArmor(1).getItem();
        ArmorItem breastplate = (ArmorItem) player.getInventory().getArmor(2).getItem();
        ArmorItem helmet      = (ArmorItem) player.getInventory().getArmor(3).getItem();

        // 四個部位的材質都必須符合才算穿著正確
        return boots.getMaterial()       == material &&
               leggings.getMaterial()    == material &&
               breastplate.getMaterial() == material &&
               helmet.getMaterial()      == material;
    }
}
