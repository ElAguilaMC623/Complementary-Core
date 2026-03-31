package net.elaguilamc623.complementary_core.world.features;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class CCFeatureConfiguration {
    public static <FC extends FeatureConfiguration, F extends Feature<FC>>
    Holder<ConfiguredFeature<?, ?>> register(
            BootstapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key,
            F feature,
            FC config
    ) {
        ConfiguredFeature<FC, F> configured = new ConfiguredFeature<>(feature, config);
        return context.register(key, configured);
    }
}
