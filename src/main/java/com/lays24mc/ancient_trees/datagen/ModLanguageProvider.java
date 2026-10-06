package com.lays24mc.ancient_trees.datagen;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.registry.ModBlocks;
import com.lays24mc.ancient_trees.registry.ModEntities;
import com.lays24mc.ancient_trees.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    private final boolean german;

    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, AncientTrees.MOD_ID, locale);
        this.german = locale.equals("de_de");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + AncientTrees.MOD_ID, german ? "Vergessene Bäume" : "Ancient Trees");
        addItem(ModItems.PARCEL, german ? "Uraltes Paket" : "Ancient Parcel");
        add("item.ancient_trees.parcel.tooltip",
                german ? "Was haben die Alten darin verborgen?" : "What have the Ancients hidden within?");
        add("item.ancient_trees.parcel.empty",
                german ? "Das leere Paket zerfällt zu Staub." : "The empty parcel crumbles into dust.");
        add("item.ancient_trees.parcel.full",
                german ? "Das Öffnen des Pakets enthüllt ein uraltes Geschenk. (%s)"
                        : "Opening the parcel reveals an ancient gift. (%s)");
        for (TreeSpecies species : TreeSpecies.values()) {
            String n = species.displayName();
            addBlock(ModBlocks.LOGS.get(species), german ? n + "stamm" : n + " Log");
            addBlock(ModBlocks.WOODS.get(species), german ? n + "holz" : n + " Wood");
            addBlock(ModBlocks.STRIPPED_LOGS.get(species), german ? "Entrindeter " + n + "stamm" : "Stripped " + n + " Log");
            addBlock(ModBlocks.STRIPPED_WOODS.get(species), german ? "Entrindetes " + n + "holz" : "Stripped " + n + " Wood");
            addBlock(ModBlocks.LEAVES.get(species), german ? n + "laub" : n + " Leaves");
            addBlock(ModBlocks.SAPLINGS.get(species), german ? n + "setzling" : n + " Sapling");
            addBlock(ModBlocks.PLANKS.get(species), german ? n + "holzbretter" : n + " Planks");
            addBlock(ModBlocks.STAIRS.get(species), german ? n + "holztreppe" : n + " Stairs");
            addBlock(ModBlocks.SLABS.get(species), german ? n + "holzstufe" : n + " Slab");
            addBlock(ModBlocks.FENCES.get(species), german ? n + "zaun" : n + " Fence");
            addBlock(ModBlocks.FENCE_GATES.get(species), german ? n + "zauntor" : n + " Fence Gate");
            addBlock(ModBlocks.DOORS.get(species), german ? n + "tür" : n + " Door");
            addBlock(ModBlocks.TRAPDOORS.get(species), german ? n + "falltür" : n + " Trapdoor");
            addBlock(ModBlocks.PRESSURE_PLATES.get(species), german ? n + "druckplatte" : n + " Pressure Plate");
            addBlock(ModBlocks.BUTTONS.get(species), german ? n + "knopf" : n + " Button");
            addItem(ModItems.BOATS.get(species), german ? n + "boot" : n + " Boat");
            addItem(ModItems.CHEST_BOATS.get(species), german ? n + "boot mit Truhe" : n + " Boat with Chest");
            addEntityType(ModEntities.BOATS.get(species), german ? n + "boot" : n + " Boat");
            addEntityType(ModEntities.CHEST_BOATS.get(species), german ? n + "boot mit Truhe" : n + " Boat with Chest");
            addBlock(ModBlocks.SIGNS.get(species), german ? n + "schild" : n + " Sign");
            addBlock(ModBlocks.HANGING_SIGNS.get(species), german ? n + "-Hängeschild" : n + " Hanging Sign");
        }
    }
}
