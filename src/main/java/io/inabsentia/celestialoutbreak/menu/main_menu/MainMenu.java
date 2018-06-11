package io.inabsentia.celestialoutbreak.menu.main_menu;

import io.inabsentia.celestialoutbreak.controller.GameController;
import io.inabsentia.celestialoutbreak.controller.GameController.State;
import io.inabsentia.celestialoutbreak.data.dao.IPlayerDAO;
import io.inabsentia.celestialoutbreak.data.dao.PlayerDAO;
import io.inabsentia.celestialoutbreak.data.dto.PlayerDTO;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;
import io.inabsentia.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class MainMenu extends Menu {

    private final Rectangle playRect, scoreRect, controlsRect, settingsRect, aboutRect, exitRect;
    private final Font btnFont;

    private String[] options = {textHandler.BTN_PLAY_TEXT, textHandler.BTN_SCORES_TEXT, textHandler.BTN_CONTROLS_TEXT,
            textHandler.BTN_SETTINGS_TEXT, textHandler.BTN_ABOUT_TEXT, textHandler.BTN_EXIT_TEXT};
    private Color[] rectColors;

    private Color rectColor, selectedColor;

    private int selected = 0;
    private int inputTimer = 18;

    private final SoundHandler soundHandler;

    private final IPlayerDAO playerDAO = PlayerDAO.getInstance();
    private PlayerDTO playerDTO = null;

    private boolean isFirstUpdate = true;

    public MainMenu(GameController gameController, InputHandler inputHandler, SoundHandler soundHandler,
                    Color fontColor, Color rectColor, Color selectedColor) {
        super(gameController, inputHandler, fontColor);
        this.soundHandler = soundHandler;
        this.rectColor = rectColor;
        this.selectedColor = selectedColor;

        int initialBtnYPos = 230;
        int btnYIncrement = 75;

        /* Menu buttons */
        playRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos, 160, 50);
        scoreRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement, 160, 50);
        controlsRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement * 2, 160, 50);
        settingsRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement * 3, 160, 50);
        aboutRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement * 4, 160, 50);
        exitRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement * 5, 160, 50);

        rectColors = new Color[options.length];

        for (Color c : rectColors)
            c = rectColor;

        btnFont = utils.getGameFont().deriveFont(20F);
    }

    @Override
    public void update() {
        if (inputTimer > 0)
            inputTimer--;

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
                    inputTimer = 10;
                    isFirstUpdate = true;

                    switch (i) {
                        case 0:
                            gameController.switchState(State.PLAY);
                            break;
                        case 1:
                            gameController.switchState(State.SCORES_MENU);
                            break;
                        case 2:
                            gameController.switchState(State.CONTROLS_MENU);
                            break;
                        case 3:
                            gameController.switchState(State.SETTINGS_MENU);
                            break;
                        case 4:
                            gameController.switchState(State.ABOUT_MENU);
                            break;
                        case 5:
                            gameController.switchState(State.EXIT_MENU);
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

        if (isFirstUpdate) {
            isFirstUpdate = false;
            updatePlayerDTO();
        }

        /* Render game title */
        drawMenuTitle(g);

        /* Show player name */
        if (playerDTO.isPlayerSelected()) {
            drawSubmenuTitle("Welcome " + playerDTO.getSelectedPlayer(), g);
        } else {
            drawSubmenuTitle("Welcome", g);
        }

        /* Render buttons  */
        g.setFont(btnFont);

        /* Play button */
        g.setColor(fontColor);
        drawXCenteredString(options[0], playRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[0]);
        g.draw(playRect);

        /* Score button */
        g.setColor(fontColor);
        drawXCenteredString(options[1], scoreRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[1]);
        g.draw(scoreRect);

        /* Controls button */
        g.setColor(fontColor);
        drawXCenteredString(options[2], controlsRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[2]);
        g.draw(controlsRect);

        /* Settings button */
        g.setColor(fontColor);
        drawXCenteredString(options[3], settingsRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[3]);
        g.draw(settingsRect);

        /* About button */
        g.setColor(fontColor);
        drawXCenteredString(options[4], aboutRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[4]);
        g.draw(aboutRect);

        /* Exit button */
        g.setColor(fontColor);
        drawXCenteredString(options[5], exitRect.y + yBtnOffset, g, btnFont);
        g.setColor(rectColors[5]);
        g.draw(exitRect);

        drawInformationPanel(g);
    }

    private void updatePlayerDTO() {
        try {
            playerDAO.loadPlayerDTO();
            playerDTO = playerDAO.getPlayerDTO();
        } catch (IPlayerDAO.PlayerDAOException e) {
            e.printStackTrace();
        }
    }

}