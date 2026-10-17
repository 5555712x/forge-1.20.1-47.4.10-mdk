package net.john.tutorialmod.item.custom;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;

public class SapphirePickaxeAndAxeItem extends PickaxeItem {

    public SapphirePickaxeAndAxeItem(Tier tier, int attackDamageBonus, float attackSpeed, Properties properties) {
        super(tier, attackDamageBonus, attackSpeed, properties);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        // 對斧頭可挖的方塊（木頭、木製品等）也套用工具速度
        if (state.is(BlockTags.MINEABLE_WITH_AXE)) {
            return this.speed;
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        // 讓需要斧頭才能掉落的方塊也能正確掉落
        if (state.is(BlockTags.MINEABLE_WITH_AXE)) {
            return true;
        }
        return super.isCorrectToolForDrops(stack, state);
    }
}
