package xyz.tildejustin.atum.mixin.hotkey;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerPositionLookC2SPacket;
import net.minecraft.server.ServerPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.tildejustin.atum.Atum;

@Mixin(ServerPacketListener.class)
public class ClientPlayerNetworkHandlerMixin {
    @Inject(method = "onPlayerMove", at = @At("TAIL"))
    public void atum_trackInGame(PlayerMoveC2SPacket par1, CallbackInfo ci) {
        Atum.hotkeyState = Atum.HotkeyState.INSIDE_WORLD;
    }
}
