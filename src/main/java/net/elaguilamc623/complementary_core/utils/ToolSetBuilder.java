package net.elaguilamc623.complementary_core.utils;

import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import java.util.function.BiFunction;

public class ToolSetBuilder {

    private final String name;
    private final DeferredRegister<Item> register;
    private final Tier tier;

    private BiFunction<Tier, Item.Properties, Item> swordFactory;
    private BiFunction<Tier, Item.Properties, Item> axeFactory;
    private BiFunction<Tier, Item.Properties, Item> pickaxeFactory;
    private BiFunction<Tier, Item.Properties, Item> shovelFactory;
    private BiFunction<Tier, Item.Properties, Item> hoeFactory;

    private RegistryObject<Item> swordItem;
    private RegistryObject<Item> axeItem;
    private RegistryObject<Item> pickaxeItem;
    private RegistryObject<Item> shovelItem;
    private RegistryObject<Item> hoeItem;

    private ToolSetBuilder(String name, DeferredRegister<Item> register, Tier tier) {
        this.name = name;
        this.register = register;
        this.tier = tier;
    }

    public static ToolSetBuilder create(String name, DeferredRegister<Item> register, Tier tier) {
        return new ToolSetBuilder(name, register, tier);
    }

    public ToolSetBuilder sword() {
        this.swordFactory = (t, p) -> new SwordItem(t, 3, -2.4F, p);
        return this;
    }

    public ToolSetBuilder axe() {
        this.axeFactory = (t, p) -> new AxeItem(t, 6.0F, -3.1F, p);
        return this;
    }

    public ToolSetBuilder pickaxe() {
        this.pickaxeFactory = (t, p) -> new PickaxeItem(t, 1, -2.8F, p);
        return this;
    }

    public ToolSetBuilder shovel() {
        this.shovelFactory = (t, p) -> new ShovelItem(t, 1.5F, -3.0F, p);
        return this;
    }

    public ToolSetBuilder hoe() {
        this.hoeFactory = (t, p) -> new HoeItem(t, -2, 0.0F, p);
        return this;
    }


    public ToolSetBuilder customSword(BiFunction<Tier, Item.Properties, Item> factory) {
        this.swordFactory = factory;
        return this;
    }

    public ToolSetBuilder customAxe(BiFunction<Tier, Item.Properties, Item> factory) {
        this.axeFactory = factory;
        return this;
    }

    public ToolSetBuilder customPickaxe(BiFunction<Tier, Item.Properties, Item> factory) {
        this.pickaxeFactory = factory;
        return this;
    }

    public ToolSetBuilder customShovel(BiFunction<Tier, Item.Properties, Item> factory) {
        this.shovelFactory = factory;
        return this;
    }

    public ToolSetBuilder customHoe(BiFunction<Tier, Item.Properties, Item> factory) {
        this.hoeFactory = factory;
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

    public RegistryObject<Item> getSword() { return swordItem; }
    public RegistryObject<Item> getAxe() { return axeItem; }
    public RegistryObject<Item> getPickaxe() { return pickaxeItem; }
    public RegistryObject<Item> getShovel() { return shovelItem; }
    public RegistryObject<Item> getHoe() { return hoeItem; }
}