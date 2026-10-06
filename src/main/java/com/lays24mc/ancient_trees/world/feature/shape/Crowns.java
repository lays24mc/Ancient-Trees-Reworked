package com.lays24mc.ancient_trees.world.feature.shape;

import com.lays24mc.ancient_trees.world.feature.TreePlacer;

final class Crowns {
    private Crowns() {}

    static void round(TreePlacer tree, int x, int y, int z) {
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                if ((Math.abs(dx) != 3 || Math.abs(dz) != 3) && (Math.abs(dx) != 2 || Math.abs(dz) != 3)
                        && (Math.abs(dx) != 3 || Math.abs(dz) != 2)) {
                    tree.leaves(x + dx, y, z + dz);
                }
                if (Math.abs(dx) < 3 && Math.abs(dz) < 3 && (Math.abs(dx) != 2 || Math.abs(dz) != 2)) {
                    tree.leaves(x + dx, y + 1, z + dz);
                    tree.leaves(x + dx, y - 1, z + dz);
                }
                if (Math.abs(dx) + Math.abs(dz) < 2) {
                    tree.leaves(x + dx, y + 2, z + dz);
                    tree.leaves(x + dx, y - 2, z + dz);
                }
            }
        }
    }
}
