package net.john.tutorialmod.item;

import net.john.tutorialmod.TutoriolMod;
import net.john.tutorialmod.block.ModBlocks;
import net.john.tutorialmod.item.custom.ModFoods;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.behavior.declarative.MemoryCondition;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
                DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TutoriolMod.MOD_ID);

        public  static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("tutorial_tab",
                () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SAPPHIRE.get()))
                        .title(Component.translatable("creativetab.tutorial_tab"))
                        .displayItems((itemDisplayParameters, output) -> {
                            output.accept(ModItems.SAPPHIRE.get());
                            output.accept(ModItems.RUBY.get());
                            output.accept(ModItems.RAW_SAPPHIRE.get());
                            output.accept(ModItems.METAL_DETECTOR.get());

                            output.accept(ModItems.SAPPHIRE_SWORD.get());
                            output.accept(ModItems.SAPPHIRE_PICKAXE.get());
                            output.accept(ModItems.SAPPHIRE_AXE.get());
                            output.accept(ModItems.SAPPHIRE_SHOVEL.get());
                            output.accept(ModItems.SAPPHIRE_HOE.get());
                            output.accept(ModItems.SAPPHIRE_PICKAXE_AND_AXE.get());
                            output.accept(ModItems.THE_BLOCK_SWORD.get());
                            // 藍寶石矛，跟其他武器放在一起
                            output.accept(ModItems.SAPPHIRE_SPEAR.get());

                            output.accept(ModItems.ONE_PUNCH.get());

                            output.accept(ModItems.ATOMIC_SAMURAI_SWORD.get());


                            output.accept(ModItems.SAPPHIRE_HELMET.get());
                            output.accept(ModItems.SAPPHIRE_CHESTPLATE.get());
                            output.accept(ModItems.SAPPHIRE_LEGGINGS.get());
                            output.accept(ModItems.SAPPHIRE_BOOTS.get());


                            output.accept(ModBlocks.SAPPHIRE_BLOCK.get());
                            output.accept(ModBlocks.RUBY_BLOCK.get());
                            output.accept(ModBlocks.RAW_SAPPHIRE_BLOCK.get());

                            output.accept(ModBlocks.SOUND_BLOCK.get());

                            //FOOD
                            output.accept(ModItems.MARSHMALLOW_SKEWERS.get());
                            output.accept(ModItems.BEEF_SANDWICH.get());
                            output.accept(ModItems.WINNIE_HONEY.get());
                            output.accept(ModItems.KAOLIANG_BREAD.get());

                            output.accept(ModItems.CORN_SEEDS.get());
                            output.accept(ModItems.CORN.get());
                            output.accept(ModItems.KAOLIANG.get());
                            output.accept(ModItems.KAOLIANG_SEEDS.get());


                            output.accept(ModBlocks.BLAZING_FLOWER.get());


                            //FUEL
                            output.accept(ModItems.SMALL_COAL.get());


                            output.accept(ModBlocks.SAPPHIRE_ORE.get());
                            output.accept(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get());
                            output.accept(ModBlocks.NETHER_SAPPHIRE_ORE.get());
                            output.accept(ModBlocks.END_STONE_SAPPHIRE_ORE.get());
                            output.accept(ModBlocks.NETHER_RUBY_ORE.get());

                            output.accept((ModBlocks.SAPPHIRE_STAIRS.get()));
                            output.accept((ModBlocks.SAPPHIRE_BUTTON.get()));
                            output.accept((ModBlocks.SAPPHIRE_PRESSURE_PLATE.get()));
                            output.accept((ModBlocks.SAPPHIRE_TRAPDOOR.get()));
                            output.accept((ModBlocks.SAPPHIRE_FENCE.get()));
                            output.accept((ModBlocks.SAPPHIRE_FENCE_GATE.get()));
                            output.accept((ModBlocks.SAPPHIRE_WALL.get()));
                            output.accept(ModBlocks.SAPPHIRE_DOOR.get());
                            output.accept(ModBlocks.SAPPHIRE_SLAB.get());






                        })

                        .build());

        public static void register(IEventBus eventBus){
            CREATIVE_MODE_TABS.register(eventBus);
        }


}
