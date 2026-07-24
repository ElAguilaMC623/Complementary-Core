package net.elaguilamc623.complementary_core.world.features.custom;

import net.elaguilamc623.complementary_core.world.features.config.custom.DefaultCustomDripstoneConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class DefaultCustomDripstoneFeature extends Feature<DefaultCustomDripstoneConfiguration> {

    public DefaultCustomDripstoneFeature() {
        super(DefaultCustomDripstoneConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<DefaultCustomDripstoneConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        DefaultCustomDripstoneConfiguration config = context.config();

        if (!level.isEmptyBlock(origin)) {
            return false;
        }

        BlockPos floorPos = findSurface(level, origin, Direction.DOWN);
        BlockPos ceilingPos = findSurface(level, origin, Direction.UP);

        if (floorPos == null && ceilingPos == null) {
            return false;
        }

        boolean generatedSomething = false;

        if (ceilingPos != null && random.nextFloat() < config.stalactiteChance) {
            int maxHeight = config.heightRange.sample(random);
            if (generateDripstoneColumn(level, ceilingPos.below(), Direction.DOWN, maxHeight, config.block)) {
                generatedSomething = true;
            }
        }

        if (floorPos != null && random.nextFloat() < config.stalagmiteChance) {
            int maxHeight = config.heightRange.sample(random);
            if (generateDripstoneColumn(level, floorPos.above(), Direction.UP, maxHeight, config.block)) {
                generatedSomething = true;
            }
        }

        return generatedSomething;
    }

    private BlockPos findSurface(WorldGenLevel level, BlockPos start, Direction direction) {
        BlockPos.MutableBlockPos mutablePos = start.mutable();
        for (int i = 0; i < 15; i++) {
            BlockState state = level.getBlockState(mutablePos);
            if (state.isSolidRender(level, mutablePos)) {
                return mutablePos.immutable();
            }
            mutablePos.move(direction);
        }
        return null;
    }

    private boolean generateDripstoneColumn(WorldGenLevel level, BlockPos startPos, Direction direction, int height, BlockState configuredState) {
        BlockPos.MutableBlockPos currentPos = startPos.mutable();

        for (int i = 0; i < height; i++) {
            if (!level.isEmptyBlock(currentPos)) {
                height = i;
                break;
            }
            currentPos.move(direction);
        }

        if (height <= 0) return false;

        if (!configuredState.hasProperty(BlockStateProperties.VERTICAL_DIRECTION) || !configuredState.hasProperty(BlockStateProperties.DRIPSTONE_THICKNESS)) {
            return false;
        }

        BlockState baseBlock = configuredState.setValue(BlockStateProperties.VERTICAL_DIRECTION, direction);
        currentPos.set(startPos);

        for (int i = 0; i < height; i++) {
            DripstoneThickness thickness;
            if (height == 1) {
                thickness = DripstoneThickness.FRUSTUM;
            } else if (i == height - 1) {
                thickness = DripstoneThickness.TIP;
            } else if (i == 0) {
                thickness = DripstoneThickness.BASE;
            } else {
                thickness = DripstoneThickness.MIDDLE;
            }

            BlockState stateToPlace = baseBlock.setValue(BlockStateProperties.DRIPSTONE_THICKNESS, thickness);
            level.setBlock(currentPos, stateToPlace, 2);
            currentPos.move(direction);
        }

        return true;
    }
}
