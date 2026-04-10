package net.elaguilamc623.complementary_core.datagen.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.LinkedHashMap;

public abstract class CCItemModelProvider extends ItemModelProvider {

    protected final String modId;

    protected static final LinkedHashMap<ResourceKey<TrimMaterial>, Integer> TRIM_MATERIALS = new LinkedHashMap<>();

    static {
        TRIM_MATERIALS.put(TrimMaterials.QUARTZ, 1);
        TRIM_MATERIALS.put(TrimMaterials.IRON, 2);
        TRIM_MATERIALS.put(TrimMaterials.NETHERITE, 3);
        TRIM_MATERIALS.put(TrimMaterials.REDSTONE, 4);
        TRIM_MATERIALS.put(TrimMaterials.COPPER, 5);
        TRIM_MATERIALS.put(TrimMaterials.GOLD, 6);
        TRIM_MATERIALS.put(TrimMaterials.EMERALD, 7);
        TRIM_MATERIALS.put(TrimMaterials.DIAMOND, 8);
        TRIM_MATERIALS.put(TrimMaterials.LAPIS, 9);
        TRIM_MATERIALS.put(TrimMaterials.AMETHYST, 10);
    }

    public CCItemModelProvider(PackOutput output, String modId, ExistingFileHelper existingFileHelper) {
        super(output, modId, existingFileHelper);
        this.modId = modId;
    }

    protected ItemModelBuilder simpleItem(DeferredItem<Item> item) {
        String name = item.getId().getPath();
        return withExistingParent(name, mcLoc("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(modId, "item/" + name));
    }

    protected ItemModelBuilder handheldItem(DeferredItem<Item> item) {
        String name = item.getId().getPath();
        return withExistingParent(name, mcLoc("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(modId, "item/" + name));
    }

    protected void blockItem(DeferredBlock<Block> block) {
        String name = block.getId().getPath();
        withExistingParent(name, modLoc("block/" + name));
    }

    protected void trapdoorItem(DeferredBlock<Block> block) {
        String name = block.getId().getPath();
        withExistingParent(name, modLoc("block/" + name + "_bottom"));
    }

    protected void fenceItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        String name = block.getId().getPath();
        withExistingParent(name, mcLoc("block/fence_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(modId, "block/" + baseBlock.getId().getPath()));
    }

    protected void buttonItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        String name = block.getId().getPath();
        withExistingParent(name, mcLoc("block/button_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(modId, "block/" + baseBlock.getId().getPath()));
    }

    protected void wallItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
        String name = block.getId().getPath();
        withExistingParent(name, mcLoc("block/wall_inventory"))
                .texture("wall", ResourceLocation.fromNamespaceAndPath(modId, "block/" + baseBlock.getId().getPath()));
    }

    protected void torchItem(Block torch) {
        String name = BuiltInRegistries.BLOCK.getKey(torch).getPath();
        withExistingParent(name, modLoc("block/" + name));
    }

    protected void trimmedArmorItem(DeferredItem<Item> item, String armorType) {
        String itemName = item.getId().getPath();
        ResourceLocation baseTexture = ResourceLocation.fromNamespaceAndPath(modId, "item/" + itemName);

        withExistingParent(itemName, mcLoc("item/generated"))
                .texture("layer0", baseTexture);

        TRIM_MATERIALS.forEach((trimMaterial, trimValue) -> {
            String trimName = trimMaterial.location().getPath();
            String modelName = itemName + "_trim_" + trimName;

            ResourceLocation trimTexture = ResourceLocation.parse(
                    "minecraft:trims/items/" + armorType + "_trim_" + trimName
            );

            existingFileHelper.trackGenerated(trimTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            getBuilder(modelName)
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", baseTexture)
                    .texture("layer1", trimTexture);

            withExistingParent(itemName, mcLoc("item/generated"))
                    .override()
                    .model(new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(modId, modelName)))
                    .predicate(mcLoc("trim_type"), trimValue)
                    .end()
                    .texture("layer0", baseTexture);
        });
    }
}