package net.elaguilamc623.complementary_core.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class CCPlacedFeaturesTemplates {

    public static PlacedFeature patchFeaturePlaced(
            HolderGetter<ConfiguredFeature<?, ?>> configured,
            ResourceKey<ConfiguredFeature<?, ?>> featureKey,
            int rarity
    ) {
        return new PlacedFeature(
                configured.getOrThrow(featureKey),
                List.of(
                        RarityFilter.onAverageOnceEvery(rarity),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );
    }

    public static PlacedFeature patchFeaturePlacedWithCount(
            HolderGetter<ConfiguredFeature<?, ?>> configured,
            ResourceKey<ConfiguredFeature<?, ?>> featureKey,
            int count,
            int rarity
    ) {
        return new PlacedFeature(
                configured.getOrThrow(featureKey),
                List.of(
                        CountPlacement.of(count),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome(),
                        RarityFilter.onAverageOnceEvery(rarity)
                )
        );
    }

    public static PlacedFeature treePlacedFeature(
            HolderGetter<ConfiguredFeature<?, ?>> configured,
            ResourceKey<ConfiguredFeature<?, ?>> featureKey,
            int count,
            Block sapling
    ) {
        BlockState saplingState = sapling.defaultBlockState();

        return new PlacedFeature(
                configured.getOrThrow(featureKey),
                List.of(
                        CountPlacement.of(count),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        BlockPredicateFilter.forPredicate(BlockPredicate.replaceable()),
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.wouldSurvive(saplingState, BlockPos.ZERO)
                        ),
                        BiomeFilter.biome()
                )
        );
    }
}
