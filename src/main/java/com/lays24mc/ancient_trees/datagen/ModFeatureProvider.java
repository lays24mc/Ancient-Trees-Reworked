package com.lays24mc.ancient_trees.datagen;

import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.registry.ModBlocks;
import com.lays24mc.ancient_trees.registry.ModFeatures;
import com.lays24mc.ancient_trees.registry.ModTrees;
import com.lays24mc.ancient_trees.world.feature.AncientTreeFeature;
import com.lays24mc.ancient_trees.world.feature.RandomTreeFeature;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BlockStateProviders;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;

public final class ModFeatureProvider {
    private ModFeatureProvider() {}

    public static void bootstrap(BootstrapContext<Feature> context) {
        var belowTrunk = context.lookup(Registries.BLOCK_STATE_PROVIDER).getOrThrow(BlockStateProviders.SOIL_BENEATH_TREE);

        for (TreeSpecies species : TreeSpecies.values()) {
            var log = ModBlocks.LOGS.get(species).get();
            var leaves = ModBlocks.LEAVES.get(species).get();

            List<ModTrees.Variant> variants = ModTrees.variants(species);
            List<RandomTreeFeature.Entry> natural = new ArrayList<>();
            for (ModTrees.Variant variant : variants) {
                Holder<Feature> holder;
                if (variant.shape() != null) {
                    holder = context.register(ModFeatures.key(variant.name()),
                            new AncientTreeFeature(variant.shape(), log.defaultBlockState(), leaves.defaultBlockState()));
                } else {
                    // Large tree: 1.7 used the oak's big tree algorithm here, in 26.x the fancy tree.
                    holder = context.register(ModFeatures.key(variant.name()), new TreeFeature.Builder(
                            BlockStateProvider.of(log),
                            new FancyTrunkPlacer(3, 11, 0),
                            BlockStateProvider.of(leaves),
                            new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)),
                            belowTrunk)
                            .ignoreVines()
                            .build());
                }
                natural.add(new RandomTreeFeature.Entry(holder, variant.weight()));
            }
            // Natural trees pick the variant by weight, like the sapling
            context.register(ModFeatures.naturalKey(species), new RandomTreeFeature(natural));
        }
    }
}
