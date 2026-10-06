package com.lays24mc.ancient_trees.datagen;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.registry.ModFeatures;
import com.lays24mc.ancient_trees.registry.ModHabitats;
import com.lays24mc.ancient_trees.world.ConfigRarityFilter;
import java.util.List;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModWorldgenProvider {
    private ModWorldgenProvider() {}

    private static ResourceKey<PlacedFeature> placedKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(AncientTrees.MOD_ID, name));
    }

    private static ResourceKey<BiomeModifier> modifierKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(AncientTrees.MOD_ID, name));
    }

    public static void placedFeatures(BootstrapContext<PlacedFeature> context) {
        for (TreeSpecies species : TreeSpecies.values()) {
            var feature = context.lookup(Registries.FEATURE).getOrThrow(ModFeatures.naturalKey(species));
            List<ModHabitats.Habitat> habitats = ModHabitats.of(species);
            for (int i = 0; i < habitats.size(); i++) {
                context.register(placedKey(ModHabitats.name(species, i)), new PlacedFeature(feature,
                        VegetationPlacements.treePlacement(new ConfigRarityFilter(habitats.get(i).share()))));
            }
        }
    }

    public static void biomeModifiers(BootstrapContext<BiomeModifier> context) {
        for (TreeSpecies species : TreeSpecies.values()) {
            List<ModHabitats.Habitat> habitats = ModHabitats.of(species);
            for (int i = 0; i < habitats.size(); i++) {
                String name = ModHabitats.name(species, i);
                context.register(modifierKey(name), new BiomeModifiers.AddFeaturesBiomeModifier(
                        habitats.get(i).biomes(context.lookup(Registries.BIOME)),
                        HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(placedKey(name))),
                        GenerationStep.Decoration.VEGETAL_DECORATION));
            }
        }
    }
}
