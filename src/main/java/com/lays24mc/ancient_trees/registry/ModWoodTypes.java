package com.lays24mc.ancient_trees.registry;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.TreeSpecies;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public final class ModWoodTypes {
    public static final Map<TreeSpecies, BlockSetType> BLOCK_SET_TYPES = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, WoodType> WOOD_TYPES = new EnumMap<>(TreeSpecies.class);

    static {
        for (TreeSpecies species : TreeSpecies.values()) {
            String name = AncientTrees.MOD_ID + ":" + species.id();
            BlockSetType setType = BlockSetType.register(new BlockSetType(name));
            BLOCK_SET_TYPES.put(species, setType);
            WOOD_TYPES.put(species, WoodType.register(new WoodType(name, setType)));
        }
    }

    private ModWoodTypes() {}

    public static void init() {}
}
