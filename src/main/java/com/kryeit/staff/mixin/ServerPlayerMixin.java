package com.kryeit.staff.mixin;

import com.kryeit.staff.MinecraftServerSupplier;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// This class has been mostly made by afkdisplay mod
// https://github.com/beabfc/afkdisplay
@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin {


    @Inject(method = "sendPickup", at = @At("RETURN"))
    private void onPickUp(Entity item, int count, CallbackInfo ci) {
        if (!item.getName().getString().toLowerCase().contains("creative")) return;
        MinecraftServerSupplier.getServer().getPlayerManager().broadcast(Text.literal(
                "Player " + ((ServerPlayerEntity) (Object) this).getName().getString() + " picked up a creative item. Report it to Staff."
        ), false);
    }
}

