package com.lays24mc.ancient_trees.datagen;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.registry.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.BrewingProvider;
import net.minecraft.data.recipes.BrewingRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

public class ModBrewingProvider extends BrewingProvider {
    private final RecipeOutput recipeOutput;

    public ModBrewingProvider(RecipeOutput output) {
        super(output);
        this.recipeOutput = output;
    }

    @Override
    protected void addContainers() {
        addContainer(Items.POTION);
        addContainer(Items.SPLASH_POTION);
        addContainer(Items.LINGERING_POTION);
    }

    @Override
    protected void addContainerTransformations() {}

    @Override
    protected void buildMixes() {
        buildStartMix(ModBlocks.SAPLINGS.get(TreeSpecies.EWCALY).get().asItem(), Potions.SWIFTNESS);
        buildStartMix(ModBlocks.SAPLINGS.get(TreeSpecies.KIPARIS).get().asItem(), Potions.POISON);
    }

    @Override
    protected void save(BrewingRecipeBuilder builder) {
        var id = builder.defaultId();
        builder.save(recipeOutput, net.minecraft.resources.ResourceKey.create(Registries.RECIPE,
                Identifier.fromNamespaceAndPath(AncientTrees.MOD_ID, id.identifier().getPath())));
    }
}
