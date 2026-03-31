package net.elaguilamc623.complementary_core.world.features.custom;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public abstract class AbstractGroundFeature<FC extends FeatureConfiguration> extends Feature<FC> {

    public AbstractGroundFeature(Codec<FC> codec) {
        super(codec);
    }

    @Override
    public final boolean place(FeaturePlaceContext<FC> context) {
        WorldGenLevel level = context.level();
        RandomSource randomSource = context.random();
        BlockPos origin = context.origin();
        FC config = context.config();

        BlockPos surface = findSurface(level, origin);
        if (surface == null) {
            return false;
        }

        if (!isValidGround(level, surface.below())) {
            return false;
        }

        if (!isAirOrReplaceable(level, surface)) {
            return false;
        }

        return placeGroundFeature(level, randomSource, surface, config);
    }

    protected abstract boolean placeGroundFeature(WorldGenLevel level, RandomSource randomSource, BlockPos blockPos, FC config);

    protected boolean isValidGround(WorldGenLevel level, BlockPos blockPos) {
        BlockState state = level.getBlockState(blockPos);
        return state.is(BlockTags.DIRT) || state.is(BlockTags.SAND) || state.is(BlockTags.SNOW);
    }

    protected boolean isAirOrReplaceable(WorldGenLevel level, BlockPos blockPos) {
        BlockState state = level.getBlockState(blockPos);
        return state.isAir() || state.canBeReplaced();
    }

    protected BlockPos findSurface(WorldGenLevel level, BlockPos blockPos) {
        BlockPos.MutableBlockPos cursor = blockPos.mutable();

        for (int i = 0; i < 10; i++) {
            BlockState state = level.getBlockState(cursor);
            BlockState below = level.getBlockState(cursor.below());

            if (!state.isAir() && below.isSolid()) {
                return cursor;
            }

            cursor.move(Direction.DOWN);
        }

        return null;
    }
}