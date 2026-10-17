package net.john.tutorialmod.item.custom;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

// 自訂盔甲基底類別，繼承原版 ArmorItem
// 盔甲效果邏輯已移至 ArmorEffectHandler（用 TickEvent.PlayerTickEvent 實作）
// 這樣可以避免使用 Forge 47.x 已 deprecated 的 onArmorTick 方法
public class ModArmorItem extends ArmorItem {

    public ModArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }
}
