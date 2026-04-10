package net.elaguilamc623.complementary_core.utils;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public interface ItemFactory {
    Item create(Tier tier, Item.Properties props);
}