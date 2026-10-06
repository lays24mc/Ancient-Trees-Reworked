package com.lays24mc.ancient_trees.registry;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.TreeSpecies;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntities {
    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(AncientTrees.MOD_ID);

    public static final Map<TreeSpecies, DeferredHolder<EntityType<?>, EntityType<Boat>>> BOATS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredHolder<EntityType<?>, EntityType<ChestBoat>>> CHEST_BOATS = new EnumMap<>(TreeSpecies.class);

    static {
        for (TreeSpecies species : TreeSpecies.values()) {
            BOATS.put(species, ENTITIES.registerEntityType(species.id() + "_boat",
                    (type, level) -> new Boat(type, level, () -> ModItems.BOATS.get(species).get()),
                    MobCategory.MISC,
                    builder -> builder.noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10)));
            CHEST_BOATS.put(species, ENTITIES.registerEntityType(species.id() + "_chest_boat",
                    (type, level) -> new ChestBoat(type, level, () -> ModItems.CHEST_BOATS.get(species).get()),
                    MobCategory.MISC,
                    builder -> builder.noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10)));
        }
    }

    private ModEntities() {}

    public static void register(IEventBus modEventBus) {
        ENTITIES.register(modEventBus);
    }
}
