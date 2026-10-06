package com.lays24mc.ancient_trees.item;

import com.lays24mc.ancient_trees.AncientTrees;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class ParcelItem extends Item {
    public static final ResourceKey<LootTable> CONTENT = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(AncientTrees.MOD_ID, "gameplay/parcel"));

    public ParcelItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack parcel = player.getItemInHand(hand);
        if (level instanceof ServerLevel serverLevel) {
            LootTable table = serverLevel.getServer().reloadableRegistries().getLootTable(CONTENT);
            LootParams params = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, player.position())
                    .withParameter(LootContextParams.THIS_ENTITY, player)
                    .create(LootContextParamSets.GIFT);
            List<ItemStack> contents = table.getRandomItems(params);

            if (contents.isEmpty()) {
                player.sendSystemMessage(Component.translatable("item.ancient_trees.parcel.empty"));
            }
            for (ItemStack content : contents) {
                player.sendSystemMessage(Component.translatable("item.ancient_trees.parcel.full", content.getHoverName()));
                if (!player.getInventory().add(content.copy())) {
                    player.drop(content.copy(), false, net.minecraft.util.Prediction.SERVER_ONLY);
                }
            }
            parcel.consume(1, player);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display,
            Consumer<Component> builder, TooltipFlag flag) {
        builder.accept(Component.translatable("item.ancient_trees.parcel.tooltip"));
    }
}
