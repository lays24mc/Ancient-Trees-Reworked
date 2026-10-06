package com.lays24mc.ancient_trees.registry;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.TreeSpecies;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class ModTags {
    private ModTags() {}

    public static TagKey<Block> logsBlock(TreeSpecies species) {
        return TagKey.create(Registries.BLOCK, id(species.id() + "_logs"));
    }

    public static TagKey<Item> logsItem(TreeSpecies species) {
        return TagKey.create(Registries.ITEM, id(species.id() + "_logs"));
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(AncientTrees.MOD_ID, path);
    }
}
