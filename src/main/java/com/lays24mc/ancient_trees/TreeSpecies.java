package com.lays24mc.ancient_trees;

import java.util.Locale;

public enum TreeSpecies {
    ACEMUS("Acemus", LeafColor.ACEMUS),
    CEDRUM("Cedrum", LeafColor.NONE),
    CERASU("Cerasu", LeafColor.CERASU),
    DELNAS("Delnas", LeafColor.NONE),
    EWCALY("Ewcaly", LeafColor.NONE),
    HEKUR("Hekur", LeafColor.FOLIAGE),
    KIPARIS("Kiparis", LeafColor.NONE),
    KULIST("Kulist", LeafColor.KULIST),
    LATA("Lata", LeafColor.FOLIAGE),
    NUCIS("Nucis", LeafColor.FOLIAGE),
    PORFFOR("Porffor", LeafColor.NONE),
    SALYX("Salyx", LeafColor.NONE),
    TUOPA("Tuopa", LeafColor.FOLIAGE);

    private final String displayName;
    private final LeafColor leafColor;

    TreeSpecies(String displayName, LeafColor leafColor) {
        this.displayName = displayName;
        this.leafColor = leafColor;
    }

    public String id() {
        return name().toLowerCase(Locale.ROOT);
    }

    public LeafColor leafColor() {
        return leafColor;
    }

    public String displayName() {
        return displayName;
    }
}
