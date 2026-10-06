package com.lays24mc.ancient_trees.world.feature;

import com.lays24mc.ancient_trees.world.feature.shape.CedrumShape;
import com.lays24mc.ancient_trees.world.feature.shape.ClassicShape;
import com.lays24mc.ancient_trees.world.feature.shape.DelnasShape;
import com.lays24mc.ancient_trees.world.feature.shape.EwcalyShape;
import com.lays24mc.ancient_trees.world.feature.shape.HekurShape;
import com.lays24mc.ancient_trees.world.feature.shape.KiparisShape;
import com.lays24mc.ancient_trees.world.feature.shape.KulistShape;
import com.lays24mc.ancient_trees.world.feature.shape.LataShape;
import com.lays24mc.ancient_trees.world.feature.shape.NucisShape;
import com.lays24mc.ancient_trees.world.feature.shape.SalyxShape;
import com.lays24mc.ancient_trees.world.feature.shape.TuopaShape;
import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;

public enum TreeShape implements StringRepresentable {
    CLASSIC("classic", false, false, ClassicShape::generate),
    CEDRUM("cedrum", true, true, CedrumShape::generateNormal),
    CEDRUM_LARGE("cedrum_large", true, true, CedrumShape::generateLarge),
    DELNAS("delnas", false, false, DelnasShape::generate),
    EWCALY("ewcaly", false, false, EwcalyShape::generateNormal),
    EWCALY_LARGE("ewcaly_large", false, false, EwcalyShape::generateLarge),
    HEKUR("hekur", true, true, HekurShape::generateNormal),
    HEKUR_LARGE("hekur_large", true, true, HekurShape::generateLarge),
    KIPARIS("kiparis", true, false, KiparisShape::generate),
    KULIST("kulist", true, false, KulistShape::generateNormal),
    KULIST_LARGE("kulist_large", true, false, KulistShape::generateLarge),
    LATA("lata", false, false, LataShape::generate),
    NUCIS("nucis", false, false, NucisShape::generate),
    SALYX("salyx", true, false, SalyxShape::generate),
    TUOPA("tuopa", false, false, TuopaShape::generate);

    public static final Codec<TreeShape> CODEC = StringRepresentable.fromEnum(TreeShape::values);

    @FunctionalInterface
    public interface Generator {
        boolean generate(TreePlacer tree, RandomSource random);
    }

    private final String serializedName;
    private final boolean replacesWater;
    private final boolean descendsWater;
    private final Generator generator;

    TreeShape(String serializedName, boolean replacesWater, boolean descendsWater, Generator generator) {
        this.serializedName = serializedName;
        this.replacesWater = replacesWater;
        this.descendsWater = descendsWater;
        this.generator = generator;
    }

    public boolean replacesWater() {
        return replacesWater;
    }

    public boolean descendsWater() {
        return descendsWater;
    }

    public Generator generator() {
        return generator;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }
}
