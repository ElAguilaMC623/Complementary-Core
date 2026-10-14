package net.elaguilamc623.complementary_core.utils.armors;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.BiFunction;

public class ArmorSetBuilder {

    private final String name;
    private final DeferredRegister.Items register;
    private final Holder<ArmorMaterial> material;

    private BiFunction<Holder<ArmorMaterial>, Item.Properties, Item> helmetFactory;
    private BiFunction<Holder<ArmorMaterial>, Item.Properties, Item> chestplateFactory;
    private BiFunction<Holder<ArmorMaterial>, Item.Properties, Item> leggingsFactory;
    private BiFunction<Holder<ArmorMaterial>, Item.Properties, Item> bootsFactory;

    private DeferredItem<Item> helmetItem;
    private DeferredItem<Item> chestplateItem;
    private DeferredItem<Item> leggingsItem;
    private DeferredItem<Item> bootsItem;

    private ArmorSetBuilder(String name, DeferredRegister.Items register, Holder<ArmorMaterial> material) {
        this.name = name;
        this.register = register;
        this.material = material;
    }

    public static ArmorSetBuilder create(String name, DeferredRegister.Items register, Holder<ArmorMaterial> material) {
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

    public ArmorSetBuilder customHelmet(BiFunction<Holder<ArmorMaterial>, Item.Properties, Item> factory) {
        this.helmetFactory = factory;
        return this;
    }

    public ArmorSetBuilder customChestplate(BiFunction<Holder<ArmorMaterial>, Item.Properties, Item> factory) {
        this.chestplateFactory = factory;
        return this;
    }

    public ArmorSetBuilder customLeggings(BiFunction<Holder<ArmorMaterial>, Item.Properties, Item> factory) {
        this.leggingsFactory = factory;
        return this;
    }

    public ArmorSetBuilder customBoots(BiFunction<ArmorMaterial, Item.Properties, Item> factory) {
        this.bootsFactory = (m, p) -> factory.apply(m.value(), p);
        return this;
    }

    public ArmorSetBuilder customBootsHolder(BiFunction<Holder<ArmorMaterial>, Item.Properties, Item> factory) {
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

    public DeferredItem<Item> getHelmet() { return helmetItem; }
    public DeferredItem<Item> getChestplate() { return chestplateItem; }
    public DeferredItem<Item> getLeggings() { return leggingsItem; }
    public DeferredItem<Item> getBoots() { return bootsItem; }
}
