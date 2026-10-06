package com.lays24mc.ancient_trees.world.feature.shape;

import com.lays24mc.ancient_trees.world.feature.TreePlacer;
import net.minecraft.util.RandomSource;

public final class TuopaShape {
    private TuopaShape() {}

    public static boolean generate(TreePlacer tree, RandomSource random) {
        int height = 1 + (random.nextInt(7) < 2 ? 1 : 0) + (random.nextInt(7) < 2 ? 1 : 0)
                + (random.nextBoolean() ? 1 : 0);

        // The original only checks 'height' blocks of space, but the trunk is 6 * height + 1 tall.
        if (!tree.canGrow(6 * height + 1)) {
            return false;
        }

        for (int level = 0; level <= 6 * height + 1; level++) {
            if (level != 6 * height + 1) {
                tree.log(0, level, 0);
            }

            if (height == 1 && level > 2) {
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (Math.abs(dx) != 1 || Math.abs(dz) != 1) {
                            tree.leaves(dx, level, dz);
                        }
                    }
                }
            }

            if (height == 2 && level > 2) {
                for (int dx = -2; dx <= 2; dx++) {
                    for (int dz = -2; dz <= 2; dz++) {
                        if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1 && (Math.abs(dx) != 1 || Math.abs(dz) != 1)) {
                            tree.leaves(dx, level, dz);
                        }
                        if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1 && level == 7) {
                            tree.leaves(dx, 7, dz);
                        }
                        if ((Math.abs(dx) != 2 || Math.abs(dz) != 2) && (Math.abs(dx) != 2 || Math.abs(dz) != 1)
                                && (Math.abs(dx) != 1 || Math.abs(dz) != 2) && level <= 6 * height - 1 && level > 3) {
                            tree.leaves(dx, level, dz);
                        }
                    }
                }
            }

            if (height == 3 && level > 2) {
                for (int dx = -2; dx <= 2; dx++) {
                    for (int dz = -2; dz <= 2; dz++) {
                        if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1 && (Math.abs(dx) != 1 || Math.abs(dz) != 1)) {
                            tree.leaves(dx, level, dz);
                        }
                        if ((Math.abs(dx) != 2 || Math.abs(dz) != 2) && (Math.abs(dx) != 2 || Math.abs(dz) != 1)
                                && (Math.abs(dx) != 1 || Math.abs(dz) != 2) && level <= 6 * height && level > 3) {
                            tree.leaves(dx, level, dz);
                        }
                    }
                }
            }

            if (height == 4 && level > 2) {
                for (int dx = -3; dx <= 3; dx++) {
                    for (int dz = -3; dz <= 3; dz++) {
                        if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1 && (Math.abs(dx) != 1 || Math.abs(dz) != 1)) {
                            tree.leaves(dx, level, dz);
                        }
                        if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1 && level <= 14 && level >= 2) {
                            tree.leaves(dx, level, dz);
                        }
                        if (Math.abs(dx) <= 2 && Math.abs(dz) <= 2 && (Math.abs(dx) != 2 || Math.abs(dz) != 2)
                                && (level == 6 * height || level == 5)) {
                            tree.leaves(dx, level, dz);
                        }
                        if ((Math.abs(dx) != 3 || Math.abs(dz) != 3) && (Math.abs(dx) != 3 || Math.abs(dz) != 2)
                                && (Math.abs(dx) != 2 || Math.abs(dz) != 3) && level <= 6 * height - 1 && level > 5) {
                            tree.leaves(dx, level, dz);
                        }
                    }
                }
            }
        }
        return true;
    }
}
