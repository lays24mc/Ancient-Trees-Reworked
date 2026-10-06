package com.lays24mc.ancient_trees.world.feature.shape;

import com.lays24mc.ancient_trees.world.feature.TreePlacer;
import net.minecraft.core.Direction;

final class Trail {
    private int x;
    private int y;
    private int z;

    Trail(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    void log(TreePlacer tree, int nx, int ny, int nz, Direction.Axis axis) {
        while (y != ny) {
            y += Integer.signum(ny - y);
            tree.log(x, y, z, Direction.Axis.Y);
        }
        while (x != nx) {
            x += Integer.signum(nx - x);
            tree.log(x, y, z, Direction.Axis.X);
        }
        while (z != nz) {
            z += Integer.signum(nz - z);
            tree.log(x, y, z, Direction.Axis.Z);
        }
        tree.log(nx, ny, nz, axis);
    }
}
