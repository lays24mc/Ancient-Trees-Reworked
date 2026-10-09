package com.lays24mc.ancient_trees.config;

import java.util.List;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class AncientTreesConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue NATURAL_TREES = BUILDER
            .comment("Whether the ancient trees grow naturally in newly generated overworld chunks.")
            .define("worldgen.naturalTrees", true);

    public static final ModConfigSpec.IntValue CHUNKS_PER_TREE = BUILDER
            .comment("On average one natural tree is attempted per this many chunks. Higher means rarer.")
            .defineInRange("worldgen.chunksPerTree", 4, 1, 10000);

    public static final ModConfigSpec.DoubleValue SAPLING_DROP_MULTIPLIER = BUILDER
            .comment("Multiplier for the chance that leaves drop saplings. 1.0 is the vanilla chance (5 %), 0 disables drops.")
            .defineInRange("saplings.leafDropMultiplier", 1.0, 0.0, 20.0);

    public static final ModConfigSpec.DoubleValue PARCEL_CHEST_CHANCE = BUILDER
            .comment("Chance that a configured chest contains 1-2 Ancient Parcels. 0 disables.",
                    "Takes effect after a world reload.")
            .defineInRange("parcels.chestChance", 0.1, 0.0, 1.0);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> PARCEL_CHEST_TABLES = BUILDER
            .comment("Loot tables of chests that can contain Ancient Parcels. Any chest loot table id works, also from other mods.")
            .defineListAllowEmpty("parcels.chestTables", List.of(
                    "minecraft:chests/simple_dungeon",
                    "minecraft:chests/abandoned_mineshaft",
                    "minecraft:chests/stronghold_corridor",
                    "minecraft:chests/stronghold_crossing",
                    "minecraft:chests/stronghold_library",
                    "minecraft:chests/desert_pyramid",
                    "minecraft:chests/jungle_temple"),
                    () -> "minecraft:chests/simple_dungeon",
                    value -> value instanceof String);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private AncientTreesConfig() {}

    public static boolean naturalTrees() {
        return !SPEC.isLoaded() || NATURAL_TREES.get();
    }

    public static int chunksPerTree() {
        return SPEC.isLoaded() ? CHUNKS_PER_TREE.get() : CHUNKS_PER_TREE.getDefault();
    }

    public static float saplingDropMultiplier() {
        return SPEC.isLoaded() ? SAPLING_DROP_MULTIPLIER.get().floatValue() : 1.0F;
    }

    public static float parcelChestChance() {
        return SPEC.isLoaded() ? PARCEL_CHEST_CHANCE.get().floatValue() : PARCEL_CHEST_CHANCE.getDefault().floatValue();
    }

    public static boolean isParcelChestTable(String table) {
        List<? extends String> tables = SPEC.isLoaded() ? PARCEL_CHEST_TABLES.get() : PARCEL_CHEST_TABLES.getDefault();
        return tables.contains(table);
    }
}
