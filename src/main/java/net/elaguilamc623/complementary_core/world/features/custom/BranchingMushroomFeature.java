package net.elaguilamc623.complementary_core.world.features.custom;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public class BranchingMushroomFeature extends AbstractHugeMushroomFeature {
    public BranchingMushroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    protected void makeCap(LevelAccessor levelAccessor, RandomSource random, BlockPos blockPos, int height, BlockPos.MutableBlockPos mutablePos, HugeMushroomFeatureConfiguration config) {
        int i = config.foliageRadius;

        for(int j = -i; j <= i; ++j) {
            for(int k = -i; k <= i; ++k) {
                boolean flag = j == -i;
                boolean flag1 = j == i;
                boolean flag2 = k == -i;
                boolean flag3 = k == i;
                boolean flag4 = flag || flag1;
                boolean flag5 = flag2 || flag3;
                if (!flag4 || !flag5) {
                    mutablePos.setWithOffset(blockPos, j, height, k);
                    if (!levelAccessor.getBlockState(mutablePos).isSolidRender(levelAccessor, mutablePos)) {
                        boolean flag6 = flag || flag5 && j == 1 - i;
                        boolean flag7 = flag1 || flag5 && j == i - 1;
                        boolean flag8 = flag2 || flag4 && k == 1 - i;
                        boolean flag9 = flag3 || flag4 && k == i - 1;
                        BlockState blockstate = config.capProvider.getState(random, blockPos);
                        if (blockstate.hasProperty(HugeMushroomBlock.WEST) && blockstate.hasProperty(HugeMushroomBlock.EAST) && blockstate.hasProperty(HugeMushroomBlock.NORTH) && blockstate.hasProperty(HugeMushroomBlock.SOUTH)) {
                            blockstate = blockstate.setValue(HugeMushroomBlock.WEST, Boolean.valueOf(flag6)).setValue(HugeMushroomBlock.EAST, Boolean.valueOf(flag7)).setValue(HugeMushroomBlock.NORTH, Boolean.valueOf(flag8)).setValue(HugeMushroomBlock.SOUTH, Boolean.valueOf(flag9));
                        }

                        this.setBlock(levelAccessor, mutablePos, blockstate);
                    }
                }
            }
        }
    }

    @Override
    protected int getTreeRadiusForHeight(int i1, int i2, int i3, int height) {
        return height <= 3 ? 0 : i3;
    }

    @Override
    public boolean place(FeaturePlaceContext<HugeMushroomFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        HugeMushroomFeatureConfiguration config = context.config();

        int height = this.getTreeHeight(random);
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        if (!this.isValidPosition(level, origin, height, mutablePos, config)) {
            return false;
        }

        this.makeCap(level, random, origin, height, mutablePos, config);

        this.placeTrunk(level, random, origin, config, height, mutablePos);

        this.makeBranches(level, random, origin, height, config);

        return true;
    }

    protected void makeBranches(LevelAccessor level, RandomSource random, BlockPos origin, int height, HugeMushroomFeatureConfiguration config) {
        for (int y = 2; y < height - 1; y++) {
            if (random.nextFloat() < 0.25f) {
                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                BlockPos branchPos = origin.above(y).relative(direction);

                if (level.getBlockState(branchPos).isAir()) {
                    this.setBlock(level, branchPos, config.stemProvider.getState(random, branchPos));

                    BlockPos centerCap = branchPos.above();
                    this.placeSmallCap(level, random, centerCap, config);
                }
            }
        }
    }

    protected void placeSmallCap(LevelAccessor level, RandomSource random, BlockPos center, HugeMushroomFeatureConfiguration config) {
        BlockState capState = config.capProvider.getState(random, center);

        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                boolean isCorner = (Math.abs(x) == 1 && Math.abs(z) == 1);
                if (!isCorner) {
                    BlockPos currentPos = center.offset(x, 0, z);

                    if (level.getBlockState(currentPos).isAir()) {
                        this.setBlock(level, currentPos, capState);
                    }
                }
            }
        }
    }
}
