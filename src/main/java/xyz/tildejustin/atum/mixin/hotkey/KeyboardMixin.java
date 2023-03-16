package xyz.tildejustin.atum.mixin.hotkey;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.tildejustin.atum.Atum;

@Mixin(Minecraft.class)
public class KeyboardMixin {
    @Shadow
    public Screen currentScreen;
    long atum_lastHeld = 0;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 5))
    public void atum_onKey(CallbackInfo ci) {
        if (System.currentTimeMillis() - atum_lastHeld > 1000) {
            Atum.hotkeyHeld = false;
        }
        if (Keyboard.isKeyDown(Atum.resetKey.code) && !(this.currentScreen instanceof ControlsOptionsScreen) && !Atum.hotkeyHeld) {
            Atum.hotkeyHeld = true;
            atum_lastHeld = System.currentTimeMillis();
            KeyBinding.setKeyPressed(Atum.resetKey.code, true);
            Atum.hotkeyPressed = true;

        } else {
            Atum.hotkeyHeld = false;
        }
    }
}
