package net.elaguilamc623.complementary_core.world.features.config.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.elaguilamc623.complementary_core.world.features.config.IFeatureConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FallenLogConfig(BlockStateProvider toPlace, TagKey<Block> allowedGround, IntProvider logLength) implements IFeatureConfig {
    public static final Codec<FallenLogConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("to_place").forGetter(FallenLogConfig::toPlace),
            TagKey.codec(Registries.BLOCK).fieldOf("allowed_ground").forGetter(FallenLogConfig::allowedGround),
            IntProvider.codec(1, 16).fieldOf("log_length").forGetter(FallenLogConfig::logLength)
    ).apply(instance, FallenLogConfig::new));
}
