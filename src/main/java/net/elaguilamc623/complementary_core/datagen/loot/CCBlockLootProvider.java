package net.elaguilamc623.complementary_core.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public abstract class CCBlockLootProvider extends BlockLootSubProvider {

    protected CCBlockLootProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected abstract void generate();

    @Override
    protected abstract Iterable<Block> getKnownBlocks();
}