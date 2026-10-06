package com.lays24mc.ancient_trees.registry;

import com.lays24mc.ancient_trees.TreeSpecies;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.Feature;

public final class ModTreeGrowers {
    private static final Map<TreeSpecies, TreeGrower> GROWERS = new EnumMap<>(TreeSpecies.class);

    static {
        for (TreeSpecies species : TreeSpecies.values()) {
            List<Weighted<ResourceKey<Feature>>> trees = new ArrayList<>();
            for (ModTrees.Variant variant : ModTrees.variants(species)) {
                trees.add(new Weighted<>(ModFeatures.key(variant.name()), variant.weight()));
            }
            GROWERS.put(species, new TreeGrower("ancient_trees_" + species.id(), WeightedList.of(trees),
                    WeightedList.of(), WeightedList.of(), ModFeatures.key(species.id())));
        }
    }

    private ModTreeGrowers() {}

    public static TreeGrower get(TreeSpecies species) {
        return GROWERS.get(species);
    }
}
