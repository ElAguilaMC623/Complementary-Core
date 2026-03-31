package net.elaguilamc623.complementary_core.world.features;

import net.elaguilamc623.complementary_core.ComplementaryCore;
import net.elaguilamc623.complementary_core.world.features.custom.FallenLogFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CCBaseFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, ComplementaryCore.MOD_ID);

    public static final RegistryObject<Feature<SimpleBlockConfiguration>> FALLEN_LOG =
            FEATURES.register("fallen_log", () -> new FallenLogFeature(SimpleBlockConfiguration.CODEC));

    public static void register(IEventBus bus) {
        FEATURES.register(bus);
    }
}