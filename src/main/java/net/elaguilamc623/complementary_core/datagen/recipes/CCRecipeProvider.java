package net.elaguilamc623.complementary_core.datagen.recipes;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public abstract class CCRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public CCRecipeProvider(PackOutput output) {
        super(output);
    }

    protected void stonecutterRecipes(Consumer<FinishedRecipe> consumer,
                                      ItemLike base,
                                      ItemLike... results) {

        for (ItemLike result : results) {
            int count = (result instanceof SlabBlock) ? 2 : 1;

            SingleItemRecipeBuilder.stonecutting(
                            Ingredient.of(base),
                            RecipeCategory.BUILDING_BLOCKS,
                            result
                    )
                    .unlockedBy(getHasName(base), has(base))
                    .save(consumer, getConversionRecipeName(result, base) + "_stonecutting");
        }
    }

    protected void slabRecipe(Consumer<FinishedRecipe> consumer, ItemLike base, ItemLike slab) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .pattern("###")
                .define('#', base)
                .unlockedBy(getHasName(base), has(base))
                .save(consumer);
    }

    protected void stairsRecipe(Consumer<FinishedRecipe> consumer, ItemLike base, ItemLike stairs) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', base)
                .unlockedBy(getHasName(base), has(base))
                .save(consumer);
    }

    protected void wallRecipe(Consumer<FinishedRecipe> consumer, ItemLike base, ItemLike wall) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
                .pattern("###")
                .pattern("###")
                .define('#', base)
                .unlockedBy(getHasName(base), has(base))
                .save(consumer);
    }

    protected void bricksRecipe(Consumer<FinishedRecipe> consumer,
                                ItemLike input, ItemLike output,
                                String unlockName) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy(unlockName, has(input))
                .save(consumer);
    }

    protected void chiseledRecipe(Consumer<FinishedRecipe> consumer,
                                  ItemLike slab, ItemLike output,
                                  String unlockName) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("#")
                .pattern("#")
                .define('#', slab)
                .unlockedBy(unlockName, has(slab))
                .save(consumer);
    }

    protected void mossyBlockRecipe(Consumer<FinishedRecipe> consumer,
                                    ItemLike base, ItemLike output,
                                    String unlockName) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("S")
                .pattern("V")
                .define('S', base)
                .define('V', Items.VINE)
                .unlockedBy(unlockName, has(net.minecraft.world.item.Items.VINE))
                .save(consumer);
    }

    protected void buttonRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike planks) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 1)
                .requires(planks)
                .unlockedBy("has_planks", has(planks))
                .save(consumer);
    }

    protected void pressurePlateRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .define('#', planks)
                .pattern("##")
                .unlockedBy("has_planks", has(planks))
                .save(consumer);
    }

    protected void fenceAndGateRecipes(Consumer<FinishedRecipe> consumer,
                                       ItemLike planks,
                                       ItemLike fence,
                                       ItemLike fenceGate) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fence, 3)
                .define('#', planks)
                .define('X', net.minecraft.world.item.Items.STICK)
                .pattern("#X#")
                .pattern("#X#")
                .unlockedBy("has_planks", has(planks))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fenceGate, 1)
                .define('#', planks)
                .define('X', net.minecraft.world.item.Items.STICK)
                .pattern("X#X")
                .pattern("X#X")
                .unlockedBy("has_planks", has(planks))
                .save(consumer);
    }

    protected void doorRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .define('#', planks)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_planks", has(planks))
                .save(consumer);
    }

    protected void trapdoorRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 2)
                .define('#', planks)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_planks", has(planks))
                .save(consumer);
    }

    protected void oreCompactRecipe(Consumer<FinishedRecipe> consumer,
                                    ItemLike item,
                                    ItemLike block,
                                    String name) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block, 2)
                .define('#', item)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(item), has(item))
                .save(consumer, name + "_block");
    }

    protected void oreUncompactRecipe(Consumer<FinishedRecipe> consumer,
                                      ItemLike block,
                                      ItemLike item,
                                      String name) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item, 9)
                .requires(block)
                .unlockedBy(getHasName(block), has(block))
                .save(consumer, name + "_from_block");
    }
}