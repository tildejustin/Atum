package me.voidxwalker.autoreset.atum.mixin;


import me.voidxwalker.autoreset.atum.Atum;
import me.voidxwalker.autoreset.atum.Pingable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class DebugHudMixin extends DrawableHelper implements Pingable {

    @Inject(
            method = {"method_979"},
            at = {@At("TAIL")}
    )
    private void getRightText(float bl, boolean i, int j, int par4, CallbackInfo ci) {
        if (Atum.isRunning && Minecraft.getMinecraft().options.debugEnabled) {
            this.drawWithShadow(Minecraft.getMinecraft().textRenderer, "Resetting" + (Atum.seed == null || Atum.seed.isEmpty() ? " a random seed" : (" the seed: \"" + Atum.seed + "\"")), 2, 114, 14737632);
        }

    }

    @Override
    public boolean ping() {
        return true;
    }
}