package io.iyyel.celestialoutbreak.menu.game;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class WelcomeMenu extends AbstractMenu {

    private final Rectangle startRect, exitRect;
    private final Font btnFont;

    private String[] options = {textHandler.BTN_START_TEXT, textHandler.BTN_EXIT_TEXT};
    private Color[] rectColors;

    private int selected = 0;
    private int inputTimer = 18;
    private int yBtnOffset = 33;

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
        if (inputTimer > 0)
            inputTimer--;

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
                            if (playerDAO.getPlayerList().isEmpty()) {
                                gameController.switchState(GameController.State.PLAYER_NEW_SCREEN);
                            } else {
                                gameController.switchState(GameController.State.MAIN_MENU);
                            }
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

        drawSubmenuTitle("Welcome", g);

        drawCenterString("Are you ready to explore planets and their secrets?", 300, g, msgFont);
        drawCenterString("Then please, do proceed.", 350, g, msgFont);

        /* Start button */
        g.setColor(menuFontColor);
        drawCenterString(options[0], startRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[0]);
        g.draw(startRect);

        /* Exit button */
        g.setColor(menuFontColor);
        drawCenterString(options[1], exitRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[1]);
        g.draw(exitRect);

        drawInformationPanel(g);
    }

}