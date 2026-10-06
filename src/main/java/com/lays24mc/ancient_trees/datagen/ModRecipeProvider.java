package com.lays24mc.ancient_trees.datagen;

import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.registry.ModBlocks;
import com.lays24mc.ancient_trees.registry.ModItems;
import com.lays24mc.ancient_trees.registry.ModTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class ModRecipeProvider extends net.minecraft.data.recipes.RecipeProvider {
    public ModRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    protected void buildRecipes() {
        for (TreeSpecies species : TreeSpecies.values()) {
            var planks = ModBlocks.PLANKS.get(species).get();

            planksFromLog(planks, ModTags.logsItem(species), 4);
            woodFromLogs(ModBlocks.WOODS.get(species).get(), ModBlocks.LOGS.get(species).get());
            woodFromLogs(ModBlocks.STRIPPED_WOODS.get(species).get(), ModBlocks.STRIPPED_LOGS.get(species).get());
            stairBuilder(ModBlocks.STAIRS.get(species).get(), Ingredient.of(planks))
                    .group("wooden_stairs")
                    .unlockedBy(getHasName(planks), has(planks))
                    .save(output);
            slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SLABS.get(species).get(), planks);

            Ingredient ingredient = Ingredient.of(planks);
            fenceBuilder(ModBlocks.FENCES.get(species).get(), ingredient)
                    .group("wooden_fence").unlockedBy(getHasName(planks), has(planks)).save(output);
            fenceGateBuilder(ModBlocks.FENCE_GATES.get(species).get(), ingredient)
                    .group("wooden_fence_gate").unlockedBy(getHasName(planks), has(planks)).save(output);
            doorBuilder(ModBlocks.DOORS.get(species).get(), ingredient)
                    .group("wooden_door").unlockedBy(getHasName(planks), has(planks)).save(output);
            trapdoorBuilder(ModBlocks.TRAPDOORS.get(species).get(), ingredient)
                    .group("wooden_trapdoor").unlockedBy(getHasName(planks), has(planks)).save(output);
            pressurePlateBuilder(RecipeCategory.REDSTONE, ModBlocks.PRESSURE_PLATES.get(species).get(), ingredient)
                    .group("wooden_pressure_plate").unlockedBy(getHasName(planks), has(planks)).save(output);
            buttonBuilder(ModBlocks.BUTTONS.get(species).get(), ingredient)
                    .group("wooden_button").unlockedBy(getHasName(planks), has(planks)).save(output);
            signBuilder(ModBlocks.SIGNS.get(species).get(), ingredient)
                    .unlockedBy(getHasName(planks), has(planks)).save(output);
            var strippedLog = ModBlocks.STRIPPED_LOGS.get(species).get();
            hangingSignBuilder(ModBlocks.HANGING_SIGNS.get(species).get(), Ingredient.of(strippedLog))
                    .unlockedBy(getHasName(strippedLog), has(strippedLog)).save(output);
        }

        for (TreeSpecies species : TreeSpecies.values()) {
            woodenBoat(ModItems.BOATS.get(species).get(), ModBlocks.PLANKS.get(species).get());
            chestBoat(ModItems.CHEST_BOATS.get(species).get(), ModItems.BOATS.get(species).get());
        }

        new ModBrewingProvider(output).buildRecipes();
    }
}
