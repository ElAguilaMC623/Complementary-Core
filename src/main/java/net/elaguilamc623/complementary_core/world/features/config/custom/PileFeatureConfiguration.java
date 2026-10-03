package net.elaguilamc623.complementary_core.world.features.config.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record PileFeatureConfiguration(BlockStateProvider stateProvider, float cornerCutChance) implements FeatureConfiguration {

    public static final Codec<PileFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BlockStateProvider.CODEC.fieldOf("state_provider").forGetter(PileFeatureConfiguration::stateProvider),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("corner_cut_chance").forGetter(PileFeatureConfiguration::cornerCutChance)
            ).apply(instance, PileFeatureConfiguration::new)
    );
}
