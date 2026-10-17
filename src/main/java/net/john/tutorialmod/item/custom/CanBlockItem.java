package net.john.tutorialmod.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

import java.util.UUID;

public class CanBlockItem extends ShieldItem {

    // 攻擊傷害和攻擊速度，原本由 SwordItem 的 Tier 決定
    // 現在改成直接傳入數值，透過 getAttributeModifiers 加上去
    private final float attackDamage;
    private final float attackSpeed;

    // 固定 UUID，讓屬性修改器有唯一識別碼
    private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    private static final UUID ATTACK_SPEED_UUID  = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    /**
     * @param attackDamage 攻擊傷害（直接數值，例如 7.0 = 劍的 +6 + 基礎 1）
     * @param attackSpeed  攻擊速度（負值，例如 -2.4）
     * @param properties   物品屬性（需自行設定 durability）
     */
    public CanBlockItem(float attackDamage, float attackSpeed, Properties properties) {
        super(properties);
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    // 加上攻擊傷害和攻擊速度屬性，讓這個物品可以當武器用
    // 原版 SwordItem 也是用這個方法加屬性的
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        // 只在主手時套用攻擊屬性
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                    ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(
                    ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        // 其他槽位用 ShieldItem 的預設（空的）
        return super.getAttributeModifiers(slot, stack);
    }
}
