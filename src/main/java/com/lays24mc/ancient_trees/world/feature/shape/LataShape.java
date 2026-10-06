package com.lays24mc.ancient_trees.world.feature.shape;

import com.lays24mc.ancient_trees.world.feature.TreePlacer;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;

public final class LataShape {
    private static final int[][] DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}
    };

    private LataShape() {}

    public static boolean generate(TreePlacer tree, RandomSource random) {
        int height = random.nextInt(15) + 6;
        if (!tree.canGrow(height)) {
            return false;
        }

        for (int level = 0; level <= height; level++) {
            if (level == height) {
                Crowns.round(tree, 0, level, 0);
            } else {
                tree.log(0, level, 0);
            }

            if (level > 3 && level < height) {
                int branchRarity = height / level + 1;
                for (int[] d : DIRECTIONS) {
                    if (random.nextInt(branchRarity) == 0) {
                        branch(tree, random, height, level, d[0], d[1]);
                    }
                }
            }
        }
        return true;
    }

    private static void branch(TreePlacer tree, RandomSource random, int treeHeight, int branchLevel, int dx, int dz) {
        int length = treeHeight - branchLevel;
        Trail trail = new Trail(0, branchLevel, 0);
        int x1 = 0;
        int y1 = branchLevel;
        int z1 = 0;
        for (int i = 0; i <= length; i++) {
            Direction.Axis axis = Direction.Axis.Y;
            if (dx == -1 && random.nextInt(3) > 0) {
                x1--;
                axis = Direction.Axis.X;
                if (dz == 0 && random.nextInt(4) == 0) {
                    z1 += random.nextInt(3) - 1;
                }
            } else if (dx == 1 && random.nextInt(3) > 0) {
                x1++;
                axis = Direction.Axis.X;
                if (dz == 0 && random.nextInt(4) == 0) {
                    z1 += random.nextInt(3) - 1;
                }
            }
            if (dz == -1 && random.nextInt(3) > 0) {
                z1--;
                axis = Direction.Axis.Z;
                if (dx == 0 && random.nextInt(4) == 0) {
                    x1 += random.nextInt(3) - 1;
                }
            } else if (dz == 1 && random.nextInt(3) > 0) {
                z1++;
                axis = Direction.Axis.Z;
                if (dx == 0 && random.nextInt(4) == 0) {
                    x1 += random.nextInt(3) - 1;
                }
            }
            trail.log(tree, x1, y1, z1, axis);
            if (random.nextInt(3) == 0) {
                Crowns.round(tree, x1, y1, z1);
            }
            if (random.nextInt(3) > 0) {
                y1++;
            }
            if (i == length) {
                trail.log(tree, x1, y1, z1, Direction.Axis.Y);
                Crowns.round(tree, x1, y1, z1);
            }
        }
    }
}
