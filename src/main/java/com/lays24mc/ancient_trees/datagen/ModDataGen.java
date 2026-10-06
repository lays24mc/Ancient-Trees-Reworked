package com.lays24mc.ancient_trees.datagen;

import com.lays24mc.ancient_trees.AncientTrees;
import java.util.List;
import java.util.Set;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@EventBusSubscriber(modid = AncientTrees.MOD_ID)
public final class ModDataGen {
    private ModDataGen() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createWorldRegistryObjects(new RegistrySetBuilder()
                .add(Registries.FEATURE, ModFeatureProvider::bootstrap)
                .add(Registries.PLACED_FEATURE, ModWorldgenProvider::placedFeatures)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModWorldgenProvider::biomeModifiers));

        event.createReloadableRegistryObjects(new RegistrySetBuilder()
                .add(RecipeProvider.asBootstrap(ModRecipeProvider::new))
                .add(Registries.LOOT_TABLE, new LootTableProvider(Set.of(), List.of(
                        new LootTableProvider.SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(ParcelLoot::new, LootContextParamSets.GIFT)))));

        event.createBlockAndItemTags(ModBlockTagsProvider::new, ModItemTagsProvider::new);
        event.createProvider(output -> new ModModelProvider(output));
        event.createProvider(ModDataMapProvider::new);
        event.createProvider(output -> new ModLanguageProvider(output, "en_us"));
        event.createProvider(output -> new ModLanguageProvider(output, "de_de"));
    }
}
