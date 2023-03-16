package xyz.tildejustin.atum.mixin;


import net.minecraft.client.Minecraft;
import net.minecraft.client.render.LoadingScreenRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.tildejustin.atum.Atum;
import xyz.tildejustin.atum.Pingable;

@Mixin(value = LoadingScreenRenderer.class, priority = 1100) // Hello WorldPreview, don't let us get in your way
public class LoadingScreenRendererMixin implements Pingable {

    @Shadow
    private Minecraft client;


    @Inject(method = "setProgressPercentage", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;method_956(Ljava/lang/String;III)I", ordinal = 1, shift = At.Shift.AFTER))
    public void renderSeed(int percentage, CallbackInfo ci) {
        if (Atum.isRunning && Atum.seed != null && !Atum.seed.isEmpty()) {
            int j = client.width;
            int k = client.height;
            String string = Atum.seed;
            this.client.textRenderer.method_956(string, (j - this.client.textRenderer.getStringWidth(string)) / 2, k / 2 - 4 - 40, 0xFFFFFF);
        }
    }

    @Override
    public boolean ping() {
        return true;
    }
}
