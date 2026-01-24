package net.elaguilamc623.complementary_core.utils;

import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class TieredWeaponBuilder {

    private final String baseName;
    private final DeferredRegister<Item> register;

    @FunctionalInterface
    public interface ItemFactory {
        Item create(Tier tier, Item.Properties props);
    }

    private final Map<Tier, ItemFactory> factories = new LinkedHashMap<>();

    private TieredWeaponBuilder(String baseName, DeferredRegister<Item> register) {
        this.baseName = baseName;
        this.register = register;
    }

    public static TieredWeaponBuilder create(String baseName, DeferredRegister<Item> register) {
        return new TieredWeaponBuilder(baseName, register);
    }

    public TieredWeaponBuilder add(Tier tier, int damage, float speed) {
        factories.put(tier, (t, props) -> new SwordItem(t, damage, speed, props));
        return this;
    }

    public TieredWeaponBuilder add(Tier tier, ItemFactory factory) {
        factories.put(tier, factory);
        return this;
    }

    private final Map<Tier, RegistryObject<Item>> registeredItems = new LinkedHashMap<>();

    public void register() {
        for (var entry : factories.entrySet()) {
            Tier tier = entry.getKey();
            ItemFactory factory = entry.getValue();

            String name = tierName(tier) + "_" + baseName;

            RegistryObject<Item> item = register.register(name,
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

    public Collection<RegistryObject<Item>> getItems() {
        return registeredItems.values();
    }

    public RegistryObject<Item> get(Tier tier) {
        return registeredItems.get(tier);
    }
}