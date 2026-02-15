package net.elaguilamc623.complementary_core.datagen.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.LinkedHashMap;

public abstract class CCItemModelProvider extends ItemModelProvider {

    protected final String modId;

    protected static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> TRIM_MATERIALS = new LinkedHashMap<>();

    static {
        TRIM_MATERIALS.put(TrimMaterials.QUARTZ, 0.1F);
        TRIM_MATERIALS.put(TrimMaterials.IRON, 0.2F);
        TRIM_MATERIALS.put(TrimMaterials.NETHERITE, 0.3F);
        TRIM_MATERIALS.put(TrimMaterials.REDSTONE, 0.4F);
        TRIM_MATERIALS.put(TrimMaterials.COPPER, 0.5F);
        TRIM_MATERIALS.put(TrimMaterials.GOLD, 0.6F);
        TRIM_MATERIALS.put(TrimMaterials.EMERALD, 0.7F);
        TRIM_MATERIALS.put(TrimMaterials.DIAMOND, 0.8F);
        TRIM_MATERIALS.put(TrimMaterials.LAPIS, 0.9F);
        TRIM_MATERIALS.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public CCItemModelProvider(PackOutput output, String modId, ExistingFileHelper existingFileHelper) {
        super(output, modId, existingFileHelper);
        this.modId = modId;
    }

    protected ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", new ResourceLocation(modId, "item/" + item.getId().getPath()));
    }

    protected ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), mcLoc("item/handheld"))
                .texture("layer0", new ResourceLocation(modId, "item/" + item.getId().getPath()));
    }
 
    protected void blockItem(RegistryObject<Block> block) {
        withExistingParent(block.getId().getPath(), modLoc("block/" + block.getId().getPath()));
    }

    protected void trapdoorItem(RegistryObject<Block> block) {
        withExistingParent(block.getId().getPath(),
                modLoc("block/" + block.getId().getPath() + "_bottom"));
    }

    protected void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", new ResourceLocation(modId,
                        "block/" + baseBlock.getId().getPath()));
    }

    protected void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture", new ResourceLocation(modId,
                        "block/" + baseBlock.getId().getPath()));
    }

    protected void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", new ResourceLocation(modId,
                        "block/" + baseBlock.getId().getPath()));
    }

    protected void torchItem(Block torch) {
        String name = ForgeRegistries.BLOCKS.getKey(torch).getPath();
        withExistingParent(name, modLoc("block/" + name));
    }

    protected void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {

        if (itemRegistryObject.get() instanceof ArmorItem armorItem) {

            TRIM_MATERIALS.forEach((trimMaterial, trimValue) -> {

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String itemName = itemRegistryObject.getId().getPath();
                String basePath = "item/" + itemName;

                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String trimmedModelName = basePath + "_" + trimMaterial.location().getPath() + "_trim";

                ResourceLocation baseTexture = new ResourceLocation(modId, basePath);
                ResourceLocation trimTexture = new ResourceLocation(trimPath);
                ResourceLocation trimmedModel = new ResourceLocation(modId, trimmedModelName);

                existingFileHelper.trackGenerated(trimTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

                getBuilder(trimmedModelName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", baseTexture)
                        .texture("layer1", trimTexture);

                withExistingParent(itemName, mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimmedModel))
                        .predicate(mcLoc("trim_type"), trimValue)
                        .end()
                        .texture("layer0", baseTexture);
            });
        }
    }
}