package io.iyyel.celestialoutbreak.menu.player_settings;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class PlayerDeleteMenu extends AbstractMenu {

    private Rectangle[] playerRects;
    private Color[] rectColors;

    private int selected = 0;
    private boolean isFirstUpdate = true;
    private int playerAmount = 0;

    private Map<String, Boolean> deleteMap;

    public PlayerDeleteMenu(GameController gameController) {
        super(gameController);
        playerRects = new Rectangle[0];
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            deletePlayers();
            isFirstUpdate = true;
            menuUseClip.play(false);
            gameController.switchState(GameController.State.PLAYER_SETTINGS_MENU);
        }

        if (inputHandler.isDownPressed() && selected < playerAmount - 1 && isInputAvailable()) {
            resetInputTimer();
            selected++;
            menuNavClip.play(false);
        }

        if (inputHandler.isUpPressed() && selected > 0 && isInputAvailable()) {
            resetInputTimer();
            selected--;
            menuNavClip.play(false);
        }

        int i = 0;
        for (String player : deleteMap.keySet()) {
            if (selected == i) {
                rectColors[i] = menuSelectedBtnColor;

                if (inputHandler.isUsePressed() && isInputAvailable()) {
                    resetInputTimer();

                    try {
                        if (player.equals(playerDAO.getSelectedPlayer())) {
                            badActionClip.play(false);
                        } else {
                            menuUseClip.play(false);
                            if (deleteMap.get(player)) {
                                deleteMap.put(player, false);
                            } else {
                                deleteMap.put(player, true);
                            }
                        }
                    } catch (IPlayerDAO.PlayerDAOException e) {
                        e.printStackTrace();
                    }
                }

            } else {

                try {
                    String selectedPlayer = playerDAO.getSelectedPlayer();

                    if (deleteMap.get(player)) {
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
            i++;
        }

    }

    @Override
    public void render(Graphics2D g) {
        /*
         * Do this ONCE everytime the user is on this screen.
         */
        if (isFirstUpdate) {
            isFirstUpdate = false;
            selected = 0;
            updatePlayerData();
        }

        /* Render game title */
        drawMenuTitle(g);

        /* Show sub menu */
        drawSubmenuTitle("Delete Player", g);

        /* Render buttons  */
        g.setFont(inputFont);

        int i = 0;
        for (String player : deleteMap.keySet()) {
            g.setColor(menuFontColor);
            g.drawString(player, playerRects[i].x + 5, playerRects[i].y + 32);

            g.setColor(rectColors[i]);

            g.draw(playerRects[i]);
            i++;
        }

        drawInformationPanel(g);
    }

    private void updatePlayerData() {
        deleteMap = new HashMap<>();

        try {
            playerDAO.loadPlayerDTO();
        } catch (IPlayerDAO.PlayerDAOException e) {
            e.printStackTrace();
        }

        for (String player : playerDAO.getPlayerList()) {
            deleteMap.put(player, false);
        }

        playerAmount = deleteMap.size();

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
        for (String player : deleteMap.keySet()) {
            boolean isDeleted = deleteMap.get(player);
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