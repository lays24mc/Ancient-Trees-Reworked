package com.lays24mc.ancient_trees.world.feature.shape;

import com.lays24mc.ancient_trees.world.feature.TreePlacer;
import net.minecraft.util.RandomSource;

public final class ClassicShape {
    private ClassicShape() {}

    public static boolean generate(TreePlacer tree, RandomSource random) {
        int height = 4 + random.nextInt(3) + random.nextInt(7);

        if (tree.hasPoorGround(height) || !hasRoomToGrow(tree, height)) {
            return false;
        }

        placeCanopy(tree, random, height);
        for (int dy = 0; dy < height; dy++) {
            tree.log(0, dy, 0);
        }
        return true;
    }

    private static boolean hasRoomToGrow(TreePlacer tree, int height) {
        for (int dy = 0; dy <= 1 + height; dy++) {
            int radius = dy >= 1 + height - 2 ? 2 : dy == 0 ? 0 : 1;
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (!tree.isInHeightRange(dy) || !tree.isReplaceable(dx, dy, dz)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void placeCanopy(TreePlacer tree, RandomSource random, int height) {
        for (int dy = height - 3; dy <= height; dy++) {
            int distanceToTopOfTrunk = dy - height;
            int radius = 1 - distanceToTopOfTrunk / 2;

            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (Math.abs(dx) != radius || Math.abs(dz) != radius
                            || random.nextInt(2) != 0 && distanceToTopOfTrunk != 0) {
                        tree.leaves(dx, dy, dz);
                    }
                }
            }
        }
    }
}
