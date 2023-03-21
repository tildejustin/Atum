package me.voidxwalker.autoreset.atum.mixin;

import me.voidxwalker.autoreset.atum.Atum;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;
import net.minecraft.world.level.LevelGeneratorType;
import net.minecraft.world.level.LevelInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;


@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin extends Screen {
    @Shadow
    private TextFieldWidget levelNameField;
    @Shadow
    private boolean creatingLevel;
    @Shadow
    private boolean structures;
    @Shadow
    private boolean hardcore;
    @Shadow
    private int generatorType;
    @Shadow
    private boolean bonusChest;
    @Shadow
    private boolean tweakedCheats;
    @Shadow
    private String saveDirectoryName;
    @Shadow
    private String gamemodeName;

    @Inject(method = "init", at = @At("TAIL"))
    private void createDesiredWorld(CallbackInfo info) {
        if (Atum.isRunning) {
            if (Atum.difficulty == -1) {
                hardcore = true;
            }

            createLevel();
        }
    }

    private void createLevel() {
        this.field_1229.openScreen((Screen) null);
        if (this.creatingLevel) {
            return;
        }
        this.creatingLevel = true;
        long l = (new Random()).nextLong();
        String string = Atum.seed;
        if (!MathHelper.isEmpty(string)) {
            try {
                long m = Long.parseLong(string);
                if (m != 0L) {
                    l = m;
                }
            } catch (NumberFormatException var7) {
                l = string.hashCode();
            }
        }
        if (Atum.seed == null || Atum.seed.isEmpty()) {
            Atum.rsgAttempts++;
        } else {
            Atum.ssgAttempts++;
        }
        LevelInfo levelInfo = new LevelInfo(l, GameMode.setGameModeWithString(this.gamemodeName), Atum.structures, this.hardcore, LevelGeneratorType.TYPES[Atum.generatorType]);
        if (Atum.bonusChest && !this.hardcore) {
            levelInfo.setBonusChest();
        }
        if (this.tweakedCheats && !this.hardcore) {
            levelInfo.enableCommands();
        }
        Atum.saveProperties();
        Atum.log((Atum.seed == null || Atum.seed.isEmpty() ? "Resetting a random seed" : "Resetting the set seed" + "\"" + l + "\""));
        levelNameField.setText((Atum.seed == null || Atum.seed.isEmpty()) ? "Random Speedrun #" + Atum.rsgAttempts : "Set Speedrun #" + Atum.ssgAttempts);
        this.field_1229.method_2935(levelNameField.getText().trim(), levelNameField.getText().trim(), levelInfo);


    }
}
