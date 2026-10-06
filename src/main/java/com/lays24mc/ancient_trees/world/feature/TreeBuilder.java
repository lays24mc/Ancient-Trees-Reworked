package com.lays24mc.ancient_trees.world.feature;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;

public final class TreeBuilder implements TreePlacer {
    private final WorldGenLevel level;
    private final BlockPos origin;
    private final BlockState logState;
    private final BlockState leavesState;
    private final boolean replacesWater;
    private final Map<BlockPos, BlockState> logs = new LinkedHashMap<>();
    private final Map<BlockPos, BlockState> leaves = new LinkedHashMap<>();
    private boolean overflowed;

    public TreeBuilder(WorldGenLevel level, BlockPos origin, BlockState logState, BlockState leavesState,
            boolean replacesWater) {
        this.level = level;
        this.origin = origin;
        this.logState = logState;
        this.leavesState = leavesState;
        this.replacesWater = replacesWater;
    }

    private BlockPos at(int dx, int dy, int dz) {
        return origin.offset(dx, dy, dz);
    }

    private boolean outsideWriteZone(BlockPos pos) {
        return level instanceof WorldGenRegion region && !region.isWithinWriteZone(pos);
    }

    public boolean overflowed() {
        return overflowed;
    }

    private boolean isWater(BlockPos pos) {
        return replacesWater && level.getFluidState(pos).is(FluidTags.WATER);
    }

    @Override
    public boolean isReplaceable(int dx, int dy, int dz) {
        BlockPos pos = at(dx, dy, dz);
        if (outsideWriteZone(pos)) {
            return false;
        }
        return TreeFeature.validTreePos(level, pos) || level.getBlockState(pos).is(BlockTags.LOGS) || isWater(pos);
    }

    public boolean hasRoomInColumn(int height) {
        for (int dy = 0; dy <= 1 + height; dy++) {
            if (!isReplaceable(0, dy, 0)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasPoorGround(int height) {
        if (origin.getY() < level.getMinY() + 1 || origin.getY() + height + 1 > level.getMaxY() + 1) {
            return true;
        }
        return !level.getBlockState(origin.below()).is(BlockTags.SUPPORTS_VEGETATION);
    }

    @Override
    public boolean canGrow(int height) {
        return !hasPoorGround(height) && hasRoomInColumn(height);
    }

    @Override
    public boolean isInHeightRange(int dy) {
        int y = origin.getY() + dy;
        return y >= level.getMinY() && y <= level.getMaxY();
    }

    @Override
    public boolean hasSupportingGround() {
        return level.getBlockState(origin.below()).is(BlockTags.SUPPORTS_VEGETATION);
    }

    @Override
    public void log(int dx, int dy, int dz) {
        log(dx, dy, dz, Direction.Axis.Y);
    }

    @Override
    public void log(int dx, int dy, int dz, Direction.Axis axis) {
        BlockPos pos = at(dx, dy, dz);
        if (logs.containsKey(pos)) {
            return;
        }
        if (outsideWriteZone(pos)) {
            overflowed = true;
            return;
        }
        BlockState existing = level.getBlockState(pos);
        if (existing.isAir() || existing.is(BlockTags.LEAVES) || TreeFeature.validTreePos(level, pos) || isWater(pos)) {
            leaves.remove(pos);
            logs.put(pos, logState.setValue(RotatedPillarBlock.AXIS, axis));
        }
    }

    @Override
    public void root(int dx, int dy, int dz, Direction.Axis axis) {
        BlockPos pos = at(dx, dy, dz);
        if (logs.containsKey(pos)) {
            return;
        }
        if (outsideWriteZone(pos)) {
            overflowed = true;
            return;
        }
        BlockState existing = level.getBlockState(pos);
        if (existing.is(BlockTags.SAND) || existing.is(BlockTags.SUPPORTS_VEGETATION)) {
            leaves.remove(pos);
            logs.put(pos, logState.setValue(RotatedPillarBlock.AXIS, axis));
        } else {
            log(dx, dy, dz, axis);
        }
    }

    @Override
    public void clear(int dx, int dy, int dz) {
        BlockPos pos = at(dx, dy, dz);
        logs.remove(pos);
        leaves.remove(pos);
    }

    @Override
    public void leaves(int dx, int dy, int dz) {
        BlockPos pos = at(dx, dy, dz);
        if (logs.containsKey(pos)) {
            return;
        }
        if (outsideWriteZone(pos)) {
            overflowed = true;
            return;
        }
        if (TreeFeature.validTreePos(level, pos) || isWater(pos)) {
            leaves.put(pos, leavesState);
        }
    }

    public boolean isEmpty() {
        return logs.isEmpty() && leaves.isEmpty();
    }

    public void commit() {
        BlockPos below = origin.below();
        if (level.getBlockState(below).is(Blocks.GRASS_BLOCK) || level.getBlockState(below).is(Blocks.PODZOL)
                || level.getBlockState(below).is(Blocks.MYCELIUM)) {
            level.setBlock(below, Blocks.DIRT.defaultBlockState(), 19);
        }

        Map<BlockPos, Integer> distance = new HashMap<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        for (BlockPos pos : logs.keySet()) {
            distance.put(pos, 0);
            queue.add(pos);
        }
        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();
            int next = distance.get(pos) + 1;
            if (next >= 7) {
                continue;
            }
            for (Direction direction : Direction.values()) {
                BlockPos neighbor = pos.relative(direction);
                if (leaves.containsKey(neighbor) && !distance.containsKey(neighbor)) {
                    distance.put(neighbor, next);
                    queue.add(neighbor);
                }
            }
        }

        logs.forEach((pos, state) -> level.setBlock(pos, state, 19));
        leaves.forEach((pos, state) -> {
            if (!distance.containsKey(pos)) {
                return; // too far from the wood, would decay immediately
            }
            BlockState result = state.setValue(BlockStateProperties.DISTANCE, distance.get(pos));
            if (result.hasProperty(BlockStateProperties.WATERLOGGED)) {
                result = result.setValue(BlockStateProperties.WATERLOGGED, level.getFluidState(pos).is(FluidTags.WATER));
            }
            level.setBlock(pos, result, 19);
        });
    }
}
