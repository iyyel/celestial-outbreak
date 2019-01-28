package io.iyyel.celestialoutbreak.screen.welcome;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.screen.AbstractScreen;

import java.awt.*;

public final class WelcomeScreen extends AbstractScreen {

    private final Rectangle startRect, exitRect;

    private String[] btnText = {textHandler.BTN_START_TEXT, textHandler.BTN_EXIT_TEXT};
    private Color[] rectColors;

    private int selected = 0;

    public WelcomeScreen(GameController gameController) {
        super(gameController);

        int initialBtnYPos = 230;
        int btnYIncrement = 75;

        rectColors = new Color[btnText.length];

        startRect = new Rectangle(getHalfWidth() - 80, initialBtnYPos + btnYIncrement * 3, 160, 50);
        exitRect = new Rectangle(getHalfWidth() - 80, initialBtnYPos + btnYIncrement * 4, 160, 50);

        for (int i = 0; i < rectColors.length; i++) {
            rectColors[i] = menuBtnColor;
        }

    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isDownPressed() && selected < btnText.length - 1 && isInputAvailable()) {
            resetInputTimer();
            selected++;
            menuNavClip.play(false);
        }

        if (inputHandler.isUpPressed() && selected > 0 && isInputAvailable()) {
            resetInputTimer();
            selected--;
            menuNavClip.play(false);
        }

        for (int i = 0, n = btnText.length; i < n; i++) {
            if (selected == i) {
                rectColors[i] = menuSelectedBtnColor;

                if (inputHandler.isOKPressed() && isInputAvailable()) {
                    resetInputTimer();
                    menuUseClip.play(false);

                    switch (i) {
                        case 0:
                            proceed();
                            break;
                        case 1:
                            gameController.switchState(GameController.State.EXIT);
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
        drawScreenTitle(g);
        drawScreenSubtitle(textHandler.TITLE_WELCOME_SCREEN, g);
        drawScreenMessage(textHandler.WELCOME_SCREEN_TEXT, 0, g);

        /* Start button */
        g.setColor(screenFontColor);
        drawScreenCenterString(btnText[0], startRect.y + BTN_Y_OFFSET, inputBtnFont, g);
        g.setColor(rectColors[0]);
        g.draw(startRect);

        /* Exit button */
        g.setColor(screenFontColor);
        drawScreenCenterString(btnText[1], exitRect.y + BTN_Y_OFFSET, inputBtnFont, g);
        g.setColor(rectColors[1]);
        g.draw(exitRect);

        /* Draw screen tooltip */
        drawScreenToolTip(textHandler.WELCOME_SCREEN_TOOLTIP_TEXT, g);

        drawScreenInfoPanel(g);
    }

    private void proceed() {
        if (playerDAO.getPlayerList().isEmpty()) {
            gameController.switchState(GameController.State.PLAYER_CREATE);
        } else {
            gameController.switchState(GameController.State.MAIN);
        }
    }

}