package net.elaguilamc623.complementary_core.world.biomes;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import java.util.function.BiFunction;

public abstract class AbstractBiomeFactory implements BiFunction<BootstrapContext<Biome>, CCBiomeBuilder, CCBiomeBuilder> {

    @Override
    public final CCBiomeBuilder apply(BootstrapContext<Biome> context, CCBiomeBuilder builder) {
        return builder
                .hasPrecipitation(hasPrecipitation(context))
                .temperature(temperature(context))
                .downfall(downfall(context))
                .specialEffects(specialEffects(context))
                .mobSpawnSettings(mobSpawnSettings(context))
                .generationSettings(defaultGenerationSettings(context))
                .temperatureAdjustment(temperatureModifier(context));
    }

    public boolean hasPrecipitation(BootstrapContext<Biome> context) {
        return true;
    }

    public float temperature(BootstrapContext<Biome> ctx) { return 0.0F; }
    public float downfall(BootstrapContext<Biome> ctx) { return 0.5F; }

    public BiomeSpecialEffects.Builder specialEffects(BootstrapContext<Biome> context) {
        return new BiomeSpecialEffects.Builder()
                .fogColor(0xAABBCC)
                .waterColor(0x445566)
                .waterFogColor(0x223344);
    }

    public MobSpawnSettings.Builder mobSpawnSettings(BootstrapContext<Biome> context) {
        return new MobSpawnSettings.Builder();
    }

    public BiomeGenerationSettings.Builder defaultGenerationSettings(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> worldCarvers = context.lookup(Registries.CONFIGURED_CARVER);

        return new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
    }

    public Biome.TemperatureModifier temperatureModifier(BootstrapContext<Biome> context) {
        return Biome.TemperatureModifier.NONE;
    }
}