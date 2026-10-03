package net.john.tutorialmod.item;

import net.john.tutorialmod.TutoriolMod;
import net.john.tutorialmod.block.ModBlocks;
import net.john.tutorialmod.item.custom.*;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
        public static final DeferredRegister<Item> ITEMS =
                DeferredRegister.create(ForgeRegistries.ITEMS, TutoriolMod.MOD_ID);

        public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
                 () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
                () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> RAW_SAPPHIRE = ITEMS.register("raw_sapphire",
            () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties().durability(100)));

        public static final RegistryObject<Item> MARSHMALLOW_SKEWERS = ITEMS.register("marshmallow_skewers",
            () -> new Item(new Item.Properties().food(ModFoods.MARSHMALLOW_SKEWERS)));
        public static final RegistryObject<Item> BEEF_SANDWICH = ITEMS.register("beef_sandwich",
            () -> new Item(new Item.Properties().food(ModFoods.BEEF_SANDWICH)));
        public static final RegistryObject<Item> WINNIE_HONEY = ITEMS.register("winnie_honey",
            () -> new Item(new Item.Properties().food(ModFoods.WINNIE_HONEY)));
        public static final RegistryObject<Item> CORN = ITEMS.register("corn",
            () -> new Item(new Item.Properties().food(ModFoods.CORN)));
        public static final RegistryObject<Item> ROASTED_BEETS = ITEMS.register("roasted_beets",
            () -> new Item(new Item.Properties().food(ModFoods.ROASTED_BEETS)));

        public static final RegistryObject<Item> SMALL_COAL = ITEMS.register("small_coal",
            () -> new FuelItem(new Item.Properties(), 400));


        public static final RegistryObject<Item> CORN_SEEDS = ITEMS.register("corn_seeds",
            () -> new ItemNameBlockItem(ModBlocks.CORN_CROP.get(), new Item.Properties()));


        public static final RegistryObject<Item> SAPPHIRE_SWORD = ITEMS.register("sapphire_sword",
            () -> new SwordItem(ModToolTiers.SAPPHIRE,  3, -1f, new Item.Properties()));
        public static final RegistryObject<Item> SAPPHIRE_PICKAXE = ITEMS.register("sapphire_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SAPPHIRE,  2, -2f, new Item.Properties()));
        public static final RegistryObject<Item> SAPPHIRE_AXE = ITEMS.register("sapphire_axe",
            () -> new AxeItem(ModToolTiers.SAPPHIRE,  6, -3f, new Item.Properties()));
        public static final RegistryObject<Item> SAPPHIRE_SHOVEL = ITEMS.register("sapphire_shovel",
            () -> new ShovelItem(ModToolTiers.SAPPHIRE,  2, -2f, new Item.Properties()));
        public static final RegistryObject<Item> SAPPHIRE_HOE = ITEMS.register("sapphire_hoe",
            () -> new HoeItem(ModToolTiers.SAPPHIRE,  1, -2.5f, new Item.Properties()));
        public static final RegistryObject<Item> SAPPHIRE_PICKAXE_AND_AXE = ITEMS.register("sapphire_pickaxe_and_axe",
            () -> new SapphirePickaxeAndAxeItem(ModToolTiers.SAPPHIRE, 4, -2.5f, new Item.Properties()));

        public static final RegistryObject<Item> ATOMIC_SAMURAI_SWORD = ITEMS.register("atomic_samurai_sword",
            () -> new SwordItem(Tiers.IRON, 2, -2.2f, new Item.Properties().durability(575)));
    public static final RegistryObject<Item> ONE_PUNCH = ITEMS.register("one_punch",
            () -> new PunchItem(Tiers.WOOD, 5, -2f, -1.0, new Item.Properties().durability(1575)));

        // 格檔劍：攻擊傷害 7.0、攻擊速度 -2.4、耐久 336
        // 繼承 ShieldItem，格檔邏輯和客戶端減傷同步由原版處理
        public static final RegistryObject<Item> THE_BLOCK_SWORD = ITEMS.register("the_block_sword",
            () -> new CanBlockItem(6.0f, -2.6f, new Item.Properties().durability(336)));

        // 藍寶石矛：攻擊傷害 +5、攻擊速度 -2.4、攻擊距離 5 格
        // ForgeMod.ATTACK_RANGE 是 Forge 新增的屬性，控制玩家的近戰 raycast 距離
        // 原版預設值是 3.0，這裡用 AttributeModifier 加上額外 2.0（總共 5 格）
        public static final RegistryObject<Item> SAPPHIRE_SPEAR = ITEMS.register("sapphire_spear",
            () -> new SpearItem(ModToolTiers.SAPPHIRE, 5, -2.75f, 5.0, new Item.Properties()));


        // 藍寶石盔甲：使用 ModArmorItem，讓 ArmorEffectHandler 能識別材質並套用效果
        public static final RegistryObject<Item> SAPPHIRE_HELMET = ITEMS.register("sapphire_helmet",
            () -> new ModArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.HELMET, new Item.Properties()));
        public static final RegistryObject<Item> SAPPHIRE_CHESTPLATE = ITEMS.register("sapphire_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
        public static final RegistryObject<Item> SAPPHIRE_LEGGINGS = ITEMS.register("sapphire_leggings",
            () -> new ModArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
        public static final RegistryObject<Item> SAPPHIRE_BOOTS = ITEMS.register("sapphire_boots",
            () -> new ModArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.BOOTS, new Item.Properties()));

        public static void register(IEventBus eventBus) {
            ITEMS.register(eventBus);
        }
}
