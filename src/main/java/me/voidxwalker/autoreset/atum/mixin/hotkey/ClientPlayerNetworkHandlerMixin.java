package me.voidxwalker.autoreset.atum.mixin.hotkey;

import me.voidxwalker.autoreset.atum.Atum;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.ServerPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPacketListener.class)
public class ClientPlayerNetworkHandlerMixin {
    @Inject(method = "onPlayerMove", at = @At("TAIL"))
    public void atum_trackInGame(PlayerMoveC2SPacket par1, CallbackInfo ci) {
        Atum.hotkeyState = Atum.HotkeyState.INSIDE_WORLD;
    }
}
