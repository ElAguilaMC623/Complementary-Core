package net.elaguilamc623.complementary_core.world.features.custom;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public abstract class AbstractTreeFeature<FC extends FeatureConfiguration> extends AbstractGroundFeature<FC> {

    public AbstractTreeFeature(Codec<FC> codec) {
        super(codec);
    }

    @Override
    protected final boolean placeGroundFeature(WorldGenLevel level, RandomSource randomSource, BlockPos blockPos, FC config) {

        if (!canTreeFit(level, blockPos, config)) {
            return false;
        }

        if (!placeTrunk(level, randomSource, blockPos, config)) {
            return false;
        }

        placeLeaves(level, randomSource, blockPos, config);

        placeDecorations(level, randomSource, blockPos, config);

        return true;
    }

    protected abstract boolean canTreeFit(WorldGenLevel level, BlockPos pos, FC config);

    protected abstract boolean placeTrunk(WorldGenLevel level, RandomSource rand, BlockPos pos, FC config);

    protected abstract void placeLeaves(WorldGenLevel level, RandomSource rand, BlockPos pos, FC config);

    protected void placeDecorations(WorldGenLevel level, RandomSource rand, BlockPos pos, FC config) {}
}