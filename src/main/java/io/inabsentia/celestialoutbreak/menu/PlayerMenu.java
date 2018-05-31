package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.GameController;
import io.inabsentia.celestialoutbreak.data.dao.IPlayerDAO;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class PlayerMenu extends Menu {

    private final Rectangle createRect, deleteRect, updateRect;
    private final List<Rectangle> playerRectList;
    private List<String> playerNameList;
    private final Font btnFont;

    private Color rectColor, selectedColor;

    private String[] options = {"Create", "Update", "Delete"};
    private Color[] rectColors;
    private Color[] playerRectColors;

    private final IPlayerDAO playerDAO;
    private final SoundHandler soundHandler;

    private int selected = 0;
    private int inputTimer = 18;

    private boolean isPlayerSelectionMode = false;

    public PlayerMenu(GameController gameController, InputHandler inputHandler, SoundHandler soundHandler, Color fontColor, Color rectColor, Color selectedColor, IPlayerDAO playerDAO) {
        super(gameController, inputHandler, fontColor);
        this.soundHandler = soundHandler;
        this.rectColor = rectColor;
        this.selectedColor = selectedColor;
        this.playerDAO = playerDAO;

        int initialBtnYPos = 230;
        int btnYIncrement = 75;

        /* Menu buttons */
        createRect = new Rectangle(gameController.getWidth() / 2 - 50 - 150, initialBtnYPos + btnYIncrement * 5, 110, 50);
        updateRect = new Rectangle(gameController.getWidth() / 2 - 50, initialBtnYPos + btnYIncrement * 5, 110, 50);
        deleteRect = new Rectangle(gameController.getWidth() / 2 - 50 + 150, initialBtnYPos + btnYIncrement * 5, 110, 50);

        playerRectList = new ArrayList<>();

        rectColors = new Color[options.length];

        for (Color c : rectColors)
            c = rectColor;

        btnFont = utils.getGameFont().deriveFont(20F);

        updatePlayerStructures();
    }

    @Override
    public void update() {
        if (inputTimer > 0) inputTimer--;

        // PLAYER_MENU SELECTION MODE
        if (isPlayerSelectionMode) {
            if (inputHandler.isCancelPressed() && inputTimer == 0) {
                isPlayerSelectionMode = false;
                inputTimer = 10;
            }

            if (inputHandler.isUpPressed() && selected > 0 && inputTimer == 0) {
                selected--;
                soundHandler.MENU_BTN_SELECTION_CLIP.play(false);
                inputTimer = 10;
            }

            if (inputHandler.isDownPressed() && selected < playerNameList.size() - 1 && inputTimer == 0) {
                selected++;
                soundHandler.MENU_BTN_SELECTION_CLIP.play(false);
                inputTimer = 10;
            }

            for (int i = 0, n = playerNameList.size(); i < n; i++) {
                if (selected == i) {
                    playerRectColors[i] = selectedColor;
                    String selectedPlayer = playerNameList.get(i);

                    if (inputHandler.isUsePressed()) {
                        System.out.println("You selected " + selectedPlayer + "!");
                    }

                } else {
                    playerRectColors[i] = rectColor;
                }
            }
        } else {
            // MAIN_MENU MODE
            if (inputHandler.isCancelPressed() && inputTimer == 0) {
                gameController.switchState(GameController.State.MAIN_MENU);
                inputTimer = 10;
            }

            if (inputHandler.isRightPressed() && selected < options.length - 1 && inputTimer == 0) {
                selected++;
                soundHandler.MENU_BTN_SELECTION_CLIP.play(false);
                inputTimer = 10;
            }

            if (inputHandler.isLeftPressed() && selected > 0 && inputTimer == 0) {
                selected--;
                soundHandler.MENU_BTN_SELECTION_CLIP.play(false);
                inputTimer = 10;
            }

            for (int i = 0, n = options.length; i < n; i++) {
                if (selected == i) {
                    rectColors[i] = selectedColor;

                    if (inputHandler.isUsePressed()) {
                        switch (i) {
                            case 0:
                                // CREATE
                                // Bring up creation screen.
                                break;
                            case 1:
                                // UPDATE
                                // Update needs to select a player.
                                isPlayerSelectionMode = true;
                                rectColors[i] = rectColor;
                                selected = 0;
                                break;
                            case 2:
                                // DELETE
                                // Delete needs to select a player.
                                isPlayerSelectionMode = true;
                                rectColors[i] = rectColor;
                                selected = 0;
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

    }

    @Override
    public void render(Graphics2D g) {
        /* Render game title */
        drawMenuTitle(g);

        /* Show sub menu */
        drawSubmenuTitle("Player Selection", g);

        /* Render buttons  */
        g.setFont(btnFont);

        /* Create button */
        g.setColor(fontColor);
        g.setFont(btnFont);
        g.drawString(options[0], createRect.x + 8, createRect.y + 32);
        g.setColor(rectColors[0]);
        g.draw(createRect);

        /* Update button */
        g.setColor(fontColor);
        g.setFont(btnFont);
        g.drawString(options[1], updateRect.x + 3, updateRect.y + 32);
        g.setColor(rectColors[1]);
        g.draw(updateRect);

        /* Delete button */
        g.setColor(fontColor);
        g.setFont(btnFont);
        g.drawString(options[2], deleteRect.x + 9, deleteRect.y + 32);
        g.setColor(rectColors[2]);
        g.draw(deleteRect);

        for (int i = 0; i < playerRectList.size(); i++) {
            Rectangle rect = playerRectList.get(i);
            String playerName = playerNameList.get(i);
            int xPos = (rect.x + playerName.length()) - (playerName.length() / 2);

            g.setColor(fontColor);
            g.setFont(btnFont);
            g.drawString(playerName, xPos, rect.y + 32);
            g.setColor(playerRectColors[i]);
            g.draw(rect);
        }

        drawInformationPanel(g);
    }

    private void updatePlayerStructures() {
        int playerAmount = -1;

        try {
            playerNameList = playerDAO.getPlayerList();
            playerAmount = playerNameList.size();
            playerRectColors = new Color[playerAmount];
        } catch (IPlayerDAO.PlayerDAOException e) {
            e.printStackTrace();
        }

        if (playerNameList == null) {
            System.out.println("PlayerNameList equals null!");
            return;
        }

        if (playerAmount == 0) {
            System.out.println("PLAYERS ARE 0");
            return;
        }

        int initialYPos = 170;
        int xPos = 325;
        int yPos = initialYPos;
        int xPosInc = 160;
        int yPosInc = 80;

        for (int i = 0; i < playerAmount; i++) {
            playerRectColors[i] = rectColor;
            if (i != 0 && i % 4 == 0) {
                xPos += xPosInc;
                yPos = initialYPos;
            }
            yPos += yPosInc;

            Rectangle rect = new Rectangle(xPos, yPos, 150, 50);
            playerRectList.add(rect);
        }

    }

}
