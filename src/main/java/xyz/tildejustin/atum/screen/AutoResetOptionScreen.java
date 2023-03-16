package xyz.tildejustin.atum.screen;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.Language;
import net.minecraft.world.level.LevelGeneratorType;
import org.jetbrains.annotations.Nullable;
import xyz.tildejustin.atum.Atum;


public class AutoResetOptionScreen extends Screen {
    private final Screen parent;
    private TextFieldWidget seedField;
    private String seed;
    private boolean isHardcore;
    private int generatorType;
    private boolean structures;
    private final String title;
    private boolean bonusChest;

    public AutoResetOptionScreen(@Nullable Screen parent) {
        super();
        title = Atum.getTranslation("menu.autoresetTitle", "Autoreset Options");
        this.parent = parent;
    }

    public void init() {

        this.isHardcore = Atum.difficulty == -1;
        this.seedField = new TextFieldWidget(Minecraft.getMinecraft().textRenderer, this.width / 2 - 100, this.height - 160, 200, 20) {
        };
        this.seedField.setText(Atum.seed == null ? "" : Atum.seed);
        this.seedField.setFocused(true);
        this.seed = Atum.seed;
        this.generatorType = Atum.generatorType;
        this.structures = Atum.structures;
        this.bonusChest = Atum.bonusChest;

        this.buttons.add(new ButtonWidget(340, this.width / 2 + 5, this.height - 100, 150, 20, "Is Hardcore: " + isHardcore));
        this.buttons.add(new ButtonWidget(341, this.width / 2 - 155, this.height - 100, 150, 20, Language.getInstance().translate("selectWorld.mapType") + " " + Language.getInstance().translate(LevelGeneratorType.TYPES[generatorType].getTranslationKey())));

        this.buttons.add(new ButtonWidget(342, this.width / 2 - 155, this.height - 64, 150, 20, Language.getInstance().translate("selectWorld.mapFeatures") + " " + structures));

        this.buttons.add(new ButtonWidget(344, this.width / 2 + 5, this.height - 64, 150, 20, Language.getInstance().translate("selectWorld.bonusItems") + " " + bonusChest));

        this.buttons.add(new ButtonWidget(345, this.width / 2 - 155, this.height - 28, 150, 20, Atum.getTranslation("menu.done", "Done")));
        this.buttons.add(new ButtonWidget(343, this.width / 2 + 5, this.height - 28, 150, 20, Language.getInstance().translate("gui.cancel")));
    }

    public void tick() {
        seedField.tick();
    }

    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        drawCenteredString(Minecraft.getMinecraft().textRenderer, this.title, this.width / 2, this.height - 210, -1);
        this.drawWithShadow(Minecraft.getMinecraft().textRenderer, "Seed (Leave empty for a random seed)", this.width / 2 - 100, this.height - 180, -6250336);

        this.seedField.render();
        super.render(mouseX, mouseY, delta);
    }


    public void keyPressed(char character, int code) {
        if (this.seedField.isFocused()) {
            this.seedField.keyPressed(character, code);
            this.seed = this.seedField.getText();

        }


    }

    protected void buttonClicked(ButtonWidget button) {
        switch (button.id) {
            case 340:
                isHardcore = !isHardcore;
                button.message = ("Is Hardcore: " + isHardcore);
                break;
            case 341:
                ++generatorType;
                if (generatorType >= LevelGeneratorType.TYPES.length) {
                    generatorType = 0;
                }
                while (LevelGeneratorType.TYPES[this.generatorType] == null || !LevelGeneratorType.TYPES[this.generatorType].isVisible()) {
                    ++this.generatorType;
                    if (this.generatorType < LevelGeneratorType.TYPES.length) continue;
                    this.generatorType = 0;
                }
                button.message = (Language.getInstance().translate("selectWorld.mapType") + " " + Language.getInstance().translate(LevelGeneratorType.TYPES[generatorType].getTranslationKey()));
                break;
            case 342:
                structures = !structures;
                button.message = (Language.getInstance().translate("selectWorld.mapFeatures") + " " + structures);
                break;
            case 344:
                bonusChest = !bonusChest;
                button.message = (Language.getInstance().translate("selectWorld.bonusItems") + " " + bonusChest);
                break;
            case 345:
                Atum.seed = seed;
                Atum.difficulty = isHardcore ? -1 : 0;
                Atum.structures = structures;
                Atum.bonusChest = bonusChest;
                Atum.generatorType = generatorType;
                Atum.saveProperties();
                Minecraft.getMinecraft().openScreen(parent);
                break;
            case 343:
                Minecraft.getMinecraft().openScreen(parent);
                break;

        }
    }
}
