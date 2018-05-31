package io.inabsentia.celestialoutbreak.menu.settings;

import io.inabsentia.celestialoutbreak.controller.GameController;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;
import io.inabsentia.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class SettingsMenu extends Menu {

    private final Rectangle playerRect, customInfoRect;
    private final Font btnFont;

    private String[] options = {"PLAYER", "CUSTOMIZATION"};
    private Color[] rectColors;

    private Color rectColor, selectedColor;

    private int selected = 0;
    private int inputTimer = 18;
    private boolean isFirstUse = true;

    private final SoundHandler soundHandler;

    public SettingsMenu(GameController gameController, InputHandler inputHandler, SoundHandler soundHandler,
                        Color fontColor, Color rectColor, Color selectedColor) {
        super(gameController, inputHandler, fontColor);
        this.soundHandler = soundHandler;
        this.rectColor = rectColor;
        this.selectedColor = selectedColor;

        int initialBtnYPos = 230;
        int btnYInc = 75;

        /* buttons */
        playerRect = new Rectangle(gameController.getWidth() / 2 - 65, initialBtnYPos, 130, 50);
        customInfoRect = new Rectangle(gameController.getWidth() / 2 - 120, initialBtnYPos + btnYInc, 240, 50);

        rectColors = new Color[options.length];

        for (Color c : rectColors)
            c = rectColor;

        btnFont = utils.getGameFont().deriveFont(20F);
    }

    @Override
    public void update() {
        if (inputTimer > 0)
            inputTimer--;

        if (inputHandler.isCancelPressed() && inputTimer == 0) {
            if (isFirstUse) {
                isFirstUse = false;
                inputTimer = 10;
                return;
            }
            gameController.switchState(GameController.State.MAIN_MENU);
            isFirstUse = true;
            inputTimer = 10;
        }

        if (inputHandler.isDownPressed() && selected < options.length - 1 && inputTimer == 0) {
            selected++;
            soundHandler.MENU_BTN_SELECTION_CLIP.play(false);
            inputTimer = 10;
        }

        if (inputHandler.isUpPressed() && selected > 0 && inputTimer == 0) {
            selected--;
            soundHandler.MENU_BTN_SELECTION_CLIP.play(false);
            inputTimer = 10;
        }

        for (int i = 0, n = options.length; i < n; i++) {
            if (selected == i) {
                rectColors[i] = selectedColor;

                if (inputHandler.isUsePressed() && inputTimer == 0) {
                    switch (i) {
                        case 0:
                            // Player settings
                            gameController.switchState(GameController.State.PLAYER_SETTINGS_MENU);
                            break;
                        case 1:
                            // Customization settings
                            gameController.switchState(GameController.State.CUSTOM_SETTINGS_MENU);
                            break;
                        default:
                            break;
                    }
                }
            } else {
                rectColors[i] = rectColor;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        int yBtnOffset = 33;

        /* Render game title */
        drawMenuTitle(g);

        /* Show player name */
        drawSubmenuTitle("Settings", g);

        /* Render buttons  */
        g.setFont(btnFont);

        /* Play button */
        g.setColor(fontColor);
        drawXCenteredString(options[0], playerRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[0]);
        g.draw(playerRect);

        /* Score button */
        g.setColor(fontColor);
        drawXCenteredString(options[1], customInfoRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[1]);
        g.draw(customInfoRect);

        drawInformationPanel(g);
    }

}