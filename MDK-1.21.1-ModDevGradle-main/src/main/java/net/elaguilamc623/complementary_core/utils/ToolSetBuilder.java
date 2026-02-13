package net.elaguilamc623.complementary_core.utils;

import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.BiFunction;

public class ToolSetBuilder {

    private final String name;
    private final DeferredRegister.Items register;
    private final Tier tier;

    private BiFunction<Tier, Item.Properties, Item> swordFactory;
    private BiFunction<Tier, Item.Properties, Item> axeFactory;
    private BiFunction<Tier, Item.Properties, Item> pickaxeFactory;
    private BiFunction<Tier, Item.Properties, Item> shovelFactory;
    private BiFunction<Tier, Item.Properties, Item> hoeFactory;

    private DeferredItem<Item> swordItem;
    private DeferredItem<Item> axeItem;
    private DeferredItem<Item> pickaxeItem;
    private DeferredItem<Item> shovelItem;
    private DeferredItem<Item> hoeItem;

    private ToolSetBuilder(String name, DeferredRegister.Items register, Tier tier) {
        this.name = name;
        this.register = register;
        this.tier = tier;
    }

    public static ToolSetBuilder create(String name, DeferredRegister.Items register, Tier tier) {
        return new ToolSetBuilder(name, register, tier);
    }

    public ToolSetBuilder sword() {
        this.swordFactory = (t, p) -> new SwordItem(t, p);
        return this;
    }

    public ToolSetBuilder axe() {
        this.axeFactory = (t, p) -> new AxeItem(t, p);
        return this;
    }

    public ToolSetBuilder pickaxe() {
        this.pickaxeFactory = (t, p) -> new PickaxeItem(t, p);
        return this;
    }

    public ToolSetBuilder shovel() {
        this.shovelFactory = (t, p) -> new ShovelItem(t, p);
        return this;
    }

    public ToolSetBuilder hoe() {
        this.hoeFactory = (t, p) -> new HoeItem(t, p);
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