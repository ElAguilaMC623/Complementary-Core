package net.elaguilamc623.complementary_core.utils.tiers;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;
import java.util.function.Supplier;

public class TierBuilder {

    private final String name;

    private int level = 2; // iron
    private int durability = 250;
    private float speed = 6.0f;
    private float attackDamage = 2.0f;
    private int enchantability = 14;

    private TagKey<Block> requiredTag = BlockTags.NEEDS_IRON_TOOL;

    private Supplier<Item> repairItem = () -> Items.IRON_INGOT;

    private TierBuilder(String name) {
        this.name = name;
    }

    public static TierBuilder create(String name) {
        return new TierBuilder(name);
    }

    public TierBuilder level(int level) {
        this.level = level;
        return this;
    }

    public TierBuilder durability(int durability) {
        this.durability = durability;
        return this;
    }

    public TierBuilder speed(float speed) {
        this.speed = speed;
        return this;
    }

    public TierBuilder attackDamage(float damage) {
        this.attackDamage = damage;
        return this;
    }

    public TierBuilder enchantability(int enchantability) {
        this.enchantability = enchantability;
        return this;
    }

    public TierBuilder incorrectBlocks(TagKey<Block> tag) {
        this.requiredTag = tag;
        return this;
    }

    public TierBuilder repairItem(Supplier<Item> item) {
        this.repairItem = item;
        return this;
    }

    public Tier build() {
        Tier tier = new ForgeTier(
                level,
                durability,
                speed,
                attackDamage,
                enchantability,
                requiredTag,
                () -> Ingredient.of(repairItem.get())
        );

        TierSortingRegistry.registerTier(
                tier,
                new ResourceLocation("complementary_core", name),
                List.of(),
                List.of()
        );

        return tier;
    }
}