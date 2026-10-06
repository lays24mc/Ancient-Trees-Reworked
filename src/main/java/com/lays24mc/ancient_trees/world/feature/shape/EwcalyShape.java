package com.lays24mc.ancient_trees.world.feature.shape;

import com.lays24mc.ancient_trees.world.feature.TreePlacer;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;

public final class EwcalyShape {
    private static final int[][] DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}
    };

    private EwcalyShape() {}

    public static boolean generateNormal(TreePlacer tree, RandomSource random) {
        int height = random.nextInt(24) + 2;
        if (!tree.canGrow(height)) {
            return false;
        }
        trunkAndLayers(tree, random, height);

        for (int dy = height / 2; dy <= height - 3; dy++) {
            for (int[] d : DIRECTIONS) {
                if (random.nextInt(11) == 0) {
                    branches(tree, random, dy, d[0], d[1], height);
                }
            }
        }
        return true;
    }

    public static boolean generateLarge(TreePlacer tree, RandomSource random) {
        // Original: 8 to 31. Small large trees consisted only of thin branches, hence at least 14.
        int height = random.nextInt(18) + 14;
        if (!tree.canGrow(height)) {
            return false;
        }
        trunkAndLayers(tree, random, height);

        for (int dy = height / 2; dy <= height - 5; dy++) {
            for (int[] d : DIRECTIONS) {
                if (random.nextInt(9) == 0) {
                    longBranches(tree, dy, d[0], d[1], height);
                }
            }
        }
        return true;
    }

    private static void trunkAndLayers(TreePlacer tree, RandomSource random, int height) {
        for (int dy = 0; dy <= height; dy++) {
            tree.log(0, dy, 0);
        }

        int size = 1;
        for (int y1 = height / 2; y1 <= height; y1++) {
            if (random.nextInt(5) > 2 || y1 == height) {
                if (random.nextInt(20) < 1) {
                    size = 2;
                }
                if (random.nextInt(4) == 0 && y1 > 10 && y1 < 20) {
                    size = 2;
                }
                if (y1 >= 20) {
                    size = 3;
                }
                for (int dx = -size; dx <= size; dx++) {
                    for (int dz = -size; dz <= size; dz++) {
                        tree.leaves(dx, y1, dz);
                        if (size == 3 && (Math.abs(dx) == 3 && Math.abs(dz) == 2
                                || Math.abs(dx) == 2 && Math.abs(dz) == 3)) {
                            tree.clear(dx, y1, dz);
                        }
                        if (y1 == height && Math.abs(dx) < 3 && Math.abs(dz) < 3
                                && (Math.abs(dx) != 2 || Math.abs(dz) != 2)) {
                            if (size > 1) {
                                tree.leaves(dx, y1 + 1, dz);
                            }
                            if (size == 1 && (Math.abs(dx) != 1 || Math.abs(dz) != 1)) {
                                tree.leaves(dx, y1 + 1, dz);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void branches(TreePlacer tree, RandomSource random, int y, int dx, int dz, int height) {
        Trail trail = new Trail(0, y, 0);
        int x1 = 0;
        int y1 = y;
        int z1 = 0;
        for (int i = 0; i < 5; i++) {
            Direction.Axis axis = Direction.Axis.Y;
            if (dx == -1 && random.nextInt(3) == 0) {
                x1--;
                axis = Direction.Axis.X;
            }
            if (dx == 1 && random.nextInt(3) == 0) {
                x1++;
                axis = Direction.Axis.X;
            }
            if (dz == -1 && random.nextInt(3) == 0) {
                z1--;
                axis = Direction.Axis.Z;
            }
            if (dz == 1 && random.nextInt(3) == 0) {
                z1++;
                axis = Direction.Axis.Z;
            }
            trail.log(tree, x1, y1, z1, axis);
            if (i == 4 && height >= 18) {
                bigLeaves(tree, x1, y1, z1);
            }
            if (i == 4 && height < 18) {
                smallLeaves(tree, x1, y1, z1);
            }
            y1++;
        }
    }

    /** Branches of the large Ewcaly: eight straight steps, leaves after four and seven steps. */
    private static void longBranches(TreePlacer tree, int y, int dx, int dz, int height) {
        Trail trail = new Trail(0, y, 0);
        int x1 = 0;
        int y1 = y;
        int z1 = 0;
        for (int i = 0; i < 8; i++) {
            Direction.Axis axis = Direction.Axis.Y;
            if (dx == -1) {
                x1--;
                axis = Direction.Axis.X;
            }
            if (dx == 1) {
                x1++;
                axis = Direction.Axis.X;
            }
            if (dz == -1) {
                z1--;
                axis = Direction.Axis.Z;
            }
            if (dz == 1) {
                z1++;
                axis = Direction.Axis.Z;
            }
            trail.log(tree, x1, y1, z1, axis);
            if ((i == 4 || i == 7) && height >= 13) {
                bigLeaves(tree, x1, y1, z1);
            }
            if ((i == 4 || i == 7) && height < 13) {
                smallLeaves(tree, x1, y1, z1);
            }
            y1++;
        }
    }

    private static void bigLeaves(TreePlacer tree, int x, int y, int z) {
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                if ((Math.abs(dx) != 3 || Math.abs(dz) != 3) && (Math.abs(dx) != 2 || Math.abs(dz) != 3)
                        && (Math.abs(dx) != 3 || Math.abs(dz) != 2)) {
                    tree.leaves(x + dx, y, z + dz);
                }
                if (Math.abs(dx) < 3 && Math.abs(dz) < 3 && (Math.abs(dx) != 2 || Math.abs(dz) != 2)) {
                    tree.leaves(x + dx, y - 1, z + dz);
                    tree.leaves(x + dx, y + 1, z + dz);
                }
            }
        }
    }

    private static void smallLeaves(TreePlacer tree, int x, int y, int z) {
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                if (Math.abs(dx) != 2 || Math.abs(dz) != 2) {
                    tree.leaves(x + dx, y, z + dz);
                }
                if (Math.abs(dx) < 2 && Math.abs(dz) < 2 && (Math.abs(dx) != 1 || Math.abs(dz) != 1)) {
                    tree.leaves(x + dx, y + 1, z + dz);
                    tree.leaves(x + dx, y - 1, z + dz);
                }
            }
        }
    }
}
