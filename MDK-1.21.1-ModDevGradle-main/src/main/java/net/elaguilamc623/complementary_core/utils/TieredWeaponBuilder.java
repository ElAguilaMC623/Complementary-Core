package net.elaguilamc623.complementary_core.utils;

import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class TieredWeaponBuilder {

    private final String baseName;
    private final DeferredRegister.Items register;

    private final Map<Tier, ItemFactory> factories = new LinkedHashMap<>();
    private final Map<Tier, DeferredItem<Item>> registeredItems = new LinkedHashMap<>();

    private TieredWeaponBuilder(String baseName, DeferredRegister.Items register) {
        this.baseName = baseName;
        this.register = register;
    }

    public static TieredWeaponBuilder create(String baseName, DeferredRegister.Items register) {
        return new TieredWeaponBuilder(baseName, register);
    }

    public TieredWeaponBuilder add(Tier tier, int damage, float speed) {
        factories.put(tier, (t, props) ->
                new SwordItem(t, props.attributes(SwordItem.createAttributes(t, damage, speed)))
        );
        return this;
    }

    public TieredWeaponBuilder add(Tier tier, ItemFactory factory) {
        factories.put(tier, factory);
        return this;
    }

    public void register() {
        for (var entry : factories.entrySet()) {
            Tier tier = entry.getKey();
            ItemFactory factory = entry.getValue();

            String name = tierName(tier) + "_" + baseName;

            DeferredItem<Item> item = register.register(name,
                    () -> factory.create(tier, new Item.Properties())
            );

            registeredItems.put(tier, item);
        }
    }

    private static final Map<Tier, String> TIER_NAMES = Map.of(
            Tiers.STONE, "stone",
            Tiers.IRON, "iron",
            Tiers.GOLD, "gold",
            Tiers.DIAMOND, "diamond",
            Tiers.NETHERITE, "netherite"
    );

    private String tierName(Tier tier) {
        return TIER_NAMES.getOrDefault(tier, "unknown");
    }

    public Collection<DeferredItem<Item>> getItems() {
        return registeredItems.values();
    }

    public DeferredItem<Item> get(Tier tier) {
        return registeredItems.get(tier);
    }
}