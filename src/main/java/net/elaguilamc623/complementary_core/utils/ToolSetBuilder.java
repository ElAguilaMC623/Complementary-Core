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

    public ToolSetBuilder sword(float attackDamage, float attackSpeed) {
        this.swordFactory = (t, p) -> new SwordItem(t, p.attributes(SwordItem.createAttributes(t, attackDamage, attackSpeed)));
        return this;
    }

    public ToolSetBuilder axe(float attackDamage, float attackSpeed) {
        this.axeFactory = (t, p) -> new AxeItem(t, p.attributes(SwordItem.createAttributes(t, attackDamage, attackSpeed)));
        return this;
    }

    public ToolSetBuilder pickaxe(float attackDamage, float attackSpeed) {
        this.pickaxeFactory = (t, p) -> new PickaxeItem(t, p.attributes(SwordItem.createAttributes(t, attackDamage, attackSpeed)));
        return this;
    }

    public ToolSetBuilder shovel(float attackDamage, float attackSpeed) {
        this.shovelFactory = (t, p) -> new ShovelItem(t, p.attributes(SwordItem.createAttributes(t, attackDamage, attackSpeed)));
        return this;
    }

    public ToolSetBuilder hoe(float attackDamage, float attackSpeed) {
        this.hoeFactory = (t, p) -> new HoeItem(t, p.attributes(SwordItem.createAttributes(t, attackDamage, attackSpeed)));
        return this;
    }

    public ToolSetBuilder register() {
        if (this.swordFactory != null) {
            this.swordItem = this.register.register(this.name + "_sword", () -> this.swordFactory.apply(this.tier, new Item.Properties()));
        }

        if (this.axeFactory != null) {
            this.axeItem = this.register.register(this.name + "_axe", () -> this.axeFactory.apply(this.tier, new Item.Properties()));
        }

        if (this.pickaxeFactory != null) {
            this.pickaxeItem = this.register.register(this.name + "_pickaxe", () -> this.pickaxeFactory.apply(this.tier, new Item.Properties()));
        }

        if (this.shovelFactory != null) {
            this.shovelItem = this.register.register(this.name + "_shovel", () -> this.shovelFactory.apply(this.tier, new Item.Properties()));
        }

        if (this.hoeFactory != null) {
            this.hoeItem = this.register.register(this.name + "_hoe", () -> this.hoeFactory.apply(this.tier, new Item.Properties()));
        }

        return this;
    }

    public DeferredItem<Item> getSword() { return swordItem; }
    public DeferredItem<Item> getAxe() { return axeItem; }
    public DeferredItem<Item> getPickaxe() { return pickaxeItem; }
    public DeferredItem<Item> getShovel() { return shovelItem; }
    public DeferredItem<Item> getHoe() { return hoeItem; }
}