package com.lays24mc.ancient_trees.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class FlammableFenceGateBlock extends FenceGateBlock {
    private final Flammability flammability;

    public FlammableFenceGateBlock(WoodType type, Properties properties, Flammability flammability) {
        super(type, properties);
        this.flammability = flammability;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return flammability.burn();
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return flammability.spread();
    }
}
