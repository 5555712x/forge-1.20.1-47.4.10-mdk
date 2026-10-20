package net.john.tutorialmod.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class SpearItem extends SwordItem {

    // 自訂攻擊距離，單位為方塊（原版近戰預設 3.0）
    private final double attackRange;

    // 攻擊距離屬性修改器的固定 UUID，每個物品唯一
    private static final UUID ATTACK_RANGE_UUID = UUID.fromString("a8f6c301-7c4e-4b1a-9d2e-3f5a6b7c8d9e");

    /**
     * @param tier              工具等級
     * @param attackDamageBonus 額外攻擊傷害
     * @param attackSpeed       攻擊速度（負值）
     * @param attackRange       攻擊距離（方塊數，原版預設 3.0）
     * @param properties        物品屬性
     */
    public SpearItem(Tier tier, int attackDamageBonus, float attackSpeed,
                     double attackRange, Properties properties) {
        super(tier, attackDamageBonus, attackSpeed, properties);
        this.attackRange = attackRange;
    }

    /**
     * 完全移除劍的橫掃：回傳 false 後，flag3 為 false，
     * 原版就不會跑橫掃傷害、白色刃氣粒子和 sweep 音效，
     * 改播一般的強攻擊音效（跟斧頭等非橫掃武器相同）。
     */
    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction action) {
        if (action == net.minecraftforge.common.ToolActions.SWORD_SWEEP) {
            return false;
        }
        return super.canPerformAction(stack, action);
    }

    /**
     * 透過 Forge 的 ATTACK_RANGE 屬性延伸攻擊距離。
     *
     * 原版的近戰 raycast 距離由 ForgeMod.ATTACK_RANGE 屬性控制，預設基礎值為 3.0。
     * 在這裡加入一個 ADDITION 修改器，讓持矛時的攻擊距離變成 attackRange。
     * 這樣客戶端和伺服器都會使用正確的距離判定，不需要額外的 Event Handler。
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        // 先取得父類（SwordItem）的屬性，包含攻擊傷害和攻擊速度
        Multimap<Attribute, AttributeModifier> parentModifiers = super.getAttributeModifiers(slot, stack);

        // 只在主手裝備時才套用攻擊距離修改
        if (slot != EquipmentSlot.MAINHAND) {
            return parentModifiers;
        }

        // 計算需要增加的距離（原版基礎 3.0，加上差值讓總距離 = attackRange）
        double rangeBonus = attackRange - 3.0;

        // 建立新的屬性 Map，在父類的基礎上加入攻擊距離修改器
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(parentModifiers);

        // 只有需要延伸距離時才加（若 attackRange <= 3.0 就不加）
        if (rangeBonus > 0) {
            builder.put(
                // Forge 1.20.1 中攻擊距離屬性的正確欄位名稱是 ENTITY_REACH
                // （舊名稱 ATTACK_RANGE 已在此版本改名，原始碼中有 alias 對應）
                ForgeMod.ENTITY_REACH.get(),
                new AttributeModifier(
                    ATTACK_RANGE_UUID,
                    "Spear attack range bonus",
                    rangeBonus,
                    AttributeModifier.Operation.ADDITION
                )
            );
        }

        return builder.build();
    }

    /**
     * 讓 SpearAttackHandler 可以讀取這把矛的攻擊距離。
     */
    public double getAttackRange() {
        return attackRange;
    }
}
