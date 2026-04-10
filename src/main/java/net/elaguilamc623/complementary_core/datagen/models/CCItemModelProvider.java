package net.elaguilamc623.complementary_core.datagen.models;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class CCItemModelProvider extends ItemModelProvider {

    protected final String modId;

    public CCItemModelProvider(PackOutput packOutput,
                               String modId,
                               ExistingFileHelper existingFileHelper) {
        super(packOutput, modId, existingFileHelper);
        this.modId = modId;
    }

    protected ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", new ResourceLocation(modId,
                        "item/" + item.getId().getPath()));
    }

    protected ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), mcLoc("item/handheld"))
                .texture("layer0", new ResourceLocation(modId,
                        "item/" + item.getId().getPath()));
    }

    protected void blockItem(RegistryObject<Block> block) {
        withExistingParent(block.getId().getPath(),
                modLoc("block/" + block.getId().getPath()));
    }

    protected void trapdoorItem(RegistryObject<Block> block) {
        withExistingParent(block.getId().getPath(),
                modLoc("block/" + block.getId().getPath() + "_bottom"));
    }

    protected void fenceItem(RegistryObject<Block> block,
                             RegistryObject<Block> baseBlock) {

        withExistingParent(block.getId().getPath(),
                mcLoc("block/fence_inventory"))
                .texture("texture", new ResourceLocation(modId,
                        "block/" + baseBlock.getId().getPath()));
    }

    protected void buttonItem(RegistryObject<Block> block,
                              RegistryObject<Block> baseBlock) {

        withExistingParent(block.getId().getPath(),
                mcLoc("block/button_inventory"))
                .texture("texture", new ResourceLocation(modId,
                        "block/" + baseBlock.getId().getPath()));
    }

    protected void wallItem(RegistryObject<Block> block,
                            RegistryObject<Block> baseBlock) {

        withExistingParent(block.getId().getPath(),
                mcLoc("block/wall_inventory"))
                .texture("wall", new ResourceLocation(modId,
                        "block/" + baseBlock.getId().getPath()));
    }

    protected void torchItem(Block torch) {
        String name = ForgeRegistries.BLOCKS.getKey(torch).getPath();
        withExistingParent(name, modLoc("block/" + name));
    }
}