package net.elaguilamc623.complementary_core.world.biomes;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import java.util.function.Supplier;

public class CCBiomeBuilder {

    private final AbstractBiomeFactory factory;

    private Boolean hasPrecipitation;
    private Float temperature;
    private Float downfall;
    private BiomeSpecialEffects.Builder effects;
    private MobSpawnSettings.Builder spawns;
    private BiomeGenerationSettings.Builder generation;
    private Biome.TemperatureModifier temperatureModifier = Biome.TemperatureModifier.NONE;

    public CCBiomeBuilder(Supplier<AbstractBiomeFactory> factory) {
        this.factory = factory.get();
    }

    public CCBiomeBuilder hasPrecipitation(boolean v) { this.hasPrecipitation = v; return this; }
    public CCBiomeBuilder temperature(float v) { this.temperature = v; return this; }
    public CCBiomeBuilder downfall(float v) { this.downfall = v; return this; }
    public CCBiomeBuilder specialEffects(BiomeSpecialEffects.Builder v) { this.effects = v; return this; }
    public CCBiomeBuilder mobSpawnSettings(MobSpawnSettings.Builder v) { this.spawns = v; return this; }
    public CCBiomeBuilder generationSettings(BiomeGenerationSettings.Builder v) { this.generation = v; return this; }
    public CCBiomeBuilder temperatureAdjustment(Biome.TemperatureModifier v) { this.temperatureModifier = v; return this; }

    public Biome build(BootstapContext<Biome> context) {

        this.factory.apply(context, this);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(this.hasPrecipitation)
                .temperature(this.temperature)
                .downfall(this.downfall)
                .temperatureAdjustment(this.temperatureModifier)
                .specialEffects(this.effects.build())
                .mobSpawnSettings(this.spawns.build())
                .generationSettings(this.generation.build())
                .build();
    }

}