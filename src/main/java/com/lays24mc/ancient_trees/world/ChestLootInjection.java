package com.lays24mc.ancient_trees.world;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.config.AncientTreesConfig;
import com.lays24mc.ancient_trees.registry.ModItems;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;

@EventBusSubscriber(modid = AncientTrees.MOD_ID)
public final class ChestLootInjection {
    private ChestLootInjection() {}

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        float chance = AncientTreesConfig.parcelChestChance();
        if (chance > 0 && AncientTreesConfig.isParcelChestTable(event.getName().toString())) {
            LootPool pool = LootPool.lootPool()
                    .setRolls(ContextIntProviders.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(chance))
                    .add(LootItem.lootTableItem(ModItems.PARCEL.get())
                            .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                    .build();
            event.getTable().addPool(pool);
        }
    }
}
