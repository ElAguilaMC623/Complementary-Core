package net.elaguilamc623.complementary_core.world.features.custom;

import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import java.util.List;

public class OreFeatures {
    public static ConfiguredFeature<?, ?> oreFeature(
            List<OreConfiguration.TargetBlockState> targets,
            int size,
            float discardChance
    ) {
        return new ConfiguredFeature<>(
                Feature.ORE,
                new OreConfiguration(targets, size, discardChance)
        );
    }

    public static OreConfiguration.TargetBlockState blockTarget(Block block, BlockState ore) {
        return OreConfiguration.target(new BlockMatchTest(block), ore);
    }

    public static OreConfiguration.TargetBlockState tagTarget(TagKey<Block> tag, BlockState ore) {
        return OreConfiguration.target(new TagMatchTest(tag), ore);
    }

    public static PlacedFeature orePlacedFeature(
            HolderGetter<ConfiguredFeature<?, ?>> configured,
            ResourceKey<ConfiguredFeature<?, ?>> featureKey,
            int count,
            int minY,
            int maxY
    ) {
        return new PlacedFeature(
                configured.getOrThrow(featureKey),
                List.of(
                        CountPlacement.of(count),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.absolute(minY),
                                VerticalAnchor.absolute(maxY)
                        ),
                        BiomeFilter.biome()
                )
        );
    }
}
