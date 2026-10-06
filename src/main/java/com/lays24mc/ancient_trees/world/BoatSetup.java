package com.lays24mc.ancient_trees.world;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.registry.ModEntities;
import com.lays24mc.ancient_trees.registry.ModItems;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = AncientTrees.MOD_ID)
public final class BoatSetup {
    private BoatSetup() {}

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            for (TreeSpecies species : TreeSpecies.values()) {
                DispenserBlock.registerBehavior(ModItems.BOATS.get(species).get(),
                        new BoatDispenseItemBehavior(ModEntities.BOATS.get(species).get()));
                DispenserBlock.registerBehavior(ModItems.CHEST_BOATS.get(species).get(),
                        new BoatDispenseItemBehavior(ModEntities.CHEST_BOATS.get(species).get()));
            }
        });
    }
}
