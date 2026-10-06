package com.lays24mc.ancient_trees.world.feature.shape;

import com.lays24mc.ancient_trees.world.feature.TreePlacer;
import net.minecraft.util.RandomSource;

public final class KiparisShape {
    private KiparisShape() {}

    public static boolean generate(TreePlacer tree, RandomSource random) {
        int size = 1 + (random.nextInt(7) < 2 ? 1 : 0) + (random.nextInt(7) < 2 ? 1 : 0)
                + (random.nextInt(2) == 0 ? 1 : 0);
        int height = 4 * size + 1;

        if (!tree.canGrow(height)) {
            return false;
        }

        for (int dy = 0; dy <= height; dy++) {
            if (dy != height) {
                tree.log(0, dy, 0);
            }

            if (dy >= 1) {
                switch (size) {
                    case 1 -> genSmallLeaves(tree, dy);
                    case 2 -> genMediumLeaves(tree, dy);
                    case 3 -> genLargeLeaves(tree, dy);
                    default -> genExtraLargeLeaves(tree, dy);
                }
            }

            if (dy == height) {
                tree.leaves(0, dy + 1, 0);
            }
            if (dy == height && (size == 4 || size == 3)) {
                tree.leaves(0, dy + 2, 0);
            }
        }
        return true;
    }

    private static void genExtraLargeLeaves(TreePlacer tree, int dy) {
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1 && (Math.abs(dx) != 1 || Math.abs(dz) != 1)) {
                    tree.leaves(dx, dy, dz);
                }
                if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1 && dy <= 14 && dy >= 2) {
                    tree.leaves(dx, dy, dz);
                }
                if (Math.abs(dx) <= 2 && Math.abs(dz) <= 2 && (Math.abs(dx) != 2 || Math.abs(dz) != 2) && dy == 12
                        || dy == 11 || dy == 3) {
                    tree.leaves(dx, dy, dz);
                }
                if ((Math.abs(dx) != 3 || Math.abs(dz) != 3) && (Math.abs(dx) != 3 || Math.abs(dz) != 2)
                        && (Math.abs(dx) != 2 || Math.abs(dz) != 3) && dy <= 10 && dy >= 4) {
                    tree.leaves(dx, dy, dz);
                }
            }
        }
    }

    private static void genLargeLeaves(TreePlacer tree, int dy) {
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1 && (Math.abs(dx) != 1 || Math.abs(dz) != 1)) {
                    tree.leaves(dx, dy, dz);
                }
                if ((Math.abs(dx) != 2 || Math.abs(dz) != 2) && (Math.abs(dx) != 2 || Math.abs(dz) != 1)
                        && (Math.abs(dx) != 1 || Math.abs(dz) != 2) && dy <= 10 && dy >= 2) {
                    tree.leaves(dx, dy, dz);
                }
            }
        }
    }

    private static void genMediumLeaves(TreePlacer tree, int dy) {
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1 && (Math.abs(dx) != 1 || Math.abs(dz) != 1)) {
                    tree.leaves(dx, dy, dz);
                }
                if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1 && dy == 7) {
                    tree.leaves(dx, 7, dz);
                }
                if ((Math.abs(dx) != 2 || Math.abs(dz) != 2) && (Math.abs(dx) != 2 || Math.abs(dz) != 1)
                        && (Math.abs(dx) != 1 || Math.abs(dz) != 2) && dy <= 6 && dy >= 2) {
                    tree.leaves(dx, dy, dz);
                }
            }
        }
    }

    private static void genSmallLeaves(TreePlacer tree, int dy) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (Math.abs(dx) != 1 || Math.abs(dz) != 1) {
                    tree.leaves(dx, dy, dz);
                }
            }
        }
    }
}
