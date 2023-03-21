package me.voidxwalker.autoreset.atum.mixin;


import me.voidxwalker.autoreset.atum.Atum;
import net.minecraft.entity.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void preventLoop(CallbackInfo ci) {
        Atum.loopPrevent2 = true;
    }
}
