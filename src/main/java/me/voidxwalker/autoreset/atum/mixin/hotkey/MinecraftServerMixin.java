package me.voidxwalker.autoreset.atum.mixin.hotkey;


import me.voidxwalker.autoreset.atum.Atum;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "prepareWorlds", at = @At(value = "INVOKE", target = "Ljava/lang/System;currentTimeMillis()J", ordinal = 0))
    public void trackWorldGenStart(CallbackInfo ci) {
        Atum.hotkeyState = Atum.HotkeyState.WORLD_GEN;
    }
}
