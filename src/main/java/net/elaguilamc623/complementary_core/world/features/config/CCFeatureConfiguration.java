package net.elaguilamc623.complementary_core.world.features.config;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import java.util.List;

public class CCFeatureConfiguration {

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> register(
            BootstrapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key,
            F feature,
            FC config
    ) {
        ConfiguredFeature<FC, F> configured = new ConfiguredFeature<>(feature, config);
        return context.register(key, configured);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> configuredKey(String namespace, String name) {
        return ResourceKey.create(
                Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(namespace, name)
        );
    }

    public static ResourceKey<PlacedFeature> placedKey(String namespace, String name) {
        return ResourceKey.create(
                Registries.PLACED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(namespace, name)
        );
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>>
    void registerConfigured(
            BootstrapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key,
            F feature,
            FC config
    ) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }

    public static void registerPlaced(
            BootstrapContext<PlacedFeature> context,
            ResourceKey<PlacedFeature> key,
            HolderGetter<ConfiguredFeature<?, ?>> configured,
            ResourceKey<ConfiguredFeature<?, ?>> configuredKey,
            List<PlacementModifier> modifiers
    ) {
        context.register(
                key,
                new PlacedFeature(
                        configured.getOrThrow(configuredKey),
                        modifiers
                )
        );
    }

}
