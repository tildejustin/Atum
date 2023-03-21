package me.voidxwalker.autoreset.atum.mixin;


import me.voidxwalker.autoreset.atum.Atum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.voidxwalker.autoreset.atum.screen.AutoResetOptionScreen;


@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    private String difficulty;


    @SuppressWarnings("unchecked")
    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        if (Atum.isRunning && Atum.loopPrevent2) {
            Atum.loopPrevent2 = false;
            Minecraft.getMinecraft().openScreen(new CreateWorldScreen(this));
        } else {
            Atum.hotkeyState = Atum.HotkeyState.OUTSIDE_WORLD;
            this.buttons.add(new ButtonWidget(69, this.width / 2 - 124, this.height / 4 + 48, 20, 20, ""));
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void goldBootsOverlay(int mouseX, int mouseY, float delta, CallbackInfo ci) {
        getDifficulty();

        Minecraft.getMinecraft().textureManager.bindTexture(Minecraft.getMinecraft().textureManager.getTextureFromPath("/gui/items.png"));
        drawTexture(this.width / 2 - 124+2, this.height / 4 + 48+2, 4*16, 3*16, 16, 16);
//        you should do this, but it blacks out the whole screen when called with an item so...no
//          new ItemRenderer().method_1546(Minecraft.getMinecraft().textRenderer, Minecraft.getMinecraft().textureManager, new ItemStack(Item.field_4276), this.width / 2 - 124+2, this.height / 4 + 48+2);

        if (mouseX > this.width / 2 - 124 && mouseX < this.width / 2 - 124 + 20 && mouseY > this.height / 4 + 48 && mouseY < this.height / 4 + 48 + 20 && hasShiftDown()) {
            drawCenteredString(Minecraft.getMinecraft().textRenderer, difficulty, this.width / 2 - 124 + 11, this.height / 4 + 48 - 15, 16777215);
        }
    }

    @Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
    public void buttonClicked(ButtonWidget button, CallbackInfo ci) {
        if (button.id == 69) {
            if (hasShiftDown()) {
                Minecraft.getMinecraft().openScreen(new AutoResetOptionScreen(null));
            } else {
                Atum.isRunning = true;
                Minecraft.getMinecraft().openScreen(null);
            }
            ci.cancel();
        }

    }

    private void getDifficulty() {
        if (Atum.difficulty == -1) {
            difficulty = "Hardcore: ON";
        } else {
            difficulty = "Hardcore: OFF";
        }

    }

}
