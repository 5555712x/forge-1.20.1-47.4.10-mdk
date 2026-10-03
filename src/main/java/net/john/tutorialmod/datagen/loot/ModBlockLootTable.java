package net.john.tutorialmod.datagen.loot;

import net.john.tutorialmod.block.ModBlocks;
import net.john.tutorialmod.block.custom.CornCropBlock;
import net.john.tutorialmod.block.custom.KaoliangCropBLock;
import net.john.tutorialmod.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTable extends BlockLootSubProvider {
    public ModBlockLootTable() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.SAPPHIRE_BLOCK.get());
        this.dropSelf(ModBlocks.RUBY_BLOCK.get());

        this.dropSelf(ModBlocks.SAPPHIRE_STAIRS.get());
        this.dropSelf(ModBlocks.SAPPHIRE_BUTTON.get());
        this.dropSelf(ModBlocks.SAPPHIRE_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.SAPPHIRE_TRAPDOOR.get());
        this.dropSelf(ModBlocks.SAPPHIRE_FENCE.get());
        this.dropSelf(ModBlocks.SAPPHIRE_FENCE_GATE.get());
        this.dropSelf(ModBlocks.SAPPHIRE_WALL.get());

        this.add(ModBlocks.SAPPHIRE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SAPPHIRE_SLAB.get()));
        this.add(ModBlocks.SAPPHIRE_DOOR.get(),
                block -> createDoorTable(ModBlocks.SAPPHIRE_DOOR.get()));

        this.add(ModBlocks.SAPPHIRE_ORE.get(),
                block -> createLikeCopperOreDrops(ModBlocks.SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        this.add(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(),
                block -> createLikeCopperOreDrops(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        this.add(ModBlocks.NETHER_SAPPHIRE_ORE.get(),
                block -> createLikeCopperOreDrops3(ModBlocks.NETHER_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        this.add(ModBlocks.END_STONE_SAPPHIRE_ORE.get(),
                block -> createLikeCopperOreDrops4(ModBlocks.END_STONE_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        this.add(ModBlocks.NETHER_RUBY_ORE.get(),
                block -> createLikeCopperOreDrops2(ModBlocks.NETHER_RUBY_ORE.get(), ModItems.RUBY.get()));

        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.CORN_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornCropBlock.AGE, 5));

        this.add(ModBlocks.CORN_CROP.get(), createCropDrops(
                ModBlocks.CORN_CROP.get(),
                ModItems.CORN.get(),
                ModItems.CORN_SEEDS.get(),
                lootitemcondition$builder
                ));

        LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.KAOLIANG_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(KaoliangCropBLock.AGE, 4))
                .or(LootItemBlockStatePropertyCondition
                        .hasBlockStateProperties(ModBlocks.KAOLIANG_CROP.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(KaoliangCropBLock.AGE, 8)));

        this.add(ModBlocks.KAOLIANG_CROP.get(),
                createCropDrops(
                        ModBlocks.KAOLIANG_CROP.get(),
                        ModItems.KAOLIANG.get(),
                        ModItems.KAOLIANG_SEEDS.get(),
                        lootitemcondition$builder2
                ));
    }



    protected LootTable.Builder createLikeCopperOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected LootTable.Builder createLikeCopperOreDrops2(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected LootTable.Builder createLikeCopperOreDrops3(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected LootTable.Builder createLikeCopperOreDrops4(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }





    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Set.of(
                ModBlocks.SAPPHIRE_BLOCK.get(),
                ModBlocks.RUBY_BLOCK.get(),
                ModBlocks.SAPPHIRE_ORE.get(),
                ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(),
                ModBlocks.NETHER_SAPPHIRE_ORE.get(),
                ModBlocks.END_STONE_SAPPHIRE_ORE.get(),
                ModBlocks.NETHER_RUBY_ORE.get(),
                ModBlocks.SAPPHIRE_STAIRS.get(),
                ModBlocks.SAPPHIRE_SLAB.get(),
                ModBlocks.SAPPHIRE_BUTTON.get(),
                ModBlocks.SAPPHIRE_PRESSURE_PLATE.get(),
                ModBlocks.SAPPHIRE_TRAPDOOR.get(),
                ModBlocks.SAPPHIRE_FENCE.get(),
                ModBlocks.SAPPHIRE_FENCE_GATE.get(),
                ModBlocks.SAPPHIRE_WALL.get(),
                ModBlocks.SAPPHIRE_DOOR.get(),
                ModBlocks.CORN_CROP.get(),
                ModBlocks.KAOLIANG_CROP.get()
        );
    }
}
