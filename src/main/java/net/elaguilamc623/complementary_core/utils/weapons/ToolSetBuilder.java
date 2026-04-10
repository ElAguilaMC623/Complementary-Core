package net.elaguilamc623.complementary_core.utils.weapons;

import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.BiFunction;

public class ToolSetBuilder {

    private final String name;
    private final DeferredRegister.Items register;
    private final ToolMaterial tier;

    private BiFunction<ToolMaterial, Item.Properties, Item> swordFactory;
    private BiFunction<ToolMaterial, Item.Properties, Item> axeFactory;
    private BiFunction<ToolMaterial, Item.Properties, Item> pickaxeFactory;
    private BiFunction<ToolMaterial, Item.Properties, Item> shovelFactory;
    private BiFunction<ToolMaterial, Item.Properties, Item> hoeFactory;

    private DeferredItem<Item> swordItem;
    private DeferredItem<Item> axeItem;
    private DeferredItem<Item> pickaxeItem;
    private DeferredItem<Item> shovelItem;
    private DeferredItem<Item> hoeItem;

    private ToolSetBuilder(String name, DeferredRegister.Items register, ToolMaterial tier) {
        this.name = name;
        this.register = register;
        this.tier = tier;
    }

    public static ToolSetBuilder create(String name, DeferredRegister.Items register, ToolMaterial tier) {
        return new ToolSetBuilder(name, register, tier);
    }

    public ToolSetBuilder sword() {
        this.swordFactory = (m, p) -> new Item(p.sword(m, 3, -2.4f));
        return this;
    }

    public ToolSetBuilder axe() {
        this.axeFactory = (m, p) -> new AxeItem(m, 6, -3.2f, p);
        return this;
    }

    public ToolSetBuilder pickaxe() {
        this.pickaxeFactory = (m, p) -> new Item(p.pickaxe(m, 1, -2.8f));
        return this;
    }

    public ToolSetBuilder shovel() {
        this.shovelFactory = (m, p) -> new ShovelItem(m, 1.5f, -3.0f, p);
        return this;
    }

    public ToolSetBuilder hoe() {
        this.hoeFactory = (m, p) -> new HoeItem(m, 0, -3.0f, p);
        return this;
    }

    public void register() {

        if (swordFactory != null) {
            swordItem = register.register(name + "_sword",
                    () -> swordFactory.apply(tier, new Item.Properties()));
        }

        if (axeFactory != null) {
            axeItem = register.register(name + "_axe",
                    () -> axeFactory.apply(tier, new Item.Properties()));
        }

        if (pickaxeFactory != null) {
            pickaxeItem = register.register(name + "_pickaxe",
                    () -> pickaxeFactory.apply(tier, new Item.Properties()));
        }

        if (shovelFactory != null) {
            shovelItem = register.register(name + "_shovel",
                    () -> shovelFactory.apply(tier, new Item.Properties()));
        }

        if (hoeFactory != null) {
            hoeItem = register.register(name + "_hoe",
                    () -> hoeFactory.apply(tier, new Item.Properties()));
        }
    }

    public DeferredItem<Item> getSword() { return swordItem; }
    public DeferredItem<Item> getAxe() { return axeItem; }
    public DeferredItem<Item> getPickaxe() { return pickaxeItem; }
    public DeferredItem<Item> getShovel() { return shovelItem; }
    public DeferredItem<Item> getHoe() { return hoeItem; }
}