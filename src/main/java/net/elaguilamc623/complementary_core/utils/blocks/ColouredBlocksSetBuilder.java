package net.elaguilamc623.complementary_core.utils.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import java.util.function.Function;

public class ColouredBlocksSetBuilder {

    public final String name;
    public final DeferredRegister<Block> register;

    private Function<Block.Properties, Block> whiteBlockFactory;
    private Function<Block.Properties, Block> blueBlockFactory;

    private RegistryObject<Block> whiteBlock;
    private RegistryObject<Block> blueBlock;

    public ColouredBlocksSetBuilder(String name, DeferredRegister<Block> register) {
        this.name = name;
        this.register = register;
    }

    public static ColouredBlocksSetBuilder create(String name, DeferredRegister<Block> register) {
        return new ColouredBlocksSetBuilder(name, register);
    }

    public ColouredBlocksSetBuilder whiteBlock() {
        this.whiteBlockFactory = ( p) -> new Block(p);
        return this;
    }

    public ColouredBlocksSetBuilder blueBlock() {
        this.blueBlockFactory = ( p) -> new Block(p);
        return this;
    }

    public ColouredBlocksSetBuilder customWhiteBlock(Function<BlockBehaviour.Properties, Block> factory) {
        this.whiteBlockFactory = factory;
        return this;
    }

    public ColouredBlocksSetBuilder customBlueBlock(Function<BlockBehaviour.Properties, Block> factory) {
        this.blueBlockFactory = factory;
        return this;
    }

    public void register(Block baseBlockProperties) {

        if (whiteBlockFactory != null) {
            whiteBlock = register.register("white_" + name,
                    () -> whiteBlockFactory.apply(BlockBehaviour.Properties.copy(baseBlockProperties)));
        }

        if (blueBlockFactory != null) {
            blueBlock = register.register("blue_" + name,
                    () -> blueBlockFactory.apply(BlockBehaviour.Properties.copy(baseBlockProperties)));
        }
    }

    public RegistryObject<Block> getWhiteBlock() { return whiteBlock; }
    public RegistryObject<Block> getBlueBlock() { return blueBlock; }
}
