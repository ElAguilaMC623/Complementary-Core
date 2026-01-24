package net.elaguilamc623.complementary_core.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public abstract class CCBlockLootTables extends BlockLootSubProvider {

    protected CCBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    protected LootTable.Builder slab(Block block) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block))
        );
    }

    protected LootTable.Builder singleItem(ItemLike item) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(item))
        );
    }

    protected LootTable.Builder singleItemWithSilkTouch(Block block, ItemLike drop) {
        return createSingleItemTableWithSilkTouch(block, drop.asItem());
    }

    protected LootTable.Builder oreDrop(Block block, ItemLike drop) {
        return createOreDrop(block, drop.asItem());
    }

    protected LootTable.Builder leaves(Block leaves, Block sapling, float... chances) {
        return createLeavesDrops(leaves, sapling, chances);
    }

    protected LootTable.Builder door(Block door) {
        return createDoorTable(door);
    }

    protected LootTable.Builder crop(Block crop, ItemLike product, ItemLike seed,
                                     LootItemBlockStatePropertyCondition.Builder condition) {
        return createCropDrops(crop, product.asItem(), seed.asItem(), condition);
    }

    protected LootTable.Builder fortuneShards(Block block, ItemLike shard, float min, float max) {
        return createSilkTouchDispatchTable(
                block,
                applyExplosionDecay(block,
                        LootItem.lootTableItem(shard.asItem())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
                )
        );
    }
}