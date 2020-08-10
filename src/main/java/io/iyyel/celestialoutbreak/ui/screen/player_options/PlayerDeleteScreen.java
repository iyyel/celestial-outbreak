package io.iyyel.celestialoutbreak.ui.screen.player_options;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.PlayerDAO;
import io.iyyel.celestialoutbreak.data.dao.contract.IPlayerDAO;
import io.iyyel.celestialoutbreak.ui.screen.AbstractNavigationScreen;
import io.iyyel.celestialoutbreak.ui.screen.component.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PlayerDeleteScreen extends AbstractNavigationScreen {

    private Button[] buttons;
    private int playerAmount = 0;
    private List<String> playerList;

    private final String origToolTip = "Press '" + textHandler.BTN_CONTROL_USE + "' to mark a player for deletion. Press '" + textHandler.BTN_CONTROL_FORWARD_OK + "' to delete marked players.";
    private String tooltipString = origToolTip;

    private boolean isDeleting = false;
    private boolean[] deleteMarkings;

    public PlayerDeleteScreen(NavStyle navStyle, int btnAmount,
                              int btnWrapAmount, GameController gameController) {
        super(navStyle, btnAmount, btnWrapAmount, gameController);
    }

    protected void updateNavUse(int index) {
        if (isButtonUsed(index)) {
            String selectedPlayer = playerList.get(index);
            try {
                if (selectedPlayer.equals(playerDAO.getSelectedPlayer())) {
                    menuBadClip.play(false);
                } else {
                    menuUseClip.play(false);
                    // invert
                    deleteMarkings[index] = !deleteMarkings[index];
                }
            } catch (IPlayerDAO.PlayerDAOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void updateNavOK(int index) {
        if (isButtonOK(index)) {
            if (isDeleting) {
                if (isAnyMarkedPlayers()) {
                    resetInputTimer();
                    selectedIndex = 0;
                    tooltipString = origToolTip;
                    isDeleting = false;
                    menuUseClip.play(false);
                    deletePlayers();
                    updatePlayerData();
                } else {
                    menuBadClip.play(false);
                }
            } else {
                if (isAnyMarkedPlayers()) {
                    resetInputTimer();
                    menuUseClip.play(false);
                    tooltipString = "Are you sure you want to delete marked players?";
                    menuUseClip.play(false);
                    isDeleting = true;
                } else {
                    menuBadClip.play(false);
                }
            }
        }
    }

    @Override
    public void update() {
        super.update();
        updateNavUp();
        updateNavDown();
        updateNavLeft();
        updateNavRight();
        updateNavOK(selectedIndex);
        updateNavCancel(gameController.getPrevState());
        updateNavUse(selectedIndex);
        updatePlayerColors();
        updateSelectedButtonColor(buttons);
    }

    @Override
    public void render(Graphics2D g) {
        if (isFirstRender) {
            isFirstRender = false;
            selectedIndex = 0;
            updatePlayerData();
        }

        /* Render game title */
        drawScreenTitles(textHandler.TITLE_DELETE_PLAYER_SCREEN, g);

        /* Render buttons  */
        g.setFont(inputBtnFont);

        renderButtons(buttons, g);

        drawToolTip(tooltipString, g);
        drawInfoPanel(g);
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

    private void updatePlayerColors() {
        for (int i = 0; i < playerAmount; i++) {
            try {
                String player = playerList.get(i);
                String selectedPlayer = playerDAO.getSelectedPlayer();

                if (deleteMarkings[i]) {
                    buttons[i].setFgColor(menuBtnPlayerDeletedColor);
                } else if (player.equals(selectedPlayer)) {
                    buttons[i].setFgColor(menuBtnPlayerSelectedColor);
                } else {
                    buttons[i].setFgColor(menuBtnColor);
                }

            } catch (IPlayerDAO.PlayerDAOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updatePlayerData() {
        try {
            playerDAO.loadPlayerDTO();
        } catch (IPlayerDAO.PlayerDAOException e) {
            e.printStackTrace();
        }

        playerList = new ArrayList<>(playerDAO.getPlayers());
        playerAmount = playerList.size();

        // default value = false
        deleteMarkings = new boolean[playerAmount];

        // Update rectangles
        buttons = new Button[playerAmount];

        int initialX = 240;
        int initialY = 230;
        int x = initialX;
        int y = initialY;
        int xInc = 200;
        int yInc = 80;

        for (int i = 0; i < playerAmount; i++) {

            if (i % 5 == 0 && i != 0) {
                x += xInc;
                y = initialY;
            }
            String player = playerDAO.getPlayers().get(i);
            buttons[i] = new Button(new Point(x, y), new Dimension(150, 50),
                    player, false, inputBtnFont, screenFontColor, menuBtnColor,
                    new Point(75, 0), new Point(70, -5), screenWidth, screenHeight);
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

}