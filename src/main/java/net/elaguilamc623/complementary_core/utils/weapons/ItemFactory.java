package net.elaguilamc623.complementary_core.utils.weapons;

import net.minecraft.world.item.Item;

@FunctionalInterface
public interface ItemFactory {
    Item create(Item.Properties properties);
}