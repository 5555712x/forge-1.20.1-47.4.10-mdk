package net.john.tutorialmod.item;

import net.john.tutorialmod.TutoriolMod;
import net.john.tutorialmod.block.ModBlocks;
import net.john.tutorialmod.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier SAPPHIRE  = TierSortingRegistry.registerTier(
            new ForgeTier(2, 150, 8F, 1F, 16,
                    ModTags.Blocks.NEED_SAPPHIRE_TOOL, () -> Ingredient.of(ModItems.SAPPHIRE.get())),
            ResourceLocation.fromNamespaceAndPath(TutoriolMod.MOD_ID, "sapphire"), List.of(Tiers.IRON), List.of());
}
