package net.elaguilamc623.complementary_core.utils.weapons;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.*;
import java.util.function.BiFunction;

public class TieredWeaponBuilder {

    private final String baseName;
    private final DeferredRegister.Items register;

    private final Map<ToolMaterial, BiFunction<ToolMaterial, Item.Properties, Item>> factories = new LinkedHashMap<>();
    private final Map<ToolMaterial, DeferredItem<Item>> registeredItems = new LinkedHashMap<>();

    private TieredWeaponBuilder(String baseName, DeferredRegister.Items register) {
        this.baseName = baseName;
        this.register = register;
    }

    public static TieredWeaponBuilder create(String baseName, DeferredRegister.Items register) {
        return new TieredWeaponBuilder(baseName, register);
    }

    public TieredWeaponBuilder add(ToolMaterial material, int damage, float speed) {
        factories.put(material, (tier, props) -> new Item(props.sword(tier, damage, speed)));
        return this;
    }

    public TieredWeaponBuilder add(ToolMaterial material, BiFunction<ToolMaterial, Item.Properties, Item> factory) {
        factories.put(material, factory);
        return this;
    }

    public void register() {
        Set<String> usedNames = new HashSet<>();

        for (var entry : factories.entrySet()) {
            ToolMaterial material = entry.getKey();
            BiFunction<ToolMaterial, Item.Properties, Item> factory = entry.getValue();

            String name = materialName(material) + "_" + baseName;

            if (!usedNames.add(name)) {
                throw new IllegalStateException("[CTW] Duplicate item name generated: " + name);
            }

            System.out.println("[CTW] Registering tiered item: " + name + " for material " + material);

            DeferredItem<Item> item = register.registerItem(
                    name,
                    props -> factory.apply(material, props)
            );

            registeredItems.put(material, item);
        }
    }

    private static final Map<ToolMaterial, String> MATERIAL_NAMES = Map.of(
            ToolMaterial.WOOD, "wood",
            ToolMaterial.STONE, "stone",
            ToolMaterial.IRON, "iron",
            ToolMaterial.GOLD, "gold",
            ToolMaterial.DIAMOND, "diamond",
            ToolMaterial.NETHERITE, "netherite"
    );

    private String materialName(ToolMaterial material) {
        String name = MATERIAL_NAMES.get(material);
        if (name == null) {
            throw new IllegalStateException("No name mapped for ToolMaterial: " + material);
        }
        return name;
    }

    public Collection<DeferredItem<Item>> getItems() {
        return registeredItems.values();
    }

    public DeferredItem<Item> get(ToolMaterial material) {
        return registeredItems.get(material);
    }
}
