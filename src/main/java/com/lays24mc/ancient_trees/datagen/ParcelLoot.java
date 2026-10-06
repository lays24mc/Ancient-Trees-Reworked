package com.lays24mc.ancient_trees.datagen;

import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.item.ParcelItem;
import com.lays24mc.ancient_trees.registry.ModBlocks;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class ParcelLoot implements LootTableSubProvider {
    private final LootTableSubProvider.Context output;

    public ParcelLoot(LootTableSubProvider.Context output) {
        this.output = output;
    }

    @Override
    public void run() {
        LootPool.Builder pool = LootPool.lootPool()
                .setRolls(ContextIntProviders.exactly(1))
                .add(EmptyLootItem.emptyItem().setWeight(600))
                .add(LootItem.lootTableItem(Items.OAK_SAPLING).setWeight(10))
                .add(LootItem.lootTableItem(Items.SPRUCE_SAPLING).setWeight(10))
                .add(LootItem.lootTableItem(Items.BIRCH_SAPLING).setWeight(10))
                .add(LootItem.lootTableItem(Items.JUNGLE_SAPLING).setWeight(10))
                .add(LootItem.lootTableItem(Items.ACACIA_SAPLING).setWeight(10))
                .add(LootItem.lootTableItem(Items.DARK_OAK_SAPLING).setWeight(10))
                .add(LootItem.lootTableItem(Items.CHERRY_SAPLING).setWeight(10))
                .add(LootItem.lootTableItem(Items.PALE_OAK_SAPLING).setWeight(10));
        for (TreeSpecies species : TreeSpecies.values()) {
            pool.add(LootItem.lootTableItem(ModBlocks.SAPLINGS.get(species).get()).setWeight(10));
        }
        output.accept(ParcelItem.CONTENT, LootTable.lootTable().withPool(pool));
    }
}
