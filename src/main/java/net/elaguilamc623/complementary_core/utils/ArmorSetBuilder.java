package net.elaguilamc623.complementary_core.utils;

import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiFunction;

public class ArmorSetBuilder {

    private final String name;
    private final DeferredRegister<Item> register;
    private final ArmorMaterial material;

    private BiFunction<ArmorMaterial, Item.Properties, Item> helmetFactory;
    private BiFunction<ArmorMaterial, Item.Properties, Item> chestplateFactory;
    private BiFunction<ArmorMaterial, Item.Properties, Item> leggingsFactory;
    private BiFunction<ArmorMaterial, Item.Properties, Item> bootsFactory;

    private RegistryObject<Item> helmetItem;
    private RegistryObject<Item> chestplateItem;
    private RegistryObject<Item> leggingsItem;
    private RegistryObject<Item> bootsItem;

    private ArmorSetBuilder(String name, DeferredRegister<Item> register, ArmorMaterial material) {
        this.name = name;
        this.register = register;
        this.material = material;
    }

    public static ArmorSetBuilder create(String name, DeferredRegister<Item> register, ArmorMaterial material) {
        return new ArmorSetBuilder(name, register, material);
    }

    public ArmorSetBuilder helmet() {
        this.helmetFactory = (m, p) -> new ArmorItem(m, ArmorItem.Type.HELMET, p);
        return this;
    }

    public ArmorSetBuilder chestplate() {
        this.chestplateFactory = (m, p) -> new ArmorItem(m, ArmorItem.Type.CHESTPLATE, p);
        return this;
    }

    public ArmorSetBuilder leggings() {
        this.leggingsFactory = (m, p) -> new ArmorItem(m, ArmorItem.Type.LEGGINGS, p);
        return this;
    }

    public ArmorSetBuilder boots() {
        this.bootsFactory = (m, p) -> new ArmorItem(m, ArmorItem.Type.BOOTS, p);
        return this;
    }

    public ArmorSetBuilder customHelmet(BiFunction<ArmorMaterial, Item.Properties, Item> factory) {
        this.helmetFactory = factory;
        return this;
    }

    public ArmorSetBuilder customChestplate(BiFunction<ArmorMaterial, Item.Properties, Item> factory) {
        this.chestplateFactory = factory;
        return this;
    }

    public ArmorSetBuilder customLeggings(BiFunction<ArmorMaterial, Item.Properties, Item> factory) {
        this.leggingsFactory = factory;
        return this;
    }

    public ArmorSetBuilder customBoots(BiFunction<ArmorMaterial, Item.Properties, Item> factory) {
        this.bootsFactory = factory;
        return this;
    }

    public void register() {

        if (helmetFactory != null) {
            helmetItem = register.register(name + "_helmet",
                    () -> helmetFactory.apply(material, new Item.Properties()));
        }

        if (chestplateFactory != null) {
            chestplateItem = register.register(name + "_chestplate",
                    () -> chestplateFactory.apply(material, new Item.Properties()));
        }

        if (leggingsFactory != null) {
            leggingsItem = register.register(name + "_leggings",
                    () -> leggingsFactory.apply(material, new Item.Properties()));
        }

        if (bootsFactory != null) {
            bootsItem = register.register(name + "_boots",
                    () -> bootsFactory.apply(material, new Item.Properties()));
        }
    }

    public RegistryObject<Item> getHelmet() { return helmetItem; }
    public RegistryObject<Item> getChestplate() { return chestplateItem; }
    public RegistryObject<Item> getLeggings() { return leggingsItem; }
    public RegistryObject<Item> getBoots() { return bootsItem; }
}