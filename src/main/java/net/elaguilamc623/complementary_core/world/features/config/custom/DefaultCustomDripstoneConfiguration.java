package net.elaguilamc623.complementary_core.world.features.config.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.block.state.BlockState;

public class DefaultCustomDripstoneConfiguration implements FeatureConfiguration {
    public static final Codec<DefaultCustomDripstoneConfiguration> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    BlockState.CODEC.fieldOf("block").forGetter((config) -> config.block),
                    IntProvider.codec(1, 64).fieldOf("height_range").forGetter((config) -> config.heightRange),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("stalactite_chance").forGetter((config) -> config.stalactiteChance),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("stalagmite_chance").forGetter((config) -> config.stalagmiteChance)
            ).apply(instance, DefaultCustomDripstoneConfiguration::new)
    );

    public final BlockState block;
    public final IntProvider heightRange;
    public final float stalactiteChance;
    public final float stalagmiteChance;

    public DefaultCustomDripstoneConfiguration(BlockState block, IntProvider heightRange, float stalactiteChance, float stalagmiteChance) {
        this.block = block;
        this.heightRange = heightRange;
        this.stalactiteChance = stalactiteChance;
        this.stalagmiteChance = stalagmiteChance;
    }
}