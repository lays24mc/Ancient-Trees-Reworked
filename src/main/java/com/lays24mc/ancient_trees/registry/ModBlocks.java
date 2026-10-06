package com.lays24mc.ancient_trees.registry;

import com.lays24mc.ancient_trees.AncientTrees;
import com.lays24mc.ancient_trees.TreeSpecies;
import com.lays24mc.ancient_trees.block.FlammableBlock;
import com.lays24mc.ancient_trees.block.FlammableFenceBlock;
import com.lays24mc.ancient_trees.block.FlammableFenceGateBlock;
import com.lays24mc.ancient_trees.block.FlammableLeavesBlock;
import com.lays24mc.ancient_trees.block.FlammableLogBlock;
import com.lays24mc.ancient_trees.block.FlammableSlabBlock;
import com.lays24mc.ancient_trees.block.FlammableStairBlock;
import com.lays24mc.ancient_trees.block.Flammability;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.world.item.BlockItem;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(AncientTrees.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AncientTrees.MOD_ID);

    public static final Map<TreeSpecies, DeferredBlock<FlammableLogBlock>> LOGS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<FlammableLogBlock>> WOODS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<FlammableLogBlock>> STRIPPED_LOGS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<FlammableLogBlock>> STRIPPED_WOODS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<FlammableLeavesBlock>> LEAVES = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<SaplingBlock>> SAPLINGS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<FlammableBlock>> PLANKS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<FlammableStairBlock>> STAIRS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<FlammableSlabBlock>> SLABS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<FlammableFenceBlock>> FENCES = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<FlammableFenceGateBlock>> FENCE_GATES = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<DoorBlock>> DOORS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<TrapDoorBlock>> TRAPDOORS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<PressurePlateBlock>> PRESSURE_PLATES = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<ButtonBlock>> BUTTONS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<StandingSignBlock>> SIGNS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<WallSignBlock>> WALL_SIGNS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<CeilingHangingSignBlock>> HANGING_SIGNS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredBlock<WallHangingSignBlock>> WALL_HANGING_SIGNS = new EnumMap<>(TreeSpecies.class);
    public static final Map<TreeSpecies, DeferredItem<BlockItem>> LOG_ITEMS = new EnumMap<>(TreeSpecies.class);

    static {
        ModWoodTypes.init();
        for (TreeSpecies species : TreeSpecies.values()) {
            String id = species.id();
            var setType = ModWoodTypes.BLOCK_SET_TYPES.get(species);
            var woodType = ModWoodTypes.WOOD_TYPES.get(species);

            var planks = BLOCKS.registerBlock(id + "_planks", p -> new FlammableBlock(p, Flammability.PLANKS), p -> p
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava());
            PLANKS.put(species, planks);

            // Log, wood (bark on all sides) and the stripped variants share their properties
            LOGS.put(species, registerLog(id + "_log"));
            WOODS.put(species, registerLog(id + "_wood"));
            STRIPPED_LOGS.put(species, registerLog(id + "_stripped_log"));
            STRIPPED_WOODS.put(species, registerLog(id + "_stripped_wood"));

            LEAVES.put(species, BLOCKS.registerBlock(id + "_leaves", p -> new FlammableLeavesBlock(0.01F, p, Flammability.LEAVES), p -> p
                    .mapColor(MapColor.PLANT)
                    .strength(0.2F)
                    .randomTicks()
                    .sound(SoundType.GRASS)
                    .noOcclusion()
                    .isValidSpawn((state, level, pos, type) -> false)
                    .isSuffocating((state, level, pos) -> false)
                    .ignitedByLava()
                    .pushReaction(PushReaction.POPPED)
                    .isRedstoneConductor((state, level, pos) -> false)));

            SAPLINGS.put(species, BLOCKS.registerBlock(id + "_sapling", p -> new SaplingBlock(ModTreeGrowers.get(species), p), p -> p
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.POPPED)));

            STAIRS.put(species, BLOCKS.registerBlock(id + "_stairs",
                    p -> new FlammableStairBlock(planks.get().defaultBlockState(), p, Flammability.PLANKS),
                    p -> BlockBehaviour.Properties.ofFullCopy(planks.get())));

            SLABS.put(species, BLOCKS.registerBlock(id + "_slab", p -> new FlammableSlabBlock(p, Flammability.PLANKS),
                    p -> BlockBehaviour.Properties.ofFullCopy(planks.get())));

            // Properties as for the vanilla wood variants
            FENCES.put(species, BLOCKS.registerBlock(id + "_fence", p -> new FlammableFenceBlock(p, Flammability.PLANKS), p -> p
                    .mapColor(MapColor.WOOD)
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

            FENCE_GATES.put(species, BLOCKS.registerBlock(id + "_fence_gate",
                    p -> new FlammableFenceGateBlock(woodType, p, Flammability.PLANKS), p -> p
                            .mapColor(MapColor.WOOD)
                            .forceSolidOn()
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(2.0F, 3.0F)
                            .ignitedByLava()));

            DOORS.put(species, BLOCKS.registerBlock(id + "_door", p -> new DoorBlock(setType, p), p -> p
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(3.0F)
                    .noOcclusion()
                    .ignitedByLava()
                    .pushReaction(PushReaction.POPPED)));

            TRAPDOORS.put(species, BLOCKS.registerBlock(id + "_trapdoor", p -> new TrapDoorBlock(setType, p), p -> p
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(3.0F)
                    .noOcclusion()
                    .isValidSpawn((state, level, pos, type) -> false)
                    .ignitedByLava()));

            PRESSURE_PLATES.put(species, BLOCKS.registerBlock(id + "_pressure_plate", p -> new PressurePlateBlock(setType, p), p -> p
                    .mapColor(MapColor.WOOD)
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollision()
                    .strength(0.5F)
                    .ignitedByLava()
                    .pushReaction(PushReaction.POPPED)));

            // Signs: the wall sign uses the loot and name of the standing sign (as in vanilla)
            var sign = BLOCKS.registerBlock(id + "_sign", p -> new StandingSignBlock(woodType, p), p -> signProperties(p));
            SIGNS.put(species, sign);
            WALL_SIGNS.put(species, BLOCKS.registerBlock(id + "_wall_sign", p -> new WallSignBlock(woodType, p),
                    p -> signProperties(p
                            .overrideLootTable(sign.get().getLootTable())
                            .overrideDescription(sign.get().getDescriptionId()))));
            var hangingSign = BLOCKS.registerBlock(id + "_hanging_sign", p -> new CeilingHangingSignBlock(woodType, p), p -> signProperties(p));
            HANGING_SIGNS.put(species, hangingSign);
            WALL_HANGING_SIGNS.put(species, BLOCKS.registerBlock(id + "_wall_hanging_sign", p -> new WallHangingSignBlock(woodType, p),
                    p -> signProperties(p
                            .overrideLootTable(hangingSign.get().getLootTable())
                            .overrideDescription(hangingSign.get().getDescriptionId()))));

            BUTTONS.put(species, BLOCKS.registerBlock(id + "_button", p -> new ButtonBlock(setType, 30, p), p -> p
                    .noCollision()
                    .strength(0.5F)
                    .pushReaction(PushReaction.POPPED)));
        }

        // Register block items after the blocks (order = order in the creative tab)
        for (TreeSpecies species : TreeSpecies.values()) {
            LOG_ITEMS.put(species, ITEMS.registerSimpleBlockItem(LOGS.get(species), p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS)));
            ITEMS.registerSimpleBlockItem(WOODS.get(species), p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS));
            ITEMS.registerSimpleBlockItem(STRIPPED_LOGS.get(species), p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS));
            ITEMS.registerSimpleBlockItem(STRIPPED_WOODS.get(species), p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS));
            ITEMS.registerSimpleBlockItem(PLANKS.get(species), p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS));
            ITEMS.registerSimpleBlockItem(STAIRS.get(species), p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS));
            ITEMS.registerSimpleBlockItem(SLABS.get(species), p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_SLABS));
            ITEMS.registerSimpleBlockItem(FENCES.get(species), p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS));
            ITEMS.registerSimpleBlockItem(FENCE_GATES.get(species), p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS));
            // Doors are two blocks tall and need the matching item
            ITEMS.registerItem(species.id() + "_door", p -> new DoubleHighBlockItem(DOORS.get(species).get(), p),
                    () -> new Item.Properties().useBlockDescriptionPrefix()
                            .cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_ITEMS_LARGE));
            ITEMS.registerSimpleBlockItem(TRAPDOORS.get(species), p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS));
            ITEMS.registerSimpleBlockItem(PRESSURE_PLATES.get(species), p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS));
            ITEMS.registerSimpleBlockItem(BUTTONS.get(species), p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_ITEMS_EXTRA_SMALL));
            ITEMS.registerItem(species.id() + "_sign",
                    p -> new StandingAndWallBlockItem(SIGNS.get(species).get(), WALL_SIGNS.get(species).get(), Direction.DOWN, p),
                    () -> new Item.Properties().useBlockDescriptionPrefix().stacksTo(16).signText()
                            .cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_ITEMS_LARGE));
            ITEMS.registerItem(species.id() + "_hanging_sign",
                    p -> new HangingSignItem(HANGING_SIGNS.get(species).get(), WALL_HANGING_SIGNS.get(species).get(), p),
                    () -> new Item.Properties().useBlockDescriptionPrefix().stacksTo(16).signText()
                            .cookingFuel(ContextIntProviders.COOKING_TIME_HANGING_SIGNS));
            ITEMS.registerSimpleBlockItem(LEAVES.get(species), p -> p.compostable(ContextIntProviders.COMPOSTABLE_LOW));
            ITEMS.registerSimpleBlockItem(SAPLINGS.get(species), p -> p
                    .compostable(ContextIntProviders.COMPOSTABLE_LOW)
                    .cookingFuel(ContextIntProviders.COOKING_TIME_DRY_PLANTS));
        }
    }

    private ModBlocks() {}

    private static BlockBehaviour.Properties signProperties(BlockBehaviour.Properties p) {
        return p.mapColor(MapColor.WOOD)
                .forceSolidOn()
                .instrument(NoteBlockInstrument.BASS)
                .noCollision()
                .strength(1.0F)
                .ignitedByLava();
    }

    private static DeferredBlock<FlammableLogBlock> registerLog(String name) {
        return BLOCKS.registerBlock(name, p -> new FlammableLogBlock(p, Flammability.LOG), p -> p
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava());
    }

    /** All blocks of the mod, grouped by species. */
    public static Stream<DeferredBlock<? extends Block>> all() {
        return Stream.of(LOGS, WOODS, STRIPPED_LOGS, STRIPPED_WOODS, PLANKS, STAIRS, SLABS, FENCES, FENCE_GATES, DOORS, TRAPDOORS, PRESSURE_PLATES, BUTTONS,
                SIGNS, WALL_SIGNS, HANGING_SIGNS, WALL_HANGING_SIGNS, LEAVES, SAPLINGS)
                .flatMap(map -> map.values().stream());
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
    }
}
