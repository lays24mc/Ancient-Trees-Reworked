package com.lays24mc.ancient_trees.world.feature;

import net.minecraft.core.Direction;

public interface TreePlacer {
    boolean isReplaceable(int dx, int dy, int dz);

    boolean isInHeightRange(int dy);
    boolean canGrow(int height);

    boolean hasPoorGround(int height);

    boolean hasSupportingGround();

    void log(int dx, int dy, int dz);

    void log(int dx, int dy, int dz, Direction.Axis axis);

    void root(int dx, int dy, int dz, Direction.Axis axis);

    void leaves(int dx, int dy, int dz);

    void clear(int dx, int dy, int dz);
}
