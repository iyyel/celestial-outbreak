package io.iyyel.celestialoutbreak.menu.player;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.IPlayerDAO;
import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.handler.SoundHandler;
import io.iyyel.celestialoutbreak.menu.Menu;

import javax.swing.*;
import java.awt.*;

public final class PlayerSelectMenu extends Menu {

    private Rectangle[] playerRects;
    private final Font btnFont;

    private Color rectColor, selectedColor;
    private Color[] rectColors;

    private final SoundHandler soundHandler;

    private int selected = 0;
    private int inputTimer = 18;
    private boolean firstUpdate = true;
    private int playerAmount = 0;

    private boolean isDialogOpen = false;

    public PlayerSelectMenu(GameController gameController, InputHandler inputHandler, SoundHandler soundHandler,
                            Color fontColor, Color rectColor, Color selectedColor) {
        super(gameController, inputHandler, fontColor);
        this.soundHandler = soundHandler;
        this.rectColor = rectColor;
        this.selectedColor = selectedColor;

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
            soundHandler.MENU_BTN_SELECTION_CLIP.play(false);
            inputTimer = 10;
        }

        if (inputHandler.isUpPressed() && selected > 0 && inputTimer == 0) {
            selected--;
            soundHandler.MENU_BTN_SELECTION_CLIP.play(false);
            inputTimer = 10;
        }

        for (int i = 0, n = playerAmount; i < n; i++) {
            if (selected == i) {
                rectColors[i] = selectedColor;

                if (inputHandler.isUsePressed() && inputTimer == 0) {
                    inputHandler.setIsUsePressed(false);

                    String selectedPlayer = playerDAO.getPlayerList().get(i);

                    try {
                        if (!selectedPlayer.equalsIgnoreCase(playerDAO.getSelectedPlayer())) {
                            playerDAO.selectPlayer(selectedPlayer);
                            try {
                                playerDAO.savePlayerDTO();
                                showSelectedDialog(selectedPlayer);
                            } catch (IPlayerDAO.PlayerDAOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            showAlreadySelectedDialog(selectedPlayer);
                        }
                    } catch (IPlayerDAO.PlayerDAOException e) {
                        e.printStackTrace();
                    }

                }

            } else {
                rectColors[i] = rectColor;
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
            g.setColor(fontColor);
            g.drawString(playerDAO.getPlayerList().get(i), playerRects[i].x + 5, playerRects[i].y + 32);
            g.setColor(rectColors[i]);
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

    private void showSelectedDialog(String selectedPlayer) {
        if (isDialogOpen)
            return;

        new Thread(() -> {
            String path = ClassLoader.getSystemResource("icon/app_icon_small.png").getPath();
            final ImageIcon icon = new ImageIcon(path);
            JOptionPane.showMessageDialog(null,
                    "Select Player: " + selectedPlayer, "Celestial Outbreak - Select Player", JOptionPane.PLAIN_MESSAGE, icon);
        }).start();
    }

    private void showAlreadySelectedDialog(String selectedPlayer) {
        new Thread(() -> {
            String path = ClassLoader.getSystemResource("icon/app_icon_small.png").getPath();
            final ImageIcon icon = new ImageIcon(path);
            JOptionPane.showMessageDialog(null,
                    selectedPlayer + " is already selected!", "Celestial Outbreak - Select Player", JOptionPane.PLAIN_MESSAGE, icon);
        }).start();
    }

}