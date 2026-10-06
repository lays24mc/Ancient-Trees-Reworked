package com.lays24mc.ancient_trees.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public record RandomTreeFeature(List<Entry> trees) implements Feature {
    public record Entry(Holder<Feature> feature, int weight) {
        public static final Codec<Entry> CODEC = RecordCodecBuilder.create(i -> i.group(
                Feature.CODEC.fieldOf("feature").forGetter(Entry::feature),
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("weight").forGetter(Entry::weight)
        ).apply(i, Entry::new));
    }

    public static final MapCodec<RandomTreeFeature> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Entry.CODEC.listOf().fieldOf("trees").forGetter(RandomTreeFeature::trees)
    ).apply(i, RandomTreeFeature::new));

    @Override
    public MapCodec<RandomTreeFeature> codec() {
        return CODEC;
    }

    @Override
    public Stream<Holder<Feature>> getSubFeatures() {
        return trees.stream().flatMap(entry -> Stream.concat(Stream.of(entry.feature()), entry.feature().value().getSubFeatures()));
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        int total = 0;
        for (Entry entry : trees) {
            total += entry.weight();
        }
        int pick = random.nextInt(total);
        for (Entry entry : trees) {
            pick -= entry.weight();
            if (pick < 0) {
                return entry.feature().value().place(level, chunkGenerator, random, origin);
            }
        }
        return false;
    }
}
