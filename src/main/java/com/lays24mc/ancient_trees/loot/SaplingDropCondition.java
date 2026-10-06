package com.lays24mc.ancient_trees.loot;

import com.lays24mc.ancient_trees.config.AncientTreesConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.util.context.ContextKey;

public record SaplingDropCondition(Holder<Enchantment> enchantment, List<Float> chances) implements LootItemCondition {
    public static final MapCodec<SaplingDropCondition> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Enchantment.CODEC.fieldOf("enchantment").forGetter(SaplingDropCondition::enchantment),
            ExtraCodecs.nonEmptyList(Codec.FLOAT.listOf()).fieldOf("chances").forGetter(SaplingDropCondition::chances)
    ).apply(i, SaplingDropCondition::new));

    @Override
    public MapCodec<SaplingDropCondition> codec() {
        return MAP_CODEC;
    }

    @Override
    public Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.TOOL);
    }

    @Override
    public boolean test(LootContext context) {
        ItemInstance tool = context.getOptional(LootContextParams.TOOL);
        int level = tool != null ? EnchantmentHelper.getItemEnchantmentLevel(enchantment, tool) : 0;
        level = net.neoforged.neoforge.event.EventHooks.getBlockLootEnchantmentLevel(tool, enchantment, level, context);
        float chance = chances.get(Math.min(level, chances.size() - 1)) * AncientTreesConfig.saplingDropMultiplier();
        return context.getRandom().nextFloat() < chance;
    }

    public static LootItemCondition.Builder withFortune(Holder<Enchantment> enchantment, float... chances) {
        List<Float> list = new ArrayList<>(chances.length);
        for (float chance : chances) {
            list.add(chance);
        }
        return () -> new SaplingDropCondition(enchantment, list);
    }
}
