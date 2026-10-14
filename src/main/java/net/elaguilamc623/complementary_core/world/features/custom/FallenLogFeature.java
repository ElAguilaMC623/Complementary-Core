package net.elaguilamc623.complementary_core.world.features.custom;

import com.mojang.serialization.Codec;
import net.elaguilamc623.complementary_core.world.features.config.custom.FallenLogConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FallenLogFeature extends AbstractGroundFeature<FallenLogConfig> {

    public FallenLogFeature(Codec<FallenLogConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean placeGroundFeature(WorldGenLevel level, RandomSource random, BlockPos pos, FallenLogConfig config) {
        int length = config.logLength().sample(random);
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockState logState = config.toPlace().getState(random, pos)
                .setValue(RotatedPillarBlock.AXIS, direction.getAxis());

        if (!canPlaceEntireLog(level, pos, length, direction, config.allowedGround())) {
            return false;
        }

        BlockPos.MutableBlockPos currentPos = pos.mutable();
        for (int i = 0; i < length; i++) {
            if (isAirOrReplaceable(level, currentPos)) {
                level.setBlock(currentPos, logState, 3);
            }
            currentPos.move(direction);
        }

        return true;
    }

    private boolean canPlaceEntireLog(WorldGenLevel level, BlockPos startPos, int length, Direction dir, TagKey<Block> allowedGround) {
        BlockPos.MutableBlockPos checker = startPos.mutable();
        int maxGap = 2;
        int currentGap = 0;

        for (int i = 0; i < length; i++) {
            if (!isAirOrReplaceable(level, checker)) {
                return false;
            }

            if (!level.getBlockState(checker.below()).is(allowedGround)) {
                currentGap++;
                if (currentGap > maxGap) {
                    return false;
                }
            } else {
                currentGap = 0;
            }

            checker.move(dir);
        }
        return true;
    }
}