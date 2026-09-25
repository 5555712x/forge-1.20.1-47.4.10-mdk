package net.john.tutorialmod.client;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.john.tutorialmod.TutoriolMod;
import net.john.tutorialmod.item.custom.PunchItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TutoriolMod.MOD_ID, value = Dist.CLIENT)
public class PlayerPunchAnimation {

    private static final ResourceLocation LAYER_ID = ResourceLocation.parse(TutoriolMod.MOD_ID + ":punch_layer");
    private static final ResourceLocation ANIM_ID = ResourceLocation.parse(TutoriolMod.MOD_ID + ":punch");

    public static void registerFactory() {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                LAYER_ID,
                42,
                player -> new ModifierLayer<>()
        );
    }

    public static void play(Player player) {
        if (!(player instanceof AbstractClientPlayer clientPlayer)) {
            return;
        }

        ModifierLayer<IAnimation> layer = getLayer(clientPlayer);
        var animation = PlayerAnimationRegistry.getAnimation(ANIM_ID);
        if (layer == null || animation == null) {
            return;
        }

        layer.setAnimation(new KeyframeAnimationPlayer(animation));
    }

    public static void stop(Player player) {
        if (!(player instanceof AbstractClientPlayer clientPlayer)) {
            return;
        }

        ModifierLayer<IAnimation> layer = getLayer(clientPlayer);
        if (layer != null) {
            layer.setAnimation(null);
        }
    }

    @SubscribeEvent
    public static void onClientPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !event.player.level().isClientSide) {
            return;
        }

        Player player = event.player;
        if (isPunching(player)) {
            ModifierLayer<IAnimation> layer = player instanceof AbstractClientPlayer clientPlayer
                    ? getLayer(clientPlayer)
                    : null;
            IAnimation current = layer == null ? null : layer.getAnimation();
            if (current == null || !current.isActive()) {
                play(player);
            }
        }
    }

    private static boolean isPunching(Player player) {
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof PunchItem) || !stack.hasTag()) {
            return false;
        }
        return stack.getTag().getInt("RemainingHits") > 0;
    }

    @SuppressWarnings("unchecked")
    private static ModifierLayer<IAnimation> getLayer(AbstractClientPlayer player) {
        return (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData(player).get(LAYER_ID);
    }
}
