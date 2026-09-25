package net.john.tutorialmod.datagen;

import net.john.tutorialmod.TutoriolMod;
import net.john.tutorialmod.item.ModItems;
import net.john.tutorialmod.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output, String modid) {
        super(output, TutoriolMod.MOD_ID);
    }

    @Override
    protected void start() {
        add("sapphire_from_stone", new AddItemModifier(new LootItemCondition[] {
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.STONE).build(),
                LootItemRandomChanceCondition.randomChance(0.10f).build()}, ModItems.SAPPHIRE.get()));

        add("sapphire_from_deepslate", new AddItemModifier(new LootItemCondition[] {
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.DEEPSLATE).build(),
                LootItemRandomChanceCondition.randomChance(0.20f).build()}, ModItems.SAPPHIRE.get()));

        add("sapphire_from_drowned", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.parse("minecraft:entities/drowned")).build(),
                LootItemRandomChanceCondition.randomChance(0.10f).build() }, ModItems.SAPPHIRE.get()));

        add("one_punch_from_jungle_temples", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(ResourceLocation.parse("chests/jungle_temple")).build() }, ModItems.ONE_PUNCH.get()));




    }
}
