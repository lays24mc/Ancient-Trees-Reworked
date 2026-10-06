package com.lays24mc.ancient_trees;

import com.lays24mc.ancient_trees.registry.ModBlocks;
import com.lays24mc.ancient_trees.registry.ModCreativeTabs;
import com.lays24mc.ancient_trees.registry.ModFeatures;
import com.lays24mc.ancient_trees.registry.ModItems;
import com.lays24mc.ancient_trees.config.AncientTreesConfig;
import com.lays24mc.ancient_trees.registry.ModCodecs;
import com.lays24mc.ancient_trees.registry.ModEntities;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ancient Trees Reworked - Rewrite of Ancient Trees / Dendrology
 * (Original: ScottKillen, Blorph, Ruyuna; Unlicense).
 */
@Mod(AncientTrees.MOD_ID)
public class AncientTrees {
    public static final String MOD_ID = "ancient_trees";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public AncientTrees(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.LOCAL, AncientTreesConfig.SPEC);
        ModCodecs.register(modEventBus);
        ModItems.init();
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModFeatures.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        LOGGER.info("Loading Ancient Trees Reworked");
    }
}
