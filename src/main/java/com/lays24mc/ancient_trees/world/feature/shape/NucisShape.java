package com.lays24mc.ancient_trees.world.feature.shape;

import com.lays24mc.ancient_trees.world.feature.TreePlacer;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;

public final class NucisShape {
    private static final int[][] DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}
    };

    private NucisShape() {}

    public static boolean generate(TreePlacer tree, RandomSource random) {
        int height = random.nextInt(15) + 8;
        if (!tree.canGrow(height)) {
            return false;
        }

        for (int level = 0; level < height; level++) {
            tree.log(0, level, 0);
            if (level > 3) {
                int branchRarity = height / (level - 2) + 1;
                for (int[] d : DIRECTIONS) {
                    if (random.nextInt(branchRarity) == 0) {
                        branch(tree, random, height, level, d[0], d[1]);
                    }
                }
            }
        }
        Crowns.round(tree, 0, height, 0);
        return true;
    }

    private static void branch(TreePlacer tree, RandomSource random, int height, int level, int dx, int dz) {
        int level1 = level;
        int lengthToGo = height - level1;
        Trail trail = new Trail(0, level1, 0);
        int x1 = 0;
        int z1 = 0;
        int index = 0;
        while (index <= lengthToGo) {
            Direction.Axis axis = Direction.Axis.Y;
            if (dx == -1 && random.nextInt(3) > 0) {
                x1--;
                axis = Direction.Axis.X;
                if (dz == 0 && random.nextInt(4) == 0) {
                    z1 = z1 + random.nextInt(3) - 1;
                }
            } else if (dx == 1 && random.nextInt(3) > 0) {
                x1++;
                axis = Direction.Axis.X;
                if (dz == 0 && random.nextInt(4) == 0) {
                    z1 = z1 + random.nextInt(3) - 1;
                }
            }
            if (dz == -1 && random.nextInt(3) > 0) {
                z1--;
                axis = Direction.Axis.Z;
                if (dx == 0 && random.nextInt(4) == 0) {
                    x1 = x1 + random.nextInt(3) - 1;
                }
            } else if (dz == 1 && random.nextInt(3) > 0) {
                z1++;
                axis = Direction.Axis.Z;
                if (dx == 0 && random.nextInt(4) == 0) {
                    x1 = x1 + random.nextInt(3) - 1;
                }
            }
            trail.log(tree, x1, level1, z1, axis);
            if (random.nextInt(3) > 0) {
                level1++;
            }
            if (index == lengthToGo || random.nextInt(6) == 0) {
                trail.log(tree, x1, level1, z1, axis);
                Crowns.round(tree, x1, level1, z1);
            }
            index++;
        }
    }
}
