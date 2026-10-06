package com.lays24mc.ancient_trees.datagen;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.registry.ModBlocks;
import com.lays24mc.ancient_trees.registry.ModTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, AncientTrees.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (TreeSpecies species : TreeSpecies.values()) {
            var log = ModBlocks.LOGS.get(species).getKey();
            var wood = ModBlocks.WOODS.get(species).getKey();
            var strippedLog = ModBlocks.STRIPPED_LOGS.get(species).getKey();
            var strippedWood = ModBlocks.STRIPPED_WOODS.get(species).getKey();

            tag(ModTags.logsBlock(species)).add(log, wood, strippedLog, strippedWood);
            tag(BlockItemTags.LOGS_THAT_BURN.block()).addTag(ModTags.logsBlock(species));
            tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(log);
            tag(Tags.Blocks.NATURAL_WOODS).add(wood);
            tag(Tags.Blocks.STRIPPED_LOGS).add(strippedLog);
            tag(Tags.Blocks.STRIPPED_WOODS).add(strippedWood);
            tag(BlockTags.MINEABLE_WITH_AXE).add(log, wood, strippedLog, strippedWood);

            tag(BlockItemTags.PLANKS.block()).add(ModBlocks.PLANKS.get(species).getKey());
            tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.PLANKS.get(species).getKey());

            tag(BlockItemTags.WOODEN_STAIRS.block()).add(ModBlocks.STAIRS.get(species).getKey());
            tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.STAIRS.get(species).getKey());

            tag(BlockItemTags.WOODEN_SLABS.block()).add(ModBlocks.SLABS.get(species).getKey());
            tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.SLABS.get(species).getKey());

            var fence = ModBlocks.FENCES.get(species).getKey();
            var gate = ModBlocks.FENCE_GATES.get(species).getKey();
            var door = ModBlocks.DOORS.get(species).getKey();
            var trapdoor = ModBlocks.TRAPDOORS.get(species).getKey();
            var plate = ModBlocks.PRESSURE_PLATES.get(species).getKey();
            var button = ModBlocks.BUTTONS.get(species).getKey();
            tag(BlockItemTags.WOODEN_FENCES.block()).add(fence);
            tag(Tags.Blocks.FENCES_WOODEN).add(fence);
            tag(BlockItemTags.FENCE_GATES.block()).add(gate);
            tag(Tags.Blocks.FENCE_GATES_WOODEN).add(gate);
            tag(BlockItemTags.WOODEN_DOORS.block()).add(door);
            tag(BlockItemTags.WOODEN_TRAPDOORS.block()).add(trapdoor);
            tag(BlockItemTags.WOODEN_PRESSURE_PLATES.block()).add(plate);
            tag(BlockItemTags.WOODEN_BUTTONS.block()).add(button);
            tag(BlockTags.MINEABLE_WITH_AXE).add(fence, gate, door, trapdoor, plate, button);

            var sign = ModBlocks.SIGNS.get(species).getKey();
            var wallSign = ModBlocks.WALL_SIGNS.get(species).getKey();
            var hangingSign = ModBlocks.HANGING_SIGNS.get(species).getKey();
            var wallHangingSign = ModBlocks.WALL_HANGING_SIGNS.get(species).getKey();
            tag(BlockItemTags.SIGNS.block()).add(sign);
            tag(BlockTags.WALL_SIGNS).add(wallSign);
            tag(BlockItemTags.HANGING_SIGNS.block()).add(hangingSign);
            tag(BlockTags.WALL_HANGING_SIGNS).add(wallHangingSign);
            tag(BlockTags.MINEABLE_WITH_AXE).add(sign, wallSign, hangingSign, wallHangingSign);

            tag(BlockItemTags.LEAVES.block()).add(ModBlocks.LEAVES.get(species).getKey());
            tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.LEAVES.get(species).getKey());

            tag(BlockItemTags.SAPLINGS.block()).add(ModBlocks.SAPLINGS.get(species).getKey());
        }
    }
}
