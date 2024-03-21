package com.kryeit.staff.commands;

import com.kryeit.staff.commands.completion.SuggestionsProvider;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.awt.*;

public class EnderInvsee {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("enderinvsee")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(context -> {
                            EnderInvsee.openEnderChest(context.getSource().getEntity(), EntityArgumentType.getPlayer(context, "player"));
                            return 1;
                        })
                ));
    }

    public static void openEnderChest(Entity opener, Entity target) {
        if (opener instanceof ServerPlayerEntity playerOpener && target instanceof PlayerEntity playerTarget) {
            var title = Text.literal(playerTarget.getDisplayName().getString() + "'s Ender Chest");

            playerOpener.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                    (i, inventory, playerx) -> GenericContainerScreenHandler.createGeneric9x3(i, inventory, playerTarget.getEnderChestInventory()),
                    title
            ));
        }
    }
}
