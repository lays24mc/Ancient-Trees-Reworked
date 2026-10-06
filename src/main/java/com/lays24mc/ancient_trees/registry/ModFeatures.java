package com.lays24mc.ancient_trees.registry;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.world.feature.AncientTreeFeature;
import com.lays24mc.ancient_trees.world.feature.RandomTreeFeature;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModFeatures {
    public static final DeferredRegister<MapCodec<? extends Feature>> FEATURE_TYPES =
            DeferredRegister.create(Registries.FEATURE_TYPE, AncientTrees.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends Feature>, MapCodec<AncientTreeFeature>> TREE =
            FEATURE_TYPES.register("tree", () -> AncientTreeFeature.CODEC);

    public static final DeferredHolder<MapCodec<? extends Feature>, MapCodec<RandomTreeFeature>> RANDOM_TREE =
            FEATURE_TYPES.register("random_tree", () -> RandomTreeFeature.CODEC);

    private ModFeatures() {}

    public static ResourceKey<Feature> key(String name) {
        return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(AncientTrees.MOD_ID, name));
    }

    public static ResourceKey<Feature> naturalKey(com.lays24mc.ancient_trees.TreeSpecies species) {
        return key("natural_" + species.id());
    }

    public static void register(IEventBus modEventBus) {
        FEATURE_TYPES.register(modEventBus);
    }
}
