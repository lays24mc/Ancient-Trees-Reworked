package com.lays24mc.ancient_trees.registry;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.loot.SaplingDropCondition;
import com.lays24mc.ancient_trees.world.ConfigRarityFilter;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCodecs {
    public static final DeferredRegister<MapCodec<? extends PlacementModifier>> PLACEMENT_MODIFIERS =
            DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, AncientTrees.MOD_ID);
    public static final DeferredRegister<MapCodec<? extends LootItemCondition>> LOOT_CONDITIONS =
            DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, AncientTrees.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends PlacementModifier>, MapCodec<ConfigRarityFilter>> CONFIG_RARITY =
            PLACEMENT_MODIFIERS.register("config_rarity", () -> ConfigRarityFilter.CODEC);
    public static final DeferredHolder<MapCodec<? extends LootItemCondition>, MapCodec<SaplingDropCondition>> SAPLING_DROP =
            LOOT_CONDITIONS.register("sapling_drop", () -> SaplingDropCondition.MAP_CODEC);

    private ModCodecs() {}

    public static void register(IEventBus modEventBus) {
        PLACEMENT_MODIFIERS.register(modEventBus);
        LOOT_CONDITIONS.register(modEventBus);
    }
}
