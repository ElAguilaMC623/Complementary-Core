package net.elaguilamc623.complementary_core.world.features.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class AdaptedFreezeTopLayerFeature extends Feature<NoneFeatureConfiguration> {

    public AdaptedFreezeTopLayerFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        int minX = origin.getX();
        int minZ = origin.getZ();

        Holder<Biome> b1 = level.getBiome(mutablePos.set(minX, 64, minZ));
        Holder<Biome> b2 = level.getBiome(mutablePos.set(minX + 15, 64, minZ));
        Holder<Biome> b3 = level.getBiome(mutablePos.set(minX, 64, minZ + 15));
        Holder<Biome> b4 = level.getBiome(mutablePos.set(minX + 15, 64, minZ + 15));

        boolean isBorderChunk = !(b1.equals(b2) && b1.equals(b3) && b1.equals(b4));

        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                int worldX = minX + x;
                int worldZ = minZ + z;

                if (isBorderChunk) {
                    mutablePos.set(worldX, 64, worldZ);
                    if (!level.getBiome(mutablePos).equals(b1)) {
                        continue;
                    }
                }

                int surfaceY = level.getHeight(Heightmap.Types.MOTION_BLOCKING, worldX, worldZ);
                mutablePos.set(worldX, surfaceY, worldZ);

                BlockPos liquidPos = mutablePos.below();
                BlockState currentState = level.getBlockState(liquidPos);
                if (currentState.getBlock() == Blocks.WATER) {
                    level.setBlock(liquidPos, Blocks.ICE.defaultBlockState(), 2);
                }

                if (level.getBlockState(mutablePos).isAir() && Blocks.SNOW.defaultBlockState().canSurvive(level, mutablePos)) {
                    level.setBlock(mutablePos, Blocks.SNOW.defaultBlockState(), 2);
                }
            }
        }
        return true;
    }
}
