package com.lays24mc.ancient_trees.registry;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.TreeSpecies;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = AncientTrees.MOD_ID)
public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AncientTrees.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + AncientTrees.MOD_ID))
                    .icon(() -> new ItemStack(ModBlocks.SAPLINGS.get(TreeSpecies.ACEMUS)))
                    .displayItems((parameters, output) ->
                            ModBlocks.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
                    .build());

    private ModCreativeTabs() {}

    /**
     * Also sorts the blocks into the vanilla tabs, directly after the last vanilla wood
     * (poplar), so they can be found next to the vanilla wood.
     */
    @SubscribeEvent
    public static void addToVanillaTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            List<ItemLike> items = new ArrayList<>();
            for (TreeSpecies species : TreeSpecies.values()) {
                items.add(ModBlocks.LOGS.get(species).get());
                items.add(ModBlocks.WOODS.get(species).get());
                items.add(ModBlocks.STRIPPED_LOGS.get(species).get());
                items.add(ModBlocks.STRIPPED_WOODS.get(species).get());
                items.add(ModBlocks.PLANKS.get(species).get());
                items.add(ModBlocks.STAIRS.get(species).get());
                items.add(ModBlocks.SLABS.get(species).get());
                items.add(ModBlocks.FENCES.get(species).get());
                items.add(ModBlocks.FENCE_GATES.get(species).get());
                items.add(ModBlocks.DOORS.get(species).get());
                items.add(ModBlocks.TRAPDOORS.get(species).get());
                items.add(ModBlocks.PRESSURE_PLATES.get(species).get());
                items.add(ModBlocks.BUTTONS.get(species).get());
            }
            insertAfter(event, Items.POPLAR_BUTTON, items);
        } else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            List<ItemLike> items = new ArrayList<>();
            for (TreeSpecies species : TreeSpecies.values()) {
                items.add(ModBlocks.SIGNS.get(species).get());
                items.add(ModBlocks.HANGING_SIGNS.get(species).get());
            }
            insertAfter(event, Items.POPLAR_HANGING_SIGN, items);
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            List<ItemLike> items = new ArrayList<>();
            for (TreeSpecies species : TreeSpecies.values()) {
                items.add(ModItems.BOATS.get(species).get());
                items.add(ModItems.CHEST_BOATS.get(species).get());
            }
            insertAfter(event, Items.POPLAR_CHEST_BOAT, items);
        } else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            insertAfter(event, Items.POPLAR_LOG, species(ModBlocks.LOGS));
            insertAfter(event, Items.YELLOW_POPLAR_LEAVES, species(ModBlocks.LEAVES));
            insertAfter(event, Items.POPLAR_SAPLING, species(ModBlocks.SAPLINGS));
        }
    }

    private static List<ItemLike> species(Map<TreeSpecies, ? extends Supplier<? extends Block>> blocks) {
        List<ItemLike> items = new ArrayList<>();
        for (TreeSpecies species : TreeSpecies.values()) {
            items.add(blocks.get(species).get());
        }
        return items;
    }

    private static void insertAfter(BuildCreativeModeTabContentsEvent event, ItemLike anchor, List<ItemLike> items) {
        ItemStack previous = new ItemStack(anchor);
        // If the vanilla anchor is missing (e.g. due to feature flags), append at the end instead of crashing
        boolean anchored = event.getParentEntries().contains(previous) && event.getSearchEntries().contains(previous);
        for (ItemLike item : items) {
            ItemStack stack = new ItemStack(item);
            if (anchored) {
                event.insertAfter(previous, stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            } else {
                event.accept(stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
            previous = stack;
        }
    }

    public static void register(IEventBus modEventBus) {
        TABS.register(modEventBus);
    }
}
