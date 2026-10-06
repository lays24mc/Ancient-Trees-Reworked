package com.lays24mc.ancient_trees.datagen;

import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.registry.ModBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Transformable;

public class ModDataMapProvider extends DataMapProvider {
    public ModDataMapProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        var transformables = builder(NeoForgeDataMaps.TRANSFORMABLES);
        for (TreeSpecies species : TreeSpecies.values()) {
            var log = ModBlocks.LOGS.get(species);
            var wood = ModBlocks.WOODS.get(species);
            transformables.add(log.getKey(), Transformable.stripping(log.get(), ModBlocks.STRIPPED_LOGS.get(species).get()), false);
            transformables.add(wood.getKey(), Transformable.stripping(wood.get(), ModBlocks.STRIPPED_WOODS.get(species).get()), false);
        }
    }
}
