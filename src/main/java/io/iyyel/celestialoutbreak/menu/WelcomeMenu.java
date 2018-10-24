package io.iyyel.celestialoutbreak.menu;

import io.iyyel.celestialoutbreak.controller.GameController;

import java.awt.*;

public final class WelcomeMenu extends AbstractMenu {

    private final Rectangle startRect, exitRect;
    private final Font btnFont;

    private String[] options = {textHandler.BTN_START_TEXT, textHandler.BTN_EXIT_TEXT};
    private Color[] rectColors;

    private int selected = 0;

    public WelcomeMenu(GameController gameController) {
        super(gameController);

        int initialBtnYPos = 230;
        int btnYIncrement = 75;

        startRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement * 3, 160, 50);
        exitRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement * 4, 160, 50);

        rectColors = new Color[options.length];

        for (Color c : rectColors)
            c = menuBtnColor;

        btnFont = utils.getGameFont().deriveFont(20F);
    }

    @Override
    public void update() {
        decInputTimer();

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
                    resetInputTimer();
                    menuUseClip.play(false);

                    switch (i) {
                        case 0:
                            proceed();
                            break;
                        case 1:
                            gameController.switchState(GameController.State.EXIT_SCREEN);
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
        g.setColor(menuFontColor);
        drawMenuTitle(g);

        drawSubmenuTitle(textHandler.TITLE_WELCOME_MENU, g);

        drawCenterString("Are you ready to explore planets and their secrets?", 300, g, msgFont);
        drawCenterString("Go to next menu with '" + textHandler.BTN_CONTROL_FORWARD_OK + "' or back with '" + textHandler.BTN_CONTROL_BACK_CANCEL + "'.", 350, g, msgFont);

        /* Start button */
        g.setColor(menuFontColor);
        drawCenterString(options[0], startRect.y + BTN_Y_OFFSET, g, btnFont);
        g.setColor(rectColors[0]);
        g.draw(startRect);

        /* Exit button */
        g.setColor(menuFontColor);
        drawCenterString(options[1], exitRect.y + BTN_Y_OFFSET, g, btnFont);
        g.setColor(rectColors[1]);
        g.draw(exitRect);

        drawInfoPanel(g);
    }

    private void proceed() {
        if (playerDAO.getPlayerList().isEmpty()) {
            gameController.switchState(GameController.State.PLAYER_CREATE_SCREEN);
        } else {
            gameController.switchState(GameController.State.MAIN_MENU);
        }
    }

}