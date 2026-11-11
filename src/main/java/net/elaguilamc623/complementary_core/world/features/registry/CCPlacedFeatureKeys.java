package net.elaguilamc623.complementary_core.world.features.registry;

import net.elaguilamc623.complementary_core.ComplementaryCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

@SuppressWarnings("removal")
public class CCPlacedFeatureKeys {

    public ResourceKey<PlacedFeature> ADAPTED_FREEZE_TOP_LAYER =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    new ResourceLocation(ComplementaryCore.MOD_ID, "adapted_freeze_top_layer_placed"));

}
