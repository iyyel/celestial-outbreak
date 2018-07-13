package io.iyyel.celestialoutbreak.menu.main_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class SettingsMenu extends AbstractMenu {

    private final Rectangle playerRect, configurationRect;
    private final Font btnFont;

    private String[] options = {textHandler.BTN_PLAYER_TEXT, textHandler.BTN_CONFIGURATION_TEXT};
    private Color[] rectColors;

    private int selected = 0;
    private int yBtnOffset = 33;

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
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            menuUseClip.play(false);
            gameController.switchState(GameController.State.MAIN_MENU);
        }

        if (inputHandler.isDownPressed() && selected < options.length - 1 && isInputAvailable()) {
            resetInputTimer();
            selected++;
            menuNavClip.play(false);
        }

        if (inputHandler.isUpPressed() && selected > 0 && isInputAvailable()) {
            resetInputTimer();
            selected--;
            menuNavClip.play(false);
        }

        for (int i = 0, n = options.length; i < n; i++) {
            if (selected == i) {
                rectColors[i] = menuSelectedBtnColor;

                if (inputHandler.isOKPressed() && isInputAvailable()) {
                    menuUseClip.play(false);
                    resetInputTimer();

                    switch (i) {
                        case 0:
                            // Player settings
                            gameController.switchState(GameController.State.PLAYER_SETTINGS_MENU);
                            break;
                        case 1:
                            // Configuration settings
                            gameController.switchState(GameController.State.CONFIG_SETTINGS_SCREEN);
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
        /* Render game title */
        drawMenuTitle(g);

        /* Show player name */
        drawSubmenuTitle("Settings", g);

        /* Render buttons  */
        g.setFont(btnFont);

        /* Play button */
        g.setColor(menuFontColor);
        drawCenterString(options[0], playerRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[0]);
        g.draw(playerRect);

        /* Score button */
        g.setColor(menuFontColor);
        drawCenterString(options[1], configurationRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[1]);
        g.draw(configurationRect);

        drawInformationPanel(g);
    }

}