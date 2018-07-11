package io.iyyel.celestialoutbreak.menu.settings;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.data.dao.PlayerDAO;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.handler.SoundHandler;
import io.iyyel.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class PlayerSettingsMenu extends Menu {

    private final Rectangle selectRect, newRect, removeRect;
    private final Font btnFont;

    private Color rectColor, selectedColor;

    private String[] options = {"SELECT", "NEW", "REMOVE"};
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
        selectRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos, 160, 50);
        newRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement, 160, 50);
        removeRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement * 2, 160, 50);

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
                            inputHandler.setInputMode(true);
                            gameController.switchState(GameController.State.NEW_PLAYER_MENU);
                            break;
                        case 2:
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
        g.drawString(options[0], selectRect.x + 27, selectRect.y + 33);
        g.setColor(rectColors[0]);
        g.draw(selectRect);

        /* New button */
        g.setColor(fontColor);
        g.setFont(btnFont);
        g.drawString(options[1], newRect.x + 47, newRect.y + 33);
        g.setColor(rectColors[1]);
        g.draw(newRect);

        /* Remove button */
        g.setColor(fontColor);
        g.setFont(btnFont);
        g.drawString(options[2], removeRect.x + 23, removeRect.y + 33);
        g.setColor(rectColors[2]);
        g.draw(removeRect);

        drawInformationPanel(g);
    }

}