package io.iyyel.celestialoutbreak.menu.player_settings;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class PlayerSelectMenu extends AbstractMenu {

    private Rectangle[] playerRects;
    private final Font btnFont;
    private Color[] rectColors;

    private int selected = 0;
    private int inputTimer = 18;
    private boolean firstUpdate = true;
    private int playerAmount = 0;

    public PlayerSelectMenu(GameController gameController) {
        super(gameController);
        playerRects = new Rectangle[0];
        btnFont = utils.getGameFont().deriveFont(20F);
    }

    @Override
    public void update() {
        /*
         * Do this ONCE everytime the user is on this screen.
         */
        if (firstUpdate) {
            firstUpdate = false;
            selected = 0;
            updatePlayerData();
        }

        if (inputTimer > 0) {
            inputTimer--;
        }

        if (inputHandler.isCancelPressed() && inputTimer == 0) {
            gameController.switchState(GameController.State.PLAYER_SETTINGS_MENU);
            firstUpdate = true;
            inputTimer = 10;
        }

        if (inputHandler.isDownPressed() && selected < playerAmount - 1 && inputTimer == 0) {
            selected++;
            menuNavClip.play(false);
            inputTimer = 10;
        }

        if (inputHandler.isUpPressed() && selected > 0 && inputTimer == 0) {
            selected--;
            menuNavClip.play(false);
            inputTimer = 10;
        }

        for (int i = 0, n = playerAmount; i < n; i++) {
            if (selected == i) {
                rectColors[i] = menuSelectedBtnColor;

                if (inputHandler.isUsePressed() && inputTimer == 0) {
                    menuUseClip.play(false);

                    String selectedPlayer = playerDAO.getPlayerList().get(i);

                    try {
                        if (!selectedPlayer.equalsIgnoreCase(playerDAO.getSelectedPlayer())) {
                            playerDAO.selectPlayer(selectedPlayer);
                            try {
                                playerDAO.savePlayerDTO();
                            } catch (IPlayerDAO.PlayerDAOException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    } catch (IPlayerDAO.PlayerDAOException e) {
                        e.printStackTrace();
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

        /* Show sub menu */
        drawSubmenuTitle("Select Player", g);

        /* Render buttons  */
        g.setFont(btnFont);

        for (int i = 0; i < playerAmount; i++) {
            g.setColor(menuFontColor);
            g.drawString(playerDAO.getPlayerList().get(i), playerRects[i].x + 5, playerRects[i].y + 32);

            try {
                if (playerDAO.getPlayerList().get(i).equalsIgnoreCase(playerDAO.getSelectedPlayer())) {
                    g.setColor(Color.MAGENTA);
                } else {
                    g.setColor(rectColors[i]);
                }
            } catch (IPlayerDAO.PlayerDAOException e) {
                e.printStackTrace();
            }

            g.draw(playerRects[i]);
        }

        drawInformationPanel(g);
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

}