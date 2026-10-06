package com.lays24mc.ancient_trees.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
public class FlammableStairBlock extends StairBlock {
    private final Flammability flammability;

    public FlammableStairBlock(BlockState base, Properties properties, Flammability flammability) {
        super(base, properties);
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
