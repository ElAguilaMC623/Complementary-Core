package net.elaguilamc623.complementary_core.world.features.custom;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class FallenLogFeature extends AbstractGroundFeature<SimpleBlockConfiguration> {

    public FallenLogFeature(Codec<SimpleBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    protected boolean placeGroundFeature(WorldGenLevel level, RandomSource randomSource, BlockPos blockPos, SimpleBlockConfiguration configuration) {

        BlockState log = configuration.toPlace().getState(randomSource, blockPos);

        int length = 3 + randomSource.nextInt(4);
        Direction direction = randomSource.nextBoolean() ? Direction.EAST : Direction.SOUTH;

        BlockPos.MutableBlockPos setBlock = blockPos.mutable();

        for (int i = 0; i < length; i++) {

            if (isAirOrReplaceable(level, setBlock)) {
                level.setBlock(setBlock, log.setValue(RotatedPillarBlock.AXIS, direction.getAxis()), 2);
            }

            setBlock.move(direction);
        }

        return true;
    }
}