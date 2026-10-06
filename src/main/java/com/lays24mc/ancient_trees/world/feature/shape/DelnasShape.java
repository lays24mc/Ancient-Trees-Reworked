package com.lays24mc.ancient_trees.world.feature.shape;

import com.lays24mc.ancient_trees.world.feature.TreePlacer;
import net.minecraft.util.RandomSource;

public final class DelnasShape {
    private DelnasShape() {}

    public static boolean generate(TreePlacer tree, RandomSource random) {
        int height = random.nextInt(5) + 6;
        if (!tree.canGrow(height)) {
            return false;
        }

        if (random.nextInt(10) > 0) {
            for (int dy = 0; dy <= height; dy++) {
                tree.log(0, dy, 0);
                if (dy == height) {
                    leaves(tree, 0, dy, 0);
                }
            }
        } else {
            switch (random.nextInt(4)) {
                case 0 -> growDirect(tree, random, 1, 0, height);
                case 1 -> growDirect(tree, random, 0, 1, height);
                case 2 -> growDirect(tree, random, -1, 0, height);
                default -> growDirect(tree, random, 0, -1, height);
            }
        }
        return true;
    }

    private static void leaves(TreePlacer tree, int x, int y, int z) {
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                if (Math.abs(dx) + Math.abs(dz) <= 3
                        && !(Math.abs(dx) + Math.abs(dz) == 3 && Math.abs(dx) != 0 && Math.abs(dz) != 0)) {
                    tree.leaves(x + dx, y, z + dz);
                }
                if (Math.abs(dx) < 2 && Math.abs(dz) < 2 && (Math.abs(dx) != 1 || Math.abs(dz) != 1)) {
                    tree.leaves(x + dx, y + 1, z + dz);
                }
            }
        }
    }

    private static void growDirect(TreePlacer tree, RandomSource random, int dx, int dz, int height) {
        int x1 = 0;
        int z1 = 0;
        tree.log(x1, 0, z1);
        if (dx == 1) {
            tree.log(x1 - 1, 0, z1);
        }
        if (dx == -1) {
            tree.log(x1 + 1, 0, z1);
        }
        if (dz == 1) {
            tree.log(x1, 0, z1 - 1);
        }
        if (dz == -1) {
            tree.log(x1, 0, z1 + 1);
        }

        Trail trail = new Trail(x1, 0, z1);
        int addlRandomLengthX = 0;
        int addlRandomLengthZ = 0;
        for (int level = 0; level <= height; level++) {
            if (dx == 1 && random.nextInt(2 + addlRandomLengthX) == 0) {
                x1++;
            }
            if (dx == -1 && random.nextInt(2 + addlRandomLengthX) == 0) {
                x1--;
            }
            if (dz == 1 && random.nextInt(2 + addlRandomLengthZ) == 0) {
                z1++;
            }
            if (dz == -1 && random.nextInt(2 + addlRandomLengthZ) == 0) {
                z1--;
            }
            addlRandomLengthX++;
            addlRandomLengthZ++;
            trail.log(tree, x1, level, z1, net.minecraft.core.Direction.Axis.Y);
        }
        leaves(tree, x1, height, z1);
    }
}
