package me.voidxwalker.autoreset.atum.mixin;

import me.voidxwalker.autoreset.atum.Atum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SettingsScreen.class)
public class SettingsScreenMixin extends Screen {

    // minecraft being silly
    @SuppressWarnings("unchecked")
    @Inject(method = "init", at = @At("TAIL"))
    public void addAutoResetButton(CallbackInfo ci) {

        if (Atum.isRunning) {
            this.buttons.add(new ButtonWidget(1238, 0, this.height - 20, 100, 20, "Stop Resets & Quit"));
        }
    }

    @Inject(method = "buttonClicked", at = @At("HEAD"))
    public void buttonClicked(ButtonWidget button, CallbackInfo ci) {
        if (button.id == 1238) {
            Atum.isRunning = false;
            Minecraft.getMinecraft().world.disconnect();
            Minecraft.getMinecraft().connect(null);
            Minecraft.getMinecraft().openScreen(new TitleScreen());
        }
    }
}