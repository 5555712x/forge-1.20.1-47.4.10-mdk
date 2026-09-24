package net.john.tutorialmod.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class recovery_compass_effect extends Item {
    public recovery_compass_effect(Properties properties) {
        super(new Properties());
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        // 確保在伺服器端執行，且持有者是玩家
        if(!level.isClientSide()&& entity instanceof Player player) {

            // 檢查該物品是否正被玩家拿在「主手 (Main Hand)」或「副手 (Offhand)」
            boolean isHeld = player.getMainHandItem() == stack || player.getOffhandItem() == stack;


            if (isHeld) {
                // 只有「拿在手上」時才給予抗性 III 與虛弱 II
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 2, true, false));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 1, true, false));
            }
        }

        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
}
