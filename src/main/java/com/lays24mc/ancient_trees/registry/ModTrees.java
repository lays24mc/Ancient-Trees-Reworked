package com.lays24mc.ancient_trees.registry;

import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.world.feature.TreeShape;
import java.util.List;
import org.jspecify.annotations.Nullable;

public final class ModTrees {
    public record Variant(String name, @Nullable TreeShape shape, int weight) {}

    private ModTrees() {}

    public static List<Variant> variants(TreeSpecies species) {
        String id = species.id();
        return switch (species) {
            case ACEMUS, CERASU, PORFFOR -> List.of(
                    new Variant(id, TreeShape.CLASSIC, 9),
                    new Variant(id + "_large", null, 1));
            case CEDRUM -> List.of(
                    new Variant(id, TreeShape.CEDRUM, 9),
                    new Variant(id + "_large", TreeShape.CEDRUM_LARGE, 1));
            case DELNAS -> List.of(new Variant(id, TreeShape.DELNAS, 1));
            case EWCALY -> List.of(
                    new Variant(id, TreeShape.EWCALY, 5),
                    new Variant(id + "_large", TreeShape.EWCALY_LARGE, 2));
            case HEKUR -> List.of(
                    new Variant(id, TreeShape.HEKUR, 9),
                    new Variant(id + "_large", TreeShape.HEKUR_LARGE, 1));
            case KIPARIS -> List.of(new Variant(id, TreeShape.KIPARIS, 1));
            case KULIST -> List.of(
                    new Variant(id, TreeShape.KULIST, 9),
                    new Variant(id + "_large", TreeShape.KULIST_LARGE, 1));
            case LATA -> List.of(new Variant(id, TreeShape.LATA, 1));
            case NUCIS -> List.of(new Variant(id, TreeShape.NUCIS, 1));
            case SALYX -> List.of(new Variant(id, TreeShape.SALYX, 1));
            case TUOPA -> List.of(new Variant(id, TreeShape.TUOPA, 1));
        };
    }
}
