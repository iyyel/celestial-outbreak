package io.iyyel.celestialoutbreak.screen.player_options;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.screen.AbstractScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class PlayerDeleteScreen extends AbstractScreen {

    private Rectangle[] playerRects;
    private Color[] rectColors;
    private Color[] playerNameColors;

    private final String origToolTip = "Press '" + textHandler.BTN_CONTROL_USE + "' to mark a player for deletion. Press '" + textHandler.BTN_CONTROL_FORWARD_OK + "' to delete marked players.";
    private String tooltipString = origToolTip;

    private int selected = 0;
    private boolean isFirstRender = true;
    private int playerAmount = 0;

    private boolean isDeleting = false;
    private List<String> playerList;
    private boolean[] deleteMarkings;

    public PlayerDeleteScreen(GameController gameController) {
        super(gameController);
        playerRects = new Rectangle[0];
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isOKPressed() && isInputAvailable() && !isDeleting) {
            if (isAnyMarkedPlayers()) {
                resetInputTimer();
                menuUseClip.play(false);
                tooltipString = "Are you sure you want to delete marked players?";
                menuUseClip.play(false);
                isDeleting = true;
            } else {
                menuBadActionClip.play(false);
            }
        }

        if (inputHandler.isOKPressed() && isInputAvailable() && isDeleting) {
            if (isAnyMarkedPlayers()) {
                resetInputTimer();
                selected = 0;
                tooltipString = origToolTip;
                isDeleting = false;
                menuUseClip.play(false);
                deletePlayers();
                updatePlayerData();
            } else {
                menuBadActionClip.play(false);
            }
        }

        if (inputHandler.isCancelPressed() && isInputAvailable() && isDeleting) {
            if (isAnyMarkedPlayers()) {
                resetInputTimer();
                selected = 0;
                tooltipString = origToolTip;
                menuUseClip.play(false);
                isDeleting = false;
                resetDeletions();
            } else {
                menuBadActionClip.play(false);
            }
        }

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            isFirstRender = true;
            menuUseClip.play(false);
            gameController.switchState(GameController.State.PLAYER_OPTIONS);
        }

        //
        // SELECTED
        // 1      6      11      16      21
        //
        // 0,     5,     10,     15,     20,
        // 1,     6,     11,     16,     21,
        // 2,     7,     12,     17,     22,
        // 3,     8,     13,     18,     23,
        // 4,     9,     14,     19,     24
        // + 1
        // 5, 10, 15, 20, 25
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

        for (int i = 0; i < playerList.size(); i++) {
            if (selected == i) {
                String player = playerList.get(i);
                updatePlayerColors(i);
                rectColors[i] = menuSelectedBtnColor;

                if (inputHandler.isUsePressed() && isInputAvailable()) {
                    resetInputTimer();

                    try {
                        if (player.equals(playerDAO.getSelectedPlayer())) {
                            menuBadActionClip.play(false);
                        } else {
                            menuUseClip.play(false);
                            // invert
                            deleteMarkings[i] = !deleteMarkings[i];
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
            doFirstRender();
        }

        /* Render game title */
        drawScreenTitle(g);
        drawScreenSubtitle(textHandler.TITLE_DELETE_PLAYER_SCREEN, g);

        /* Render buttons  */
        g.setFont(inputBtnFont);

        for (int i = 0; i < playerList.size(); i++) {
            String player = playerList.get(i);

            g.setColor(playerNameColors[i]);
            g.drawString(player, playerRects[i].x + 5, playerRects[i].y + 32);

            g.setColor(rectColors[i]);
            g.draw(playerRects[i]);
        }

        drawScreenToolTip(tooltipString, g);
        drawScreenInfoPanel(g);
    }

    private void updatePlayerData() {
        playerList = new ArrayList<>();

        try {
            playerDAO.loadPlayerDTO();
        } catch (IPlayerDAO.PlayerDAOException e) {
            e.printStackTrace();
        }

        playerList = new ArrayList<>(playerDAO.getPlayerList());
        playerAmount = playerList.size();

        // default value = false
        deleteMarkings = new boolean[playerAmount];

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
            playerNameColors[i] = menuBtnColor;
            if (i % 5 == 0 && i != 0) {
                x += xInc;
                y = initialY;
            }
            playerRects[i] = new Rectangle(x, y, 150, 50);
            y += yInc;
        }
    }

    private void deletePlayers() {
        for (int i = 0; i < deleteMarkings.length; i++) {
            boolean isDeleted = deleteMarkings[i];
            String player = playerList.get(i);
            if (isDeleted) {
                try {
                    playerDAO.removePlayer(player);
                } catch (IPlayerDAO.PlayerDAOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            playerDAO.savePlayerDTO();
        } catch (IPlayerDAO.PlayerDAOException e) {
            e.printStackTrace();
        }
    }

    private boolean isAnyMarkedPlayers() {
        for (boolean isDeleted : deleteMarkings) {
            if (isDeleted) {
                return true;
            }
        }
        return false;
    }

    private void resetDeletions() {
        Arrays.fill(deleteMarkings, false);
    }

    private void doFirstRender() {
        isFirstRender = false;
        selected = 0;
        updatePlayerData();
    }

    private void updatePlayerColors(int index) {
        try {
            String player = playerList.get(index);
            String selectedPlayer = playerDAO.getSelectedPlayer();

            if (deleteMarkings[index]) {
                playerNameColors[index] = menuBtnPlayerDeletedColor;
            } else if (player.equals(selectedPlayer)) {
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