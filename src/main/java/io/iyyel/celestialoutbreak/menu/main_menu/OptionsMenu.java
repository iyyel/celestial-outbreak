package io.iyyel.celestialoutbreak.menu.main_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class OptionsMenu extends AbstractMenu {

    private final Rectangle playerRect, gameRect, configurationRect;

    private String[] options = {textHandler.BTN_PLAYER_OPTIONS_TEXT, textHandler.BTN_GAME_OPTIONS_TEXT, textHandler.BTN_CONFIGURATION_OPTIONS_TEXT};
    private Color[] rectColors;

    private int selected = 0;
    private int yBtnOffset = 33;

    int initialBtnYPos = 230;
    int btnYInc = 75;

    public OptionsMenu(GameController gameController) {
        super(gameController);

        /* buttons */
        playerRect = new Rectangle(gameController.getWidth() / 2 - 195, initialBtnYPos, 390, 50);
        gameRect = new Rectangle(gameController.getWidth() / 2 - 195, initialBtnYPos + btnYInc, 390, 50);
        configurationRect = new Rectangle(gameController.getWidth() / 2 - 195, initialBtnYPos + btnYInc * 2, 390, 50);

        rectColors = new Color[options.length];

        for (Color c : rectColors)
            c = menuBtnColor;
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            selected = 0;
            menuUseClip.play(false);
            gameController.switchState(GameController.State.MAIN);
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
                            // Player options
                            gameController.switchState(GameController.State.PLAYER_OPTIONS);
                            break;
                        case 1:
                            // Game options
                            gameController.switchState(GameController.State.GAME_OPTIONS);
                            break;
                        case 2:
                            // Configuration options
                            gameController.switchState(GameController.State.CONFIG_OPTIONS);
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

        /* Show submenu title */
        drawSubmenuTitle(textHandler.TITLE_OPTIONS_MENU, g);

        /* Render buttons  */
        g.setFont(inputBtnFont);

        /* Player options button */
        g.setColor(menuFontColor);
        drawCenterString(options[0], playerRect.y + yBtnOffset, g, inputBtnFont);
        g.setColor(rectColors[0]);
        g.draw(playerRect);

        /* Configuration options button */
        g.setColor(menuFontColor);
        drawCenterString(options[1], gameRect.y + yBtnOffset, g, inputBtnFont);
        g.setColor(rectColors[1]);
        g.draw(gameRect);

        /* Configuration options button */
        g.setColor(menuFontColor);
        drawCenterString(options[2], configurationRect.y + yBtnOffset, g, inputBtnFont);
        g.setColor(rectColors[2]);
        g.draw(configurationRect);

        drawInfoPanel(g);
    }

}