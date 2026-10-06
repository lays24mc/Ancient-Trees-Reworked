package com.lays24mc.ancient_trees.world.feature.shape;

import com.lays24mc.ancient_trees.world.feature.TreePlacer;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;

public final class HekurShape {
    private static final int[][] ROOT_DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}
    };

    private final TreePlacer tree;
    private final RandomSource random;
    private Direction.Axis axis = Direction.Axis.Y;

    private HekurShape(TreePlacer tree, RandomSource random) {
        this.tree = tree;
        this.random = random;
    }

    public static boolean generateNormal(TreePlacer tree, RandomSource random) {
        if (!tree.hasSupportingGround()) {
            return false;
        }
        HekurShape shape = new HekurShape(tree, random);
        shape.genRoots();
        shape.growTrunk();
        return true;
    }

    public static boolean generateLarge(TreePlacer tree, RandomSource random) {
        if (!tree.hasSupportingGround()) {
            return false;
        }
        HekurShape shape = new HekurShape(tree, random);
        shape.genRoots();
        shape.growLargeTrunk();
        return true;
    }

    private void log(int x, int y, int z) {
        tree.log(x, y, z, axis);
    }

    private void setAxis(int dx, int dz) {
        if (dx != 0) {
            axis = Direction.Axis.X;
        }
        if (dz != 0) {
            axis = Direction.Axis.Z;
        }
    }

    private void genRoots() {
        for (int[] d : ROOT_DIRECTIONS) {
            if (random.nextInt(3) == 0) {
                genRoot(d[0], d[1]);
            }
        }
        genRoot(0, 0);
    }

    private void genRoot(int dx, int dz) {
        int x1 = 0;
        int y1 = 0;
        int z1 = 0;
        setAxis(dx, dz);
        for (int i = 0; i < 6; i++) {
            if (random.nextInt(3) == 0) {
                if (dx == -1) {
                    x1--;
                }
                if (dx == 1) {
                    x1++;
                }
                if (dz == -1) {
                    z1--;
                }
                if (dz == 1) {
                    z1++;
                }
            }
            tree.root(x1, y1, z1, axis);
            y1--;
        }
        axis = Direction.Axis.Y;
    }

    private void growTrunk() {
        log(0, 0, 0);
        switch (random.nextInt(4)) {
            case 0 -> {
                log(0, 2, 0);
                log(-1, 1, 0);
                largeDirect(1, 0, 0, 2, 0, 1, 2, 0, 2);
            }
            case 1 -> {
                log(0, 1, 0);
                log(0, 2, 0);
                log(0, 1, -1);
                largeDirect(0, 1, 0, 2, 0, 1, 2, 0, 2);
            }
            case 2 -> {
                log(0, 1, 0);
                log(0, 2, 0);
                log(1, 1, 0);
                largeDirect(-1, 0, 0, 2, 0, 1, 2, 0, 2);
            }
            default -> {
                log(0, 1, 0);
                log(0, 2, 0);
                log(0, 1, 1);
                largeDirect(0, -1, 0, 1, 0, 1, 2, 0, 2);
            }
        }
    }

    private void growLargeTrunk() {
        log(0, 0, 0);
        switch (random.nextInt(4)) {
            case 0 -> {
                log(1, 0, 0);
                log(1, 1, 0);
                largeDirect(1, 0, 0, 1, 0, 2, 5, 4, 3);
            }
            case 1 -> {
                log(0, 0, 1);
                log(0, 1, 1);
                largeDirect(0, 1, 0, 1, 0, 2, 5, 4, 3);
            }
            case 2 -> {
                log(-1, 0, 0);
                log(-1, 1, 0);
                largeDirect(-1, 0, 0, 1, 0, 2, 5, 4, 3);
            }
            default -> {
                log(0, 0, -1);
                log(0, 1, -1);
                largeDirect(0, -1, 0, 1, 0, 2, 5, 4, 3);
            }
        }
    }

    /** {@code largeDirect}; x, y, z are the start point relative to the origin. */
    private void largeDirect(int dx, int dz, int x, int y, int z, int size, int splitCount, int splitCount1,
            int splitCount2) {
        int x1 = x;
        int y1 = y;
        int z1 = z;
        setAxis(dx, dz);
        int dSize = 0;
        if (size == 2) {
            dSize = 2;
        }
        for (int next = 0; next <= 5 * size; next++) {
            if (size == 1) {
                y1++;
            }
            log(x1, y1, z1);
            if (next <= 9 && size == 2) {
                log(x1 - dx, y1, z1 - dz);
            }
            if (next == 5 * size) {
                branchAndLeaf(x1, y1 + 1, z1);
            }
            if (size == 2) {
                y1++;
            }
            x1 += dx;
            z1 += dz;
            if (next == splitCount) {
                firstBranchSplit(x1, y1, z1, dx, dz, splitCount);
                secondBranchSplit(x1, y1, z1, dx, dz, splitCount);
            }
            if (next == 3 * size && size == 2) {
                fifthBranchSplit(x1, y1, z1, dx, dz, splitCount1);
                sixthBranchSplit(x1, y1, z1, dx, dz, splitCount1);
            }
            if (next == 3 * size) {
                thirdBranchSplit(x1, y1, z1, dx, dz, 4 * size - dSize);
                fourthBranchSplit(x1, y1, z1, dx, dz, 4 * size - dSize);
            }
            if (next == 4 * size) {
                fifthBranchSplit(x1, y1, z1, dx, dz, splitCount2);
                sixthBranchSplit(x1, y1, z1, dx, dz, splitCount2);
            }
        }
        axis = Direction.Axis.Y;
    }

    private void firstBranchSplit(int x, int y, int z, int dx, int dz, int splitCount) {
        int x1 = x;
        int y1 = y;
        int z1 = z;
        for (int i = 0; i <= splitCount; i++) {
            if (dx != 0) {
                if (random.nextInt(5) > 0) {
                    if (dx == 1) {
                        x1--;
                    } else {
                        x1++;
                    }
                }
                z1 += random.nextInt(2);
            }
            if (dz == 1) {
                x1 -= random.nextInt(2);
                if (random.nextInt(5) > 0) {
                    z1--;
                }
            } else if (dz == -1) {
                x1 += random.nextInt(2);
                if (random.nextInt(5) > 0) {
                    z1++;
                }
            }
            y1++;
            log(x1, y1, z1);
            if (i == splitCount) {
                branchAndLeaf(x1, y1, z1);
            }
        }
    }

    private void secondBranchSplit(int x, int y, int z, int dx, int dz, int splitCount) {
        int x1 = x;
        int y1 = y;
        int z1 = z;
        for (int i = 0; i <= splitCount; i++) {
            if (dx != 0) {
                if (random.nextInt(5) > 0) {
                    if (dx == 1) {
                        x1--;
                    } else {
                        x1++;
                    }
                }
                z1 -= random.nextInt(2);
            }
            if (dz == 1) {
                x1 += random.nextInt(2);
                if (random.nextInt(5) > 0) {
                    z1--;
                }
            } else if (dz == -1) {
                x1 -= random.nextInt(2);
                if (random.nextInt(5) > 0) {
                    z1++;
                }
            }
            y1++;
            log(x1, y1, z1);
            if (i == splitCount) {
                branchAndLeaf(x1, y1, z1);
            }
        }
    }

    private void thirdBranchSplit(int x, int y, int z, int dx, int dz, int length) {
        int x1 = x;
        int y1 = y;
        int z1 = z;
        for (int i = 0; i <= length; i++) {
            if (dx != 0) {
                if (dx == 1) {
                    x1 += random.nextInt(2);
                } else {
                    x1 -= random.nextInt(2);
                }
                z1 += random.nextInt(2);
            }
            if (dz != 0) {
                if (dz == 1) {
                    z1 += random.nextInt(2);
                } else {
                    z1 -= random.nextInt(2);
                }
                x1 += random.nextInt(2);
            }
            if (i >= 3) {
                y1 += random.nextInt(2);
            }
            log(x1, y1, z1);
            if (i == length) {
                branchAndLeaf(x1, y1, z1);
            }
        }
    }

    private void fourthBranchSplit(int x, int y, int z, int dx, int dz, int length) {
        int x1 = x;
        int y1 = y;
        int z1 = z;
        for (int i = 0; i <= length; i++) {
            if (dx != 0) {
                if (dx == 1) {
                    x1 += random.nextInt(2);
                } else {
                    x1 -= random.nextInt(2);
                }
                z1 -= random.nextInt(2);
            }
            if (dz != 0) {
                if (dz == 1) {
                    z1 += random.nextInt(2);
                } else {
                    z1 -= random.nextInt(2);
                }
                x1 -= random.nextInt(2);
            }
            if (i >= 3) {
                y1 += random.nextInt(2);
            }
            log(x1, y1, z1);
            if (i == length) {
                branchAndLeaf(x1, y1, z1);
            }
        }
    }

    private void fifthBranchSplit(int x, int y, int z, int dx, int dz, int splitCount2) {
        int x1 = x;
        int y1 = y;
        int z1 = z;
        for (int i = 0; i <= splitCount2; i++) {
            if (dx == 1) {
                x1 -= random.nextInt(2);
                z1 += random.nextInt(2);
            }
            if (dx == -1) {
                x1 += random.nextInt(2);
                z1 += random.nextInt(2);
            }
            if (dz == 1) {
                x1 -= random.nextInt(2);
                z1 -= random.nextInt(2);
            }
            if (dz == -1) {
                x1 += random.nextInt(2);
                z1 += random.nextInt(2);
            }
            y1++;
            log(x1, y1, z1);
            if (i == splitCount2) {
                branchAndLeaf(x1, y1, z1);
            }
        }
    }

    private void sixthBranchSplit(int x, int y, int z, int dx, int dz, int splitCount2) {
        int x1 = x;
        int y1 = y;
        int z1 = z;
        for (int i = 0; i <= splitCount2; i++) {
            if (dx != 0) {
                if (dx == 1) {
                    x1 -= random.nextInt(2);
                } else {
                    x1 += random.nextInt(2);
                }
                z1 -= random.nextInt(2);
            }
            if (dz == 1) {
                x1 += random.nextInt(2);
                z1 -= random.nextInt(2);
            } else if (dz == -1) {
                x1 -= random.nextInt(2);
                z1 += random.nextInt(2);
            }
            y1++;
            log(x1, y1, z1);
            if (i == splitCount2) {
                branchAndLeaf(x1, y1, z1);
            }
        }
    }

    private void branchAndLeaf(int x, int y, int z) {
        log(x, y, z);
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                if ((Math.abs(dx) != 3 || Math.abs(dz) != 3) && (Math.abs(dx) != 2 || Math.abs(dz) != 3)
                        && (Math.abs(dx) != 3 || Math.abs(dz) != 2)) {
                    tree.leaves(x + dx, y, z + dz);
                }
                if (Math.abs(dx) < 3 && Math.abs(dz) < 3 && (Math.abs(dx) != 2 || Math.abs(dz) != 2)) {
                    tree.leaves(x + dx, y + 1, z + dz);
                }
            }
        }
    }
}
