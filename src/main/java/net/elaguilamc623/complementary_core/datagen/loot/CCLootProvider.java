package net.elaguilamc623.complementary_core.datagen.loot;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;

public abstract class CCLootProvider extends BlockLoot {

    @Override
    protected abstract void addTables();

    @Override
    protected abstract Iterable<Block> getKnownBlocks();
}