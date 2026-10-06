package com.lays24mc.ancient_trees.datagen;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.registry.ModBlocks;
import com.lays24mc.ancient_trees.registry.ModItems;
import com.lays24mc.ancient_trees.registry.ModTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockItemTagId;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;

public class ModItemTagsProvider extends BlockTagCopyingItemTagProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
            CompletableFuture<TagsProvider.TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, AncientTrees.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (BlockItemTagId id : new BlockItemTagId[] {
                BlockItemTags.LOGS_THAT_BURN, BlockItemTags.PLANKS, BlockItemTags.WOODEN_STAIRS,
                BlockItemTags.WOODEN_SLABS, BlockItemTags.LEAVES, BlockItemTags.SAPLINGS, BlockItemTags.WOODEN_FENCES,
                BlockItemTags.FENCE_GATES, BlockItemTags.WOODEN_DOORS, BlockItemTags.WOODEN_TRAPDOORS,
                BlockItemTags.WOODEN_PRESSURE_PLATES, BlockItemTags.WOODEN_BUTTONS, BlockItemTags.SIGNS,
                BlockItemTags.HANGING_SIGNS }) {
            copy(id.block(), id.item());
        }

        copy(Tags.Blocks.FENCES_WOODEN, Tags.Items.FENCES_WOODEN);
        copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
        copy(Tags.Blocks.NATURAL_WOODS, Tags.Items.NATURAL_WOODS);
        copy(Tags.Blocks.STRIPPED_LOGS, Tags.Items.STRIPPED_LOGS);
        copy(Tags.Blocks.STRIPPED_WOODS, Tags.Items.STRIPPED_WOODS);

        for (TreeSpecies species : TreeSpecies.values()) {
            // Species tag as for the blocks, so recipes and other mods can target the woods of a species
            copy(ModTags.logsBlock(species), ModTags.logsItem(species));
            // minecraft:overworld_natural_logs only exists for blocks, the c: item tag is filled directly
            tag(ItemTags.BOATS).add(ModItems.BOATS.get(species).getKey());
            tag(ItemTags.CHEST_BOATS).add(ModItems.CHEST_BOATS.get(species).getKey());
            tag(Tags.Items.OVERWORLD_NATURAL_LOGS).add(ModBlocks.LOG_ITEMS.get(species).getKey());
        }
    }
}
