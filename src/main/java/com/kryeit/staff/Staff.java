package com.kryeit.staff;

import com.kryeit.staff.commands.EnderInvsee;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Staff implements DedicatedServerModInitializer {


    @Override
    public void onInitializeServer() {

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicatedServer, commandFunction) -> {
            EnderInvsee.register(dispatcher);

        });
    }
}
