package me.voidxwalker.autoreset.atum.mixin.hotkey;

import me.voidxwalker.autoreset.atum.KeyBindingHelper;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Mutable
    @Shadow
    public KeyBinding[] allKeys;

    @Inject(at = @At("HEAD"), method = "load()V")
    public void loadHook(CallbackInfo info) {
        allKeys = KeyBindingHelper.process(allKeys);
    }

}
