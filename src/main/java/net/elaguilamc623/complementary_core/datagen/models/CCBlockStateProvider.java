package net.elaguilamc623.complementary_core.datagen.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public abstract class CCBlockStateProvider extends BlockStateProvider {

    protected final String modId;

    public CCBlockStateProvider(PackOutput output, String modId, ExistingFileHelper exFileHelper) {
        super(output, modId, exFileHelper);
        this.modId = modId;
    }

    protected void cubeAllBlock(RegistryObject<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    protected void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    protected void blockItem(RegistryObject<Block> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(
                modId + ":block/" + block.getId().getPath()
        ));
    }

    protected void stairs(RegistryObject<Block> stairs, RegistryObject<Block> base) {
        stairsBlock((StairBlock) stairs.get(), blockTexture(base.get()));
    }

    protected void slab(RegistryObject<Block> slab, RegistryObject<Block> base) {
        slabBlock((SlabBlock) slab.get(), blockTexture(base.get()), blockTexture(base.get()));
    }

    protected void wall(RegistryObject<Block> wall, RegistryObject<Block> base) {
        wallBlock((WallBlock) wall.get(), blockTexture(base.get()));
    }

    protected void button(RegistryObject<Block> button, RegistryObject<Block> base) {
        buttonBlock((ButtonBlock) button.get(), blockTexture(base.get()));
    }

    protected void pressurePlate(RegistryObject<Block> plate, RegistryObject<Block> base) {
        pressurePlateBlock((PressurePlateBlock) plate.get(), blockTexture(base.get()));
    }

    protected void fence(RegistryObject<Block> fence, RegistryObject<Block> base) {
        fenceBlock((FenceBlock) fence.get(), blockTexture(base.get()));
    }

    protected void fenceGate(RegistryObject<Block> gate, RegistryObject<Block> base) {
        fenceGateBlock((FenceGateBlock) gate.get(), blockTexture(base.get()));
    }

    protected void door(RegistryObject<Block> door, String renderType) {
        doorBlockWithRenderType(
                (DoorBlock) door.get(),
                modLoc("block/" + door.getId().getPath() + "_bottom"),
                modLoc("block/" + door.getId().getPath() + "_top"),
                renderType
        );
    }

    protected void trapdoor(RegistryObject<Block> trapdoor, boolean orientable, String renderType) {
        trapdoorBlockWithRenderType(
                (TrapDoorBlock) trapdoor.get(),
                modLoc("block/" + trapdoor.getId().getPath()),
                orientable,
                renderType
        );
    }

    protected void log(RegistryObject<Block> log) {
        logBlock((RotatedPillarBlock) log.get());
    }

    protected void axis(RegistryObject<Block> block, ResourceLocation side, ResourceLocation top) {
        axisBlock((RotatedPillarBlock) block.get(), side, top);
    }

    protected void leaves(RegistryObject<Block> leaves) {
        simpleBlockWithItem(
                leaves.get(),
                models().singleTexture(
                        leaves.getId().getPath(),
                        new ResourceLocation("minecraft:block/leaves"),
                        "all",
                        blockTexture(leaves.get())
                ).renderType("cutout")
        );
    }

    protected void torch(TorchBlock torch, WallTorchBlock wallTorch) {
        String name = torch.getName().getString().toLowerCase();

        simpleBlock(torch, models().torch(name, modLoc("block/" + name)));

        horizontalBlock(
                wallTorch,
                models().torchWall(name + "_wall", modLoc("block/" + name)),
                90
        );
    }
}