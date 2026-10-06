package com.lays24mc.ancient_trees.world.feature.shape;

import com.lays24mc.ancient_trees.world.feature.TreePlacer;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;

public final class SalyxShape {
    private static final int[][] DIRECTIONS = {
            {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}
    };

    private static final int[][] CROSS = {{0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private SalyxShape() {}

    public static boolean generate(TreePlacer tree, RandomSource random) {
        int height = 8 + random.nextInt(4);
        if (!tree.canGrow(height)) {
            return false;
        }

        for (int dy = 0; dy <= height; dy++) {
            tree.log(0, dy, 0);
        }
        // Wurzelansatz
        tree.log(1, 0, 0, Direction.Axis.X);
        tree.log(-1, 0, 0, Direction.Axis.X);
        tree.log(0, 0, 1, Direction.Axis.Z);
        tree.log(0, 0, -1, Direction.Axis.Z);

        dome(tree, random, height);

        int branches = 5 + random.nextInt(2);
        int start = random.nextInt(DIRECTIONS.length);
        for (int i = 0; i < branches; i++) {
            int[] d = DIRECTIONS[(start + i * DIRECTIONS.length / branches) % DIRECTIONS.length];
            branch(tree, random, d[0], d[1], height - 3 + random.nextInt(2));
        }
        return true;
    }

    /** Round dome around the trunk top with short curtains at the edge. */
    private static void dome(TreePlacer tree, RandomSource random, int height) {
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                int distance = Math.abs(dx) + Math.abs(dz);
                if (distance <= 3) {
                    tree.leaves(dx, height, dz);
                }
                if (distance <= 2) {
                    tree.leaves(dx, height + 1, dz);
                }
                if (distance <= 1) {
                    tree.leaves(dx, height + 2, dz);
                }
                if (distance == 3 && random.nextInt(4) > 0) {
                    int strand = 2 + random.nextInt(2);
                    for (int k = 1; k <= strand; k++) {
                        tree.leaves(dx, height - k, dz);
                    }
                }
            }
        }
    }

    private static void branch(TreePlacer tree, RandomSource random, int dx, int dz, int startY) {
        boolean diagonal = dx != 0 && dz != 0;
        int length = (diagonal ? 4 : 5) + random.nextInt(3);
        Direction.Axis axis = dx != 0 ? Direction.Axis.X : Direction.Axis.Z;
        Trail trail = new Trail(0, startY, 0);
        int x = 0;
        int y = startY;
        int z = 0;
        for (int i = 1; i <= length; i++) {
            x += dx;
            z += dz;
            // Arc: the first three steps rise, the last two droop
            y += i <= 3 ? 1 : i > length - 2 ? -1 : 0;
            trail.log(tree, x, y, z, axis);
            if (i >= 3) {
                curtain(tree, random, x, y, z);
            }
        }
    }

    private static void curtain(TreePlacer tree, RandomSource random, int x, int y, int z) {
        for (int[] c : CROSS) {
            tree.leaves(x + c[0], y, z + c[1]);
        }
        tree.leaves(x, y + 1, z);
        // Extra leaves on the outside so the curtain looks dense
        for (int[] c : CROSS) {
            if (random.nextBoolean()) {
                tree.leaves(x + 2 * c[0], y, z + 2 * c[1]);
            }
        }

        for (int[] c : CROSS) {
            if (random.nextInt(4) > 0) {
                int strand = 2 + random.nextInt(4);
                for (int k = 1; k <= strand; k++) {
                    tree.leaves(x + c[0], y - k, z + c[1]);
                }
            }
        }
    }
}
