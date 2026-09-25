package net.john.tutorialmod.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.DistExecutor;

import java.util.List;
import java.util.UUID;


public class PunchItem extends SwordItem {

    private final double attackRange;
    private static final UUID ATTACK_RANGE_UUID = UUID.fromString("a8f6c301-7c4e-4b1a-9d2e-3f5a6b7c8d9e");

    public PunchItem(Tier tier, int attackDamageBonus, float attackSpeed,
                     double attackRange, Properties properties) {
        super(tier, attackDamageBonus, attackSpeed, properties);
        this.attackRange = attackRange;
    }

    @Override
    public AABB getSweepHitBox(ItemStack stack, Player player, net.minecraft.world.entity.Entity target) {
        return null;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> parentModifiers = super.getAttributeModifiers(slot, stack);
        if (slot != EquipmentSlot.MAINHAND) {
            return parentModifiers;
        }

        double rangeBonus = attackRange - 3.0;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(parentModifiers);

        if (rangeBonus > 0) {
            builder.put(
                    ForgeMod.ENTITY_REACH.get(),
                    new AttributeModifier(
                            ATTACK_RANGE_UUID,
                            "punch attack range bonus",
                            rangeBonus,
                            AttributeModifier.Operation.ADDITION
                    )
            );
        }
        return builder.build();
    }

    public double getAttackRange() {
        return attackRange;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (hand == InteractionHand.OFF_HAND) {
            if (!level.isClientSide) {
                player.displayClientMessage(net.minecraft.network.chat.Component.literal("此武器必須裝備於主手才能使用！"), true);
            }
            return InteractionResultHolder.fail(stack);
        }

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        if (level.isClientSide) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    net.john.tutorialmod.client.PlayerPunchAnimation.play(player));
        }

        if (!level.isClientSide) {
            stack.hurtAndBreak(32, player, (p) -> {
                p.broadcastBreakEvent(player.getUsedItemHand());
            });

            player.getCooldowns().addCooldown(this, 350);

            player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN,
                    70, 5, false, false, true
            ));

            CompoundTag tag = stack.getOrCreateTag();
            tag.putInt("RemainingHits", 40);
            tag.putInt("ComboDelay", 0);

        }
        return InteractionResultHolder.success(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide || !(entity instanceof Player player)) return;

        CompoundTag tag = stack.getOrCreateTag();
        int remainingHits = tag.getInt("RemainingHits");

        if (remainingHits > 0) {
            // 連擊期間把選取格釘在拳頭所在格，並同步回客戶端
            if (slotId >= 0 && slotId < 9 && player.getInventory().selected != slotId) {
                player.getInventory().selected = slotId;
                if (player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetCarriedItemPacket(slotId));
                }
            }

            int delay = tag.getInt("ComboDelay");

            if (delay > 0) {
                tag.putInt("ComboDelay", delay - 1);
            } else {
                performSingleAttack(player, level);

                int remaining = remainingHits - 1;
                tag.putInt("RemainingHits", remaining);
                tag.putInt("ComboDelay", 1);

                // 當 40 連擊結束時，關閉動畫狀態
                if (remaining <= 0) {
                    tag.putBoolean("IsConsecutivePunching", false);
                }
            }
        }
    }

    private void performSingleAttack(Player player, Level level) {
        Vec3 look = player.getLookAngle();
        AABB area = player.getBoundingBox()
                .inflate(1.0, 0.5, 1.0)
                .move(look.scale(2.0));

        if (level instanceof ServerLevel serverLevel) {
            Vec3 spawnPos = player.position().add(look.scale(1.5)).add(0, player.getEyeHeight() * 0.5, 0);
            serverLevel.sendParticles(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    spawnPos.x, spawnPos.y, spawnPos.z,
                    3, 0.2, 0.2, 0.2, 0.02
            );
        }

        level.playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_ATTACK_SWEEP,
                SoundSource.PLAYERS,
                0.6f, 0.8f
        );

        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class,
                area,
                entity -> entity != player && entity.isAlive()
        );

        for (LivingEntity target : targets) {
            target.hurt(player.damageSources().playerAttack(player), 0.75f);

            Vec3 currentMotion = target.getDeltaMovement();
            target.setDeltaMovement(0.0, currentMotion.y, 0.0);
            target.hurtMarked = true;

            target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN,
                    10, 4, false, false, true
            ));

            target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.WEAKNESS,
                    10, 4, false, false, true
            ));

            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.CRIT,
                        target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(),
                        8, 0.3, 0.3, 0.3, 0.2
                );
            }
        }
    }

}