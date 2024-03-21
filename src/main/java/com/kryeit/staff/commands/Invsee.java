package com.kryeit.staff.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.mixin.container.ServerPlayerEntityAccessor;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
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

    public static GenericContainerScreenHandler createGeneric9x4(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X4, syncId, playerInventory, inventory, 4);
    }

    public static void openInventory(Entity opener, Entity target) {
        if (opener instanceof ServerPlayerEntity playerOpener && target instanceof PlayerEntity playerTarget) {
            var title = Text.literal(playerTarget.getDisplayName().getString() + "'s Inventory");

            playerOpener.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                    (i, inventory, playerx) -> createGeneric9x4(i, inventory, playerTarget.getInventory()),
                    title
            ));
        }
    }
}