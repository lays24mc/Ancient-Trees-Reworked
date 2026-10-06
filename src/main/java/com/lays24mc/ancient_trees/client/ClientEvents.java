package com.lays24mc.ancient_trees.client;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.registry.ModBlocks;
import com.lays24mc.ancient_trees.registry.ModEntities;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = AncientTrees.MOD_ID, value = Dist.CLIENT)
public final class ClientEvents {
    private ClientEvents() {}

    @SubscribeEvent
    public static void addReloadListeners(AddClientReloadListenersEvent event) {
        event.addListener(LeafColormaps.ID, new LeafColormaps());
    }

    private static ModelLayerLocation boatLayer(TreeSpecies species) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(AncientTrees.MOD_ID, "boat/" + species.id()), "main");
    }

    private static ModelLayerLocation chestBoatLayer(TreeSpecies species) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(AncientTrees.MOD_ID, "chest_boat/" + species.id()), "main");
    }

    @SubscribeEvent
    public static void registerBoatLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (TreeSpecies species : TreeSpecies.values()) {
            event.registerLayerDefinition(boatLayer(species), BoatModel::createBoatModel);
            event.registerLayerDefinition(chestBoatLayer(species), BoatModel::createChestBoatModel);
        }
    }

    @SubscribeEvent
    public static void registerBoatRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (TreeSpecies species : TreeSpecies.values()) {
            ModelLayerLocation boat = boatLayer(species);
            ModelLayerLocation chestBoat = chestBoatLayer(species);
            event.registerEntityRenderer(ModEntities.BOATS.get(species).get(), context -> new BoatRenderer(context, boat));
            event.registerEntityRenderer(ModEntities.CHEST_BOATS.get(species).get(), context -> new BoatRenderer(context, chestBoat));
        }
    }

    @SubscribeEvent
    public static void registerBlockTints(RegisterColorHandlersEvent.BlockTintSources event) {
        for (TreeSpecies species : TreeSpecies.values()) {
            BlockTintSource source = switch (species.leafColor()) {
                case NONE -> null;
                case FOLIAGE -> BlockTintSources.foliage();
                case ACEMUS -> colormapSource(species, (pos) ->
                        LeafColormaps.forAcemus(pos.getX(), pos.getZ(), gameTime()));
                case CERASU, KULIST -> colormapSource(species, (pos) ->
                        LeafColormaps.forPosition(species, pos.getX(), pos.getY(), pos.getZ()));
            };
            if (source != null) {
                event.register(List.of(source), ModBlocks.LEAVES.get(species).get());
            }
        }
    }

    private static long gameTime() {
        var level = Minecraft.getInstance().level;
        return level == null ? 0L : level.getGameTime();
    }

    @FunctionalInterface
    private interface PositionColor {
        int color(BlockPos pos);
    }

    private static BlockTintSource colormapSource(TreeSpecies species, PositionColor color) {
        return new BlockTintSource() {
            @Override
            public int color(BlockState state) {
                return LeafColormaps.inventory(species);
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return color.color(pos);
            }
        };
    }
}
