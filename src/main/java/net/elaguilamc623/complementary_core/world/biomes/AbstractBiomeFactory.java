package net.elaguilamc623.complementary_core.world.biomes;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import java.util.function.BiFunction;

public abstract class AbstractBiomeFactory implements BiFunction<BootstapContext<Biome>, CCBiomeBuilder, CCBiomeBuilder> {

    @Override
    public final CCBiomeBuilder apply(BootstapContext<Biome> context, CCBiomeBuilder builder) {
        return builder
                .hasPrecipitation(hasPrecipitation(context))
                .temperature(temperature(context))
                .downfall(downfall(context))
                .specialEffects(specialEffects(context))
                .mobSpawnSettings(mobSpawnSettings(context))
                .generationSettings(defaultGenerationSettings(context))
                .temperatureAdjustment(temperatureModifier(context));
    }

    public boolean hasPrecipitation(BootstapContext<Biome> context) { return true; }
    public float temperature(BootstapContext<Biome> ctx) { return 0.0F; }
    public float downfall(BootstapContext<Biome> ctx) { return 0.5F; }

    public BiomeSpecialEffects.Builder specialEffects(BootstapContext<Biome> context) {
        return new BiomeSpecialEffects.Builder()
                .fogColor(0xAABBCC)
                .waterColor(0x445566)
                .waterFogColor(0x223344);
    }

    public MobSpawnSettings.Builder mobSpawnSettings(BootstapContext<Biome> context) {
        return new MobSpawnSettings.Builder();
    }

    public BiomeGenerationSettings.Builder defaultGenerationSettings(BootstapContext<Biome> context) {
        return new BiomeGenerationSettings.Builder(
                context.lookup(Registries.PLACED_FEATURE),
                context.lookup(Registries.CONFIGURED_CARVER)
        );
    }

    public Biome.TemperatureModifier temperatureModifier(BootstapContext<Biome> context) {
        return Biome.TemperatureModifier.NONE;
    }
}