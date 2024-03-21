package com.kryeit.staff.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.mixin.container.ServerPlayerEntityAccessor;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class Invsee {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("invsee")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(context -> {
                            Invsee.openInventory(context.getSource().getEntity(), EntityArgumentType.getPlayer(context, "player"));
                            return 1;
                        })
                ));

    }

    public static void openInventory(Entity opener, Entity target) {
        if (opener instanceof ServerPlayerEntity playerOpener && target instanceof PlayerEntity playerTarget) {
            var title = Text.literal(playerTarget.getDisplayName().getString() + "'s Inventory");

            playerOpener.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                    (i, inventory, playerx) -> GenericContainerScreenHandler.createGeneric9x3(i, inventory, playerTarget.getInventory()),
                    title
            ));
        }
    }
}