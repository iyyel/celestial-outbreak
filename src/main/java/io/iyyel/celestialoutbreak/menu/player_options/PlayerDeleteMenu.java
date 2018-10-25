package io.iyyel.celestialoutbreak.menu.player_options;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class PlayerDeleteMenu extends AbstractMenu {

    private Rectangle[] playerRects;
    private Color[] rectColors;

    private final String origToolTip = "Press '" + textHandler.BTN_CONTROL_USE + "' to mark a player for deletion. Press '" + textHandler.BTN_CONTROL_FORWARD_OK + "' to delete marked players.";
    private String tooltipString = origToolTip;

    private int selected = 0;
    private boolean isFirstUpdate = true;
    private int playerAmount = 0;

    private boolean isDeleting = false;
    private List<String> playerList;
    private boolean[] deleteMarkings;

    public PlayerDeleteMenu(GameController gameController) {
        super(gameController);
        playerRects = new Rectangle[0];
    }

    @Override
    public void update() {
        decInputTimer();


        if (inputHandler.isOKPressed() && isDeletions() && isInputAvailable() && !isDeleting) {
            resetInputTimer();
            tooltipString = "Are you sure you want to delete marked players?";
            menuUseClip.play(false);
            isDeleting = true;
        }

        if (inputHandler.isOKPressed() && isDeletions() && isInputAvailable() && isDeleting) {
            resetInputTimer();
            selected = 0;
            tooltipString = origToolTip;
            isDeleting = false;
            menuUseClip.play(false);
            deletePlayers();
            updatePlayerData();
        }

        if (inputHandler.isCancelPressed() && isDeletions() && isInputAvailable() && isDeleting) {
            resetInputTimer();
            selected = 0;
            tooltipString = origToolTip;
            isDeleting = false;
            resetDeletions();
            menuUseClip.play(false);
        }

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            isFirstUpdate = true;
            menuUseClip.play(false);
            gameController.switchState(GameController.State.PLAYER_OPTIONS_MENU);
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
                try {
                    String player = playerList.get(i);
                    String selectedPlayer = playerDAO.getSelectedPlayer();

                    if (deleteMarkings[i]) {
                        rectColors[i] = menuBtnPlayerDeletedColor;
                    } else if (player.equals(selectedPlayer)) {
                        rectColors[i] = menuBtnPlayerSelectedColor;
                    } else {
                        rectColors[i] = menuBtnColor;
                    }
                } catch (IPlayerDAO.PlayerDAOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        /*
         * Do this ONCE everytime the user is on this screen.
         */
        if (isFirstUpdate) {
            doFirstUpdate();
        }

        /* Render game title */
        drawMenuTitle(g);

        /* Show sub menu */
        drawSubmenuTitle(textHandler.TITLE_DELETE_PLAYER_SCREEN, g);

        /* Render buttons  */
        g.setFont(inputFont);

        for (int i = 0; i < playerList.size(); i++) {
            String player = playerList.get(i);

            g.setColor(menuFontColor);
            g.drawString(player, playerRects[i].x + 5, playerRects[i].y + 32);

            g.setColor(rectColors[i]);
            g.draw(playerRects[i]);
        }

        drawMenuToolTip(tooltipString, g);
        drawInfoPanel(g);
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

        int initialX = 150;
        int initialY = 240;
        int x = initialX;
        int y = initialY;
        int xInc = 200;
        int yInc = 80;

        for (int i = 0; i < playerAmount; i++) {
            rectColors[i] = Color.WHITE;
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

    private boolean isDeletions() {
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

    private void doFirstUpdate() {
        isFirstUpdate = false;
        selected = 0;
        updatePlayerData();
    }

}