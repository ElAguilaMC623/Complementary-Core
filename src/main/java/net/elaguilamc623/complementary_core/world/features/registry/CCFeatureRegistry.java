package net.elaguilamc623.complementary_core.world.features.registry;

import net.elaguilamc623.complementary_core.ComplementaryCore;
import net.elaguilamc623.complementary_core.world.features.config.custom.FallenLogConfig;
import net.elaguilamc623.complementary_core.world.features.custom.FallenLogFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CCFeatureRegistry {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, ComplementaryCore.MOD_ID);

    public static final RegistryObject<Feature<FallenLogConfig>> FALLEN_LOG =
            FEATURES.register("fallen_log", () -> new FallenLogFeature(FallenLogConfig.CODEC));

    public static void register(IEventBus bus) {
        FEATURES.register(bus);
    }
}