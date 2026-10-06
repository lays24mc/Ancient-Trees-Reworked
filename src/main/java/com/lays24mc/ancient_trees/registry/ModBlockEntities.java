package com.lays24mc.ancient_trees.registry;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.TreeSpecies;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

@EventBusSubscriber(modid = AncientTrees.MOD_ID)
public final class ModBlockEntities {
    private ModBlockEntities() {}

    @SubscribeEvent
    public static void addValidBlocks(BlockEntityTypeAddBlocksEvent event) {
        for (TreeSpecies species : TreeSpecies.values()) {
            event.modify(BlockEntityTypes.SIGN, ModBlocks.SIGNS.get(species).get(), ModBlocks.WALL_SIGNS.get(species).get());
            event.modify(BlockEntityTypes.HANGING_SIGN,
                    ModBlocks.HANGING_SIGNS.get(species).get(), ModBlocks.WALL_HANGING_SIGNS.get(species).get());
        }
    }
}
