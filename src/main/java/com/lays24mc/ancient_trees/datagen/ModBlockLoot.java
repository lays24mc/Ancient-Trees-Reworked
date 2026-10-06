package com.lays24mc.ancient_trees.datagen;

import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.loot.SaplingDropCondition;
import com.lays24mc.ancient_trees.registry.ModBlocks;
import java.util.Set;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class ModBlockLoot extends BlockLootSubProvider {
    private static final float[] STICK_CHANCES = {0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};

    public ModBlockLoot(LootTableSubProvider.Context context) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), context);
    }

    @Override
    protected void generate() {
        for (TreeSpecies species : TreeSpecies.values()) {
            dropSelf(ModBlocks.LOGS.get(species).get());
            dropSelf(ModBlocks.WOODS.get(species).get());
            dropSelf(ModBlocks.STRIPPED_LOGS.get(species).get());
            dropSelf(ModBlocks.STRIPPED_WOODS.get(species).get());
            dropSelf(ModBlocks.PLANKS.get(species).get());
            dropSelf(ModBlocks.STAIRS.get(species).get());
            dropSelf(ModBlocks.FENCES.get(species).get());
            dropSelf(ModBlocks.FENCE_GATES.get(species).get());
            dropSelf(ModBlocks.TRAPDOORS.get(species).get());
            dropSelf(ModBlocks.PRESSURE_PLATES.get(species).get());
            dropSelf(ModBlocks.BUTTONS.get(species).get());
            dropSelf(ModBlocks.SIGNS.get(species).get());
            dropSelf(ModBlocks.HANGING_SIGNS.get(species).get());
            add(ModBlocks.DOORS.get(species).get(), createDoorTable(ModBlocks.DOORS.get(species).get()));
            dropSelf(ModBlocks.SAPLINGS.get(species).get());
            add(ModBlocks.SLABS.get(species).get(), createSlabItemTable(ModBlocks.SLABS.get(species).get()));
            add(ModBlocks.LEAVES.get(species).get(), leavesDrops(ModBlocks.LEAVES.get(species).get(),
                    ModBlocks.SAPLINGS.get(species).get()));
        }
    }

    /** Like {@code createLeavesDrops}, but the sapling chance is multiplied by the config factor. */
    private LootTable.Builder leavesDrops(Block leaves, Block sapling) {
        return createSilkTouchOrShearsDispatchTable(leaves,
                applyExplosionCondition(leaves, LootItem.lootTableItem(sapling))
                        .when(SaplingDropCondition.withFortune(enchantments.getOrThrow(Enchantments.FORTUNE),
                                NORMAL_LEAVES_SAPLING_CHANCES)))
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .when(new AnyOfCondition.Builder().or(hasShears()).or(hasSilkTouch()).invert())
                        .add(applyExplosionDecay(leaves, LootItem.lootTableItem(Items.STICK)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(
                                        enchantments.getOrThrow(Enchantments.FORTUNE), STICK_CHANCES))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.all().<Block>map(holder -> holder.get()).toList();
    }
}
