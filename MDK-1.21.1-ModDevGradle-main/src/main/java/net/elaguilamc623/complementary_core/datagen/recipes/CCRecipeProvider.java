package net.elaguilamc623.complementary_core.datagen.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.SlabBlock;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class CCRecipeProvider extends RecipeProvider {

    public CCRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    protected void stonecutterRecipes(RecipeOutput output,
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
                    .save(output);
        }
    }

    protected void slabRecipe(RecipeOutput output, ItemLike base, ItemLike slab) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .pattern("###")
                .define('#', base)
                .unlockedBy(getHasName(base), has(base))
                .save(output);
    }

    protected void stairsRecipe(RecipeOutput output, ItemLike base, ItemLike stairs) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', base)
                .unlockedBy(getHasName(base), has(base))
                .save(output);
    }

    protected void wallRecipe(RecipeOutput output, ItemLike base, ItemLike wall) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
                .pattern("###")
                .pattern("###")
                .define('#', base)
                .unlockedBy(getHasName(base), has(base))
                .save(output);
    }

    protected void bricksRecipe(RecipeOutput output,
                                ItemLike input, ItemLike outputItem,
                                String unlockName) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, outputItem, 4)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy(unlockName, has(input))
                .save(output);
    }

    protected void chiseledRecipe(RecipeOutput output,
                                  ItemLike slab, ItemLike result,
                                  String unlockName) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .pattern("#")
                .pattern("#")
                .define('#', slab)
                .unlockedBy(unlockName, has(slab))
                .save(output);
    }

    protected void mossyBlockRecipe(RecipeOutput output,
                                    ItemLike base, ItemLike result,
                                    String unlockName) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .pattern("S")
                .pattern("V")
                .define('S', base)
                .define('V', Items.VINE)
                .unlockedBy(unlockName, has(Items.VINE))
                .save(output);
    }

    protected void buttonRecipe(RecipeOutput output, ItemLike result, ItemLike planks) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, result)
                .requires(planks)
                .unlockedBy("has_planks", has(planks))
                .save(output);
    }

    protected void pressurePlateRecipe(RecipeOutput output, ItemLike result, ItemLike planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, result)
                .define('#', planks)
                .pattern("##")
                .unlockedBy("has_planks", has(planks))
                .save(output);
    }

    protected void fenceAndGateRecipes(RecipeOutput output,
                                       ItemLike planks,
                                       ItemLike fence,
                                       ItemLike fenceGate) {

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, fence, 3)
                .define('#', planks)
                .define('X', net.minecraft.world.item.Items.STICK)
                .pattern("#X#")
                .pattern("#X#")
                .unlockedBy("has_planks", has(planks))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, fenceGate)
                .define('#', planks)
                .define('X', net.minecraft.world.item.Items.STICK)
                .pattern("X#X")
                .pattern("X#X")
                .unlockedBy("has_planks", has(planks))
                .save(output);
    }

    protected void doorRecipe(RecipeOutput output, ItemLike result, ItemLike planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, result, 3)
                .define('#', planks)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_planks", has(planks))
                .save(output);
    }

    protected void trapdoorRecipe(RecipeOutput output, ItemLike result, ItemLike planks) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, result, 2)
                .define('#', planks)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_planks", has(planks))
                .save(output);
    }

    protected void toolRecipe(RecipeOutput output,
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
                .save(output);
    }

    protected void helmetRecipe(RecipeOutput output, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .define('#', material)
                .pattern("###")
                .pattern("# #")
                .unlockedBy("has_material", has(material))
                .save(output);
    }

    protected void chestplateRecipe(RecipeOutput output, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .define('#', material)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_material", has(material))
                .save(output);
    }

    protected void leggingsRecipe(RecipeOutput output, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .define('#', material)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .unlockedBy("has_material", has(material))
                .save(output);
    }

    protected void bootsRecipe(RecipeOutput output, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .define('#', material)
                .pattern("# #")
                .pattern("# #")
                .unlockedBy("has_material", has(material))
                .save(output);
    }

    protected void torchRecipe(RecipeOutput output,
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
                .save(output);
    }

    protected void lanternRecipe(RecipeOutput output,
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
                .save(output);
    }

    protected void nuggetFromIngotRecipe(RecipeOutput output,
                                         ItemLike nugget,
                                         ItemLike ingot,
                                         String unlockName) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget, 9)
                .requires(ingot)
                .unlockedBy(unlockName, has(ingot))
                .save(output);
    }

    protected void oreCompactRecipe(RecipeOutput output,
                                    ItemLike item,
                                    ItemLike block,
                                    String name) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block)
                .define('#', item)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(item), has(item))
                .save(output);
    }

    protected void oreUncompactRecipe(RecipeOutput output,
                                      ItemLike block,
                                      ItemLike item,
                                      String name) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item, 9)
                .requires(block)
                .unlockedBy(getHasName(block), has(block))
                .save(output);
    }
}