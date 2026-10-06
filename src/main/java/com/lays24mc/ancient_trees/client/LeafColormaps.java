package com.lays24mc.ancient_trees.client;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.TreeSpecies;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.client.resources.LegacyStuffWrapper;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public final class LeafColormaps extends SimplePreparableReloadListener<Map<TreeSpecies, int[]>> {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(AncientTrees.MOD_ID, "leaf_colormaps");

    /** Color if a colormap is missing: the default vanilla foliage color. */
    private static final int FALLBACK = -12012264;

    private static volatile Map<TreeSpecies, int[]> maps = Map.of();

    @Override
    protected Map<TreeSpecies, int[]> prepare(ResourceManager manager, ProfilerFiller profiler) {
        Map<TreeSpecies, int[]> loaded = new EnumMap<>(TreeSpecies.class);
        for (TreeSpecies species : TreeSpecies.values()) {
            if (species.leafColor().usesColormap()) {
                Identifier location = Identifier.fromNamespaceAndPath(AncientTrees.MOD_ID,
                        "textures/colormap/" + species.id() + ".png");
                try {
                    loaded.put(species, LegacyStuffWrapper.getPixels(manager, location));
                } catch (IOException e) {
                    AncientTrees.LOGGER.warn("Could not load colormap {}", location, e);
                }
            }
        }
        return loaded;
    }

    @Override
    protected void apply(Map<TreeSpecies, int[]> loaded, ResourceManager manager, ProfilerFiller profiler) {
        maps = loaded;
    }

    private static int pixel(TreeSpecies species, int x, int y) {
        int[] pixels = maps.get(species);
        if (pixels == null || pixels.length < 256 * 256) {
            return FALLBACK;
        }
        return pixels[(x & 0xff) << 8 | (y & 0xff)];
    }

    /** Color for item and hand: the middle of the colormap. */
    public static int inventory(TreeSpecies species) {
        return pixel(species, 0x80, 0x80);
    }

    /** Position and day determine the color (Acemus): the colors move through the colormap over the days. */
    public static int forAcemus(int x, int z, long gameTime) {
        int day = (int) (gameTime / 24000L) & 0xff;
        return pixel(TreeSpecies.ACEMUS, (x << 3) + day, (z << 3) + day);
    }

    /** Only the position determines the color (Cerasu, Kulist). */
    public static int forPosition(TreeSpecies species, int x, int y, int z) {
        return pixel(species, x + y, z + y);
    }
}
