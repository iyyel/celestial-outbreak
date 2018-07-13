package io.iyyel.celestialoutbreak.menu.settings_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class PlayerSettingsMenu extends AbstractMenu {

    private final Rectangle selectRect, newRect, removeRect;
    private final Font btnFont;

    private String[] options = {textHandler.BTN_SELECT_TEXT, textHandler.BTN_NEW_TEXT, textHandler.BTN_DELETE_TEXT};
    private Color[] rectColors;

    private int selected = 0;

    public PlayerSettingsMenu(GameController gameController) {
        super(gameController);

        int initialBtnYPos = 230;
        int btnYIncrement = 75;

        /* Menu buttons */
        selectRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos, 160, 50);
        newRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement, 160, 50);
        removeRect = new Rectangle(gameController.getWidth() / 2 - 80, initialBtnYPos + btnYIncrement * 2, 160, 50);

        rectColors = new Color[options.length];

        for (Color c : rectColors)
            c = menuBtnColor;

        btnFont = utils.getGameFont().deriveFont(20F);
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            menuUseClip.play(false);
            gameController.switchState(GameController.State.SETTINGS_MENU);
        }

        if (inputHandler.isUpPressed() && selected > 0 && isInputAvailable()) {
            resetInputTimer();
            selected--;
            menuNavClip.play(false);
        }

        if (inputHandler.isDownPressed() && selected < options.length - 1 && isInputAvailable()) {
            resetInputTimer();
            selected++;
            menuNavClip.play(false);
        }

        for (int i = 0, n = options.length; i < n; i++) {
            if (selected == i) {
                rectColors[i] = menuSelectedBtnColor;

                if (inputHandler.isOKPressed() && isInputAvailable()) {
                    resetInputTimer();
                    menuUseClip.play(false);

                    switch (i) {
                        case 0:
                            // SELECT
                            gameController.switchState(GameController.State.PLAYER_SELECT_SCREEN);
                            break;
                        case 1:
                            // NEW
                            gameController.switchState(GameController.State.PLAYER_NEW_SCREEN);
                            break;
                        case 2:
                            // REMOVE
                            gameController.switchState(GameController.State.PLAYER_DELETE_SCREEN);
                            break;
                        default:
                            break;
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
        drawSubmenuTitle("Player Settings", g);

        /* Render buttons  */
        g.setFont(btnFont);

        /* Select button */
        g.setColor(menuFontColor);
        g.setFont(btnFont);
        g.drawString(options[0], selectRect.x + 27, selectRect.y + 33);
        g.setColor(rectColors[0]);
        g.draw(selectRect);

        /* New button */
        g.setColor(menuFontColor);
        g.setFont(btnFont);
        g.drawString(options[1], newRect.x + 47, newRect.y + 33);
        g.setColor(rectColors[1]);
        g.draw(newRect);

        /* Remove button */
        g.setColor(menuFontColor);
        g.setFont(btnFont);
        g.drawString(options[2], removeRect.x + 23, removeRect.y + 33);
        g.setColor(rectColors[2]);
        g.draw(removeRect);

        drawInformationPanel(g);
    }

}