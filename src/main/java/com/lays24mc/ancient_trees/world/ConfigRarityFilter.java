package com.lays24mc.ancient_trees.world;

import com.lays24mc.ancient_trees.config.AncientTreesConfig;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;

public record ConfigRarityFilter(int share) implements PlacementFilter {
    public static final MapCodec<ConfigRarityFilter> CODEC = ExtraCodecs.POSITIVE_INT
            .optionalFieldOf("share", 1)
            .xmap(ConfigRarityFilter::new, ConfigRarityFilter::share);

    @Override
    public boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos origin) {
        return AncientTreesConfig.naturalTrees()
                && random.nextFloat() < 1.0F / ((float) AncientTreesConfig.chunksPerTree() * share);
    }

    @Override
    public MapCodec<ConfigRarityFilter> codec() {
        return CODEC;
    }
}
