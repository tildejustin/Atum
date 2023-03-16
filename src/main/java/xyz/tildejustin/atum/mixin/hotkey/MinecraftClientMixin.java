package xyz.tildejustin.atum.mixin.hotkey;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.LoadingScreenRenderer;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.tildejustin.atum.Atum;
import xyz.tildejustin.atum.Pingable;

@Mixin(Minecraft.class)
public abstract class MinecraftClientMixin {

    @Shadow
    @Nullable
    public Screen currentScreen;

    @Shadow
    public int width;

    @Shadow
    public int height;

    @Shadow
    public ClientWorld world;

    @Shadow
    public LoadingScreenRenderer loadingScreenRenderer;

    @Inject(method = "method_2935", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/class_469;<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/server/integrated/IntegratedServer;)V", shift = At.Shift.BEFORE))
    public void atum_trackPostWorldGen(CallbackInfo ci) {
        Atum.hotkeyState = Atum.HotkeyState.POST_WORLDGEN;
    }

    @Inject(method = "method_2935", at = @At(value = "HEAD"))
    public void atum_trackPreWorldGen(CallbackInfo ci) {
        Atum.hotkeyState = Atum.HotkeyState.PRE_WORLDGEN;
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void atum_tick(CallbackInfo ci) {
        if (Atum.hotkeyPressed) {
            if (this.loadingScreenRenderer != null && !((Pingable) (this.loadingScreenRenderer)).ping()) {
                throw new IllegalStateException();
            }
            if (Atum.hotkeyState == Atum.HotkeyState.INSIDE_WORLD || Atum.hotkeyState == Atum.HotkeyState.POST_WORLDGEN) {
                KeyBinding.setKeyPressed(Atum.resetKey.code, false);
                Atum.hotkeyPressed = false;
                Atum.isRunning = true;
                if (world != null) {
                    Minecraft.getMinecraft().world.disconnect();
                }
                Minecraft.getMinecraft().connect(null);
                Minecraft.getMinecraft().openScreen(new TitleScreen());
            } else if (Atum.hotkeyState == Atum.HotkeyState.OUTSIDE_WORLD) {
                System.out.println(1);
                KeyBinding.setKeyPressed(Atum.resetKey.code, false);
                Atum.hotkeyPressed = false;
                Atum.isRunning = true;
                Minecraft.getMinecraft().openScreen(new TitleScreen());
            }
        }
    }
}
