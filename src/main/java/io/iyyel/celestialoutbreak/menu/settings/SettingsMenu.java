package io.iyyel.celestialoutbreak.menu.settings;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class SettingsMenu extends Menu {

    private final Rectangle playerRect, configurationRect;
    private final Font btnFont;

    private String[] options = {"PLAYER", "CONFIGURATION"};
    private Color[] rectColors;

    private int selected = 0;
    private int inputTimer = 18;

    public SettingsMenu(GameController gameController) {
        super(gameController);

        int initialBtnYPos = 230;
        int btnYInc = 75;

        /* buttons */
        playerRect = new Rectangle(gameController.getWidth() / 2 - 120, initialBtnYPos, 240, 50);
        configurationRect = new Rectangle(gameController.getWidth() / 2 - 120, initialBtnYPos + btnYInc, 240, 50);

        rectColors = new Color[options.length];

        for (Color c : rectColors)
            c = menuBtnColor;

        btnFont = utils.getGameFont().deriveFont(20F);
    }

    @Override
    public void update() {
        if (inputTimer > 0)
            inputTimer--;

        if (inputHandler.isCancelPressed() && inputTimer == 0) {
            gameController.switchState(GameController.State.MAIN_MENU);
            inputTimer = 10;
        }

        if (inputHandler.isDownPressed() && selected < options.length - 1 && inputTimer == 0) {
            selected++;
            menuNavClip.play(false);
            inputTimer = 10;
        }

        if (inputHandler.isUpPressed() && selected > 0 && inputTimer == 0) {
            selected--;
            menuNavClip.play(false);
            inputTimer = 10;
        }

        for (int i = 0, n = options.length; i < n; i++) {
            if (selected == i) {
                rectColors[i] = menuSelectedBtnColor;

                if (inputHandler.isUsePressed() && inputTimer == 0) {
                    menuUseClip.play(false);
                    inputTimer = 10;

                    switch (i) {
                        case 0:
                            // Player settings
                            gameController.switchState(GameController.State.PLAYER_SETTINGS_MENU);
                            break;
                        case 1:
                            // Customization settings
                            gameController.switchState(GameController.State.CONFIG_SETTINGS_MENU);
                            break;
                        default:
                            break;
                    }
                }
            } else {
                rectColors[i] = menuBtnColor;
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
        g.setColor(menuFontColor);
        drawXCenteredString(options[0], playerRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[0]);
        g.draw(playerRect);

        /* Score button */
        g.setColor(menuFontColor);
        drawXCenteredString(options[1], configurationRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[1]);
        g.draw(configurationRect);

        drawInformationPanel(g);
    }

}