package net.john.tutorialmod.datagen;

import net.john.tutorialmod.TutoriolMod;
import net.john.tutorialmod.block.ModBlocks;
import net.john.tutorialmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.MossBlock;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> SAPPHIRE_SMELTABLES = List.of(
            ModBlocks.SAPPHIRE_ORE.get(),
            ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(),
            ModBlocks.NETHER_SAPPHIRE_ORE.get(),
            ModBlocks.END_STONE_SAPPHIRE_ORE.get(),
            ModItems.RAW_SAPPHIRE.get()
    );


    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreBlasting(pWriter, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 100, "sapphire");
        oreSmelting(pWriter, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 200, "sapphire");
        oreCooking(
                pWriter,
                RecipeSerializer.SMELTING_RECIPE,
                List.of(Items.BEETROOT),         // 第 3 個：將甜菜根包成清單傳進去
                RecipeCategory.FOOD,            // 第 4 個：食譜分類
                ModItems.ROASTED_BEETS.get(),   // 第 5 個：產出物
                0.25f,                          // 第 6 個：經驗值
                300,                            // 第 7 個：燒煉時間
                "",                             // 第 8 個：群組
                "roasted_beets"                 // 第 9 個：後綴
        );

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SAPPHIRE_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.SAPPHIRE.get())
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 9)
                .requires(ModBlocks.SAPPHIRE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SAPPHIRE_BLOCK.get()), has(ModBlocks.SAPPHIRE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RUBY_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.RUBY.get())
                .unlockedBy(getHasName(ModItems.RUBY.get()), has(ModItems.RUBY.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RUBY.get(), 9)
                .requires(ModBlocks.RUBY_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.RUBY_BLOCK.get()), has(ModBlocks.RUBY_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.METAL_DETECTOR.get())
                .pattern(" SS")
                .pattern(" S ")
                .pattern("#! ")
                .define('S',Ingredient.of(Items.IRON_INGOT))
                .define('#',Ingredient.of(Items.DIAMOND))
                .define('!',Ingredient.of(Items.REDSTONE))
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SAPPHIRE_SWORD.get())
                .pattern("  S")
                .pattern(" S ")
                .pattern("#  ")
                .define('S',Ingredient.of(ModItems.SAPPHIRE.get()))
                .define('#',Ingredient.of(Items.STICK))
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SAPPHIRE_PICKAXE.get())
                .pattern("SSS")
                .pattern(" # ")
                .pattern(" # ")
                .define('S',Ingredient.of(ModItems.SAPPHIRE.get()))
                .define('#',Ingredient.of(Items.STICK))
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SAPPHIRE_AXE.get())
                .pattern("SS ")
                .pattern("S# ")
                .pattern(" # ")
                .define('S',Ingredient.of(ModItems.SAPPHIRE.get()))
                .define('#',Ingredient.of(Items.STICK))
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SAPPHIRE_SHOVEL.get())
                .pattern(" S ")
                .pattern(" # ")
                .pattern(" # ")
                .define('S',Ingredient.of(ModItems.SAPPHIRE.get()))
                .define('#',Ingredient.of(Items.STICK))
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SAPPHIRE_HOE.get())
                .pattern("SS ")
                .pattern(" # ")
                .pattern(" # ")
                .define('S',Ingredient.of(ModItems.SAPPHIRE.get()))
                .define('#',Ingredient.of(Items.STICK))
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SAPPHIRE_PICKAXE_AND_AXE.get())
                .pattern("SSS")
                .pattern("S# ")
                .pattern(" # ")
                .define('S',Ingredient.of(ModItems.SAPPHIRE.get()))
                .define('#',Ingredient.of(Items.STICK))
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SAPPHIRE_SPEAR.get())
                .pattern(" SS")
                .pattern(" #S")
                .pattern("#  ")
                .define('S',Ingredient.of(ModItems.SAPPHIRE.get()))
                .define('#',Ingredient.of(Items.STICK))
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SMALL_COAL.get(), 4)
                .requires(Items.COAL)
                .unlockedBy(getHasName(Items.COAL), has(Items.COAL))
                .save(pWriter);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SAPPHIRE_HELMET.get())
                .pattern("SSS")
                .pattern("S S")
                .define('S',Ingredient.of(ModItems.SAPPHIRE.get()))
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SAPPHIRE_CHESTPLATE.get())
                .pattern("S S")
                .pattern("SSS")
                .pattern("SSS")
                .define('S',Ingredient.of(ModItems.SAPPHIRE.get()))
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SAPPHIRE_LEGGINGS.get())
                .pattern("SSS")
                .pattern("S S")
                .pattern("S S")
                .define('S',Ingredient.of(ModItems.SAPPHIRE.get()))
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SAPPHIRE_BOOTS.get())
                .pattern("S S")
                .pattern("S S")
                .define('S',Ingredient.of(ModItems.SAPPHIRE.get()))
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BEEF_SANDWICH.get())
                .pattern(" S ")
                .pattern(" @ ")
                .pattern(" S ")
                .define('S',Ingredient.of(Items.BREAD))
                .define('@',Ingredient.of(Items.COOKED_BEEF))
                .unlockedBy(getHasName(Items.BREAD), has(Items.BREAD))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.THE_BLOCK_SWORD.get())
                .pattern("  S")
                .pattern(" S ")
                .pattern("@A ")
                .define('S',Ingredient.of(Items.IRON_INGOT))
                .define('@',Ingredient.of(Items.STICK))
                .define('A',Ingredient.of(Items.STONE_BUTTON))
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ONE_PUNCH.get())
                .pattern(" SS")
                .pattern(" @A")
                .define('S',Ingredient.of(Items.LEATHER))
                .define('@',Ingredient.of(Items.NETHERITE_INGOT))
                .define('A',Ingredient.of(Items.IRON_INGOT))
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WINNIE_HONEY.get())
                .pattern(" SS")
                .pattern(" SA")
                .define('S',Ingredient.of(Items.HONEY_BOTTLE))
                .define('A',Ingredient.of(Items.SUGAR))
                .unlockedBy(getHasName(Items.HONEY_BOTTLE), has(Items.HONEY_BOTTLE))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.KAOLIANG_BREAD.get())
                .pattern("WWW")
                .define('W', ModItems.KAOLIANG.get())
                .unlockedBy(getHasName(ModItems.KAOLIANG.get()), has(ModItems.KAOLIANG.get()))
                .save(pWriter);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CORN_SEEDS.get(), 4)
                .requires(ModItems.CORN.get())
                .unlockedBy(getHasName(ModItems.CORN.get()), has(ModItems.CORN.get()))
                .save(pWriter);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float p_250789_, int p_252144_, String p_251687_) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, p_250789_, p_252144_, p_251687_, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> p_251504_, RecipeCategory p_248846_, ItemLike p_249735_, float p_248783_, int p_250303_, String p_251984_) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, p_251504_, p_248846_, p_249735_, p_248783_, p_250303_, p_251984_, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> p_251817_, List<ItemLike> p_249619_, RecipeCategory p_251154_, ItemLike p_250066_, float p_251871_, int p_251316_, String p_251450_, String p_249236_) {
        for(ItemLike itemlike : p_249619_) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), p_251154_, p_250066_,
                    p_251871_, p_251316_, p_251817_)
                    .group(p_251450_).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, TutoriolMod.MOD_ID + ":" + getItemName(p_250066_) + p_249236_ + "_" + getItemName(itemlike));
        }

    }


}
