package net.elaguilamc623.complementary_core.world.features.templates;

import net.elaguilamc623.complementary_core.world.features.templates.builders.PatchFeatureBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class PatchFeature {

    public static PatchFeatureBuilder collectivePatch(
            BootstrapContext<ConfiguredFeature<?, ?>> ctx,
            ResourceKey<ConfiguredFeature<?, ?>> key
    ) {
        return new PatchFeatureBuilder(ctx, key);
    }

    public static PatchFeatureBuilder individualPatch(
            BootstrapContext<ConfiguredFeature<?, ?>> ctx,
            ResourceKey<ConfiguredFeature<?, ?>> key
    ) {
        return new PatchFeatureBuilder(ctx, key).tries(1);
    }
}