package com.lays24mc.ancient_trees.block;

public record Flammability(int spread, int burn) {
    public static final Flammability LOG = new Flammability(5, 5);
    public static final Flammability PLANKS = new Flammability(5, 20);
    public static final Flammability LEAVES = new Flammability(30, 60);
}
