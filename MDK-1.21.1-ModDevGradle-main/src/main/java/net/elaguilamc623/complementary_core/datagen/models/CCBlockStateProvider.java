package net.elaguilamc623.complementary_core.datagen.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public abstract class CCBlockStateProvider extends BlockStateProvider {

    protected final String modId;

    public CCBlockStateProvider(PackOutput output, String modId, ExistingFileHelper exFileHelper) {
        super(output, modId, exFileHelper);
        this.modId = modId;
    }

    protected void cubeAllBlock(DeferredBlock<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    protected void blockWithItem(DeferredBlock<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    protected void blockItem(DeferredBlock<Block> block) {
        simpleBlockItem(
                block.get(),
                new ModelFile.UncheckedModelFile(
                        modId + ":block/" + block.getId().getPath()
                )
        );
    }

    protected void stairs(DeferredBlock<Block> stairs, DeferredBlock<Block> base) {
        stairsBlock((StairBlock) stairs.get(), blockTexture(base.get()));
    }

    protected void slab(DeferredBlock<Block> slab, DeferredBlock<Block> base) {
        slabBlock((SlabBlock) slab.get(), blockTexture(base.get()), blockTexture(base.get()));
    }

    protected void wall(DeferredBlock<Block> wall, DeferredBlock<Block> base) {
        wallBlock((WallBlock) wall.get(), blockTexture(base.get()));
    }

    protected void button(DeferredBlock<Block> button, DeferredBlock<Block> base) {
        buttonBlock((ButtonBlock) button.get(), blockTexture(base.get()));
    }

    protected void pressurePlate(DeferredBlock<Block> plate, DeferredBlock<Block> base) {
        pressurePlateBlock((PressurePlateBlock) plate.get(), blockTexture(base.get()));
    }

    protected void fence(DeferredBlock<Block> fence, DeferredBlock<Block> base) {
        fenceBlock((FenceBlock) fence.get(), blockTexture(base.get()));
    }

    protected void fenceGate(DeferredBlock<Block> gate, DeferredBlock<Block> base) {
        fenceGateBlock((FenceGateBlock) gate.get(), blockTexture(base.get()));
    }

    protected void door(DeferredBlock<Block> door, String renderType) {
        doorBlockWithRenderType(
                (DoorBlock) door.get(),
                modLoc("block/" + door.getId().getPath() + "_bottom"),
                modLoc("block/" + door.getId().getPath() + "_top"),
                renderType
        );
    }

    protected void trapdoor(DeferredBlock<Block> trapdoor, boolean orientable, String renderType) {
        trapdoorBlockWithRenderType(
                (TrapDoorBlock) trapdoor.get(),
                modLoc("block/" + trapdoor.getId().getPath()),
                orientable,
                renderType
        );
    }

    protected void log(DeferredBlock<Block> log) {
        logBlock((RotatedPillarBlock) log.get());
    }

    protected void axis(DeferredBlock<Block> block, ResourceLocation side, ResourceLocation top) {
        axisBlock((RotatedPillarBlock) block.get(), side, top);
    }

    protected void leaves(DeferredBlock<Block> leaves) {
        simpleBlockWithItem(
                leaves.get(),
                models().singleTexture(
                        leaves.getId().getPath(),
                        ResourceLocation.parse("minecraft:block/leaves"),
                        "all",
                        blockTexture(leaves.get())
                ).renderType("cutout")
        );
    }

    protected void torch(TorchBlock torch, WallTorchBlock wallTorch) {
        String name = BuiltInRegistries.BLOCK.getKey(torch).getPath();

        simpleBlock(torch, models().torch(name, modLoc("block/" + name)));

        horizontalBlock(
                wallTorch,
                models().torchWall(name + "_wall", modLoc("block/" + name)),
                90
        );
    }
}