package net.elaguilamc623.complementary_core.world.features.custom.builders;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class PatchFeatureBuilder {

    private final BootstapContext<ConfiguredFeature<?, ?>> context;
    private final ResourceKey<ConfiguredFeature<?, ?>> key;

    private BlockState state;
    private BlockPredicate predicate = BlockPredicate.matchesBlocks(BlockPos.ZERO, Blocks.AIR);
    private int tries = 1;
    private int xzSpread = 0;
    private int ySpread = 0;

    public PatchFeatureBuilder(BootstapContext<ConfiguredFeature<?, ?>> context,
                               ResourceKey<ConfiguredFeature<?, ?>> key) {
        this.context = context;
        this.key = key;
    }

    public PatchFeatureBuilder state(BlockState state) {
        this.state = state;
        return this;
    }

    public PatchFeatureBuilder below(Block block) {
        this.predicate = BlockPredicate.allOf(
                BlockPredicate.matchesBlocks(BlockPos.ZERO, Blocks.AIR),
                BlockPredicate.matchesBlocks(BlockPos.ZERO.below(), block)
        );
        return this;
    }

    public PatchFeatureBuilder tries(int tries) {
        this.tries = tries;
        return this;
    }

    public PatchFeatureBuilder spread(int xz, int y) {
        this.xzSpread = xz;
        this.ySpread = y;
        return this;
    }

    public void register() {

        Holder<PlacedFeature> filtered = PlacementUtils.filtered(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(SimpleStateProvider.simple(state)),
                predicate
        );

        ConfiguredFeature<?, ?> feature =
                new ConfiguredFeature<>(
                        Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(
                                tries,
                                xzSpread,
                                ySpread,
                                filtered
                        )
                );

        context.register(key, feature);
    }
}