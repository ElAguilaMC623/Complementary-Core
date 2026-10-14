package net.elaguilamc623.complementary_core.world.features.custom;

import com.mojang.serialization.Codec;
import net.elaguilamc623.complementary_core.world.features.config.custom.PileFeatureConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class PileFeature extends Feature<PileFeatureConfiguration> {

    public PileFeature(Codec<PileFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<PileFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        PileFeatureConfiguration config = context.config();
        float cutChance = config.cornerCutChance();

        if (!level.getBlockState(origin.below()).isSolid()) {
            return false;
        }

        if (level.getBlockState(origin.below()).is(Blocks.ICE)) {
            return false;
        }

        boolean placedAny = false;

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos targetPos = origin.offset(x, 0, z);

                if (level.getBlockState(targetPos).canBeReplaced()) {
                    BlockState state = config.stateProvider().getState(random, targetPos);
                    this.setBlock(level, targetPos, state);
                    placedAny = true;
                }
            }
        }

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos targetPos = origin.offset(x, 1, z);

                boolean isCorner = (x != 0 && z != 0);

                if (isCorner) {
                    if (random.nextFloat() > cutChance) {
                        if (level.getBlockState(targetPos).canBeReplaced()) {
                            BlockState state = config.stateProvider().getState(random, targetPos);
                            this.setBlock(level, targetPos, state);
                        }
                    }
                } else {
                    if (level.getBlockState(targetPos).canBeReplaced()) {
                        BlockState state = config.stateProvider().getState(random, targetPos);
                        this.setBlock(level, targetPos, state);
                    }
                }
            }
        }

        return placedAny;
    }
}
