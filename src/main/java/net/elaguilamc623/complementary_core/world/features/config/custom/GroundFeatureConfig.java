package net.elaguilamc623.complementary_core.world.features.config.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.elaguilamc623.complementary_core.world.features.config.IFeatureConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record GroundFeatureConfig(BlockStateProvider toPlace, TagKey<Block> allowedGround) implements IFeatureConfig {
    public static final Codec<GroundFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("to_place").forGetter(GroundFeatureConfig::toPlace),
            TagKey.codec(Registries.BLOCK).fieldOf("allowed_ground").forGetter(GroundFeatureConfig::allowedGround)
    ).apply(instance, GroundFeatureConfig::new));
}
