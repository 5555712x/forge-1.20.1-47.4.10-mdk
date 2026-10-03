package net.john.tutorialmod;

import com.mojang.logging.LogUtils;
import net.john.tutorialmod.block.ModBlocks;
import net.john.tutorialmod.item.ModCreativeModTabs;
import net.john.tutorialmod.item.ModItems;
import net.john.tutorialmod.loot.ModLootModifiers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.CreativeModeTabRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TutoriolMod.MOD_ID)

public class TutoriolMod {
    public static final String MOD_ID = "tutorialmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public TutoriolMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModCreativeModTabs.register(modEventBus);



        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModLootModifiers.register(modEventBus);


        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.SAPPHIRE);
            event.accept(ModItems.RUBY);
            event.accept(ModItems.RAW_SAPPHIRE);
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.SAPPHIRE_BLOCK);
            event.accept(ModBlocks.SAPPHIRE_SLAB);
            event.accept(ModBlocks.SAPPHIRE_STAIRS);
            event.accept(ModBlocks.SAPPHIRE_DOOR);
            event.accept(ModBlocks.SAPPHIRE_TRAPDOOR);
            event.accept(ModBlocks.SAPPHIRE_FENCE);
            event.accept(ModBlocks.SAPPHIRE_FENCE_GATE);
            event.accept(ModBlocks.SAPPHIRE_WALL);
            event.accept(ModBlocks.RUBY_BLOCK);
        }

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            net.john.tutorialmod.client.PlayerPunchAnimation.registerFactory();
        }
    }
}
