package xyz.tildejustin.atum.mixin;

import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ControlsOptionsScreen.class)
public abstract class ControlsOptionsScreenMixin {
    @ModifyArg(method = "init", at = @At(value = "", target = ""), index = 1)
    lowerOptionsDoneButton() {

    }
}
