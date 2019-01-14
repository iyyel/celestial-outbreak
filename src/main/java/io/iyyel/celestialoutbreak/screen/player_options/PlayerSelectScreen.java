package io.iyyel.celestialoutbreak.screen.player_options;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.screen.AbstractScreen;

import java.awt.*;

public final class PlayerSelectScreen extends AbstractScreen {

    private Rectangle[] playerRects;
    private Color[] rectColors;
    private Color[] playerNameColors;

    private int selected = 0;
    private boolean isFirstRender = true;
    private int playerAmount = 0;

    public PlayerSelectScreen(GameController gameController) {
        super(gameController);
        playerRects = new Rectangle[0];
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            isFirstRender = true;
            menuUseClip.play(false);
            gameController.switchState(GameController.State.PLAYER_OPTIONS);
        }

        if (inputHandler.isDownPressed() && (selected + 1) % 5 != 0 && (selected + 1) < playerAmount && isInputAvailable()) {
            resetInputTimer();
            selected++;
            menuNavClip.play(false);
        }

        if (inputHandler.isUpPressed() && selected % 5 != 0 && isInputAvailable()) {
            resetInputTimer();
            selected--;
            menuNavClip.play(false);
        }

        if (inputHandler.isLeftPressed() && selected > 4 && isInputAvailable()) {
            resetInputTimer();
            selected -= 5;
            menuNavClip.play(false);
        }

        if (inputHandler.isRightPressed() && selected < 20 && (selected + 5) < playerAmount && isInputAvailable()) {
            resetInputTimer();
            selected += 5;
            menuNavClip.play(false);
        }

        for (int i = 0, n = playerAmount; i < n; i++) {
            if (selected == i) {
                updatePlayerColors(i);
                rectColors[i] = menuSelectedBtnColor;

                if (inputHandler.isUsePressed() && isInputAvailable()) {
                    resetInputTimer();

                    String selectedPlayer = playerDAO.getPlayerList().get(i);

                    try {
                        if (!selectedPlayer.equalsIgnoreCase(playerDAO.getSelectedPlayer())) {
                            menuUseClip.play(false);
                            playerDAO.selectPlayer(selectedPlayer);
                            try {
                                playerDAO.savePlayerDTO();
                            } catch (IPlayerDAO.PlayerDAOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            menuBadActionClip.play(false);
                        }
                    } catch (IPlayerDAO.PlayerDAOException e) {
                        e.printStackTrace();
                    }

                }

            } else {
                updatePlayerColors(i);
            }
        }

    }

    @Override
    public void render(Graphics2D g) {

        /*
         * Do this ONCE everytime the user is on this screen.
         */
        if (isFirstRender) {
            isFirstRender = false;
            selected = 0;
            updatePlayerData();
        }

        /* Render game title */
        drawScreenTitle(g);
        drawScreenSubtitle(textHandler.TITLE_SELECT_PLAYER_SCREEN, g);

        /* Render buttons  */
        g.setFont(inputBtnFont);

        for (int i = 0; i < playerAmount; i++) {
            g.setColor(playerNameColors[i]);
            g.drawString(playerDAO.getPlayerList().get(i), playerRects[i].x + 5, playerRects[i].y + 32);
            g.setColor(rectColors[i]);
            g.draw(playerRects[i]);
        }

        drawScreenToolTip("Press '" + textHandler.BTN_CONTROL_USE + "' to select a player.", g);
        drawScreenInfoPanel(g);
    }

    private void updatePlayerData() {
        try {
            playerDAO.savePlayerDTO();
            playerDAO.loadPlayerDTO();
        } catch (IPlayerDAO.PlayerDAOException e) {
            e.printStackTrace();
        }

        playerAmount = playerDAO.getPlayerList().size();

        // Update rectangles
        playerRects = new Rectangle[playerAmount];
        rectColors = new Color[playerAmount];
        playerNameColors = new Color[playerAmount];

        int initialX = 150;
        int initialY = 240;
        int x = initialX;
        int y = initialY;
        int xInc = 200;
        int yInc = 80;

        for (int i = 0; i < playerAmount; i++) {
            rectColors[i] = menuBtnColor;
            if (i % 5 == 0 && i != 0) {
                x += xInc;
                y = initialY;
            }
            playerRects[i] = new Rectangle(x, y, 150, 50);
            y += yInc;
        }
    }

    private void updatePlayerColors(int index) {
        try {
            String player = playerDAO.getPlayerList().get(index);
            String selectedPlayer = playerDAO.getSelectedPlayer();

            if (player.equals(selectedPlayer)) {
                playerNameColors[index] = menuBtnPlayerSelectedColor;
            } else {
                playerNameColors[index] = menuBtnColor;
            }
            rectColors[index] = menuBtnColor;
        } catch (IPlayerDAO.PlayerDAOException e) {
            e.printStackTrace();
        }
    }

}