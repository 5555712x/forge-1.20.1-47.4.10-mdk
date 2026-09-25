package net.john.tutorialmod.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties MARSHMALLOW_SKEWERS = new FoodProperties.Builder().nutrition(1).fast()
            .saturationMod(0.4f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 0.5f).build();
    public static final FoodProperties BEEF_SANDWICH = new FoodProperties.Builder().nutrition(12)
            .saturationMod(8.0f).effect(() -> new MobEffectInstance(MobEffects.SATURATION, 100), 8.0f).build();


}
