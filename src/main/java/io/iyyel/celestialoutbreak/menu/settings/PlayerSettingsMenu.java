package io.iyyel.celestialoutbreak.menu.settings;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.IPlayerDAO;
import io.iyyel.celestialoutbreak.data.dao.PlayerDAO;
import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.handler.SoundHandler;
import io.iyyel.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class PlayerSettingsMenu extends Menu {

    private final Rectangle chooseRect, newRect, updateRect, removeRect;
    private final Font btnFont;

    private Color rectColor, selectedColor;

    private String[] options = {"SELECT", "NEW", "UPDATE", "REMOVE"};
    private Color[] rectColors;

    private final SoundHandler soundHandler;
    private final IPlayerDAO playerDAO = PlayerDAO.getInstance();

    private int selected = 0;
    private int inputTimer = 18;

    public PlayerSettingsMenu(GameController gameController, InputHandler inputHandler, SoundHandler soundHandler,
                              Color fontColor, Color rectColor, Color selectedColor) {
        super(gameController, inputHandler, fontColor);
        this.soundHandler = soundHandler;
        this.rectColor = rectColor;
        this.selectedColor = selectedColor;

        int initialBtnYPos = 230;
        int btnYIncrement = 75;

        /* Menu buttons */
        chooseRect = new Rectangle(gameController.getWidth() / 2 - 60, initialBtnYPos, 120, 50);
        newRect = new Rectangle(gameController.getWidth() / 2 - 60, initialBtnYPos + btnYIncrement, 120, 50);
        updateRect = new Rectangle(gameController.getWidth() / 2 - 65, initialBtnYPos + btnYIncrement * 2, 130, 50);
        removeRect = new Rectangle(gameController.getWidth() / 2 - 65, initialBtnYPos + btnYIncrement * 3, 130, 50);

        rectColors = new Color[options.length];

        for (Color c : rectColors)
            c = rectColor;

        btnFont = utils.getGameFont().deriveFont(20F);
    }

    @Override
    public void update() {
        if (inputTimer > 0) {
            inputTimer--;
        }

        if (inputHandler.isCancelPressed() && inputTimer == 0) {
            gameController.switchState(GameController.State.SETTINGS_MENU);
            inputTimer = 10;
        }

        if (inputHandler.isUpPressed() && selected > 0 && inputTimer == 0) {
            selected--;
            soundHandler.MENU_BTN_SELECTION_CLIP.play(false);
            inputTimer = 10;
        }

        if (inputHandler.isDownPressed() && selected < options.length - 1 && inputTimer == 0) {
            selected++;
            soundHandler.MENU_BTN_SELECTION_CLIP.play(false);
            inputTimer = 10;
        }

        for (int i = 0, n = options.length; i < n; i++) {
            if (selected == i) {
                rectColors[i] = selectedColor;

                if (inputHandler.isUsePressed() && inputTimer == 0) {
                    inputTimer = 10;
                    switch (i) {
                        case 0:
                            // SELECT
                            gameController.switchState(GameController.State.PLAYER_SELECT_MENU);
                            break;
                        case 1:
                            // NEW
                            inputHandler.setIsUsePressed(false);
                            //createNewPlayer();
                            break;
                        case 2:
                            // UPDATE
                            //
                            break;
                        case 3:
                            // REMOVE
                            //
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
        /* Render game title */
        drawMenuTitle(g);

        /* Show sub menu */
        drawSubmenuTitle("Player Settings", g);

        /* Render buttons  */
        g.setFont(btnFont);

        /* Select button */
        g.setColor(fontColor);
        g.setFont(btnFont);
        g.drawString(options[0], chooseRect.x + 7, chooseRect.y + 32);
        g.setColor(rectColors[0]);
        g.draw(chooseRect);

        /* New button */
        g.setColor(fontColor);
        g.setFont(btnFont);
        g.drawString(options[1], newRect.x + 27, newRect.y + 32);
        g.setColor(rectColors[1]);
        g.draw(newRect);

        /* Update button */
        g.setColor(fontColor);
        g.setFont(btnFont);
        g.drawString(options[2], updateRect.x + 7, updateRect.y + 32);
        g.setColor(rectColors[2]);
        g.draw(updateRect);

        /* Remove button */
        g.setColor(fontColor);
        g.setFont(btnFont);
        g.drawString(options[3], removeRect.x + 3, removeRect.y + 32);
        g.setColor(rectColors[3]);
        g.draw(removeRect);

        drawInformationPanel(g);
    }

}