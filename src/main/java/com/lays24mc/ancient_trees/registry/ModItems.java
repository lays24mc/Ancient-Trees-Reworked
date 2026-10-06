package com.lays24mc.ancient_trees.registry;

import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.item.ParcelItem;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.neoforge.registries.DeferredItem;

public final class ModItems {
    public static final DeferredItem<ParcelItem> PARCEL =
            ModBlocks.ITEMS.registerItem("parcel", ParcelItem::new, p -> p.stacksTo(16));

    public static final Map<TreeSpecies, DeferredItem<BoatItem>> BOATS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredItem<BoatItem>> CHEST_BOATS = new EnumMap<>(TreeSpecies.class);

    static {
        for (TreeSpecies species : TreeSpecies.values()) {
            BOATS.put(species, ModBlocks.ITEMS.registerItem(species.id() + "_boat",
                    p -> new BoatItem(ModEntities.BOATS.get(species).get(), p),
                    () -> new Item.Properties().cookingFuel(ContextIntProviders.COOKING_TIME_BOATS).stacksTo(1)));
            CHEST_BOATS.put(species, ModBlocks.ITEMS.registerItem(species.id() + "_chest_boat",
                    p -> new BoatItem(ModEntities.CHEST_BOATS.get(species).get(), p),
                    () -> new Item.Properties().cookingFuel(ContextIntProviders.COOKING_TIME_BOATS).stacksTo(1)));
        }
    }

    private ModItems() {}

    public static void init() {}
}
