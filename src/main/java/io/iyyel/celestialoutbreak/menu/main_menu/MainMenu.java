package io.iyyel.celestialoutbreak.menu.main_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class MainMenu extends AbstractMenu {

    private final Rectangle playRect, scoreRect, controlsRect, optionsRect, aboutRect, exitRect;
    private final Font btnFont;

    private String[] options = {textHandler.BTN_PLAY_TEXT, textHandler.BTN_SCORES_TEXT, textHandler.BTN_CONTROLS_TEXT,
            textHandler.BTN_OPTIONS_TEXT, textHandler.BTN_ABOUT_TEXT, textHandler.BTN_EXIT_TEXT};
    private Color[] rectColors;

    private int selected = 0;

    private boolean isFirstUpdate = true;

    public MainMenu(GameController gameController) {
        super(gameController);

        int initialBtnYPos = 230;
        int btnYIncrement = 75;

        /* Menu buttons */
        playRect = new Rectangle(gameController.getWidth() / 2 - 90, initialBtnYPos, 180, 50);
        scoreRect = new Rectangle(gameController.getWidth() / 2 - 90, initialBtnYPos + btnYIncrement, 180, 50);
        controlsRect = new Rectangle(gameController.getWidth() / 2 - 90, initialBtnYPos + btnYIncrement * 2, 180, 50);
        optionsRect = new Rectangle(gameController.getWidth() / 2 - 90, initialBtnYPos + btnYIncrement * 3, 180, 50);
        aboutRect = new Rectangle(gameController.getWidth() / 2 - 90, initialBtnYPos + btnYIncrement * 4, 180, 50);
        exitRect = new Rectangle(gameController.getWidth() / 2 - 90, initialBtnYPos + btnYIncrement * 5, 180, 50);

        rectColors = new Color[options.length];

        for (Color c : rectColors)
            c = menuSelectedBtnColor;

        btnFont = utils.getGameFont().deriveFont(20F);
    }

    @Override
    public void update() {
        decInputTimer();

        if (isFirstUpdate) {
            isFirstUpdate = false;
            updatePlayerDTO();
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
                    resetInputTimer();
                    isFirstUpdate = true;
                    menuUseClip.play(false);

                    switch (i) {
                        case 0:
                            gameController.switchState(GameController.State.PLAY_SCREEN);
                            break;
                        case 1:
                            gameController.switchState(GameController.State.SCORES_SCREEN);
                            break;
                        case 2:
                            gameController.switchState(GameController.State.CONTROLS_SCREEN);
                            break;
                        case 3:
                            gameController.switchState(GameController.State.OPTIONS_MENU);
                            break;
                        case 4:
                            gameController.switchState(GameController.State.ABOUT_SCREEN);
                            break;
                        case 5:
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
        /* Render game title */
        drawMenuTitle(g);

        /* Show player name */
        try {
            if (playerDAO.getSelectedPlayer() != null)
                drawSubmenuTitle("Welcome " + playerDAO.getSelectedPlayer(), g);
        } catch (IPlayerDAO.PlayerDAOException e) {
            drawSubmenuTitle("Welcome", g);
        }

        /* Render buttons  */
        g.setFont(btnFont);

        /* Play button */
        g.setColor(menuFontColor);
        drawCenterString(options[0], playRect.y + BTN_Y_OFFSET, g, btnFont);
        g.setColor(rectColors[0]);
        g.draw(playRect);

        /* Score button */
        g.setColor(menuFontColor);
        drawCenterString(options[1], scoreRect.y + BTN_Y_OFFSET, g, btnFont);
        g.setColor(rectColors[1]);
        g.draw(scoreRect);

        /* Controls button */
        g.setColor(menuFontColor);
        drawCenterString(options[2], controlsRect.y + BTN_Y_OFFSET, g, btnFont);
        g.setColor(rectColors[2]);
        g.draw(controlsRect);

        /* Settings button */
        g.setColor(menuFontColor);
        drawCenterString(options[3], optionsRect.y + BTN_Y_OFFSET, g, btnFont);
        g.setColor(rectColors[3]);
        g.draw(optionsRect);

        /* About button */
        g.setColor(menuFontColor);
        drawCenterString(options[4], aboutRect.y + BTN_Y_OFFSET, g, btnFont);
        g.setColor(rectColors[4]);
        g.draw(aboutRect);

        /* Exit button */
        g.setColor(menuFontColor);
        drawCenterString(options[5], exitRect.y + BTN_Y_OFFSET, g, btnFont);
        g.setColor(rectColors[5]);
        g.draw(exitRect);

        drawInfoPanel(g);
    }

    private void updatePlayerDTO() {
        try {
            playerDAO.savePlayerDTO();
            playerDAO.loadPlayerDTO();
        } catch (IPlayerDAO.PlayerDAOException e) {
            e.printStackTrace();
        }
    }

}