package com.lays24mc.ancient_trees.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FlammableLeavesBlock extends TintedParticleLeavesBlock {
    private final Flammability flammability;

    public FlammableLeavesBlock(float leafParticleChance, Properties properties, Flammability flammability) {
        super(leafParticleChance, properties);
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
