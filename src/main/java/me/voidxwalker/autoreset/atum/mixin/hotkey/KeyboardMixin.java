package me.voidxwalker.autoreset.atum.mixin.hotkey;


import me.voidxwalker.autoreset.atum.Atum;
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

@Mixin(Minecraft.class)
public class KeyboardMixin {
    @Shadow
    public Screen currentScreen;
    long atum_lastHeld = 0;

    @Inject(method = "runGameLoop", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V", ordinal = 4, shift = At.Shift.AFTER))
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
