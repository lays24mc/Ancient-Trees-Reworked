package com.lays24mc.ancient_trees.world.feature.shape;

import com.lays24mc.ancient_trees.world.feature.TreePlacer;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;

public final class CedrumShape {
    private CedrumShape() {}

    public static boolean generateNormal(TreePlacer tree, RandomSource random) {
        int height = random.nextInt(10) + 9;
        if (!tree.canGrow(height)) {
            return false;
        }

        for (int level = 0; level <= height; level++) {
            tree.log(0, level, 0);
            if (level == height) {
                leafTop(tree, 0, level, 0);
            }
            if (level > 5 && level < height) {
                if (level == height - 1) {
                    leafGen(tree, 2, 0, level, 0);
                }
                if (level == height - 4 || level == height - 7) {
                    crossBranches(tree, level);
                    leafGen(tree, level == height - 4 ? 3 : 4, 0, level, 0);
                }
                if (level == height - 10 || level == height - 13) {
                    leafGen(tree, level == height - 10 ? 3 : 2, 0, level, 0);
                }
            }
        }
        return true;
    }

    public static boolean generateLarge(TreePlacer tree, RandomSource random) {
        int height = random.nextInt(12) + 12;
        if (!tree.canGrow(height)) {
            return false;
        }

        for (int level = height; level >= 0; level--) {
            tree.log(0, level, 0);
            if (level > 5 && level < height) {
                if (level == height - 1) {
                    leafGen(tree, 2, 0, level, 0);
                }
                if (level == height - 4 || level == height - 7 || level == height - 10 || level == height - 13) {
                    crossBranches(tree, level);
                    int size = level == height - 4 ? 3
                            : level == height - 7 ? 4 : level == height - 10 ? 5 : random.nextInt(3) + 2;
                    leafGen(tree, size, 0, level, 0);
                }
            }
            if (level == height) {
                leafTop(tree, 0, level, 0);
            }
        }
        return true;
    }

    private static void crossBranches(TreePlacer tree, int level) {
        for (int next = 1; next < 3; next++) {
            tree.log(next, level - 2, 0, Direction.Axis.X);
            tree.log(-next, level - 2, 0, Direction.Axis.X);
            tree.log(0, level - 2, next, Direction.Axis.Z);
            tree.log(0, level - 2, -next, Direction.Axis.Z);
        }
    }

    private static void leafTop(TreePlacer tree, int x, int y, int z) {
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                if (Math.abs(dx) + Math.abs(dz) < 3) {
                    tree.leaves(x + dx, y, z + dz);
                }
                if (Math.abs(dx) + Math.abs(dz) < 2) {
                    tree.leaves(x + dx, y + 1, z + dz);
                }
                if (Math.abs(dx) == 0 && Math.abs(dz) == 0) {
                    tree.leaves(x + dx, y + 2, z + dz);
                }
            }
        }
    }

    private static void leafGen(TreePlacer tree, int size, int x, int y, int z) {
        int radius;
        int limiter1;
        int limiter2;
        int limiter3;
        switch (size) {
            case 3 -> {
                radius = 4;
                limiter1 = 3;
                limiter2 = 5;
                limiter3 = 7;
            }
            case 4 -> {
                radius = 5;
                limiter1 = 5;
                limiter2 = 7;
                limiter3 = 8;
            }
            case 5 -> {
                radius = 6;
                limiter1 = 7;
                limiter2 = 8;
                limiter3 = 9;
            }
            default -> {
                radius = 3;
                limiter1 = 2;
                limiter2 = 3;
                limiter3 = 5;
            }
        }
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (Math.abs(dx) + Math.abs(dz) < limiter1) {
                    tree.leaves(x + dx, y, z + dz);
                }
                if (Math.abs(dx) + Math.abs(dz) < limiter2) {
                    tree.leaves(x + dx, y - 1, z + dz);
                }
                if (Math.abs(dx) + Math.abs(dz) < limiter3) {
                    tree.leaves(x + dx, y - 2, z + dz);
                }
            }
        }
    }
}
