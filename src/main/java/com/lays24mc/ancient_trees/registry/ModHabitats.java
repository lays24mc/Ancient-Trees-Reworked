package com.lays24mc.ancient_trees.registry;

import com.lays24mc.ancient_trees.TreeSpecies;
import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;
import org.jspecify.annotations.Nullable;


public final class ModHabitats {
    public record Habitat(@Nullable TagKey<Biome> tag, @Nullable ResourceKey<Biome> biome, int share) {
        public HolderSet<Biome> biomes(HolderGetter<Biome> lookup) {
            return tag != null ? lookup.getOrThrow(tag) : HolderSet.direct(lookup.getOrThrow(biome));
        }
    }

    private ModHabitats() {}

    private static Habitat tag(TagKey<Biome> tag, int share) {
        return new Habitat(tag, null, share);
    }

    private static Habitat biome(ResourceKey<Biome> biome, int share) {
        return new Habitat(null, biome, share);
    }

    public static List<Habitat> of(TreeSpecies species) {
        return switch (species) {
            case ACEMUS, DELNAS, KULIST, LATA, NUCIS, PORFFOR -> List.of(
                    tag(BiomeTags.IS_FOREST, 7), tag(Tags.Biomes.IS_PLAINS, 7));
            case CERASU -> List.of(biome(Biomes.CHERRY_GROVE, 1), tag(BiomeTags.IS_FOREST, 7));
            case CEDRUM, TUOPA -> List.of(tag(BiomeTags.IS_TAIGA, 2), tag(BiomeTags.IS_MOUNTAIN, 2));
            case KIPARIS -> List.of(tag(Tags.Biomes.IS_SWAMP, 3), tag(BiomeTags.IS_JUNGLE, 1));
            case SALYX -> List.of(tag(Tags.Biomes.IS_SWAMP, 3), tag(BiomeTags.IS_RIVER, 1));
            case HEKUR -> List.of(tag(Tags.Biomes.IS_SWAMP, 3));
            case EWCALY -> List.of(tag(BiomeTags.IS_SAVANNA, 1), tag(BiomeTags.IS_BADLANDS, 1));
        };
    }

    public static String name(TreeSpecies species, int index) {
        return "natural_" + species.id() + "_" + index;
    }
}
