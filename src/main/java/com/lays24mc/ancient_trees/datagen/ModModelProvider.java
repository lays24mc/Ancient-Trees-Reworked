package com.lays24mc.ancient_trees.datagen;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.LeafColor;
import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.registry.ModBlocks;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import javax.imageio.ImageIO;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import com.lays24mc.ancient_trees.registry.ModItems;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.client.data.models.ModelProvider;

public class ModModelProvider extends ModelProvider {
    private static final int FOLIAGE_DEFAULT = -12012264;

    public ModModelProvider(PackOutput output) {
        super(output, AncientTrees.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.PARCEL.get(), ModelTemplates.FLAT_ITEM);
        for (TreeSpecies species : TreeSpecies.values()) {
            itemModels.generateFlatItem(ModItems.BOATS.get(species).get(), ModelTemplates.FLAT_ITEM);
            itemModels.generateFlatItem(ModItems.CHEST_BOATS.get(species).get(), ModelTemplates.FLAT_ITEM);
        }

        for (TreeSpecies species : TreeSpecies.values()) {
            var log = ModBlocks.LOGS.get(species).get();
            var planks = ModBlocks.PLANKS.get(species).get();
            var leaves = ModBlocks.LEAVES.get(species).get();

            blockModels.woodProvider(log).log(log).wood(ModBlocks.WOODS.get(species).get());
            var strippedLog = ModBlocks.STRIPPED_LOGS.get(species).get();
            blockModels.woodProvider(strippedLog).log(strippedLog).wood(ModBlocks.STRIPPED_WOODS.get(species).get());
            var family = blockModels.family(planks)
                    .stairs(ModBlocks.STAIRS.get(species).get())
                    .slab(ModBlocks.SLABS.get(species).get())
                    .fence(ModBlocks.FENCES.get(species).get())
                    .fenceGate(ModBlocks.FENCE_GATES.get(species).get())
                    .pressurePlate(ModBlocks.PRESSURE_PLATES.get(species).get())
                    .button(ModBlocks.BUTTONS.get(species).get());
            family.trapdoor(ModBlocks.TRAPDOORS.get(species).get());
            blockModels.createDoor(ModBlocks.DOORS.get(species).get());

            // Signs and hanging signs need a block family (wall variants and particle texture)
            BlockFamily signs = new BlockFamily.Builder(planks)
                    .strippedLog(strippedLog)
                    .sign(ModBlocks.SIGNS.get(species).get(), ModBlocks.WALL_SIGNS.get(species).get())
                    .hangingSign(ModBlocks.HANGING_SIGNS.get(species).get(), ModBlocks.WALL_HANGING_SIGNS.get(species).get())
                    .getFamily();
            family.generateFor(signs);
            if (species.leafColor() == LeafColor.NONE) {
                blockModels.createTrivialBlock(leaves, TexturedModel.LEAVES);
            } else {
                blockModels.createTintedLeaves(leaves, TexturedModel.LEAVES, itemTint(species));
            }
            blockModels.createCrossBlockWithDefaultItem(ModBlocks.SAPLINGS.get(species).get(),
                    BlockModelGenerators.PlantType.NOT_TINTED);
        }
    }

    private static int itemTint(TreeSpecies species) {
        if (!species.leafColor().usesColormap()) {
            return FOLIAGE_DEFAULT;
        }
        String path = "/assets/" + AncientTrees.MOD_ID + "/textures/colormap/" + species.id() + ".png";
        try (InputStream in = ModModelProvider.class.getResourceAsStream(path)) {
            if (in == null) {
                throw new IllegalStateException("Colormap fehlt: " + path);
            }
            return ImageIO.read(in).getRGB(0x80, 0x80) | 0xFF000000;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
