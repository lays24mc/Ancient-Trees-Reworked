package com.lays24mc.ancient_trees.world.feature;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public record AncientTreeFeature(TreeShape shape, BlockState log, BlockState leaves) implements Feature {
    public static final MapCodec<AncientTreeFeature> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            TreeShape.CODEC.fieldOf("shape").forGetter(AncientTreeFeature::shape),
            BlockState.CODEC.fieldOf("log").forGetter(AncientTreeFeature::log),
            BlockState.CODEC.fieldOf("leaves").forGetter(AncientTreeFeature::leaves)
    ).apply(i, AncientTreeFeature::new));

    @Override
    public MapCodec<AncientTreeFeature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        BlockPos base = origin;
        if (shape.descendsWater()) {
            while (level.getFluidState(base.below()).is(FluidTags.WATER)) {
                base = base.below();
            }
        }
        TreeBuilder tree = new TreeBuilder(level, base, log, leaves, shape.replacesWater());
        if (!shape.generator().generate(tree, random) || tree.isEmpty() || tree.overflowed()) {
            return false;
        }
        tree.commit();
        return true;
    }
}
