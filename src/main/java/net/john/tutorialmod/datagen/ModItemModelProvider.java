package net.john.tutorialmod.datagen;

import net.john.tutorialmod.TutoriolMod;
import net.john.tutorialmod.block.ModBlocks;
import net.john.tutorialmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {

    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }


    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = TutoriolMod.MOD_ID; // Change this to your mod id

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                // 根據盔甲槽位取得部位名稱字串（用於 trim 貼圖路徑）
                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                // 修正：使用 itemRegistryObject.getId().getPath() 取得正確的物品 ID 路徑
                // 原本 "item/" + armorItem 會呼叫 ArmorItem.toString()，輸出的是物件記憶體位址而非 ID
                String armorItemPath = "item/" + itemRegistryObject.getId().getPath();

                // trim 貼圖的路徑，位於 minecraft 命名空間的 trims/items/ 資料夾下
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();

                // 帶有 trim 後綴的模型名稱，例如 item/sapphire_helmet_quartz_trim
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";

                // 本模組盔甲物品的 ResourceLocation，例如 tutorialmod:item/sapphire_helmet
                ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);

                // 修正：fromNamespaceAndPath 需要兩個參數（命名空間 + 路徑）
                // trim 貼圖屬於原版 minecraft 命名空間，原本只傳一個字串會編譯失敗
                ResourceLocation trimResLoc = new ResourceLocation("minecraft", trimPath);

                // 帶 trim 的模型完整 ResourceLocation，例如 tutorialmod:item/sapphire_helmet_quartz_trim
                ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TutoriolMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.SAPPHIRE);
        simpleItem(ModItems.RUBY);
        simpleItem(ModItems.RAW_SAPPHIRE);

        //simpleItem(ModItems.MARSHMALLOW_SKEWERS);
        simpleItem(ModItems.BEEF_SANDWICH);
        simpleItem(ModItems.WINNIE_HONEY);
        simpleItem(ModItems.CORN);
        simpleItem(ModItems.CORN_SEEDS);
        simpleItem(ModItems.ROASTED_BEETS);

        //simpleItem(ModItems.METAL_DETECTOR);


        //handheldItem(ModItems.SAPPHIRE_SWORD);
        handheldItem(ModItems.SAPPHIRE_PICKAXE);
        handheldItem(ModItems.SAPPHIRE_AXE);
        handheldItem(ModItems.SAPPHIRE_SHOVEL);
        handheldItem(ModItems.SAPPHIRE_HOE);
        handheldItem(ModItems.SAPPHIRE_PICKAXE_AND_AXE);
        // 矛是手持武器，使用 handheld 父模型（讓持握角度正確）
        //handheldItem(ModItems.SAPPHIRE_SPEAR);

        //handheldItem(ModItems.ONE_PUNCH);


        simpleItem(ModItems.SAPPHIRE_HELMET);
        simpleItem(ModItems.SAPPHIRE_CHESTPLATE);
        simpleItem(ModItems.SAPPHIRE_LEGGINGS);
        simpleItem(ModItems.SAPPHIRE_BOOTS);


        simpleItem(ModItems.SMALL_COAL);

        simpleBlockItem(ModBlocks.SAPPHIRE_DOOR);

        fenceItem(ModBlocks.SAPPHIRE_FENCE, ModBlocks.SAPPHIRE_BLOCK);
        buttonItem(ModBlocks.SAPPHIRE_BUTTON, ModBlocks.SAPPHIRE_BLOCK);
        wallItem(ModBlocks.SAPPHIRE_WALL, ModBlocks.SAPPHIRE_BLOCK);

        evenSimpleBlockItem(ModBlocks.SAPPHIRE_STAIRS);
        evenSimpleBlockItem(ModBlocks.SAPPHIRE_SLAB);
        evenSimpleBlockItem(ModBlocks.SAPPHIRE_PRESSURE_PLATE);
        evenSimpleBlockItem(ModBlocks.SAPPHIRE_FENCE_GATE);

        trapdoorItem(ModBlocks.SAPPHIRE_TRAPDOOR);

        trimmedArmorItem(ModItems.SAPPHIRE_HELMET);
        trimmedArmorItem(ModItems.SAPPHIRE_CHESTPLATE);
        trimmedArmorItem(ModItems.SAPPHIRE_LEGGINGS);
        trimmedArmorItem(ModItems.SAPPHIRE_BOOTS);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0",
                        new ResourceLocation(
                                TutoriolMod.MOD_ID,
                                "item/" + item.getId().getPath()));
    }

    public void evenSimpleBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(TutoriolMod.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }


    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", new ResourceLocation(TutoriolMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture", new ResourceLocation(TutoriolMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", new ResourceLocation(TutoriolMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(
                ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }


    private ItemModelBuilder handheldItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(TutoriolMod.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated"))
                .texture("layer0",
                        new ResourceLocation(
                                TutoriolMod.MOD_ID,
                                "item/" + item.getId().getPath()));
    }
}
