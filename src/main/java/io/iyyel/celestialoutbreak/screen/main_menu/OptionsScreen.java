package io.iyyel.celestialoutbreak.screen.main_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.screen.AbstractScreen;

import java.awt.*;

public final class OptionsScreen extends AbstractScreen {

    private final Rectangle playerRect, gameRect, configurationRect;

    private String[] options = {textHandler.BTN_PLAYER_OPTIONS_TEXT, textHandler.BTN_GAME_OPTIONS_TEXT,
            textHandler.BTN_CONFIGURATION_OPTIONS_TEXT};
    private Color[] rectColors;

    private int selected = 0;

    public OptionsScreen(GameController gameController) {
        super(gameController);

        int initialBtnYPos = 230;
        int btnYInc = 75;

        /* buttons */
        playerRect = new Rectangle(getHalfWidth() - 195, initialBtnYPos, 390, 50);
        gameRect = new Rectangle(getHalfWidth() - 195, initialBtnYPos + btnYInc, 390, 50);
        configurationRect = new Rectangle(getHalfWidth() - 195, initialBtnYPos + btnYInc * 2, 390, 50);

        rectColors = new Color[options.length];

        for (int i = 0; i < rectColors.length; i++) {
            rectColors[i] = menuBtnColor;
        }

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
                            gameController.switchState(GameController.State.GENERAL_OPTIONS);
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
        drawScreenTitle(g);
        drawScreenSubtitle(textHandler.TITLE_OPTIONS_SCREEN, g);

        /* Render buttons  */
        g.setFont(inputBtnFont);

        /* Player options button */
        g.setColor(screenFontColor);
        drawScreenCenterString(options[0], playerRect.y + BTN_Y_OFFSET, inputBtnFont, g);
        g.setColor(rectColors[0]);
        g.draw(playerRect);

        /* Configuration options button */
        g.setColor(screenFontColor);
        drawScreenCenterString(options[1], gameRect.y + BTN_Y_OFFSET, inputBtnFont, g);
        g.setColor(rectColors[1]);
        g.draw(gameRect);

        /* Configuration options button */
        g.setColor(screenFontColor);
        drawScreenCenterString(options[2], configurationRect.y + BTN_Y_OFFSET, inputBtnFont, g);
        g.setColor(rectColors[2]);
        g.draw(configurationRect);

        drawScreenInfoPanel(g);
    }

}