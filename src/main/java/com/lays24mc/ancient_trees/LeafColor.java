package com.lays24mc.ancient_trees;

public enum LeafColor {
    NONE,
    FOLIAGE,
    ACEMUS,
    CERASU,
    KULIST;

    public boolean usesColormap() {
        return this == ACEMUS || this == CERASU || this == KULIST;
    }
}
