package net.elaguilamc623.complementary_core.datagen.recipes;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.crafting.Ingredient;
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
                            result,
                            count
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

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output)
                .pattern("#")
                .pattern("#")
                .define('#', slab)
                .unlockedBy(unlockName, has(slab))
                .save(consumer);
    }

    protected void mossyBlockRecipe(Consumer<FinishedRecipe> consumer,
                                    ItemLike base, ItemLike output,
                                    String unlockName) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output)
                .pattern("S")
                .pattern("V")
                .define('S', base)
                .define('V', net.minecraft.world.item.Items.VINE)
                .unlockedBy(unlockName, has(net.minecraft.world.item.Items.VINE))
                .save(consumer);
    }

    protected void buttonRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike planks) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, output)
                .requires(planks)
                .unlockedBy("has_planks", has(planks))
                .save(consumer);
    }

    protected void pressurePlateRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output)
                .define('#', planks)
                .pattern("##")
                .unlockedBy("has_planks", has(planks))
                .save(consumer);
    }

    protected void fenceAndGateRecipes(Consumer<FinishedRecipe> consumer,
                                       ItemLike planks,
                                       ItemLike fence,
                                       ItemLike fenceGate) {

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, fence, 3)
                .define('#', planks)
                .define('X', net.minecraft.world.item.Items.STICK)
                .pattern("#X#")
                .pattern("#X#")
                .unlockedBy("has_planks", has(planks))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, fenceGate)
                .define('#', planks)
                .define('X', net.minecraft.world.item.Items.STICK)
                .pattern("X#X")
                .pattern("X#X")
                .unlockedBy("has_planks", has(planks))
                .save(consumer);
    }

    protected void doorRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output, 3)
                .define('#', planks)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_planks", has(planks))
                .save(consumer);
    }

    protected void trapdoorRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, output, 2)
                .define('#', planks)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_planks", has(planks))
                .save(consumer);
    }

    protected void toolRecipe(Consumer<FinishedRecipe> consumer,
                              ItemLike result,
                              ItemLike material,
                              TagKey<Item> stickTag,
                              String p1, String p2, String p3,
                              String unlockName) {

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern(p1)
                .pattern(p2)
                .pattern(p3)
                .define('X', material)
                .define('#', stickTag)
                .unlockedBy("has_" + unlockName, has(material))
                .save(consumer);
    }

    protected void helmetRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .define('#', material)
                .pattern("###")
                .pattern("# #")
                .unlockedBy("has_material", has(material))
                .save(consumer);
    }

    protected void chestplateRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .define('#', material)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_material", has(material))
                .save(consumer);
    }

    protected void leggingsRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .define('#', material)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .unlockedBy("has_material", has(material))
                .save(consumer);
    }

    protected void bootsRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, output)
                .define('#', material)
                .pattern("# #")
                .pattern("# #")
                .unlockedBy("has_material", has(material))
                .save(consumer);
    }

    protected void torchRecipe(Consumer<FinishedRecipe> consumer,
                               ItemLike result,
                               ItemLike top,
                               ItemLike bottom,
                               int count,
                               String unlockName) {

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, count)
                .pattern("C")
                .pattern("S")
                .define('C', top)
                .define('S', bottom)
                .unlockedBy(unlockName, has(top))
                .save(consumer);
    }

    protected void lanternRecipe(Consumer<FinishedRecipe> consumer,
                                 ItemLike result,
                                 ItemLike center,
                                 ItemLike nugget,
                                 String unlockName) {

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result)
                .pattern("NNN")
                .pattern("NTN")
                .pattern("NNN")
                .define('N', nugget)
                .define('T', center)
                .unlockedBy(unlockName, has(center))
                .save(consumer);
    }

    protected void nuggetFromIngotRecipe(Consumer<FinishedRecipe> consumer,
                                         ItemLike nugget,
                                         ItemLike ingot,
                                         String unlockName) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget, 9)
                .requires(ingot)
                .unlockedBy(unlockName, has(ingot))
                .save(consumer);
    }

    protected void oreCompactRecipe(Consumer<FinishedRecipe> consumer,
                                    ItemLike item,
                                    ItemLike block,
                                    String name) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block)
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