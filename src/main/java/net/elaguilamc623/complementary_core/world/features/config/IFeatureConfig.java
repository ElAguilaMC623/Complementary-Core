package net.elaguilamc623.complementary_core.world.features.config;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public interface IFeatureConfig extends FeatureConfiguration {
    BlockStateProvider toPlace();
    TagKey<Block> allowedGround();
}
