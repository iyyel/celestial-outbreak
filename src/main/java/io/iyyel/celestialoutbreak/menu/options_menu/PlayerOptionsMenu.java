package io.iyyel.celestialoutbreak.menu.options_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class PlayerOptionsMenu extends AbstractMenu {

    private final Rectangle selectRect, createRect, deleteRect;
    private final Font btnFont;

    private String[] options = {textHandler.BTN_SELECT_PLAYER_TEXT, textHandler.BTN_CREATE_PLAYER_TEXT, textHandler.BTN_DELETE_DELETE_TEXT};
    private Color[] rectColors;

    private int selected = 0;

    public PlayerOptionsMenu(GameController gameController) {
        super(gameController);

        int initialBtnYPos = 230;
        int btnYIncrement = 75;

        /* Menu buttons */
        selectRect = new Rectangle(gameController.getWidth() / 2 - 135, initialBtnYPos, 270, 50);
        createRect = new Rectangle(gameController.getWidth() / 2 - 135, initialBtnYPos + btnYIncrement, 270, 50);
        deleteRect = new Rectangle(gameController.getWidth() / 2 - 135, initialBtnYPos + btnYIncrement * 2, 270, 50);

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
            gameController.switchState(GameController.State.OPTIONS_MENU);
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
                            gameController.switchState(GameController.State.PLAYER_SELECT_SCREEN);
                            break;
                        case 1:
                            gameController.switchState(GameController.State.PLAYER_CREATE_SCREEN);
                            break;
                        case 2:
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
        drawSubmenuTitle(textHandler.TITLE_PLAYER_OPTIONS_MENU, g);

        /* Render buttons  */
        g.setFont(btnFont);

        /* Select button */
        g.setColor(menuFontColor);
        g.setFont(btnFont);
        drawCenterString(options[0], selectRect.y + BTN_Y_OFFSET, g, btnFont);
        g.setColor(rectColors[0]);
        g.draw(selectRect);

        /* New button */
        g.setColor(menuFontColor);
        g.setFont(btnFont);
        drawCenterString(options[1], createRect.y + BTN_Y_OFFSET, g, btnFont);;
        g.setColor(rectColors[1]);
        g.draw(createRect);

        /* Remove button */
        g.setColor(menuFontColor);
        g.setFont(btnFont);
        drawCenterString(options[2], deleteRect.y + BTN_Y_OFFSET, g, btnFont);
        g.setColor(rectColors[2]);
        g.draw(deleteRect);

        drawInfoPanel(g);
    }

}